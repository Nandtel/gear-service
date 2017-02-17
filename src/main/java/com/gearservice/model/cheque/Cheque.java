package com.gearservice.model.cheque;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import com.gearservice.config.converter.OffsetDateTimePersistenceConverter;
import com.gearservice.model.authorization.User;
import com.gearservice.service.SampleDataService;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Class Cheque is model Entity, that consists all main data and bidirectional relationship with affiliated classes
 * for storing in database.
 * It has few date-field: introduced, guarantee, ready and issued, that handled with special serializer and json pattern.
 * It has few bidirectional one-to-many relationship: components, diagnostics and notes.
 *
 * @version 1.1
 * @author Dmitry
 * @since 21.01.2016
 */

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@NamedEntityGraphs({
        @NamedEntityGraph(name = "cheque.full", attributeNodes = {
                @NamedAttributeNode("balance"),
                @NamedAttributeNode("engineer"),
                @NamedAttributeNode("secretary")

        }),
        @NamedEntityGraph(name = "cheque.open", attributeNodes = {
                @NamedAttributeNode("components"),
                @NamedAttributeNode("diagnostics"),
                @NamedAttributeNode("notes")
        }),
        @NamedEntityGraph(name = "cheque.preview", attributeNodes = {
                @NamedAttributeNode("balance"),
                @NamedAttributeNode("engineer"),
                @NamedAttributeNode("secretary")
        }),
        @NamedEntityGraph(name = "cheque.general", attributeNodes = {
                @NamedAttributeNode("engineer"),
                @NamedAttributeNode("secretary")
        })
})
public class Cheque {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Long version;

    @Transient @JsonProperty
    private Boolean chequeRecencyStatus = false;

    @Transient @JsonProperty
    private Boolean chequeDelayStatus = false;

    private int repairPeriod;
    private String customerName;
    private String productName;
    private String modelName;
    private String serialNumber;
    private String defect;
    private String specialNotes;
    private String representativeName;
    private String address;
    private String phoneNumber;
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    @Convert(converter = OffsetDateTimePersistenceConverter.class)
    private OffsetDateTime receiptDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    @Convert(converter = OffsetDateTimePersistenceConverter.class)
    private OffsetDateTime warrantyDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    @Convert(converter = OffsetDateTimePersistenceConverter.class)
    private OffsetDateTime readyDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    @Convert(converter = OffsetDateTimePersistenceConverter.class)
    private OffsetDateTime returnedToClientDate;

    private boolean actNotWarranty;
    private boolean actVisualInspection;

    private boolean warrantyStatus;
    private boolean readyStatus;
    private boolean returnedToClientStatus;
    private boolean withoutRepair;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cheque_id", referencedColumnName = "id")
    private Set<Component> components;

    @OneToMany(mappedBy = "cheque", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference
    private Set<Diagnostic> diagnostics = new HashSet<>();

    @OneToMany(mappedBy = "cheque", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference
    private Set<Note> notes = new HashSet<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "engineer", referencedColumnName = "username")
    private User engineer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "secretary", referencedColumnName = "username")
    private User secretary;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "cheque", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Balance balance;

    /**
     * Method withRandomData handle current Cheque object filling it with random data and return this edited object
     * @return this Cheque object after editing
     */
    public Cheque withRandomData() {
        final Random random = new Random();

        final Boolean warrantyStatus = random.nextBoolean();
        final Boolean readyStatus = random.nextBoolean();
        final Boolean returnedToClientStatus = random.nextBoolean();

        this.setWarrantyStatus(warrantyStatus);
        this.setReadyStatus(readyStatus);
        this.setReturnedToClientStatus(returnedToClientStatus);
        this.setWithoutRepair(true);
        this.setCustomerName(SampleDataService.getRandomName());
        this.setProductName(SampleDataService.getRandomProduct());
        this.setRepairPeriod(SampleDataService.getRepairPeriod());
        this.setReceiptDate(SampleDataService.getRandomDate());
        this.setModelName(SampleDataService.getRandomModel());
        this.setSerialNumber(SampleDataService.getRandomSerialNumber());
        this.setDefect(SampleDataService.getRandomMalfunction());
        this.setSpecialNotes(SampleDataService.getRandomSpecialNotes());
        this.setRepresentativeName(SampleDataService.getRandomName());
        this.setAddress(SampleDataService.getRandomAddress());
        this.setPhoneNumber(SampleDataService.getRandomPhone());
        this.setEmail(SampleDataService.getRandomEmail());

        this.setComponents(SampleDataService.getSetConsistFrom(o -> new Component().withRandomData()));

        this.setWarrantyDate(warrantyStatus ? SampleDataService.getRandomDate() : null);
        this.setReadyDate(readyStatus ? SampleDataService.getRandomDate() : null);
        this.setReturnedToClientDate(returnedToClientStatus ? SampleDataService.getRandomDate() : null);

        return this;
    }

    /**
     * Method withDiagnosticUser handle current Cheque object filling it with diagnostic's user-owner
     * and return this edited object
     * @param user is owner entity in diagnostic's bidirectional one-to-many relationship
     * @return this Diagnostic object after editing
     */
    public Cheque withDiagnosticUser(User user) {
        this.setDiagnostics(SampleDataService.getSetConsistFrom(
                o -> new Diagnostic()
                        .withRandomData()
                        .withUser(user)
                        .withCheque(this)));
        return this;
    }

    /**
     * Method withNoteUser handle current Cheque object filling it with note's user-owner
     * and return this edited object
     * @param user is owner entity in note's bidirectional one-to-many relationship
     * @return this Diagnostic object after editing
     */
    public Cheque withNoteUser(User user) {
        this.setNotes(SampleDataService.getSetConsistFrom(
                o -> new Note()
                        .withRandomData()
                        .withUser(user)));
        return this;
    }

    public Cheque withRecencyStatus(Boolean status) {
        this.setChequeRecencyStatus(status);
        return this;
    }

    public Cheque withDelayStatus(Boolean status) {
        this.setChequeDelayStatus(status);
        return this;
    }

    public void setId(Long id) {this.id = id;}
    public Long getId() {return id;}
    public Long getVersion() {return version;}
    public void setVersion(Long version) {this.version = version;}
    public void setCustomerName(String customerName) {this.customerName = customerName;}
    public String getCustomerName() {return customerName;}
    public void setRepairPeriod(int repairPeriod) {this.repairPeriod = repairPeriod;}
    public int getRepairPeriod() {return repairPeriod;}
    public void setProductName(String productName) {this.productName = productName;}
    public String getProductName() {return productName;}
    public void setModelName(String modelName) {this.modelName = modelName;}
    public String getModelName() {return modelName;}
    public void setSerialNumber(String serialNumber) {this.serialNumber = serialNumber;}
    public String getSerialNumber() {return serialNumber;}
    public void setDefect(String defect) {this.defect = defect;}
    public String getDefect() {return defect;}
    public void setSpecialNotes(String specialNotes) {this.specialNotes = specialNotes;}
    public String getSpecialNotes() {return specialNotes;}
    public void setRepresentativeName(String representativeName) {this.representativeName = representativeName;}
    public String getRepresentativeName() {return representativeName;}
    public void setAddress(String address) {this.address = address;}
    public String getAddress() {return address;}
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}
    public String getPhoneNumber() {return phoneNumber;}
    public void setEmail(String email) {this.email = email;}
    public String getEmail() {return email;}
    public void setWithoutRepair(boolean withoutRepair) {this.withoutRepair = withoutRepair;}
    public boolean isWithoutRepair() {return withoutRepair;}
    public void setActNotWarranty(boolean actNotWarranty) {this.actNotWarranty = actNotWarranty;}
    public boolean isActNotWarranty() {return actNotWarranty;}
    public void setActVisualInspection(boolean actVisualInspection) {this.actVisualInspection = actVisualInspection;}
    public boolean isActVisualInspection() {return actVisualInspection;}
    public void setReceiptDate(OffsetDateTime receiptDate) {this.receiptDate = receiptDate;}
    public OffsetDateTime getReceiptDate() {return receiptDate;}
    public void setWarrantyDate(OffsetDateTime warrantyDate) {this.warrantyDate = warrantyDate;}
    public OffsetDateTime getWarrantyDate() {return warrantyDate;}
    public void setReadyDate(OffsetDateTime readyDate) {this.readyDate = readyDate;}
    public OffsetDateTime getReadyDate() {return readyDate;}
    public void setReturnedToClientDate(OffsetDateTime returnedToClientDate) {this.returnedToClientDate = returnedToClientDate;}
    public OffsetDateTime getReturnedToClientDate() {return returnedToClientDate;}
    public boolean isWarrantyStatus() {return warrantyStatus;}
    public void setWarrantyStatus(boolean warrantyStatus) {this.warrantyStatus = warrantyStatus;}
    public boolean isReadyStatus() {return readyStatus;}
    public void setReadyStatus(boolean readyStatus) {this.readyStatus = readyStatus;}
    public boolean isReturnedToClientStatus() {return returnedToClientStatus;}
    public void setReturnedToClientStatus(boolean returnedToClientStatus) {this.returnedToClientStatus = returnedToClientStatus;}
    public void setComponents(Set<Component> components) {this.components = components;}
    public Set<Component> getComponents() {return components;}
    public void setDiagnostics(Set<Diagnostic> diagnostics) {this.diagnostics = diagnostics;}
    public Set<Diagnostic> getDiagnostics() {return diagnostics;}
    public void setNotes(Set<Note> notes) {this.notes = notes;}
    public Set<Note> getNotes() {return notes;}
    public User getEngineer() {return engineer;}
    public void setEngineer(User engineer) {this.engineer = engineer;}
    public User getSecretary() {return secretary;}
    public void setSecretary(User secretary) {this.secretary = secretary;}
    public Balance getBalance() {return balance;}
    public void setBalance(Balance balance) {this.balance = balance;}

    public Boolean getChequeRecencyStatus() {return chequeRecencyStatus;}
    public void setChequeRecencyStatus(Boolean chequeRecencyStatus) {this.chequeRecencyStatus = chequeRecencyStatus;}
    public Boolean getChequeDelayStatus() {return chequeDelayStatus;}
    public void setChequeDelayStatus(Boolean chequeDelayStatus) {this.chequeDelayStatus = chequeDelayStatus;}
}
