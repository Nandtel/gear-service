package com.gearservice.service;

import com.gearservice.model.cheque.Cheque;
import com.gearservice.model.cheque.Payment;
import com.gearservice.model.repositories.ChequeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

@Service
public class AnalyticsService {

    @Autowired ChequeRepository chequeRepository;

    @SuppressWarnings("ConstantConditions")
    public void run() {

        System.out.println("In service:");

        List<Cheque> list = chequeRepository.findAll();




        Map<String, Double> byDay = list
                .stream()
                .map(Cheque::getPayments)
                .flatMap(Collection::stream)
                .collect(
                        groupingBy(
                                payment -> payment.getCurrency().getId(),
                                summingDouble(AnalyticsService::getCostInRub)
                        )
                );

        System.out.println();
        System.out.println("Grouping by day:");
        System.out.println(byDay);

        Map<String, Double> byEngineer = list
                .stream()
                .map(Cheque::getPayments)
                .flatMap(Collection::stream)
                .collect(
                        groupingBy(
                                payment -> payment.getUser().getFullname(),
                                summingDouble(AnalyticsService::getCostInRub)
                        )
                );

        System.out.println();
        System.out.println("Grouping by engineer:");
        System.out.println(byEngineer);

        Map<String, Double> byBrand = list
                .stream()
                .map(Cheque::getPayments)
                .flatMap(Collection::stream)
                .collect(
                        groupingBy(
                                payment -> payment.getPaymentOwner().getModel().split("\\.")[0],
                                summingDouble(AnalyticsService::getCostInRub)
                        )
                );

        System.out.println();
        System.out.println("Grouping by brand:");
        System.out.println(byBrand);

    }

    public static Double getCostInRub(Payment payment) {
        switch(payment.getCurrentCurrency()) {
                    case "rub": return payment.getCurrency().getRub().multiply(BigDecimal.valueOf(payment.getCost())).doubleValue();
                    case "uah": return payment.getCurrency().getUah().multiply(BigDecimal.valueOf(payment.getCost())).doubleValue();
                    case "usd": return payment.getCurrency().getUsd().multiply(BigDecimal.valueOf(payment.getCost())).doubleValue();
                    case "eur": return payment.getCurrency().getEur().multiply(BigDecimal.valueOf(payment.getCost())).doubleValue();
                    default: return null;
                }
    }

}
