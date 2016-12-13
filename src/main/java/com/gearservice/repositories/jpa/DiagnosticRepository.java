package com.gearservice.repositories.jpa;

import com.gearservice.model.cheque.Diagnostic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Interface DiagnosticRepository
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 *
 * @see <a href="http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation">more</a>
 */

public interface DiagnosticRepository extends JpaRepository<Diagnostic, Long> {

    List<Diagnostic> findByChequeId(Long chequeID);

}
