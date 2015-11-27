package com.gearservice.model.repositories;

import com.gearservice.model.cheque.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ComponentRepository extends JpaRepository<Component, Long> {

    @Query(value = "SELECT DISTINCT name FROM component", nativeQuery = true)
    List<String> listOfComponentNames();

}
