package com.gearservice.model.repositories;

import com.gearservice.model.cheque.PhotoMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhotoMongoRepository extends MongoRepository<PhotoMongo, String> {



}
