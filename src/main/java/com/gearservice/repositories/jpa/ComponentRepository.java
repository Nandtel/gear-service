package com.gearservice.repositories.jpa;

import com.gearservice.model.cheque.Component;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Interface ComponentRepository
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 *
 * @see <a href="http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation">more</a>
 */

public interface ComponentRepository extends JpaRepository<Component, Long> {

    @Cacheable("components")
    @Query(value = "SELECT DISTINCT name FROM component", nativeQuery = true)
    List<String> listOfComponentNames();

}
