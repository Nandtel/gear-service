package com.gearservice.model.repositories;

import com.gearservice.model.authorization.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository  extends JpaRepository<Authority, Long> {}
