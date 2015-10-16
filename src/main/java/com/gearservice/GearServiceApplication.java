package com.gearservice;
import com.gearservice.model.currency.Currency;
import com.gearservice.model.repositories.CurrencyRepository;
import com.gearservice.model.repositories.UserRepository;
import com.gearservice.model.authorization.Authorities;
import com.gearservice.model.authorization.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Class GearServiceApplication is main, configurable class of application.
 * It handles by Spring Boot.
 *
 * @version 1.0
 * @author Dmitry
 * @since 04.09.2015
 */
@SpringBootApplication
public class GearServiceApplication implements CommandLineRunner {

    @Autowired UserRepository userRepository;
    @Autowired CurrencyRepository currencyRepository;

    @Override
    public void run(String... args) throws Exception {

        Authorities engineer = new Authorities("ROLE_ENGINEER");
        Authorities secretary = new Authorities("ROLE_SECRETARY");
        Authorities administrator = new Authorities("ROLE_ADMIN");

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder().encode("b"));
        admin.setFullName("Грешник А.А.");
        admin.setEnabled(true);
        admin.setAuthorities(new HashSet<>(Arrays.asList(administrator.withUsername(admin))));
        userRepository.save(admin);

        User kosoy = new User();
        kosoy.setUsername("kosoy");
        kosoy.setPassword(passwordEncoder().encode("b"));
        kosoy.setFullName("Косойский Ж.Ж.");
        kosoy.setEnabled(true);
        kosoy.setAuthorities(new HashSet<>(Arrays.asList(engineer.withUsername(kosoy))));
        userRepository.save(kosoy);

        User valikozz = new User();
        valikozz.setUsername("valikozz");
        valikozz.setPassword(passwordEncoder().encode("b"));
        valikozz.setFullName("Валикоззкий Ж.Ж.");
        valikozz.setEnabled(true);
        valikozz.setAuthorities(new HashSet<>(Arrays.asList(engineer.withUsername(valikozz))));
        userRepository.save(valikozz);

        User svetka = new User();
        svetka.setUsername("svetka");
        svetka.setPassword(passwordEncoder().encode("b"));
        svetka.setFullName("Светкаская Ж.Ж.");
        svetka.setEnabled(true);
        svetka.setAuthorities(new HashSet<>(Arrays.asList(secretary.withUsername(svetka))));
        userRepository.save(svetka);

        Currency yesterday = new Currency();
        yesterday.setId(LocalDate.now().minusDays(1).toString());
        yesterday.setEur(new BigDecimal("0"));
        yesterday.setUsd(new BigDecimal("0"));
        yesterday.setUah(new BigDecimal("0"));
        currencyRepository.save(yesterday);

        Currency tomorrow = new Currency();
        tomorrow.setId(LocalDate.now().plusDays(1).toString());
        tomorrow.setEur(new BigDecimal("100"));
        tomorrow.setUsd(new BigDecimal("100"));
        tomorrow.setUah(new BigDecimal("100"));
        currencyRepository.save(tomorrow);

        Currency dayBeforeYesterday = new Currency();
        dayBeforeYesterday.setId(LocalDate.now().minusDays(2).toString());
        dayBeforeYesterday.setEur(new BigDecimal("10"));
        dayBeforeYesterday.setUsd(new BigDecimal("10"));
        dayBeforeYesterday.setUah(new BigDecimal("10"));
        currencyRepository.save(dayBeforeYesterday);
    }

    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {SpringApplication.run(GearServiceApplication.class, args);}


}
