package com.gearservice.model.repositories;

import com.gearservice.model.cheque.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PhotoRepository extends MongoRepository<Photo, String> {

    @Query(value = "{photoOwner:?0}", fields = "{bytes:0}")
    List<Photo> findByPhotoOwnerExcludeBytes(String chequeID);

    Long deleteByPhotoOwner(String chequeID);

}
