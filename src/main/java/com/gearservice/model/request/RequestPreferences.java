package com.gearservice.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.OffsetDateTime;

/**
 * Class RequestPreferences is model Entity, that not store in database
 * and consists request of frontend to return a cheques by the request's parameters
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 */

public class RequestPreferences {

    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private OffsetDateTime introducedFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private OffsetDateTime introducedTo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private OffsetDateTime returnedToClientFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private OffsetDateTime returnedToClientTo;
    private String customerName;
    private String productName;
    private String model;
    private String serialNumber;
    private String representativeName;
    private String secretary;
    private String engineer;
    private String phoneNumber;
    private Boolean warrantyStatus;
    private Boolean readyStatus;
    private Boolean returnedToClientStatus;
    private Boolean paidStatus;
    private Boolean withoutRepair;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public OffsetDateTime getIntroducedFrom() {return introducedFrom;}
    public void setIntroducedFrom(OffsetDateTime introducedFrom) {this.introducedFrom = introducedFrom;}
    public OffsetDateTime getIntroducedTo() {return introducedTo;}
    public void setIntroducedTo(OffsetDateTime introducedTo) {this.introducedTo = introducedTo;}
    public OffsetDateTime getReturnedToClientFrom() {return returnedToClientFrom;}
    public void setReturnedToClientFrom(OffsetDateTime returnedToClientFrom) {this.returnedToClientFrom = returnedToClientFrom;}
    public OffsetDateTime getReturnedToClientTo() {return returnedToClientTo;}
    public void setReturnedToClientTo(OffsetDateTime returnedToClientTo) {this.returnedToClientTo = returnedToClientTo;}
    public String getCustomerName() {return customerName;}
    public void setCustomerName(String customerName) {this.customerName = customerName;}
    public String getProductName() {return productName;}
    public void setProductName(String productName) {this.productName = productName;}
    public String getModel() {return model;}
    public void setModel(String model) {this.model = model;}
    public String getSerialNumber() {return serialNumber;}
    public void setSerialNumber(String serialNumber) {this.serialNumber = serialNumber;}
    public String getRepresentativeName() {return representativeName;}
    public void setRepresentativeName(String representativeName) {this.representativeName = representativeName;}
    public String getSecretary() {return secretary;}
    public void setSecretary(String secretary) {this.secretary = secretary;}
    public String getEngineer() {return engineer;}
    public void setEngineer(String engineer) {this.engineer = engineer;}
    public Boolean getWarrantyStatus() {return warrantyStatus;}
    public void setWarrantyStatus(Boolean warrantyStatus) {this.warrantyStatus = warrantyStatus;}
    public Boolean getReadyStatus() {return readyStatus;}
    public void setReadyStatus(Boolean readyStatus) {this.readyStatus = readyStatus;}
    public Boolean getReturnedToClientStatus() {return returnedToClientStatus;}
    public void setReturnedToClientStatus(Boolean returnedToClientStatus) {this.returnedToClientStatus = returnedToClientStatus;}
    public Boolean getPaidStatus() {return paidStatus;}
    public void setPaidStatus(Boolean paidStatus) {this.paidStatus = paidStatus;}
    public String getPhoneNumber() {return phoneNumber;}
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}
    public Boolean getWithoutRepair() {return withoutRepair;}
    public void setWithoutRepair(Boolean withoutRepair) {this.withoutRepair = withoutRepair;}
}
