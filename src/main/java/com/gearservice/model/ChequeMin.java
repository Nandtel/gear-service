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
    private OffsetDateTime introduced;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private OffsetDateTime guarantee;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private OffsetDateTime ready;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private OffsetDateTime issued;

    private String nameOfProduct;
    private String model;
    private String serialNumber;

    private String purchaserName;
    private String inspectorName;
    private String masterName;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getNameOfCustomer() {return nameOfCustomer;}
    public void setNameOfCustomer(String nameOfCustomer) {this.nameOfCustomer = nameOfCustomer;}
    public OffsetDateTime getIntroduced() {return introduced;}
    public void setIntroduced(OffsetDateTime introduced) {this.introduced = introduced;}
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
    public OffsetDateTime getGuarantee() {return guarantee;}
    public void setGuarantee(OffsetDateTime guarantee) {this.guarantee = guarantee;}
    public OffsetDateTime getReady() {return ready;}
    public void setReady(OffsetDateTime ready) {this.ready = ready;}
    public OffsetDateTime getIssued() {return issued;}
    public void setIssued(OffsetDateTime issued) {this.issued = issued;}
}
