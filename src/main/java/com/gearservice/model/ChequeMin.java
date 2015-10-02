package com.gearservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.OffsetDateTime;

/**
 * Class ChequeMin is model Entity, that no store in database, but necessary for extracting from database partial
 * object of Cheque with limited number of fields.
 *
 * @version 1.0
 * @author Dmitry
 * @since 04.09.2015
 */
@Entity
public class ChequeMin {

    @Id
    private Long id;
    private String nameOfCustomer;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private OffsetDateTime introducedDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private OffsetDateTime guaranteeDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private OffsetDateTime readyDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private OffsetDateTime issuedDate;

    private String nameOfProduct;
    private String model;
    private String serialNumber;

    private String purchaserName;
    private String inspectorName;
    private String masterName;

    private boolean hasGuaranteeStatus;
    private boolean hasReadyStatus;
    private boolean hasIssuedStatus;
    private boolean hasPaidStatus;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getNameOfCustomer() {return nameOfCustomer;}
    public void setNameOfCustomer(String nameOfCustomer) {this.nameOfCustomer = nameOfCustomer;}
    public String getNameOfProduct() {return nameOfProduct;}
    public void setNameOfProduct(String nameOfProduct) {this.nameOfProduct = nameOfProduct;}
    public String getModel() {return model;}
    public void setModel(String model) {this.model = model;}
    public String getSerialNumber() {return serialNumber;}
    public void setSerialNumber(String serialNumber) {this.serialNumber = serialNumber;}
    public String getPurchaserName() {return purchaserName;}
    public void setPurchaserName(String purchaserName) {this.purchaserName = purchaserName;}
    public String getInspectorName() {return inspectorName;}
    public void setInspectorName(String inspectorName) {this.inspectorName = inspectorName;}
    public String getMasterName() {return masterName;}
    public void setMasterName(String masterName) {this.masterName = masterName;}
    public void setIntroducedDate(OffsetDateTime introducedDate) {this.introducedDate = introducedDate;}
    public OffsetDateTime getIntroducedDate() {return introducedDate;}
    public void setGuaranteeDate(OffsetDateTime guaranteeDate) {this.guaranteeDate = guaranteeDate;}
    public OffsetDateTime getGuaranteeDate() {return guaranteeDate;}
    public void setReadyDate(OffsetDateTime readyDate) {this.readyDate = readyDate;}
    public OffsetDateTime getReadyDate() {return readyDate;}
    public void setIssuedDate(OffsetDateTime issuedDate) {this.issuedDate = issuedDate;}
    public OffsetDateTime getIssuedDate() {return issuedDate;}
    public boolean getHasPaidStatus() {return hasPaidStatus;}
    public void setHasPaidStatus(boolean hasPaidStatus) {this.hasPaidStatus = hasPaidStatus;}
    public boolean isHasGuaranteeStatus() {return hasGuaranteeStatus;}
    public void setHasGuaranteeStatus(boolean hasGuaranteeStatus) {this.hasGuaranteeStatus = hasGuaranteeStatus;}
    public boolean isHasReadyStatus() {return hasReadyStatus;}
    public void setHasReadyStatus(boolean hasReadyStatus) {this.hasReadyStatus = hasReadyStatus;}
    public boolean isHasIssuedStatus() {return hasIssuedStatus;}
    public void setHasIssuedStatus(boolean hasIssuedStatus) {this.hasIssuedStatus = hasIssuedStatus;}
}
