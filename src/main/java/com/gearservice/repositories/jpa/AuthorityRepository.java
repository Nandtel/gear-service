package com.gearservice.repositories.jpa;

import com.gearservice.model.authorization.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface AuthorityRepository
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 *
 * @see <a href="http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation">more</a>
 */

public interface AuthorityRepository extends JpaRepository<Authority, Long> {}
