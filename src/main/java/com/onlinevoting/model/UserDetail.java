package com.onlinevoting.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.sql.Date;

import com.onlinevoting.util.DateUtils;

@Entity
@Table(name = "user_detail")
public class UserDetail extends AuditDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    private String middleName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String emailId;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phoneNo;

    @OneToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @NotNull(message = "Date of birth is required")
    private Date dob;

    @NotNull(message = "Aadhar number is required")
    @Digits(integer = 12, fraction = 0, message = "Aadhar number must be 12 digits")
    private Long  aadharNumber;

    @Lob
    @Column(name = "photo", columnDefinition = "LONGBLOB")
    private byte[] photo;
    
    private String status;

    public UserDetail() {
    }

    public UserDetail(String firstName, String lastName, String middleName, String emailId, String phoneNo, Address address,
                      Date dob, Long aadharNumber, byte[] photo) {
        super();
        if (firstName == null || firstName.isBlank()) throw new IllegalArgumentException("First name is required");
        if (lastName == null || lastName.isBlank()) throw new IllegalArgumentException("Last name is required");
        if (emailId == null || emailId.isBlank()) throw new IllegalArgumentException("Email is required");
        if (!emailId.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) throw new IllegalArgumentException("Invalid email format");
        if (phoneNo != null && !phoneNo.matches("\\d{10}")) throw new IllegalArgumentException("Phone number must be 10 digits");
        if (address == null || address.getId() == null) throw new IllegalArgumentException("Address is required");
        if (dob== null || (dob !=null && !DateUtils.isDObValid(dob))) throw new IllegalArgumentException("Invalid date of birth");
        if (aadharNumber == null) throw new IllegalArgumentException("Aadhar number is required");
        if (String.valueOf(aadharNumber).length() != 12) throw new IllegalArgumentException("Aadhar number must be 12 digits");
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.emailId = emailId;
        this.phoneNo = phoneNo;
        this.address = address;
        this.dob = dob;
        this.aadharNumber = aadharNumber;
        this.photo = photo;
    }


    public void setStatus(String status) {
        this.status = status;
    }
    
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setAadharNumber(Long aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }   
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }   

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDob() {
        return dob;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public Address getAddress() {
        return address;
    }

    public Long getAadharNumber() {
        return aadharNumber;
    }

    public String fullName() {
     return String.join(" ", firstName, lastName);
    }

    public byte[] getPhoto() {
        return photo;
    }

    public String getStatus() {
        return status;
    }
}