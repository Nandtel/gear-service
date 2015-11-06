package com.gearservice.service;

import com.gearservice.model.analytics.AnalyticsPreferences;
import com.gearservice.model.cheque.Cheque;
import com.gearservice.model.cheque.Payment;
import com.gearservice.model.repositories.ChequeRepository;
import com.gearservice.model.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collector;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

@Service
public class AnalyticsService {

    @Autowired PaymentRepository paymentRepository;

    private static Function<Payment, String> getPaymentsByCreatorName = payment -> payment.getUser().getFullname();
    private static Function<Payment, String> getPaymentsByBrandName = payment -> payment.getPaymentOwner().getModel().split("\\.")[0];
    private static Function<Payment, String> getPaymentsByDate = payment -> payment.getCurrency().getId();

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

    private static Function<Payment, String> getColumnDataName(String column) {
        switch (column) {
            case "brands": return getPaymentsByBrandName;
            case "engineers": return getPaymentsByCreatorName;
            default: throw new IllegalArgumentException();
        }
    }

    public Map<String, Map<String, Double>> getAnalytics(AnalyticsPreferences analyticsPreferences) {

        System.out.println(analyticsPreferences.getColumn());
        System.out.println(analyticsPreferences.getRow());
        System.out.println(analyticsPreferences.getFindTo());
        System.out.println(analyticsPreferences.getFindFrom());

        Optional<LocalDate> findTo = Optional.ofNullable(analyticsPreferences.getFindTo());
        Optional<LocalDate> findFrom = Optional.ofNullable(analyticsPreferences.getFindFrom());

        System.out.println();
        System.out.println(findTo.orElse(LocalDate.now()).toString());
        System.out.println(findFrom.orElse(LocalDate.MIN).toString());
        System.out.println();

        return paymentRepository.findByCurrencyIdBetween(
                    findFrom.orElse(LocalDate.MIN).toString(),
                    findTo.orElse(LocalDate.now()).toString()
                )
                        .stream()
                        .collect(
                                groupingBy(
                                    getPaymentsByDate,
                                    groupingBy(
                                            AnalyticsService.getColumnDataName(analyticsPreferences.getColumn()),
                                            summingDouble(getCostInRub)
                                    )));
    }

}
