package com.gearservice.service;

import com.gearservice.model.cheque.*;
import com.gearservice.model.request.RequestPreferences;
import com.gearservice.repositories.jpa.ChequeRepository;
import com.gearservice.repositories.jpa.ExchangeRateRepository;
import com.gearservice.repositories.jpa.PaymentRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.function.ToDoubleFunction;

import static java.util.stream.Collectors.joining;

/**
 * Class AnalyticsService is service, that handle analytical controller.
 * Use @Autowired for connect to necessary repositories
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 */

@Service
public class AnalyticsService {

    private final PaymentRepository paymentRepository;
    private final ExchangeRateRepository exchangeRateRepository;
    private final ChequeRepository chequeRepository;

    private static XSSFSheet sheet;
    private static CellStyle rubleStyle;
    private static CellStyle dateStyle;
    private static int rowID = 1;

    private static final ToDoubleFunction<Payment> getCostInRub =
            payment -> {
                BigDecimal currency;

                if (payment.getType() == null || payment.getType().equalsIgnoreCase("prepayment"))
                    return 0.0d;

                switch(payment.getCurrency()) {
                    case "rub": currency = payment.getExchangeRate().getRub(); break;
                    case "uah": currency = payment.getExchangeRate().getUah(); break;
                    case "usd": currency = payment.getExchangeRate().getUsd(); break;
                    case "eur": currency = payment.getExchangeRate().getEur(); break;
                    default: throw new IllegalArgumentException();
                }

                return currency.multiply(BigDecimal.valueOf(payment.getCost())).doubleValue();
            };

    @Autowired
    public AnalyticsService(PaymentRepository paymentRepository, ExchangeRateRepository exchangeRateRepository, ChequeRepository chequeRepository) {
        this.paymentRepository = paymentRepository;
        this.exchangeRateRepository = exchangeRateRepository;
        this.chequeRepository = chequeRepository;
    }

    /**
     * Method resetRowId reset caret of current row in excel document
     */
    private static void resetRowId() {rowID = 1;}

    /**
     * Method getExcelFile creates excel document with Apache POI, that contains necessary analytics
     * @param request represents preferences sent by user
     * @return array of bytes, that contains excel document with analytics inside
     */
    @Transactional(readOnly = true)
    public byte[] getExcelFile(RequestPreferences request) {
        XSSFWorkbook wb = new XSSFWorkbook();
        sheet = wb.createSheet("Steam-Service");

        dateStyle = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();
        dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd.mm.yyyy"));

        createHeader();

        chequeRepository.findByRequest(
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
                request.getWithoutRepair()
        )
                .stream()
                .sorted(Comparator.comparingLong(Cheque::getId))
                .forEach(AnalyticsService::createRow);

        resetRowId();

        ByteArrayOutputStream file = new ByteArrayOutputStream();

        try {
            wb.write(file);
            wb.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file.toByteArray();
    }

    private static void createHeader() {
        Row row = sheet.createRow(0);
        Cell cell;
        Integer i;

        String[] headers = {
                "№",
                "Заказчик",
                "Дата приема",
                "Гарантия",
                "Наименование",
                "Модель",
                "Серийный номер",
                "Заявленная неисправность",
                "Особые отметки",
                "Комплект",
                "Сдал",
                "Телефон",
                "Адрес",
                "Эл. почта",
                "Принял в ремонт",
                "Инженер",
                "Дата продажи",
                "Ориентировочная сумма ремонта",
                "Сумма ремонта",
                "Оплачен",
                "Диагностика",
                "Специальные отметки",
                "Корзинка",
                "Срок ремонта",
                "Готов",
                "Дата готовности",
                "Выдан",
                "Дата выдачи"
        };

        for (i = 0; i < headers.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(headers[i]);
        }
    }

    private static void createRow(Cheque cheque) {
        Row row = sheet.createRow(rowID++);
        Cell cell;

        cell = row.createCell(0);
        cell.setCellValue(cheque.getId());

        cell = row.createCell(1);
        cell.setCellValue(cheque.getCustomerName());

        cell = row.createCell(2);
        cell.setCellValue(new Date(cheque.getReceiptDate().toInstant().toEpochMilli()));
        cell.setCellStyle(dateStyle);

        cell = row.createCell(3);
        cell.setCellValue(cheque.isWarrantyStatus() ? "Гарантия" : "Не гарантия");

        cell = row.createCell(4);
        cell.setCellValue(cheque.getProductName());

        cell = row.createCell(5);
        cell.setCellValue(cheque.getModelName());

        cell = row.createCell(6);
        cell.setCellValue(cheque.getSerialNumber());

        cell = row.createCell(7);
        if (cheque.getDefect() != null) {
            cell.setCellValue(cheque.getDefect());
        }

        cell = row.createCell(8);
        if (cheque.getSpecialNotes() !=  null) {
            cell.setCellValue(cheque.getSpecialNotes());
        }

        cell = row.createCell(9);
        if(!cheque.getComponents().isEmpty()) {
            cell.setCellValue(cheque.getComponents().stream().map(Component::getName).collect(joining(", ")));
        }

        cell = row.createCell(10);
        if (cheque.getRepresentativeName() != null) {
            cell.setCellValue(cheque.getRepresentativeName());
        }

        cell = row.createCell(11);
        if (cheque.getPhoneNumber() != null) {
            cell.setCellValue(cheque.getPhoneNumber());
        }

        cell = row.createCell(12);
        if (cheque.getAddress() != null) {
            cell.setCellValue(cheque.getAddress());
        }

        cell = row.createCell(13);
        if (cheque.getEmail() != null) {
            cell.setCellValue(cheque.getEmail());
        }

        cell = row.createCell(14);
        cell.setCellValue(cheque.getSecretary().getFullname());

        cell = row.createCell(15);
        cell.setCellValue(cheque.getEngineer().getFullname());

        cell = row.createCell(16);
        if(cheque.getWarrantyDate() != null) {
            cell.setCellValue(new Date(cheque.getWarrantyDate().toInstant().toEpochMilli()));
            cell.setCellStyle(dateStyle);
        }
        cell.setCellStyle(dateStyle);

        cell = row.createCell(17);
        if (cheque.getBalance().getEstimatedCost() != 0) {
            cell.setCellValue(cheque.getBalance().getEstimatedCost());
        }

        cell = row.createCell(18);
        if(!cheque.getBalance().getPayments().isEmpty()) {
            cell.setCellValue(cheque
                    .getBalance()
                    .getPayments()
                    .stream()
                    .mapToDouble(getCostInRub)
                    .sum());
        }

        cell = row.createCell(19);
        cell.setCellValue(cheque.getBalance().getPaidStatus() ? "Оплачен" : "Не оплачен");

        cell = row.createCell(20);
        if(!cheque.getDiagnostics().isEmpty()) {
            cell.setCellValue(cheque.getDiagnostics().stream().map(Diagnostic::getText).collect(joining("; ")));
        }

        cell = row.createCell(21);
        if(!cheque.getNotes().isEmpty()) {
            cell.setCellValue(cheque.getNotes().stream().map(Note::getText).collect(joining("; ")));
        }

        cell = row.createCell(22);
        cell.setCellValue(cheque.isActVisualInspection() ? "Да" : "Нет");

        cell = row.createCell(23);
        cell.setCellValue(cheque.getRepairPeriod());

        cell = row.createCell(24);
        cell.setCellValue(cheque.isWithoutRepair() ? "Без ремонта" : cheque.isReadyStatus() ? "Готов" : "Не готов");

        cell = row.createCell(25);
        if(cheque.getReadyDate() != null ) {
            cell.setCellValue(new Date(cheque.getReadyDate().toInstant().toEpochMilli()));
            cell.setCellStyle(dateStyle);
        }

        cell = row.createCell(26);
        cell.setCellValue(cheque.isReturnedToClientStatus() ? "Выдан" : "Не выдан");

        cell = row.createCell(27);
        if(cheque.getReturnedToClientDate() != null ) {
            cell.setCellValue(new Date(cheque.getReturnedToClientDate().toInstant().toEpochMilli()));
            cell.setCellStyle(dateStyle);
        }

    }

}
