package com.gearservice.model.repositories;

import com.gearservice.model.cheque.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
