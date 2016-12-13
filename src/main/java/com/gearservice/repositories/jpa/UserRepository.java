package com.gearservice.repositories.jpa;

import com.gearservice.model.authorization.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Interface UserRepository
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 *
 * @see <a href="http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation">more</a>
 */

public interface UserRepository extends JpaRepository<User, String> {

    @Cacheable("secretaries")
    @Query(value = "SELECT DISTINCT fullname FROM user", nativeQuery = true)
    List<String> listOfSecretaries();

    @Cacheable("engineers")
    @Query(value = "SELECT DISTINCT fullname FROM user", nativeQuery = true)
    List<String> listOfEngineers();

}
