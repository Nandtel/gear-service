package com.gearservice.model.cheque;

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
    private String customerName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private OffsetDateTime receiptDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private OffsetDateTime warrantyDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private OffsetDateTime readyDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private OffsetDateTime returnedToClientDate;

    private String productName;
    private String modelName;
    private String serialNumber;

    private String representativeName;
    private String secretary;
    private String engineer;

    private boolean warrantyStatus;
    private boolean readyStatus;
    private boolean returnedToClientStatus;
    private boolean paidStatus;

    public ChequeMin() {}

    public ChequeMin(Long id, String customerName, OffsetDateTime receiptDate, OffsetDateTime guaranteeDate,
                     OffsetDateTime readyDate, OffsetDateTime returnedToClientDate, String productName, String modelName,
                     String serialNumber, String representativeName, String secretary, String engineer,
                     boolean warrantyStatus, boolean readyStatus, boolean returnedToClientStatus) {

        this.id = id;
        this.customerName = customerName;
        this.receiptDate = receiptDate;
        this.warrantyDate = guaranteeDate;
        this.readyDate = readyDate;
        this.returnedToClientDate = returnedToClientDate;
        this.productName = productName;
        this.modelName = modelName;
        this.serialNumber = serialNumber;
        this.representativeName = representativeName;
        this.secretary = secretary;
        this.engineer = engineer;
        this.warrantyStatus = warrantyStatus;
        this.readyStatus = readyStatus;
        this.returnedToClientStatus = returnedToClientStatus;
        this.paidStatus = paidStatus;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getCustomerName() {return customerName;}
    public void setCustomerName(String customerName) {this.customerName = customerName;}
    public String getProductName() {return productName;}
    public void setProductName(String productName) {this.productName = productName;}
    public String getModelName() {return modelName;}
    public void setModelName(String modelName) {this.modelName = modelName;}
    public String getSerialNumber() {return serialNumber;}
    public void setSerialNumber(String serialNumber) {this.serialNumber = serialNumber;}
    public String getRepresentativeName() {return representativeName;}
    public void setRepresentativeName(String representativeName) {this.representativeName = representativeName;}
    public String getSecretary() {return secretary;}
    public void setSecretary(String secretary) {this.secretary = secretary;}
    public String getEngineer() {return engineer;}
    public void setEngineer(String engineer) {this.engineer = engineer;}
    public void setReceiptDate(OffsetDateTime receiptDate) {this.receiptDate = receiptDate;}
    public OffsetDateTime getReceiptDate() {return receiptDate;}
    public void setWarrantyDate(OffsetDateTime warrantyDate) {this.warrantyDate = warrantyDate;}
    public OffsetDateTime getWarrantyDate() {return warrantyDate;}
    public void setReadyDate(OffsetDateTime readyDate) {this.readyDate = readyDate;}
    public OffsetDateTime getReadyDate() {return readyDate;}
    public void setReturnedToClientDate(OffsetDateTime returnedToClientDate) {this.returnedToClientDate = returnedToClientDate;}
    public OffsetDateTime getReturnedToClientDate() {return returnedToClientDate;}
    public boolean getPaidStatus() {return paidStatus;}
    public void setPaidStatus(boolean paidStatus) {this.paidStatus = paidStatus;}
    public boolean isWarrantyStatus() {return warrantyStatus;}
    public void setWarrantyStatus(boolean warrantyStatus) {this.warrantyStatus = warrantyStatus;}
    public boolean isReadyStatus() {return readyStatus;}
    public void setReadyStatus(boolean readyStatus) {this.readyStatus = readyStatus;}
    public boolean isReturnedToClientStatus() {return returnedToClientStatus;}
    public void setReturnedToClientStatus(boolean returnedToClientStatus) {this.returnedToClientStatus = returnedToClientStatus;}

}
