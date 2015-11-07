package com.gearservice;
import com.gearservice.model.cheque.Cheque;
import com.gearservice.model.cheque.Payment;
import com.gearservice.model.currency.Currency;
import com.gearservice.model.repositories.ChequeRepository;
import com.gearservice.model.repositories.CurrencyRepository;
import com.gearservice.model.repositories.PaymentRepository;
import com.gearservice.model.repositories.UserRepository;
import com.gearservice.model.authorization.Authority;
import com.gearservice.model.authorization.User;
import com.gearservice.service.AnalyticsService;
import com.gearservice.service.ChequeService;
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
import java.util.stream.IntStream;

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
    @Autowired CurrencyRepository currencyRepository;
    @Autowired ChequeRepository chequeRepository;
    @Autowired PaymentRepository paymentRepository;

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
        Arrays.asList(administrator.withUsername(admin), engineer.withUsername(admin), secretary.withUsername(admin))));
        userRepository.save(admin);

        User kosoy = new User();
        kosoy.setUsername("kosoy");
        kosoy.setPassword(passwordEncoder().encode("b"));
        kosoy.setFullname("Косойский Ж.Ж.");
        kosoy.setEnabled(true);
        kosoy.setAuthorities(new HashSet<>(Arrays.asList(engineer.withUsername(kosoy))));
        userRepository.save(kosoy);

        User valikozz = new User();
        valikozz.setUsername("valikozz");
        valikozz.setPassword(passwordEncoder().encode("b"));
        valikozz.setFullname("Валикоззкий Ж.Ж.");
        valikozz.setEnabled(true);
        valikozz.setAuthorities(new HashSet<>(Arrays.asList(engineer.withUsername(valikozz))));
        userRepository.save(valikozz);

        User svetka = new User();
        svetka.setUsername("svetka");
        svetka.setPassword(passwordEncoder().encode("b"));
        svetka.setFullname("Светкаская Ж.Ж.");
        svetka.setEnabled(true);
        svetka.setAuthorities(new HashSet<>(Arrays.asList(secretary.withUsername(svetka))));
        userRepository.save(svetka);

        User yanka = new User();
        yanka.setUsername("yanka");
        yanka.setPassword(passwordEncoder().encode("b"));
        yanka.setFullname("Янкаская Ж.Ж.");
        yanka.setEnabled(true);
        yanka.setAuthorities(new HashSet<>(Arrays.asList(boss.withUsername(yanka))));
        userRepository.save(yanka);

        Currency yesterday = new Currency();
        yesterday.setId(LocalDate.now().minusDays(1).toString());
        yesterday.setEur(new BigDecimal("70"));
        yesterday.setUsd(new BigDecimal("70"));
        yesterday.setUah(new BigDecimal("70"));
        yesterday.withRUB();
        currencyRepository.save(yesterday);

        Currency tomorrow = new Currency();
        tomorrow.setId(LocalDate.now().minusDays(2).toString());
        tomorrow.setEur(new BigDecimal("100"));
        tomorrow.setUsd(new BigDecimal("100"));
        tomorrow.setUah(new BigDecimal("100"));
        tomorrow.withRUB();
        currencyRepository.save(tomorrow);

        Currency dayBeforeYesterday = new Currency();
        dayBeforeYesterday.setId(LocalDate.now().minusDays(3).toString());
        dayBeforeYesterday.setEur(new BigDecimal("10"));
        dayBeforeYesterday.setUsd(new BigDecimal("10"));
        dayBeforeYesterday.setUah(new BigDecimal("10"));
        dayBeforeYesterday.withRUB();
        currencyRepository.save(dayBeforeYesterday);

        OffsetDateTime now = OffsetDateTime.now();

        IntStream.range(0, 25)
                .forEach(i -> {

                    Cheque cheque = new Cheque().withRandomData();
                    cheque.setIntroducedDate(now.minusDays(i));
                    cheque.withDiagnosticUser(admin);
                    cheque.withNoteUser(admin);
                    cheque.setEngineer(admin);
                    cheque.setSecretary(svetka);
                    chequeRepository.save(cheque);

                    Payment repair = new Payment();
                    repair.setCost(1);
                    repair.setCurrentCurrency("usd");
                    repair.setCurrency(yesterday);
                    repair.setType("repair");
                    repair.setUser(kosoy);
                    repair.setPaymentOwner(cheque);
                    paymentRepository.save(repair);

                    Payment zip = new Payment();
                    zip.setCost(1);
                    zip.setCurrentCurrency("rub");
                    zip.setCurrency(dayBeforeYesterday);
                    zip.setType("zip");
                    zip.setUser(admin);
                    zip.setPaymentOwner(cheque);
                    paymentRepository.save(zip);

                    Payment repair2 = new Payment();
                    repair2.setCost(1);
                    repair2.setCurrentCurrency("rub");
                    repair2.setCurrency(dayBeforeYesterday);
                    repair2.setType("repair");
                    repair2.setUser(kosoy);
                    repair2.setPaymentOwner(cheque);
                    paymentRepository.save(repair2);

                    Payment prepayment = new Payment();
                    prepayment.setCost(1);
                    prepayment.setCurrentCurrency("uah");
                    prepayment.setCurrency(tomorrow);
                    prepayment.setType("zip");
                    prepayment.setUser(admin);
                    prepayment.setPaymentOwner(cheque);
                    paymentRepository.save(prepayment);


                });
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {SpringApplication.run(GearServiceApplication.class, args);}


}
