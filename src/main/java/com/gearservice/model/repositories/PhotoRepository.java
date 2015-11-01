package com.gearservice.model.repositories;

import com.gearservice.model.cheque.Photo;
import com.gearservice.model.cheque.PhotoMin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    @Query("SELECT NEW PhotoMin(p.id, p.name, p.addedDate, p.photoOwner, p.user) " +
            "FROM Photo p WHERE p.photoOwner.id = ?1")
    List<PhotoMin> getListOfCompactPhotoFromCheque(Long ChequeID);

}
