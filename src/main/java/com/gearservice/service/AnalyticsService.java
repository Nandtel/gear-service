package com.gearservice.service;

import com.gearservice.model.request.AnalyticsPreferences;
import com.gearservice.model.cheque.Payment;
import com.gearservice.model.repositories.ExchangeRateRepository;
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
    @Autowired ExchangeRateRepository exchangeRateRepository;

    private static Function<Payment, String> getPaymentsByCreatorName = payment -> payment.getUser().getFullname();
    private static Function<Payment, String> getPaymentsByBrandName = payment -> payment.getBalance().getCheque().getModelName().split("\\.")[0];
    private static Function<Payment, String> getPaymentsByDate = payment -> payment.getExchangeRate().getAddDate();
    private static Function<Payment, String> getPaymentsByCheque = payment -> payment.getBalance().getCheque().getId().toString();
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
                        .orElse(LocalDate.parse(exchangeRateRepository.findMaximumDistantDate())).toString();

        String findTo =
                Optional.ofNullable(analyticsPreferences.getFindTo())
                        .orElse(LocalDate.now()).toString();

        String column = analyticsPreferences.getColumn();
        String row = analyticsPreferences.getRow();
        String fund = analyticsPreferences.getFund();

        return paymentRepository.findByExchangeRateAddDateBetween(findFrom, findTo)
                .stream()
                .filter(payment -> payment.getBalance().getPaidStatus())
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
