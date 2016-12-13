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
import java.util.HashSet;
import java.util.List;
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

    @Autowired UserRepository userRepository;
    @Autowired
    ExchangeRateRepository exchangeRateRepository;
    @Autowired
    ChequeRepository chequeRepository;
    @Autowired
    BalanceRepository balanceRepository;
    @Autowired
    DiagnosticRepository diagnosticRepository;
    @Autowired NoteRepository noteRepository;
    @Autowired
    ComponentRepository componentRepository;

    /**
     * Method getAutocompleteData return autocomplete data for request item name
     * @param itemName for autocomplete
     * @return List of autocomplete data(String or User)
     */
    @Transactional(readOnly = true)
    public List<?> getAutocompleteData(String itemName) {
        switch (itemName) {
            case "customers": return chequeRepository.listOfCustomerNames();
            case "products": return chequeRepository.listOfProductNames();
            case "models": return chequeRepository.listOfModelNames();
            case "serials": return chequeRepository.listOfSerialNumbers();
            case "representatives": return chequeRepository.listOfRepresentativeNames();
            case "emails": return chequeRepository.listOfEmails();
            case "components": return componentRepository.listOfComponentNames();
            case "secretaries": return userRepository.listOfSecretaries();
            case "engineers": return userRepository.listOfEngineers();
            case "users": return userRepository.findAll();
            case "phoneNumbers": return chequeRepository.listOfPhoneNumbers();
            default: throw new IllegalArgumentException();
        }
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
        admin.setUsername("parinov_a");
        admin.setPassword(passwordEncoder().encode("hren"));
        admin.setFullname("Паринов А.А.");
        admin.setEnabled(true);
        admin.setAuthorities(new HashSet<>(asList(administrator.withUsername(admin), engineer.withUsername(admin), secretary.withUsername(admin))));
        userRepository.save(admin);

        User kosoy = new User();
        kosoy.setUsername("kositsky_a");
        kosoy.setPassword(passwordEncoder().encode("hren"));
        kosoy.setFullname("Косицкий А.В.");
        kosoy.setEnabled(true);
        kosoy.setAuthorities(new HashSet<>(asList(engineer.withUsername(kosoy))));
        userRepository.save(kosoy);

        User novik = new User();
        novik.setUsername("novik_m");
        novik.setPassword(passwordEncoder().encode("hren"));
        novik.setFullname("Новик М.С.");
        novik.setEnabled(false);
        novik.setAuthorities(new HashSet<>(asList(engineer.withUsername(novik))));
        userRepository.save(novik);

        User levchenko = new User();
        levchenko.setUsername("levchenko_d");
        levchenko.setPassword(passwordEncoder().encode("hren"));
        levchenko.setFullname("Левченко Д.А.");
        levchenko.setEnabled(false);
        levchenko.setAuthorities(new HashSet<>(asList(engineer.withUsername(levchenko))));
        userRepository.save(levchenko);

        User nechesa = new User();
        nechesa.setUsername("nechesa_e");
        nechesa.setPassword(passwordEncoder().encode("hren"));
        nechesa.setFullname("Нечеса Е.М.");
        nechesa.setEnabled(false);
        nechesa.setAuthorities(new HashSet<>(asList(engineer.withUsername(nechesa))));
        userRepository.save(nechesa);

        User valikozz = new User();
        valikozz.setUsername("alexeev_v");
        valikozz.setPassword(passwordEncoder().encode("hren"));
        valikozz.setFullname("Алексеев В.Ю.");
        valikozz.setEnabled(true);
        valikozz.setAuthorities(new HashSet<>(asList(engineer.withUsername(valikozz))));
        userRepository.save(valikozz);

        User svetka = new User();
        svetka.setUsername("moroz_s");
        svetka.setPassword(passwordEncoder().encode("hren"));
        svetka.setFullname("Мороз С.В.");
        svetka.setEnabled(true);
        svetka.setAuthorities(new HashSet<>(asList(secretary.withUsername(svetka))));
        userRepository.save(svetka);

        User yanka = new User();
        yanka.setUsername("rudenko_y");
        yanka.setPassword(passwordEncoder().encode("hren"));
        yanka.setFullname("Руденко-Алексеева Я.А.");
        yanka.setEnabled(true);
        yanka.setAuthorities(new HashSet<>(asList(boss.withUsername(yanka))));
        userRepository.save(yanka);

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

        ExchangeRate yesterday1 = new ExchangeRate();
        yesterday1.setAddDate(LocalDate.now().plusDays(1).toString());
        yesterday1.setEur(new BigDecimal("70"));
        yesterday1.setUsd(new BigDecimal("70"));
        yesterday1.setUah(new BigDecimal("70"));
        exchangeRateRepository.save(yesterday1);

        ExchangeRate tomorrow1 = new ExchangeRate();
        tomorrow1.setAddDate(LocalDate.now().plusDays(2).toString());
        tomorrow1.setEur(new BigDecimal("100"));
        tomorrow1.setUsd(new BigDecimal("100"));
        tomorrow1.setUah(new BigDecimal("100"));
        exchangeRateRepository.save(tomorrow1);

        ExchangeRate dayBeforeYesterday1 = new ExchangeRate();
        dayBeforeYesterday1.setAddDate(LocalDate.now().plusDays(3).toString());
        dayBeforeYesterday1.setEur(new BigDecimal("10"));
        dayBeforeYesterday1.setUsd(new BigDecimal("10"));
        dayBeforeYesterday1.setUah(new BigDecimal("10"));
        exchangeRateRepository.save(dayBeforeYesterday1);

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
                    repair.setUser(kosoy);

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
                    repair2.setUser(kosoy);

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

                    diagnosticRepository.save(SampleDataService.getSetConsistFrom(
                            o -> new Diagnostic()
                                    .withRandomData()
                                    .withUser(admin)
                                    .withCheque(cheque)));

                    noteRepository.save(SampleDataService.getSetConsistFrom(
                            o -> new Note()
                                    .withRandomData()
                                    .withUser(admin)
                                    .withCheque(cheque)));
                });
    }
}
