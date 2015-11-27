package com.gearservice.model.repositories;

import com.gearservice.model.authorization.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    @Query(value = "SELECT DISTINCT fullname FROM user", nativeQuery = true)
    List<String> listOfSecretaries();

    @Query(value = "SELECT DISTINCT fullname FROM user", nativeQuery = true)
    List<String> listOfEngineers();

}
