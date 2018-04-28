package com.gearservice.service;

import com.gearservice.model.authorization.Authority;
import com.gearservice.model.authorization.User;
import com.gearservice.model.cheque.*;
import com.gearservice.model.exchangeRate.ExchangeRate;
import com.gearservice.repositories.jpa.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;

/**
 * Class ApplicationService is service, that handle ApplicationController.
 * Use @Autowired for connect to necessary repositories
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 */

@Service
public class ApplicationService {

    private final UserRepository userRepository;
    private final ExchangeRateRepository exchangeRateRepository;
    private final ChequeRepository chequeRepository;
    private final BalanceRepository balanceRepository;
    private final DiagnosticRepository diagnosticRepository;
    private final NoteRepository noteRepository;
    private final ComponentRepository componentRepository;

    @Autowired
    public ApplicationService(UserRepository userRepository, ExchangeRateRepository exchangeRateRepository, ChequeRepository chequeRepository, BalanceRepository balanceRepository, DiagnosticRepository diagnosticRepository, NoteRepository noteRepository, ComponentRepository componentRepository) {
        this.userRepository = userRepository;
        this.exchangeRateRepository = exchangeRateRepository;
        this.chequeRepository = chequeRepository;
        this.balanceRepository = balanceRepository;
        this.diagnosticRepository = diagnosticRepository;
        this.noteRepository = noteRepository;
        this.componentRepository = componentRepository;
    }

    /**
     * Method getAutocompleteData return autocomplete data for request item name
     * @param itemName for autocomplete
     * @return List of autocomplete data(String or User)
     */
    @Transactional(readOnly = true)
    public List<String> getAutocompleteData(String itemName, String searchText) {
        List<String> items = new ArrayList<>();

        switch (itemName) {
            case "customers": items = chequeRepository.listOfCustomerNames(); break;
            case "products": items = chequeRepository.listOfProductNames(); break;
            case "models": items = chequeRepository.listOfModelNames(); break;
            case "serials": items = chequeRepository.listOfSerialNumbers(); break;
            case "representatives": items = chequeRepository.listOfRepresentativeNames(); break;
            case "emails": items = chequeRepository.listOfEmails(); break;
            case "components": items = componentRepository.listOfComponentNames(); break;
            case "secretaries": items = userRepository.listOfSecretaries(); break;
            case "engineers": items = userRepository.listOfEngineers(); break;
            case "phoneNumbers": items = chequeRepository.listOfPhoneNumbers(); break;
        }

        return items.stream()
                .filter(Objects::nonNull)
                .filter(item -> item.toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }

    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}

    /**
     * Method makeSample fill DB with sample data
     * Only for testing
     */
    public void makeSample() {
        Authority engineer = new Authority("ROLE_ENGINEER");
        Authority secretary = new Authority("ROLE_SECRETARY");
        Authority administrator = new Authority("ROLE_ADMIN");
        Authority boss = new Authority("ROLE_BOSS");

        User admin = new User();
        admin.setUsername("sirouga");
        admin.setPassword(passwordEncoder().encode("12345"));
        admin.setFullname("Калинюк С.В.");
        admin.setEnabled(true);
        admin.setAuthorities(new HashSet<>(asList(administrator.withUsername(admin), engineer.withUsername(admin), secretary.withUsername(admin))));
        userRepository.save(admin);

        User svetka = new User();
        svetka.setUsername("svetik");
        svetka.setPassword(passwordEncoder().encode("12345"));
        svetka.setFullname("Светик С.В.");
        svetka.setEnabled(true);
        svetka.setAuthorities(new HashSet<>(asList(secretary.withUsername(svetka))));
        userRepository.save(svetka);

        ExchangeRate yesterday = new ExchangeRate();
        yesterday.setAddDate(LocalDate.now().minusDays(1).toString());
        yesterday.setEur(new BigDecimal("70"));
        yesterday.setUsd(new BigDecimal("70"));
        yesterday.setUah(new BigDecimal("70"));
        exchangeRateRepository.save(yesterday);

        ExchangeRate tomorrow = new ExchangeRate();
        tomorrow.setAddDate(LocalDate.now().minusDays(2).toString());
        tomorrow.setEur(new BigDecimal("100"));
        tomorrow.setUsd(new BigDecimal("100"));
        tomorrow.setUah(new BigDecimal("100"));
        exchangeRateRepository.save(tomorrow);

        ExchangeRate dayBeforeYesterday = new ExchangeRate();
        dayBeforeYesterday.setAddDate(LocalDate.now().minusDays(3).toString());
        dayBeforeYesterday.setEur(new BigDecimal("10"));
        dayBeforeYesterday.setUsd(new BigDecimal("10"));
        dayBeforeYesterday.setUah(new BigDecimal("10"));
        exchangeRateRepository.save(dayBeforeYesterday);

        OffsetDateTime now = OffsetDateTime.now();

        IntStream.range(0, 40).parallel()
                .forEach(i -> {
                    Cheque cheque = new Cheque().withRandomData();
                    cheque.setReceiptDate(now.minusDays(i));
                    cheque.setEngineer(admin);
                    cheque.setSecretary(svetka);

                    Balance balance = new Balance();
                    balance.setPaidStatus(true);
                    balance.setCheque(cheque);
                    balance.setEstimatedCost(300);

                    Payment repair = new Payment();
                    repair.setCost(1);
                    repair.setCurrency("usd");
                    repair.setExchangeRate(yesterday);
                    repair.setType("repair");
                    repair.setUser(admin);

                    Payment zip = new Payment();
                    zip.setCost(1);
                    zip.setCurrency("rub");
                    zip.setExchangeRate(dayBeforeYesterday);
                    zip.setType("zip");
                    zip.setUser(admin);

                    Payment repair2 = new Payment();
                    repair2.setCost(1);
                    repair2.setCurrency("rub");
                    repair2.setExchangeRate(dayBeforeYesterday);
                    repair2.setType("repair");
                    repair2.setUser(admin);

                    Payment prepayment = new Payment();
                    prepayment.setCost(1);
                    prepayment.setCurrency("uah");
                    prepayment.setExchangeRate(tomorrow);
                    prepayment.setType("zip");
                    prepayment.setUser(admin);

                    repair.setBalance(balance);
                    zip.setBalance(balance);
                    repair2.setBalance(balance);
                    prepayment.setBalance(balance);
                    balance.setPayments(new HashSet<>(asList(repair, zip, repair2, prepayment)));

                    chequeRepository.save(cheque);
                    cheque.setBalance(balance);
                    balanceRepository.save(balance);

                    diagnosticRepository.saveAll(SampleDataService.getSetConsistFrom(
                            o -> new Diagnostic()
                                    .withRandomData()
                                    .withUser(admin)
                                    .withCheque(cheque)));

                    noteRepository.saveAll(SampleDataService.getSetConsistFrom(
                            o -> new Note()
                                    .withRandomData()
                                    .withUser(admin)
                                    .withCheque(cheque)));
                });
    }
}
