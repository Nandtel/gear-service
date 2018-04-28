package com.gearservice.repositories.mongo;

import com.gearservice.model.cheque.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Interface PhotoRepository
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 *
 * @see <a href="http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation">more</a>
 */

public interface PhotoRepository extends MongoRepository<Photo, String> {

    @Query(value = "{chequeId:?0}", fields = "{bytes:0}")
    List<Photo> findByChequeIdExcludeBytes(String chequeID);

    Long deleteByChequeId(String chequeID);

}
