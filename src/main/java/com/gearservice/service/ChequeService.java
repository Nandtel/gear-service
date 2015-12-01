package com.gearservice.service;

import com.gearservice.model.authorization.User;
import com.gearservice.model.cheque.*;
import com.gearservice.model.repositories.*;
import com.gearservice.model.request.RequestPreferences;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.FileInputStream;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.*;

@Service
public class ChequeService {

    @Autowired ChequeRepository chequeRepository;
    @Autowired DiagnosticRepository diagnosticRepository;
    @Autowired NoteRepository noteRepository;
    @Autowired PhotoRepository photoRepository;
    @Autowired UserRepository userRepository;
    @Autowired PaymentRepository paymentRepository;
    @Autowired EntityManager em;

    /**
     * Method getCheques call by client-side and return all cheques from database
     * Native query use for create partial object of Cheque � ChequeMin, that has only necessary for client-side fields
     * Call with value "/cheques" and request method GET
     * @return list of all cheques, that database has
     */
    public List<Cheque> getMinChequesList() {
        return chequeRepository.findAll();
    }

    /**
     * Method saveCheque call by client-side with data for cheque.class
     * Call with value "/cheque" and request method POST
     * @param cheque is data for Cheque.class, that was create on client-side
     * @return Cheque, that added
     */
    public Cheque synchronizeCheque(@RequestBody Cheque cheque) {
        Long ID = cheque.getId();

        if(cheque.getBalance().getCheque() == null)
            cheque.getBalance().setCheque(cheque);

        chequeRepository.save(cheque);

        if(ID == null)
            return chequeRepository.findFirstByOrderByIdDesc();
        else
            return chequeRepository.findOne(ID);
    }

    /**
     * Method getCheque call by client-side, when it needs in one cheque for represent
     * Call with value of request "/cheques/{chequeID}" and request method GET
     * @param chequeID is ID of cheque in database, that client-side wants
     * @return Cheque, that client-side was request
     */
    public Cheque getCheque(@PathVariable Long chequeID) {
        return chequeRepository.findById(chequeID);
    }

    /**
     * Method deleteCheque call by client-side, when it needs to delete one cheque
     * Call with value of request "/cheques/{chequeID}" and request method DELETE
     * @param chequeID is ID of cheque in database, that client-side wants to delete
     * @return redirect to main page
     */
    public void deleteCheque(@PathVariable Long chequeID) {
        chequeRepository.delete(chequeID);
        photoRepository.deleteByChequeId(chequeID.toString());
    }

    public List<Cheque> attentionCheques() {
        return chequeRepository.findByReturnedToClientStatusFalseAndDiagnosticsIsNull();
    }

    @Transactional(readOnly = true)
    public List<Cheque> attentionChequesByDelay() {
        return chequeRepository.findWithDelay(OffsetDateTime.now().minusDays(3).toString());
    }

    public List<String> getAutocompleteData(String itemName) {
        switch (itemName) {
            case "customerName": return chequeRepository.listOfCustomerNames();
            case "productName": return chequeRepository.listOfProductNames();
            case "modelName": return chequeRepository.listOfModelNames();
            case "serialNumber": return chequeRepository.listOfSerialNumbers();
            case "representativeName": return chequeRepository.listOfRepresentativeNames();
            case "address": return chequeRepository.listOfAddresses();
            case "email": return chequeRepository.listOfEmails();
            default: throw new IllegalArgumentException();
        }
    }

    public List<Cheque> getMinChequesListPre(RequestPreferences request) {

        return chequeRepository.findByRequest(
                request.getId(),
                request.getIntroducedFrom(),
                request.getIntroducedTo(),
                request.getCustomerName(),
                request.getProductName(),
                request.getModel(),
                request.getSerialNumber(),
                request.getRepresentativeName(),
                request.getSecretary(),
                request.getEngineer(),
                request.getWarrantyStatus(),
                request.getReadyStatus(),
                request.getReturnedToClientStatus(),
                request.getPaidStatus()
        );
    }

    public void readFromExcelToDB() throws Exception {

        FileInputStream excel = new FileInputStream(new File("read.xlsx"));

        XSSFWorkbook wb = new XSSFWorkbook(excel);
        XSSFSheet sheet = wb.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.rowIterator();

        rowIterator.next();

        List<User> users = userRepository.findAll();
        Map<String, User> userMap =
                users.stream().collect(toMap(User::getFullname, user -> user));

        while(rowIterator.hasNext()) {

            Row row = rowIterator.next();

            Iterator<Cell> cellIterator = row.cellIterator();

            Cheque cheque = new Cheque();
            cheque.setRepairPeriod(99);

            Balance balance = new Balance();
            balance.setPaidStatus(false);

            Diagnostic diagnosticCheque = new Diagnostic();
            Note noteCheque = new Note();
            Note phoneNote = new Note();

            long chequeID = (long) cellIterator.next().getNumericCellValue();
            cheque.setId(chequeID);
            System.out.println("chequeID: " + chequeID);

            String customerName = cellIterator.next().getStringCellValue();
            cheque.setCustomerName(customerName);
            System.out.println("customerName: " + customerName);

            OffsetDateTime receiptDate = getOffsetDateTime(cellIterator.next().getDateCellValue());
            cheque.setReceiptDate(receiptDate);
            System.out.println("receiptDate: " + receiptDate);

            boolean warrantyStatus = cellIterator.next().getStringCellValue().trim().equals("Гарантия");
            cheque.setWarrantyStatus(warrantyStatus);
            System.out.println("warrantyStatus: " + warrantyStatus);

            String productName = cellIterator.next().getStringCellValue().trim();
            cheque.setProductName(productName);
            System.out.println("productName: " + productName);

            String modelName = cellIterator.next().getStringCellValue().trim();
            cheque.setModelName(modelName);
            System.out.println("modelName: " + modelName);

            Cell serialNumber = cellIterator.next();
            if (serialNumber.getCellType() == Cell.CELL_TYPE_STRING) {
                cheque.setSerialNumber(serialNumber.getStringCellValue().trim());
            } else if (serialNumber.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                cheque.setSerialNumber(String.valueOf(serialNumber.getNumericCellValue()));
            }
            System.out.println("serialNumber: " + serialNumber);

            String defect = cellIterator.next().getStringCellValue().trim();
            cheque.setDefect(defect);
            System.out.println("defect: " + defect);

            Cell specialNotes = cellIterator.next();
            if (specialNotes.getCellType() == Cell.CELL_TYPE_STRING) {
                cheque.setSpecialNotes(specialNotes.getStringCellValue().trim());
            } else if (specialNotes.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                cheque.setSpecialNotes(String.valueOf(specialNotes.getNumericCellValue()));
            }

            cellIterator.next();

            String representativeName = cellIterator.next().getStringCellValue().trim();
            cheque.setRepresentativeName(representativeName);
            System.out.println("representativeName: " + representativeName);

            String[] phoneNumber = cellIterator.next().getStringCellValue().trim().split(" ", 1);
            cheque.setPhoneNumber(phoneNumber[0]);
            if (phoneNumber.length > 1) {
                phoneNote.setText("Дополнительные номера телефонов: " +
                        Stream.of(phoneNumber).filter(pn -> !pn.equals(phoneNumber[0])).collect(joining(", ")));
            }
            System.out.println("phoneNumber: " + phoneNumber);

            String address = cellIterator.next().getStringCellValue().trim();
            cheque.setAddress(address);
            System.out.println("address: " + address);

            String secretary = cellIterator.next().getStringCellValue().trim();
            User userSecretary = userMap.get(secretary);
            System.out.println("secretary: " + secretary);

            String engineer = cellIterator.next().getStringCellValue().trim();
            User userEngineer = userMap.get(engineer);
            System.out.println("engineer: " + engineer);

            OffsetDateTime warrantyDate = getOffsetDateTime(cellIterator.next().getDateCellValue());
            cheque.setWarrantyDate(warrantyDate);
            System.out.println("warrantyDate: " + warrantyDate);

            int estimatedCost = (int) cellIterator.next().getNumericCellValue();
            balance.setEstimatedCost(estimatedCost);
            System.out.println("estimatedCost: " + estimatedCost);

            String diagnostic = cellIterator.next().getStringCellValue().trim();
            if(diagnostic.length() > 2) {
                diagnosticCheque.setText(diagnostic);
                diagnosticCheque.setDate(receiptDate);
                diagnosticCheque.setUser(userEngineer);
            }
            System.out.println("diagnostic: " + diagnostic);

            String ready = cellIterator.next().getStringCellValue().trim();
            boolean readyStatus = false;
            if (ready.equals("Готов") || ready.equals("Акт в т\'с") || ready.equals("Без ремонта"))
                readyStatus = true;
            cheque.setReadyStatus(readyStatus);
            System.out.println("readyStatus: " + readyStatus);

            Cell cell = cellIterator.next();
            if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                OffsetDateTime readyDate = getOffsetDateTime(cell.getDateCellValue());
                cheque.setReadyDate(readyDate);
                System.out.println("readyDate: " + readyDate);
            } else {
                System.out.println("ERROR: " + cell.getStringCellValue());
            }

            String notes = cellIterator.next().getStringCellValue().trim();
            if(notes.length() > 2) {
                noteCheque.setText(notes);
                noteCheque.setDate(receiptDate);
                noteCheque.setUser(userSecretary);
            }
            System.out.println("notes: " + notes);

            boolean paidStatus = cellIterator.next().getStringCellValue().trim().equals("Да");
            balance.setPaidStatus(paidStatus);

            boolean returnedToClientStatus = cellIterator.next().getStringCellValue().trim().equals("Да");
            cheque.setReturnedToClientStatus(returnedToClientStatus);
            System.out.println("returnedToClientStatus: " + returnedToClientStatus);

            OffsetDateTime returnedToClientDate = getOffsetDateTime(cellIterator.next().getDateCellValue());
            cheque.setReturnedToClientDate(returnedToClientDate);
            System.out.println("returnedToClientDate: " + returnedToClientDate);

            System.out.println();
            System.out.println();

            cheque.setBalance(balance);
            balance.setCheque(cheque);

            cheque.setEngineer(userEngineer);
            cheque.setSecretary(userSecretary);

            chequeRepository.save(cheque);

            if (notes.length() > 2) {
                noteCheque.setCheque(cheque);
                noteRepository.save(noteCheque);
            }

            if (phoneNumber.length > 1) {
                phoneNote.setCheque(cheque);
                noteRepository.save(phoneNote);
            }

            if (diagnostic.length() > 2) {
                diagnosticCheque.setCheque(cheque);
                diagnosticRepository.save(diagnosticCheque);
            }

        }
        excel.close();
    }

    private static OffsetDateTime getOffsetDateTime(Date date) {
        if (date != null)
            return OffsetDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        else
            return null;
    }

    private static String getString(Cell cell) {
        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            return cell.getStringCellValue().trim();
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return String.valueOf(cell.getNumericCellValue()).trim();
        }
        return null;
    }

    public static String getCell(Cell cell) {

        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                System.out.print(cell.getNumericCellValue() + "\t\t");
                break;
            case Cell.CELL_TYPE_STRING:
                System.out.print(cell.getStringCellValue() + "\t\t");
                break;
        }

        return null;
    }

}
