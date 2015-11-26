package com.gearservice.service;

import com.gearservice.model.authorization.User;
import com.gearservice.model.cheque.Cheque;
import com.gearservice.model.request.AnalyticsPreferences;
import com.gearservice.model.cheque.Payment;
import com.gearservice.model.repositories.ExchangeRateRepository;
import com.gearservice.model.repositories.PaymentRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;

import static java.util.stream.Collectors.groupingBy;

@Service
public class AnalyticsService {

    @Autowired PaymentRepository paymentRepository;
    @Autowired ExchangeRateRepository exchangeRateRepository;

    private static XSSFSheet sheet;
    private static CellStyle rubleStyle;
    private static CellStyle dateStyle;
    private static int rowID = 1;

    private static Function<Payment, Cheque> getPaymentsByCheque = payment -> payment.getBalance().getCheque();
    private static Predicate<Payment> getPaymentIncome = payment -> !payment.getType().equalsIgnoreCase("prepayment");
    private static Predicate<Payment> getPaymentProfit = payment -> payment.getType().equalsIgnoreCase("repair");
    private static Predicate<Payment> getPaymentWithPositivePaidStatus = payment -> payment.getBalance().getPaidStatus();

    private static ToDoubleFunction<Payment> getCostInRub =
            payment -> {
                BigDecimal currency;

                switch(payment.getCurrency()) {
                    case "rub": currency = payment.getExchangeRate().getRub(); break;
                    case "uah": currency = payment.getExchangeRate().getUah(); break;
                    case "usd": currency = payment.getExchangeRate().getUsd(); break;
                    case "eur": currency = payment.getExchangeRate().getEur(); break;
                    default: throw new IllegalArgumentException();
                }

                return currency.multiply(BigDecimal.valueOf(payment.getCost())).doubleValue();
            };

    public byte[] getExcelFile(AnalyticsPreferences analyticsPreferences) throws Exception {
        FileInputStream excel = new FileInputStream(new File("analytics.xlsx"));

        XSSFWorkbook wb = new XSSFWorkbook(excel);
        sheet = wb.getSheetAt(0);

        rubleStyle = wb.createCellStyle();
        DataFormat df = wb.createDataFormat();
        rubleStyle.setDataFormat(df.getFormat("\u20BD#,#0.00"));

        dateStyle = wb.createCellStyle();
        CreationHelper createHelper = wb.getCreationHelper();
        dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd.mm.yyyy"));

        String findFrom =
                Optional.ofNullable(analyticsPreferences.getFindFrom())
                        .orElse(LocalDate.parse(exchangeRateRepository.findMaximumDistantDate())).toString();

        String findTo =
                Optional.ofNullable(analyticsPreferences.getFindTo())
                        .orElse(LocalDate.now()).toString();

        Map<Cheque, Map<User, List<Payment>>> map = paymentRepository.findByExchangeRateAddDateBetween(findFrom, findTo)
                .stream()
                .filter(getPaymentWithPositivePaidStatus)
                .filter(getPaymentIncome)
                .collect(groupingBy(getPaymentsByCheque, groupingBy(Payment::getUser)));

        map.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .flatMap(userListMap -> userListMap.entrySet().stream())
                .forEach(userListEntry -> {
                    User user = userListEntry.getKey();
                    List<Payment> payments = userListEntry.getValue();
                    Cheque cheque = payments.get(0).getBalance().getCheque();

                    double income = payments.stream()
                            .filter(getPaymentIncome)
                            .mapToDouble(getCostInRub)
                            .sum();

                    double profit = payments.stream()
                            .filter(getPaymentProfit)
                            .mapToDouble(getCostInRub)
                            .sum();

                    createRow(cheque.getId(),
                            cheque.getReceiptDate().toLocalDate(),
                            cheque.getReturnedToClientDate().toLocalDate(),
                            cheque.getModelName().split("\\.")[0],
                            user.getFullname(),
                            income,
                            profit);
                });

        resetRowId();

        ByteArrayOutputStream file = new ByteArrayOutputStream();
        wb.write(file);
        file.close();
        wb.close();

        return file.toByteArray();
    }

    private static void createRow(Long chequeID, LocalDate receiptDate, LocalDate returnedToClientDate,
                                  String brandName, String fullname, double income, double profit) {
        Row row1 = sheet.createRow(rowID++);
        Cell cell11 = row1.createCell(0);
        cell11.setCellValue(chequeID);
        Cell cell12 = row1.createCell(1);
        cell12.setCellValue(Date.from(receiptDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        cell12.setCellStyle(dateStyle);
        Cell cell13 = row1.createCell(2);
        cell13.setCellValue(Date.from(returnedToClientDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
        cell13.setCellStyle(dateStyle);
        Cell cell14 = row1.createCell(3);
        cell14.setCellValue(brandName);
        Cell cell15 = row1.createCell(4);
        cell15.setCellValue(fullname);
        Cell cell16 = row1.createCell(5);
        cell16.setCellStyle(rubleStyle);
        cell16.setCellValue(income);
        Cell cell17 = row1.createCell(6);
        cell17.setCellValue(profit);
        cell17.setCellStyle(rubleStyle);
    }

    private static void resetRowId() {
        rowID = 1;
    }

}
