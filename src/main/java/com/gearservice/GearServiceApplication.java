package com.gearservice;
import com.gearservice.model.authorization.Authority;
import com.gearservice.model.authorization.User;
import com.gearservice.model.repositories.*;
import com.gearservice.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashSet;

import static java.util.Arrays.asList;

/**
 * Class GearServiceApplication is main, configurable class of application.
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 */

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
public class GearServiceApplication {

    @Autowired UserRepository userRepository;
    @Autowired ExchangeRateRepository exchangeRateRepository;
    @Autowired ChequeRepository chequeRepository;
    @Autowired PaymentRepository paymentRepository;
    @Autowired BalanceRepository balanceRepository;
    @Autowired DiagnosticRepository diagnosticRepository;
    @Autowired NoteRepository noteRepository;
    @Autowired AnalyticsService analyticsService;

    public static void main(String[] args) {SpringApplication.run(GearServiceApplication.class, args);}

    @Bean
    @Profile("dev")
    CommandLineRunner init() {
        return args -> {
            if (!userRepository.exists("admin")) {
                Authority administrator = new Authority("ROLE_ADMIN");
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(new BCryptPasswordEncoder().encode("b"));
                admin.setFullname("admin");
                admin.setEnabled(true);
                admin.setAuthorities(new HashSet<>(asList(administrator.withUsername(admin))));
                userRepository.save(admin);
            }
        };
    }
}
