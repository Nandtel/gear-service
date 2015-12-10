package com.gearservice.model.repositories;

import com.gearservice.model.cheque.Cheque;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
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
            "WHERE d2.date IS NULL AND d1.date <= ?1 AND c.ready_status = FALSE AND c.returned_to_client_status = FALSE ) "
            , nativeQuery = true)
    List<Cheque> findWithDelay(String delay);

    Cheque findFirstByOrderByIdDesc();
    List<Cheque> findByReturnedToClientStatusFalseAndReadyStatusFalseAndDiagnosticsIsNull();

    @Cacheable("customers")
    @Query(value = "SELECT DISTINCT customer_name FROM cheque", nativeQuery = true)
    List<String> listOfCustomerNames();

    @Cacheable("products")
    @Query(value = "SELECT DISTINCT product_name FROM cheque", nativeQuery = true)
    List<String> listOfProductNames();

    @Cacheable("models")
    @Query(value = "SELECT DISTINCT model_name FROM cheque", nativeQuery = true)
    List<String> listOfModelNames();

    @Cacheable("serials")
    @Query(value = "SELECT DISTINCT serial_number FROM cheque", nativeQuery = true)
    List<String> listOfSerialNumbers();

    @Cacheable("representatives")
    @Query(value = "SELECT DISTINCT representative_name FROM cheque", nativeQuery = true)
    List<String> listOfRepresentativeNames();

    @Cacheable("emails")
    @Query(value = "SELECT DISTINCT email FROM cheque", nativeQuery = true)
    List<String> listOfEmails();

    @EntityGraph(value = "cheque.preview", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT c FROM Cheque c, Balance b, User e, User s " +
            "WHERE c.id = b.cheque AND c.engineer = e.username AND c.secretary = s.username " +
            "AND (c.id = :id OR :id IS NULL) " +
            "AND (c.receiptDate >= :introducedFrom OR :introducedFrom IS NULL) " +
            "AND (c.receiptDate <= :introducedTo OR :introducedTo IS NULL) " +
            "AND (c.returnedToClientDate >= :returnedToClientFrom OR :returnedToClientFrom IS NULL) " +
            "AND (c.returnedToClientDate <= :returnedToClientTo OR :returnedToClientTo IS NULL) " +
            "AND (c.customerName LIKE %:customerName% OR :customerName IS NULL) " +
            "AND (c.productName LIKE %:productName% OR :productName IS NULL) " +
            "AND (c.modelName LIKE %:modelName% OR :modelName IS NULL) " +
            "AND (c.serialNumber LIKE %:serialNumber% OR :serialNumber IS NULL) " +
            "AND (c.representativeName LIKE %:representativeName% OR :representativeName IS NULL) " +
            "AND (s.fullname LIKE %:secretary% OR :secretary IS NULL) " +
            "AND (e.fullname LIKE %:engineer% OR :engineer IS NULL) " +
            "AND (c.warrantyStatus = :warrantyStatus OR :warrantyStatus IS NULL) " +
            "AND (c.readyStatus = :readyStatus OR :readyStatus IS NULL) " +
            "AND (c.returnedToClientStatus = :returnedToClientStatus OR :returnedToClientStatus IS NULL) " +
            "AND (b.paidStatus = :paidStatus OR :paidStatus IS NULL) ")
    List<Cheque> findByRequest(
            @Param("id") Long id,
            @Param("introducedFrom") OffsetDateTime introducedFrom,
            @Param("introducedTo") OffsetDateTime introducedTo,
            @Param("returnedToClientFrom") OffsetDateTime returnedToClientFrom,
            @Param("returnedToClientTo") OffsetDateTime returnedToClientTo,
            @Param("customerName") String customerName,
            @Param("productName") String productName,
            @Param("modelName") String model,
            @Param("serialNumber") String serialNumber,
            @Param("representativeName") String representativeName,
            @Param("secretary") String secretary,
            @Param("engineer") String engineer,
            @Param("warrantyStatus") Boolean warrantyStatus,
            @Param("readyStatus") Boolean readyStatus,
            @Param("returnedToClientStatus") Boolean returnedToClientStatus,
            @Param("paidStatus") Boolean paidStatus);

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
