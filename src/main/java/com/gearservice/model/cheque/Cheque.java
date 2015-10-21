package com.gearservice.model.cheque;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import com.gearservice.config.converter.OffsetDateTimePersistenceConverter;
import com.gearservice.service.SampleDataService;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Set;

/**
 * Class Cheque is model Entity, that consists all main data and bidirectional relationship with affiliated classes
 * for storing in database.
 * It has few date-field: introduced, guarantee, ready and issued � that handled with special serializer and json pattern.
 * It has few bidirectional one-to-many relationship: kits, diagnostics and notes.
 *
 * @version 1.0
 * @author Dmitry
 * @since 04.09.2015
 */
@Entity
public class Cheque {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int repairPeriod;
    private String nameOfCustomer;
    private String nameOfProduct;
    private String model;
    private String serialNumber;
    private String malfunction;
    private String specialNotes;
    private String purchaserName;
    private String address;
    private String phone;
    private String email;
    private String inspectorName;
    private String masterName;
    private int prediction;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    @Convert(converter = OffsetDateTimePersistenceConverter.class)
    private OffsetDateTime introducedDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    @Convert(converter = OffsetDateTimePersistenceConverter.class)
    private OffsetDateTime guaranteeDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    @Convert(converter = OffsetDateTimePersistenceConverter.class)
    private OffsetDateTime readyDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    @Convert(converter = OffsetDateTimePersistenceConverter.class)
    private OffsetDateTime issuedDate;

    private boolean withoutRepair;
    private boolean actNG;
    private boolean actVO;

    private boolean hasGuaranteeStatus;
    private boolean hasReadyStatus;
    private boolean hasIssuedStatus;
    private boolean hasPaidStatus;

    @OneToMany(mappedBy = "kitOwner", cascade = CascadeType.ALL, orphanRemoval=true)
    @JsonManagedReference
    private Set<Kit> kits;

    @OneToMany(mappedBy = "diagnosticOwner", cascade = CascadeType.ALL, orphanRemoval=true)
    @JsonManagedReference
    private Set<Diagnostic> diagnostics;

    @OneToMany(mappedBy = "noteOwner", cascade = CascadeType.ALL, orphanRemoval=true)
    @JsonManagedReference
    private Set<Note> notes;

    @OneToMany(mappedBy = "paymentOwner", cascade = CascadeType.ALL, orphanRemoval=true)
    @JsonManagedReference
    private Set<Payment> payments;

    /**
     * Method withRandomData handle current Cheque object filling it with random data and return this edited object
     * @return this Cheque object after editing
     */
    public Cheque withRandomData() {
        this.setNameOfCustomer(SampleDataService.getRandomName());
        this.setNameOfProduct(SampleDataService.getRandomProduct());
        this.setRepairPeriod(SampleDataService.getRepairPeriod());
        this.setIntroducedDate(SampleDataService.getRandomDate());
        this.setModel(SampleDataService.getRandomModel());
        this.setSerialNumber(SampleDataService.getRandomSerialNumber());
        this.setMalfunction(SampleDataService.getRandomMalfunction());
        this.setSpecialNotes(SampleDataService.getRandomSpecialNotes());
        this.setPurchaserName(SampleDataService.getRandomName());
        this.setAddress(SampleDataService.getRandomAddress());
        this.setPhone(SampleDataService.getRandomPhone());
        this.setEmail(SampleDataService.getRandomEmail());
        this.setInspectorName(SampleDataService.getRandomName());
        this.setMasterName(SampleDataService.getRandomName());

        this.setDiagnostics(SampleDataService.getSetConsistFrom(o -> new Diagnostic().withRandomData().withOwner(this)));
        this.setNotes(SampleDataService.getSetConsistFrom(o -> new Note().withRandomData().withOwner(this)));
        this.setKits(SampleDataService.getSetConsistFrom(o -> new Kit().withRandomData().withOwner(this)));

        this.setGuaranteeDate(SampleDataService.getRandomDate());
        this.setReadyDate(SampleDataService.getRandomDate());
        this.setIssuedDate(SampleDataService.getRandomDate());

        return this;
    }

    public void setId(Long id) {this.id = id;}
    public Long getId() {return id;}
    public void setNameOfCustomer(String nameOfCustomer) {this.nameOfCustomer = nameOfCustomer;}
    public String getNameOfCustomer() {return nameOfCustomer;}
    public void setRepairPeriod(int repairPeriod) {this.repairPeriod = repairPeriod;}
    public int getRepairPeriod() {return repairPeriod;}
    public void setNameOfProduct(String nameOfProduct) {this.nameOfProduct = nameOfProduct;}
    public String getNameOfProduct() {return nameOfProduct;}
    public void setModel(String model) {this.model = model;}
    public String getModel() {return model;}
    public void setSerialNumber(String serialNumber) {this.serialNumber = serialNumber;}
    public String getSerialNumber() {return serialNumber;}
    public void setMalfunction(String malfunction) {this.malfunction = malfunction;}
    public String getMalfunction() {return malfunction;}
    public void setSpecialNotes(String specialNotes) {this.specialNotes = specialNotes;}
    public String getSpecialNotes() {return specialNotes;}
    public void setPurchaserName(String purchaserName) {this.purchaserName = purchaserName;}
    public String getPurchaserName() {return purchaserName;}
    public void setAddress(String address) {this.address = address;}
    public String getAddress() {return address;}
    public void setPhone(String phone) {this.phone = phone;}
    public String getPhone() {return phone;}
    public void setEmail(String email) {this.email = email;}
    public String getEmail() {return email;}
    public void setInspectorName(String inspectorName) {this.inspectorName = inspectorName;}
    public String getInspectorName() {return inspectorName;}
    public void setMasterName(String masterName) {this.masterName = masterName;}
    public String getMasterName() {return masterName;}
    public void setWithoutRepair(boolean withoutRepair) {this.withoutRepair = withoutRepair;}
    public boolean isWithoutRepair() {return withoutRepair;}
    public void setActNG(boolean actNG) {this.actNG = actNG;}
    public boolean isActNG() {return actNG;}
    public void setActVO(boolean actVO) {this.actVO = actVO;}
    public boolean isActVO() {return actVO;}
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
    public int getPrediction() {return prediction;}
    public void setPrediction(int prediction) {this.prediction = prediction;}
    public boolean isHasGuaranteeStatus() {return hasGuaranteeStatus;}
    public void setHasGuaranteeStatus(boolean hasGuaranteeStatus) {this.hasGuaranteeStatus = hasGuaranteeStatus;}
    public boolean isHasReadyStatus() {return hasReadyStatus;}
    public void setHasReadyStatus(boolean hasReadyStatus) {this.hasReadyStatus = hasReadyStatus;}
    public boolean isHasIssuedStatus() {return hasIssuedStatus;}
    public void setHasIssuedStatus(boolean hasIssuedStatus) {this.hasIssuedStatus = hasIssuedStatus;}
    public void setKits(Set<Kit> kits) {this.kits = kits;}
    public Set<Kit> getKits() {return kits;}
    public void setDiagnostics(Set<Diagnostic> diagnostics) {this.diagnostics = diagnostics;}
    public Set<Diagnostic> getDiagnostics() {return diagnostics;}
    public void setNotes(Set<Note> notes) {this.notes = notes;}
    public Set<Note> getNotes() {return notes;}
    public Set<Payment> getPayments() {return payments;}
    public void setPayments(Set<Payment> payments) {this.payments = payments;}

    @Override
    public String toString() {
        return "Cheque{" +
                "id=" + id +
                ", repairPeriod=" + repairPeriod +
                ", nameOfCustomer='" + nameOfCustomer + '\'' +
                ", nameOfProduct='" + nameOfProduct + '\'' +
                ", model='" + model + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", malfunction='" + malfunction + '\'' +
                ", specialNotes='" + specialNotes + '\'' +
                ", purchaserName='" + purchaserName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", inspectorName='" + inspectorName + '\'' +
                ", masterName='" + masterName + '\'' +
                ", prediction=" + prediction +
                ", introducedDate=" + introducedDate +
                ", guaranteeDate=" + guaranteeDate +
                ", readyDate=" + readyDate +
                ", issuedDate=" + issuedDate +
                ", withoutRepair=" + withoutRepair +
                ", actNG=" + actNG +
                ", actVO=" + actVO +
                ", hasGuaranteeStatus=" + hasGuaranteeStatus +
                ", hasReadyStatus=" + hasReadyStatus +
                ", hasIssuedStatus=" + hasIssuedStatus +
                ", hasPaidStatus=" + hasPaidStatus +
                ", kits=" + kits +
                ", diagnostics=" + diagnostics +
                ", notes=" + notes +
                ", payments=" + payments +
                '}';
    }
}
