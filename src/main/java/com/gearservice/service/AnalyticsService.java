package com.gearservice.service;

import com.gearservice.model.authorization.User;
import com.gearservice.model.cheque.Cheque;
import com.gearservice.model.request.AnalyticsPreferences;
import com.gearservice.model.cheque.Payment;
import com.gearservice.model.repositories.ExchangeRateRepository;
import com.gearservice.model.repositories.PaymentRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFPivotTable;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

@Service
public class AnalyticsService {

    @Autowired PaymentRepository paymentRepository;
    @Autowired ExchangeRateRepository exchangeRateRepository;

    static int i = 1;

    private static Function<Payment, User> getPaymentsByCreatorName = payment -> payment.getUser();
    private static Function<Payment, String> getPaymentsByBrandName = payment -> payment.getBalance().getCheque().getModelName().split("\\.")[0];
    private static Function<Payment, String> getPaymentsByDate = payment -> payment.getExchangeRate().getAddDate();
    private static Function<Payment, Cheque> getPaymentsByCheque = payment -> payment.getBalance().getCheque();
    private static Predicate<Payment> getPaymentIncome = payment -> !payment.getType().equalsIgnoreCase("prepayment");
    private static Predicate<Payment> getPaymentProfit = payment -> payment.getType().equalsIgnoreCase("repair");

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

//    private static Function<Payment, String> getColumnDataFunction(String column) {
//        switch (column) {
//            case "brands": return getPaymentsByBrandName;
//            case "engineers": return getPaymentsByCreatorName;
//            default: throw new IllegalArgumentException();
//        }
//    }
//
//    private static Function<Payment, String> getRowDataFunction(String row) {
//        switch (row) {
//            case "date": return getPaymentsByDate;
//            case "cheques": return getPaymentsByCheque;
//            default: throw new IllegalArgumentException();
//        }
//    }
//
//    private static Predicate<Payment> filterByFunds(String fund) {
//        switch (fund) {
//            case "income": return getPaymentIncome;
//            case "profit": return getPaymentProfit;
//            default: throw new IllegalArgumentException();
//        }
//    }

//    public Map<String, Map<String, Double>> getAnalytics(AnalyticsPreferences analyticsPreferences) {
//        String findFrom =
//                Optional.ofNullable(analyticsPreferences.getFindFrom())
//                        .orElse(LocalDate.parse(exchangeRateRepository.findMaximumDistantDate())).toString();
//
//        String findTo =
//                Optional.ofNullable(analyticsPreferences.getFindTo())
//                        .orElse(LocalDate.now()).toString();
//
//        String column = analyticsPreferences.getColumn();
//        String row = analyticsPreferences.getRow();
//        String fund = analyticsPreferences.getFund();
//
//        return paymentRepository.findByExchangeRateAddDateBetween(findFrom, findTo)
//                .stream()
//                .filter(payment -> payment.getBalance().getPaidStatus())
//                .filter(AnalyticsService.filterByFunds(fund))
//                .collect(
//                        groupingBy(
//                                AnalyticsService.getRowDataFunction(row),
//                                groupingBy(
//                                        AnalyticsService.getColumnDataFunction(column),
//                                        summingDouble(getCostInRub)
//                                )));
//    }

    public byte[] getExcelFile(AnalyticsPreferences analyticsPreferences) throws Exception {
        String findFrom =
                Optional.ofNullable(analyticsPreferences.getFindFrom())
                        .orElse(LocalDate.parse(exchangeRateRepository.findMaximumDistantDate())).toString();

        String findTo =
                Optional.ofNullable(analyticsPreferences.getFindTo())
                        .orElse(LocalDate.now()).toString();

        Map<Cheque, Map<User, List<Payment>>> map = paymentRepository.findByExchangeRateAddDateBetween(findFrom, findTo)
                .stream()
                .filter(payment -> payment.getBalance().getPaidStatus())
                .filter(getPaymentIncome)
                .collect(groupingBy(getPaymentsByCheque, groupingBy(Payment::getUser)));

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("greCHnick");

        CellStyle dollarStyle=wb.createCellStyle();
        DataFormat df = wb.createDataFormat();
        dollarStyle.setDataFormat(df.getFormat("\u20BD#,#0.00"));

        createHead(sheet);

        map.entrySet()
                .stream()
                .forEach(chequeMapEntry -> {
                    Cheque cheque = chequeMapEntry.getKey();
                    chequeMapEntry.getValue().entrySet()
                            .stream()
                            .forEach(userListEntry -> {
                                User user = userListEntry.getKey();
                                List<Payment> payments = userListEntry.getValue();

                                double income = payments.stream()
                                        .filter(payment -> !payment.getType().equalsIgnoreCase("prepayment"))
                                        .mapToDouble(getCostInRub)
                                        .sum();

                                double profit = payments.stream()
                                        .filter(payment -> payment.getType().equals("repair"))
                                        .mapToDouble(getCostInRub)
                                        .sum();

                                createRow(sheet, i, dollarStyle,
                                        cheque.getId(),
                                        cheque.getReceiptDate().toLocalDate(),
                                        cheque.getModelName().split("\\.")[0],
                                        user.getFullname(),
                                        income,
                                        profit
                                );

                                i++;
                            });
                });



        XSSFPivotTable pivotTable = sheet.createPivotTable(new AreaReference(new CellReference("A1"), new CellReference("F11")), new CellReference("H1"));
        //Configure the pivot table
        //Use first column as row label
        pivotTable.addRowLabel(0);
        pivotTable.addRowLabel(1);
        pivotTable.addRowLabel(2);
        pivotTable.addRowLabel(3);
        pivotTable.addColumnLabel(DataConsolidateFunction.SUM, 4, "Income" );
        pivotTable.addColumnLabel(DataConsolidateFunction.SUM, 5, "Profit");


//        //Sum up the second column
//        pivotTable.addColumnLabel(DataConsolidateFunction.SUM, 1);
//        //Set the third column as filter
//        pivotTable.addColumnLabel(DataConsolidateFunction.AVERAGE, 2);
//        //Add filter on forth column
//        pivotTable.addReportFilter(3);

        i = 1;

        ByteArrayOutputStream file = new ByteArrayOutputStream();
        wb.write(file);
        file.close();
        wb.close();

        return file.toByteArray();
    }

    private static void createHead(XSSFSheet sheet) {
        Row row1 = sheet.createRow(0);
        Cell cell11 = row1.createCell(0);
        cell11.setCellValue("ID");
        Cell cell12 = row1.createCell(1);
        cell12.setCellValue("ReceiptDate");
        Cell cell13 = row1.createCell(2);
        cell13.setCellValue("Brand");
        Cell cell14 = row1.createCell(3);
        cell14.setCellValue("Engineer");
        Cell cell15 = row1.createCell(4);
        cell15.setCellValue("Income");
        Cell cell16 = row1.createCell(5);
        cell16.setCellValue("Profit");
    }

    private static void createRow(XSSFSheet sheet, int i, CellStyle dollarStyle, Long chequeID, LocalDate receiptDate, String brandName, String fullname, double income, double profit) {


        Row row1 = sheet.createRow(i);
        Cell cell11 = row1.createCell(0);
        cell11.setCellValue(chequeID);
        Cell cell12 = row1.createCell(1);
        cell12.setCellValue(receiptDate.toString());
        Cell cell13 = row1.createCell(2);
        cell13.setCellValue(brandName);
        Cell cell14 = row1.createCell(3);
        cell14.setCellValue(fullname);
        Cell cell15 = row1.createCell(4);
        cell15.setCellStyle(dollarStyle);
        cell15.setCellValue(income);
        Cell cell16 = row1.createCell(5);
        cell16.setCellValue(profit);
        cell16.setCellStyle(dollarStyle);
    }

}
