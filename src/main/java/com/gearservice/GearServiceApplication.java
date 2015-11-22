package com.gearservice;
import com.gearservice.model.cheque.*;
import com.gearservice.model.exchangeRate.ExchangeRate;
import com.gearservice.model.repositories.*;
import com.gearservice.model.authorization.Authority;
import com.gearservice.model.authorization.User;
import com.gearservice.service.SampleDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;

/**
 * Class GearServiceApplication is main, configurable class of application.
 * It handles by Spring Boot.
 *
 * @version 1.0
 * @author Dmitry
 * @since 04.09.2015
 */
@SpringBootApplication
@EnableTransactionManagement
public class GearServiceApplication implements CommandLineRunner {

    @Autowired UserRepository userRepository;
    @Autowired ExchangeRateRepository exchangeRateRepository;
    @Autowired ChequeRepository chequeRepository;
    @Autowired PaymentRepository paymentRepository;
    @Autowired BalanceRepository balanceRepository;
    @Autowired DiagnosticRepository diagnosticRepository;
    @Autowired NoteRepository noteRepository;

    @Override
    public void run(String... args) throws Exception {

        Authority engineer = new Authority("ROLE_ENGINEER");
        Authority secretary = new Authority("ROLE_SECRETARY");
        Authority administrator = new Authority("ROLE_ADMIN");
        Authority boss = new Authority("ROLE_BOSS");

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder().encode("b"));
        admin.setFullname("Грешник А.А.");
        admin.setEnabled(true);
        admin.setAuthorities(new HashSet<>(
        asList(administrator.withUsername(admin), engineer.withUsername(admin), secretary.withUsername(admin))));
        userRepository.save(admin);

        User kosoy = new User();
        kosoy.setUsername("kosoy");
        kosoy.setPassword(passwordEncoder().encode("b"));
        kosoy.setFullname("Косойский Ж.Ж.");
        kosoy.setEnabled(true);
        kosoy.setAuthorities(new HashSet<>(asList(engineer.withUsername(kosoy))));
        userRepository.save(kosoy);

        User valikozz = new User();
        valikozz.setUsername("valikoz");
        valikozz.setPassword(passwordEncoder().encode("b"));
        valikozz.setFullname("Валикоззкий Ж.Ж.");
        valikozz.setEnabled(true);
        valikozz.setAuthorities(new HashSet<>(asList(engineer.withUsername(valikozz))));
        userRepository.save(valikozz);

        User svetka = new User();
        svetka.setUsername("sveta");
        svetka.setPassword(passwordEncoder().encode("b"));
        svetka.setFullname("Светкаская Ж.Ж.");
        svetka.setEnabled(true);
        svetka.setAuthorities(new HashSet<>(asList(secretary.withUsername(svetka))));
        userRepository.save(svetka);

        User yanka = new User();
        yanka.setUsername("yana");
        yanka.setPassword(passwordEncoder().encode("b"));
        yanka.setFullname("Янкаская Ж.Ж.");
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

        IntStream.range(0, 5).parallel()
                .forEach(i -> {
                    Cheque cheque = new Cheque().withRandomData();
                    cheque.setReceiptDate(now.minusDays(i));
//                    cheque.withDiagnosticUser(admin);
//                    cheque.withNoteUser(admin);
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

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {SpringApplication.run(GearServiceApplication.class, args);}


}
