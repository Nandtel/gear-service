package com.gearservice.model.repositories;

import com.gearservice.model.cheque.Diagnostic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interface DiagnosticRepository with extending of jpa repository get all capabilities of Spring Boot JPA
 *
 * @version 1.0
 * @author Dmitry
 * @since 04.09.2015
 *
 * @see <a href="http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation">more</a>
 */
public interface DiagnosticRepository extends JpaRepository<Diagnostic, Long> {}
