package com.gearservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import com.gearservice.model.samples.SampleData;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

/**
 * Class Cheque is model Entity, that consists all main data and bidirectional relationship with affiliated classes
 * for storing in database.
 * It has few date-field: introduced, guarantee, ready and issued — that handled with special serializer and json pattern.
 * It has few bidirectional one-to-many relationship: kits, diagnostics and notes.
 *
 * @version 1.0
 * @author Dmitry
 * @since 04.09.2015
 */
@Entity
public class Cheque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private OffsetDateTime introduced;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime guarantee;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime ready;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime issued;

    private boolean withoutRepair;
    private boolean actNG;
    private boolean actVO;
    private boolean paid;

    @OneToMany(mappedBy = "kitOwner", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JsonManagedReference
    private Set<Kit> kits;

    @OneToMany(mappedBy = "diagnosticOwner", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JsonManagedReference
    private Set<Diagnostic> diagnostics;

    @OneToMany(mappedBy = "noteOwner", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JsonManagedReference
    private Set<Note> notes;

    @OneToMany(mappedBy = "paymentOwner", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval=true)
    @JsonManagedReference
    private Set<Payment> payments;

    /**
     * Method withRandomData handle current Cheque object filling it with random data and return this edited object
     * @return this Cheque object after editing
     */
    public Cheque withRandomData() {
        this.setNameOfCustomer(SampleData.getRandomName());
        this.setNameOfProduct(SampleData.getRandomProduct());
        this.setRepairPeriod(SampleData.getRepairPeriod());
        this.setIntroduced(SampleData.getRandomDate());
        this.setModel(SampleData.getRandomModel());
        this.setSerialNumber(SampleData.getRandomSerialNumber());
        this.setMalfunction(SampleData.getRandomMalfunction());
        this.setSpecialNotes(SampleData.getRandomSpecialNotes());
        this.setPurchaserName(SampleData.getRandomName());
        this.setAddress(SampleData.getRandomAddress());
        this.setPhone(SampleData.getRandomPhone());
        this.setEmail(SampleData.getRandomEmail());
        this.setInspectorName(SampleData.getRandomName());
        this.setMasterName(SampleData.getRandomName());

        this.setDiagnostics(SampleData.getSetConsistFrom(o -> new Diagnostic().withRandomData().withOwner(this)));
        this.setNotes(SampleData.getSetConsistFrom(o -> new Note().withRandomData().withOwner(this)));
        this.setKits(SampleData.getSetConsistFrom(o -> new Kit().withRandomData().withOwner(this)));

        this.setGuarantee(SampleData.getRandomDate());
        this.setReady(SampleData.getRandomDate());
        this.setIssued(SampleData.getRandomDate());

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
    public void setIntroduced(OffsetDateTime introduced) {this.introduced = introduced;}
    public OffsetDateTime getIntroduced() {return introduced;}
    public void setGuarantee(OffsetDateTime guarantee) {this.guarantee = guarantee;}
    public OffsetDateTime getGuarantee() {return guarantee;}
    public void setReady(OffsetDateTime ready) {this.ready = ready;}
    public OffsetDateTime getReady() {return ready;}
    public void setIssued(OffsetDateTime issued) {this.issued = issued;}
    public OffsetDateTime getIssued() {return issued;}
    public boolean isPaid() {return paid;}
    public void setPaid(boolean paid) {this.paid = paid;}
    public int getPrediction() {return prediction;}
    public void setPrediction(int prediction) {this.prediction = prediction;}
    public void setKits(Set<Kit> kits) {this.kits = kits;}
    public Set<Kit> getKits() {return kits;}
    public void setDiagnostics(Set<Diagnostic> diagnostics) {this.diagnostics = diagnostics;}
    public Set<Diagnostic> getDiagnostics() {return diagnostics;}
    public void setNotes(Set<Note> notes) {this.notes = notes;}
    public Set<Note> getNotes() {return notes;}
    public Set<Payment> getPayments() {return payments;}
    public void setPayments(Set<Payment> payments) {this.payments = payments;}

}
