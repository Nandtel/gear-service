package com.gearservice.service;

import com.gearservice.model.cheque.Cheque;
import com.gearservice.model.cheque.Diagnostic;
import com.gearservice.model.request.RequestPreferences;
import com.gearservice.repositories.jpa.*;
import com.gearservice.repositories.mongo.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityNotFoundException;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * Class ChequeService is service, that handle ChequeController
 * Use @Autowired for connect to necessary repositories and entity manager
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 */

@Service
public class ChequeService {

    private final ChequeRepository chequeRepository;
    private final DiagnosticRepository diagnosticRepository;
    private final NoteRepository noteRepository;
    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;

    @Autowired
    public ChequeService(ChequeRepository chequeRepository, DiagnosticRepository diagnosticRepository, NoteRepository noteRepository, PhotoRepository photoRepository, UserRepository userRepository, PaymentRepository paymentRepository) {
        this.chequeRepository = chequeRepository;
        this.diagnosticRepository = diagnosticRepository;
        this.noteRepository = noteRepository;
        this.photoRepository = photoRepository;
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
    }

    /**
     * Method synchronizeCheque save cheque to DB and return it to frontend
     * @param cheque is data for Cheque.class, that was create on client-side
     * @return Cheque, that added
     */
    @Modifying
    @Transactional
    public Cheque synchronizeCheque(@RequestBody Cheque cheque) {
        var ID = cheque.getId();

        if(cheque.getBalance().getCheque() == null)
            cheque.getBalance().setCheque(cheque);

        chequeRepository.save(cheque);

        if(ID == null)
            return chequeRepository.findFirstByOrderByIdDesc();
        else
            return chequeRepository.findById(ID).orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Method getCheque get cheque from DB by id
     * @param chequeID is ID of cheque in database, that client-side wants
     * @return Cheque, that client-side was request
     */
    @Transactional(readOnly = true)
    public Cheque getCheque(@PathVariable Long chequeID) {
        return chequeRepository.findById(chequeID).orElseThrow(EntityNotFoundException::new);
    }

    /**
     * Method deleteCheque remove cheque from DB by id
     * @param chequeID is ID of cheque in database, that client-side wants to delete
     */
    @Modifying
    @Transactional
    public void deleteCheque(@PathVariable Long chequeID) {
        chequeRepository.deleteById(chequeID);
        photoRepository.deleteByChequeId(chequeID.toString());
    }

    /**
     * Method attentionCheques return list of cheque,
     * that wasn't returned to client and wasn't close and hasn't diagnostic comment
     * Use for engineers
     * @return list of cheque filtered by special rules
     */
    @Transactional(readOnly = true)
    public List<Cheque> attentionCheques() {
        return chequeRepository.findByReturnedToClientStatusFalseAndReadyStatusFalseAndDiagnosticsIsNull();
    }

    /**
     * Method attentionChequesByDelay return list of cheque, that has delay in diagnostic
     * Use for engineers
     * @return list of cheque, that has delay in diagnostic
     */
    @Transactional(readOnly = true)
    public List<Cheque> attentionChequesByDelay() {
        return chequeRepository.findWithDelay(OffsetDateTime.now().minusDays(7).toString());
    }

    /**
     * Method getMinChequesList return list of cheque by request without some field for size optimization
     * @param request by which should be returned list of cheques
     * @return list of cheque by request without some field for size optimization
     */
    @Transactional(readOnly = true)
    public Page<Cheque> getMinChequesList(RequestPreferences request, Pageable pageable) {

        return chequeRepository.findByRequest(
                request.getId(),
                request.getIntroducedFrom(),
                request.getIntroducedTo(),
                request.getReturnedToClientFrom(),
                request.getReturnedToClientTo(),
                request.getCustomerName(),
                request.getProductName(),
                request.getModel(),
                request.getSerialNumber(),
                request.getRepresentativeName(),
                request.getPhoneNumber(),
                request.getSecretary(),
                request.getEngineer(),
                request.getWarrantyStatus(),
                request.getReadyStatus(),
                request.getReturnedToClientStatus(),
                request.getPaidStatus(),
                request.getWithoutRepair(),
                pageable
        ).map(source -> {
            Boolean emptyDiagnostics = source.getDiagnostics().isEmpty();
            Boolean hasDelay = !emptyDiagnostics && source.getDiagnostics().stream()
                    .map(Diagnostic::getDate)
                    .max(OffsetDateTime::compareTo)
                    .filter(date -> date.isBefore(OffsetDateTime.now().minusDays(30)))
                    .isPresent();

            if(emptyDiagnostics)
                source.withRecencyStatus(true);

            if(!source.isReadyStatus() && hasDelay)
                source.withDelayStatus(true);

            source.setDiagnostics(null);
            return source;
        });
    }

//    /**
//     * Method readFromExcelToDB read from excel files to DB
//     * @throws Exception
//     */
//    public void readFromExcelToDB() throws Exception {
//
//        FileInputStream excel = new FileInputStream(new File("read.xlsx"));
//
//        XSSFWorkbook wb = new XSSFWorkbook(excel);
//        XSSFSheet sheet = wb.getSheetAt(0);
//
//        Iterator<Row> rowIterator = sheet.rowIterator();
//
//        rowIterator.next();
//
//        List<User> users = userRepository.findAll();
//        Map<String, User> userMap =
//                users.stream().collect(toMap(User::getFullname, user -> user));
//
//        while(rowIterator.hasNext()) {
//
//            Row row = rowIterator.next();
//
//            Iterator<Cell> cellIterator = row.cellIterator();
//
//            Cheque cheque = new Cheque();
//            cheque.setRepairPeriod(99);
//
//            Balance balance = new Balance();
//            balance.setPaidStatus(false);
//
//            Diagnostic diagnosticCheque = new Diagnostic();
//            Note noteCheque = new Note();
//            Note phoneNote = new Note();
//
//            long chequeID = (long) cellIterator.next().getNumericCellValue();
//            cheque.setId(chequeID);
//            System.out.println("chequeID: " + chequeID);
//
//            String customerName = cellIterator.next().getStringCellValue();
//            cheque.setCustomerName(customerName);
//            System.out.println("customerName: " + customerName);
//
//            OffsetDateTime receiptDate = getOffsetDateTime(cellIterator.next().getDateCellValue());
//            cheque.setReceiptDate(receiptDate);
//            System.out.println("receiptDate: " + receiptDate);
//
//            boolean warrantyStatus = cellIterator.next().getStringCellValue().trim().equals("Гарантия");
//            cheque.setWarrantyStatus(warrantyStatus);
//            System.out.println("warrantyStatus: " + warrantyStatus);
//
//            String productName = cellIterator.next().getStringCellValue().trim();
//            cheque.setProductName(productName);
//            System.out.println("productName: " + productName);
//
//            String modelName = cellIterator.next().getStringCellValue().trim();
//            cheque.setModelName(modelName);
//            System.out.println("modelName: " + modelName);
//
//            Cell serialNumber = cellIterator.next();
//            if (serialNumber.getCellType() == Cell.CELL_TYPE_STRING) {
//                cheque.setSerialNumber(serialNumber.getStringCellValue().trim());
//            } else if (serialNumber.getCellType() == Cell.CELL_TYPE_NUMERIC) {
//                cheque.setSerialNumber(String.valueOf(serialNumber.getNumericCellValue()));
//            }
//            System.out.println("serialNumber: " + serialNumber);
//
//            String defect = cellIterator.next().getStringCellValue().trim();
//            cheque.setDefect(defect);
//            System.out.println("defect: " + defect);
//
//            Cell specialNotes = cellIterator.next();
//            if (specialNotes.getCellType() == Cell.CELL_TYPE_STRING) {
//                cheque.setSpecialNotes(specialNotes.getStringCellValue().trim());
//            } else if (specialNotes.getCellType() == Cell.CELL_TYPE_NUMERIC) {
//                cheque.setSpecialNotes(String.valueOf(specialNotes.getNumericCellValue()));
//            }
//
//            cellIterator.next();
//
//            String representativeName = cellIterator.next().getStringCellValue().trim();
//            cheque.setRepresentativeName(representativeName);
//            System.out.println("representativeName: " + representativeName);
//
//            String[] phoneNumber = cellIterator.next().getStringCellValue().trim().split(" ", 1);
//            cheque.setPhoneNumber(phoneNumber[0]);
//            if (phoneNumber.length > 1) {
//                phoneNote.setText("Дополнительные номера телефонов: " +
//                        Stream.of(phoneNumber).filter(pn -> !pn.equals(phoneNumber[0])).collect(joining(", ")));
//            }
//            System.out.println("phoneNumber: " + phoneNumber);
//
//            String address = cellIterator.next().getStringCellValue().trim();
//            cheque.setAddress(address);
//            System.out.println("address: " + address);
//
//            String secretary = cellIterator.next().getStringCellValue().trim();
//            User userSecretary = userMap.get(secretary);
//            System.out.println("secretary: " + secretary);
//
//            String engineer = cellIterator.next().getStringCellValue().trim();
//            User userEngineer = userMap.get(engineer);
//            System.out.println("engineer: " + engineer);
//
//            OffsetDateTime warrantyDate = getOffsetDateTime(cellIterator.next().getDateCellValue());
//            cheque.setWarrantyDate(warrantyDate);
//            System.out.println("warrantyDate: " + warrantyDate);
//
//            int estimatedCost = (int) cellIterator.next().getNumericCellValue();
//            balance.setEstimatedCost(estimatedCost);
//            System.out.println("estimatedCost: " + estimatedCost);
//
//            String diagnostic = cellIterator.next().getStringCellValue().trim();
//            if(diagnostic.length() > 2) {
//                diagnosticCheque.setText(diagnostic);
//                diagnosticCheque.setDate(receiptDate);
//                diagnosticCheque.setUser(userEngineer);
//            }
//            System.out.println("diagnostic: " + diagnostic);
//
//            String ready = cellIterator.next().getStringCellValue().trim();
//            boolean readyStatus = false;
//            if (ready.equals("Готов") || ready.equals("Акт в т\'с") || ready.equals("Без ремонта"))
//                readyStatus = true;
//            cheque.setReadyStatus(readyStatus);
//            System.out.println("readyStatus: " + readyStatus);
//
//            Cell cell = cellIterator.next();
//            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
//                OffsetDateTime readyDate = getOffsetDateTime(cell.getDateCellValue());
//                cheque.setReadyDate(readyDate);
//                System.out.println("readyDate: " + readyDate);
//            } else {
//                System.out.println("ERROR: " + cell.getStringCellValue());
//            }
//
//            String notes = cellIterator.next().getStringCellValue().trim();
//            if(notes.length() > 2) {
//                noteCheque.setText(notes);
//                noteCheque.setDate(receiptDate);
//                noteCheque.setUser(userSecretary);
//            }
//            System.out.println("notes: " + notes);
//
//            boolean paidStatus = cellIterator.next().getStringCellValue().trim().equals("Да");
//            balance.setPaidStatus(paidStatus);
//
//            boolean returnedToClientStatus = cellIterator.next().getStringCellValue().trim().equals("Да");
//            cheque.setReturnedToClientStatus(returnedToClientStatus);
//            System.out.println("returnedToClientStatus: " + returnedToClientStatus);
//
//            OffsetDateTime returnedToClientDate = getOffsetDateTime(cellIterator.next().getDateCellValue());
//            cheque.setReturnedToClientDate(returnedToClientDate);
//            System.out.println("returnedToClientDate: " + returnedToClientDate);
//
//            System.out.println();
//            System.out.println();
//
//            cheque.setBalance(balance);
//            balance.setCheque(cheque);
//
//            cheque.setEngineer(userEngineer);
//            cheque.setSecretary(userSecretary);
//
//            chequeRepository.save(cheque);
//
//            if (notes.length() > 2) {
//                noteCheque.setCheque(cheque);
//                noteRepository.save(noteCheque);
//            }
//
//            if (phoneNumber.length > 1) {
//                phoneNote.setCheque(cheque);
//                noteRepository.save(phoneNote);
//            }
//
//            if (diagnostic.length() > 2) {
//                diagnosticCheque.setCheque(cheque);
//                diagnosticRepository.save(diagnosticCheque);
//            }
//
//        }
//        excel.close();
//    }
//
//    /**
//     * Method getOffsetDateTime
//     * @param date that should be converted
//     * @return converted OffsetDateTime of null
//     */
//    private static OffsetDateTime getOffsetDateTime(Date date) {
//        if (date != null)
//            return OffsetDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
//        else
//            return null;
//    }
//
//    private static String getString(Cell cell) {
//        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
//            return cell.getStringCellValue().trim();
//        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
//            return String.valueOf(cell.getNumericCellValue()).trim();
//        }
//        return null;
//    }
//
//    public static String getCell(Cell cell) {
//
//        switch (cell.getCellType()) {
//            case Cell.CELL_TYPE_NUMERIC:
//                System.out.print(cell.getNumericCellValue() + "\t\t");
//                break;
//            case Cell.CELL_TYPE_STRING:
//                System.out.print(cell.getStringCellValue() + "\t\t");
//                break;
//        }
//
//        return null;
//    }

}
