package com.onlinevoting.model;
import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
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

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Date of birth is required")
    private Date dob;

    @NotNull(message = "Aadhar number is required")
    @Digits(integer = 12, fraction = 0, message = "Aadhar number must be 12 digits")
    private Long  aadharNumber;
   
    public UserDetail() {
    }

    public UserDetail(String firstName, String lastName, String middleName, String emailId, String phoneNo, String address,
                      Date dob, Long aadharNumber) {
        super();
        if (firstName == null || firstName.isBlank()) throw new IllegalArgumentException("First name is required");
        if (lastName == null || lastName.isBlank()) throw new IllegalArgumentException("Last name is required");
        if (emailId == null || emailId.isBlank()) throw new IllegalArgumentException("Email is required");
        if (!emailId.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) throw new IllegalArgumentException("Invalid email format");
        if (phoneNo != null && !phoneNo.matches("\\d{10}")) throw new IllegalArgumentException("Phone number must be 10 digits");
        if (address == null || address.isBlank()) throw new IllegalArgumentException("Address is required");
        if (dob == null) throw new IllegalArgumentException("Date of birth is required");
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

    public String getAddress() {
        return address;
    }

    public Long getAadharNumber() {
        return aadharNumber;
    }

    public String fullName() {
     return String.join(" ", firstName, lastName);
    }
}