package com.gearservice.model.repositories;

import com.gearservice.model.cheque.Cheque;
import com.gearservice.model.cheque.ChequeMin;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * Interface ChequeRepository with extending of jpa repository get all capabilities of Spring Boot JPA
 *
 * @version 1.0
 * @author Dmitry
 * @since 04.09.2015
 *
 * @see <a href="http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation">more</a>
 */
public interface ChequeRepository extends JpaRepository<Cheque, Long> {

    @EntityGraph(value = "cheque.full", type = EntityGraph.EntityGraphType.LOAD)
    Cheque findById(Long id);

    @EntityGraph(value = "cheque.preview", type = EntityGraph.EntityGraphType.LOAD)
    @Override
    List<Cheque> findAll();

    @EntityGraph(value = "cheque.general", type = EntityGraph.EntityGraphType.LOAD)
    @Override
    Cheque findOne(Long id);

    @Query(value = "SELECT * FROM cheque c LEFT JOIN balance b ON b.cheque_id = c.id " +
            "WHERE b.paid_status = FALSE AND c.id IN ( " +
            "SELECT d1.cheque_id FROM diagnostic d1 LEFT JOIN diagnostic d2 " +
            "ON d1.cheque_id = d2.cheque_id AND d1.date < d2.date " +
            "WHERE d2.date IS NULL AND d1.date <= ?1 ) ", nativeQuery = true)
    List<Cheque> findWithDelay(String delay);

    Cheque findFirstByOrderByIdDesc();
    List<Cheque> findByDiagnosticsIsNull();

    @Query(value = "SELECT DISTINCT customer_name FROM cheque", nativeQuery = true)
    List<String> listOfCustomerNames();

    @Query(value = "SELECT DISTINCT product_name FROM cheque", nativeQuery = true)
    List<String> listOfProductNames();

    @Query(value = "SELECT DISTINCT model_name FROM cheque", nativeQuery = true)
    List<String> listOfModelNames();

//    @Query("SELECT NEW ChequeMin(c.id, c.customerName, c.receiptDate, c.warrantyDate, c.readyDate, c.returnedToClientDate," +
//            "c.productName, c.modelName, c.serialNumber, c.representativeName, secretary.fullname, engineer.fullname," +
//            "c.warrantyStatus, c.readyStatus, c.returnedToClientStatus, b.paidStatus) " +
//            "FROM Balance b " +
//            "LEFT JOIN b.cheque c " +
//            "LEFT JOIN c.engineer engineer " +
//            "LEFT JOIN c.secretary secretary ")
//    List<ChequeMin> getListOfCompactCheques();
//
//    @Query("SELECT NEW ChequeMin(c.id, c.customerName, c.receiptDate, c.warrantyDate, c.readyDate, c.returnedToClientDate," +
//            "c.productName, c.modelName, c.serialNumber, c.representativeName, secretary.fullname, engineer.fullname, " +
//            "c.warrantyStatus, c.readyStatus, c.returnedToClientStatus, b.paidStatus) " +
//            "FROM Balance b " +
//            "LEFT JOIN b.cheque c " +
//            "LEFT JOIN c.engineer engineer " +
//            "LEFT JOIN c.secretary secretary " +
//            "WHERE c.id IN (?1)")
//    List<ChequeMin> getListOfCompactChequesWithIDs(Long... IDs);
//
//    @Query(value = "SELECT d1.cheque_id FROM diagnostic d1 LEFT JOIN diagnostic d2 " +
//            "ON d1.cheque_id = d2.cheque_id AND d1.date < d2.date " +
//            "WHERE d2.date IS NULL AND d1.date <= ?1", nativeQuery = true)
//    Long[] findIdOfChequesWithDelay(String delay);

//    @Query(value = "SELECT * FROM cheque WHERE cheque.id IN " +
//            "(SELECT d1.cheque FROM diagnostic d1 LEFT JOIN diagnostic d2 " +
//            "ON d1.cheque = d2.cheque AND d1.time < d2.time " +
//            "WHERE d2.time IS NULL AND d1.time <= ?1)", nativeQuery = true)
//    List<Cheque> findChequesWithDelay(String delay);
}
