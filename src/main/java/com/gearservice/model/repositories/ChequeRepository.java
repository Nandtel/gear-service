package com.gearservice.model.repositories;

import com.gearservice.model.cheque.Cheque;
import com.gearservice.model.cheque.ChequeMin;
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
    Cheque findFirstByOrderByIdDesc();
    List<Cheque> findByDiagnosticsIsNull();

    @Query("SELECT NEW ChequeMin(c.id, c.nameOfCustomer, c.introducedDate, c.guaranteeDate, c.readyDate, c.issuedDate," +
            "c.nameOfProduct, c.model, c.serialNumber, c.purchaserName, c.inspectorName, c.masterName," +
            "c.hasGuaranteeStatus, c.hasReadyStatus, c.hasIssuedStatus, c.hasPaidStatus) " +
            "FROM Cheque c ")
    List<ChequeMin> getListOfCompactCheques();

    @Query("SELECT NEW ChequeMin(c.id, c.nameOfCustomer, c.introducedDate, c.guaranteeDate, c.readyDate, c.issuedDate," +
            "c.nameOfProduct, c.model, c.serialNumber, c.purchaserName, c.inspectorName, c.masterName," +
            "c.hasGuaranteeStatus, c.hasReadyStatus, c.hasIssuedStatus, c.hasPaidStatus) " +
            "FROM Cheque c WHERE c.id IN (?1)")
    List<ChequeMin> getListOfCompactChequesWithIDs(Long... IDs);

    @Query(value = "SELECT d1.cheque FROM diagnostic d1 LEFT JOIN diagnostic d2 " +
            "ON d1.cheque = d2.cheque AND d1.time < d2.time " +
            "WHERE d2.time IS NULL AND d1.time <= ?1", nativeQuery = true)
    Long[] findIdOfChequesWithDelay(String delay);

//    @Query(value = "SELECT * FROM cheque WHERE cheque.id IN " +
//            "(SELECT d1.cheque FROM diagnostic d1 LEFT JOIN diagnostic d2 " +
//            "ON d1.cheque = d2.cheque AND d1.time < d2.time " +
//            "WHERE d2.time IS NULL AND d1.time <= ?1)", nativeQuery = true)
//    List<Cheque> findChequesWithDelay(String delay);
}
