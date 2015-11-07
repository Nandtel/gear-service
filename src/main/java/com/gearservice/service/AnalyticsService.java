package com.gearservice.service;

import com.gearservice.model.analytics.AnalyticsPreferences;
import com.gearservice.model.cheque.Payment;
import com.gearservice.model.repositories.CurrencyRepository;
import com.gearservice.model.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

@Service
public class AnalyticsService {

    @Autowired PaymentRepository paymentRepository;
    @Autowired CurrencyRepository currencyRepository;

    private static Function<Payment, String> getPaymentsByCreatorName = payment -> payment.getUser().getFullname();
    private static Function<Payment, String> getPaymentsByBrandName = payment -> payment.getPaymentOwner().getModel().split("\\.")[0];
    private static Function<Payment, String> getPaymentsByDate = payment -> payment.getCurrency().getId();
    private static Function<Payment, String> getPaymentsByCheque = payment -> payment.getPaymentOwner().getId().toString();
    private static Predicate<Payment> getPaymentIncome = payment -> !payment.getType().equalsIgnoreCase("prepayment");
    private static Predicate<Payment> getPaymentProfit = payment -> payment.getType().equalsIgnoreCase("repair");

    private static ToDoubleFunction<Payment> getCostInRub =
            payment -> {
                BigDecimal currency;

                switch(payment.getCurrentCurrency()) {
                    case "rub": currency = payment.getCurrency().getRub(); break;
                    case "uah": currency = payment.getCurrency().getUah(); break;
                    case "usd": currency = payment.getCurrency().getUsd(); break;
                    case "eur": currency = payment.getCurrency().getEur(); break;
                    default: throw new IllegalArgumentException();
                }

                return currency.multiply(BigDecimal.valueOf(payment.getCost())).doubleValue();
            };

    private static Function<Payment, String> getColumnDataFunction(String column) {
        switch (column) {
            case "brands": return getPaymentsByBrandName;
            case "engineers": return getPaymentsByCreatorName;
            default: throw new IllegalArgumentException();
        }
    }

    private static Function<Payment, String> getRowDataFunction(String row) {
        switch (row) {
            case "date": return getPaymentsByDate;
            case "cheques": return getPaymentsByCheque;
            default: throw new IllegalArgumentException();
        }
    }

    private static Predicate<Payment> filterByFunds(String fund) {
        switch (fund) {
            case "income": return getPaymentIncome;
            case "profit": return getPaymentProfit;
            default: throw new IllegalArgumentException();
        }
    }

    public Map<String, Map<String, Double>> getAnalytics(AnalyticsPreferences analyticsPreferences) {
        String findFrom =
                Optional.ofNullable(analyticsPreferences.getFindFrom())
                        .orElse(LocalDate.parse(currencyRepository.findMaximumDistantDate())).toString();

        String findTo =
                Optional.ofNullable(analyticsPreferences.getFindTo())
                        .orElse(LocalDate.now()).toString();

        String column = analyticsPreferences.getColumn();
        String row = analyticsPreferences.getRow();
        String fund = analyticsPreferences.getFund();

        return paymentRepository.findByCurrencyIdBetween(findFrom, findTo)
                .stream()
                .filter(AnalyticsService.filterByFunds(fund))
                .collect(
                        groupingBy(
                                AnalyticsService.getRowDataFunction(row),
                                groupingBy(
                                        AnalyticsService.getColumnDataFunction(column),
                                        summingDouble(getCostInRub)
                                )));
    }

}
