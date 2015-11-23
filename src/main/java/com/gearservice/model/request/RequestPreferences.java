package com.gearservice.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.OffsetDateTime;

public class RequestPreferences {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    OffsetDateTime introducedFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    OffsetDateTime introducedTo;
    String customerName;
    String productName;
    String model;
    String serialNumber;
    String representativeName;
    String secretary;
    String engineer;
    Boolean warrantyStatus;
    Boolean readyStatus;
    Boolean returnedToClientStatus;
    Boolean paidStatus;

    public OffsetDateTime getIntroducedFrom() {return introducedFrom;}
    public void setIntroducedFrom(OffsetDateTime introducedFrom) {this.introducedFrom = introducedFrom;}
    public OffsetDateTime getIntroducedTo() {return introducedTo;}
    public void setIntroducedTo(OffsetDateTime introducedTo) {this.introducedTo = introducedTo;}
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
}
