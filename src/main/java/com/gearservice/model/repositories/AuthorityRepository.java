package com.gearservice.model.repositories;

import com.gearservice.model.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository  extends JpaRepository<Authorities, Long> {
}
