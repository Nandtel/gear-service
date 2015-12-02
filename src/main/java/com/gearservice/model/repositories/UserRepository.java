package com.gearservice.model.repositories;

import com.gearservice.model.authorization.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    @Cacheable("secretaries")
    @Query(value = "SELECT DISTINCT fullname FROM user", nativeQuery = true)
    List<String> listOfSecretaries();

    @Cacheable("engineers")
    @Query(value = "SELECT DISTINCT fullname FROM user", nativeQuery = true)
    List<String> listOfEngineers();

}
