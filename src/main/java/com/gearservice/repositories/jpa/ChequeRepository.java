package com.gearservice.repositories.jpa;

import com.gearservice.model.cheque.Cheque;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Interface ChequeRepository
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 *
 * @see <a href="http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation">more</a>
 */
public interface ChequeRepository extends JpaRepository<Cheque, Long> {

    String searchQuery =  "SELECT c FROM Cheque c, Balance b, User e, User s " +
            "WHERE c.id = b.cheque AND c.engineer = e.username AND c.secretary = s.username " +
            "AND (c.id = :id OR :id IS NULL) " +
            "AND (c.receiptDate >= :introducedFrom OR :introducedFrom IS NULL) " +
            "AND (c.receiptDate <= :introducedTo OR :introducedTo IS NULL) " +
            "AND (c.returnedToClientDate >= :returnedToClientFrom OR :returnedToClientFrom IS NULL) " +
            "AND (c.returnedToClientDate <= :returnedToClientTo OR :returnedToClientTo IS NULL) " +
            "AND (lower(c.customerName) LIKE concat('%', lower(:customerName), '%') OR :customerName IS NULL) " +
            "AND (lower(c.productName) LIKE concat('%', lower(:productName), '%') OR :productName IS NULL) " +
            "AND (lower(c.modelName) LIKE concat('%', lower(:modelName), '%') OR :modelName IS NULL) " +
            "AND (lower(c.representativeName) LIKE concat('%', lower(:representativeName), '%') OR :representativeName IS NULL) " +
            "AND (lower(s.fullname) LIKE concat('%', lower(:secretary), '%') OR :secretary IS NULL) " +
            "AND (lower(e.fullname) LIKE concat('%', lower(:engineer), '%') OR :engineer IS NULL) " +
            "AND (c.phoneNumber LIKE %:phoneNumber% OR :phoneNumber IS NULL) " +
            "AND (c.serialNumber LIKE %:serialNumber% OR :serialNumber IS NULL) " +
            "AND (c.warrantyStatus = :warrantyStatus OR :warrantyStatus IS NULL) " +
            "AND (c.readyStatus = :readyStatus OR :readyStatus IS NULL) " +
            "AND (c.returnedToClientStatus = :returnedToClientStatus OR :returnedToClientStatus IS NULL) " +
            "AND (b.paidStatus = :paidStatus OR :paidStatus IS NULL) " +
            "AND (c.withoutRepair = :withoutRepair OR :withoutRepair IS NULL) ";

    @EntityGraph(value = "cheque.open", type = EntityGraph.EntityGraphType.LOAD)
    Optional<Cheque> findById(Long id);

    @EntityGraph(value = "cheque.preview", type = EntityGraph.EntityGraphType.LOAD)
    @Override
    List<Cheque> findAll();

    @EntityGraph(value = "cheque.general", type = EntityGraph.EntityGraphType.LOAD)
    @Override
    Cheque getOne(Long id);

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

    @Cacheable("phoneNumbers")
    @Query(value = "SELECT DISTINCT phone_number FROM cheque", nativeQuery = true)
    List<String> listOfPhoneNumbers();

    @EntityGraph(value = "cheque.preview", type = EntityGraph.EntityGraphType.LOAD)
    @Query(value = searchQuery)
    Page<Cheque> findByRequest(
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
            @Param("phoneNumber") String phoneNumber,
            @Param("secretary") String secretary,
            @Param("engineer") String engineer,
            @Param("warrantyStatus") Boolean warrantyStatus,
            @Param("readyStatus") Boolean readyStatus,
            @Param("returnedToClientStatus") Boolean returnedToClientStatus,
            @Param("paidStatus") Boolean paidStatus,
            @Param("withoutRepair") Boolean withoutRepair,
            Pageable pageable);

    @EntityGraph(value = "cheque.preview", type = EntityGraph.EntityGraphType.LOAD)
    @Query(value = searchQuery)
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
            @Param("phoneNumber") String phoneNumber,
            @Param("secretary") String secretary,
            @Param("engineer") String engineer,
            @Param("warrantyStatus") Boolean warrantyStatus,
            @Param("readyStatus") Boolean readyStatus,
            @Param("returnedToClientStatus") Boolean returnedToClientStatus,
            @Param("paidStatus") Boolean paidStatus,
            @Param("withoutRepair") Boolean withoutRepair);
}
