package com.gearservice.model.repositories;

import com.gearservice.model.authorization.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
