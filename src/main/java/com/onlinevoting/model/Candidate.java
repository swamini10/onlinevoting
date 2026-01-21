package com.onlinevoting.model;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "candidate_form")
@Data
public class Candidate extends AuditDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;

    @NotBlank(message = "name is mandatory")
    @Column(name = "first_name", nullable = false, length = 255)
    private String firstName;

    @Column(name = "middle_name", nullable = false, length = 255)
    private String middleName;

    @NotBlank(message = "last name is mandatory")
    @Column(name = "last_name", nullable = false, length = 255)
    private String lastName;

    @NotBlank(message = "occupation is mandatory")
    @Column(name = "occupation", nullable = false, length = 255)
    private String occupation;

    @NotNull(message="Income is mandatory")
    @Column(name = "income", nullable = false)
    private String income;

    @NotNull(message = "Party is mandatory")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id", referencedColumnName = "id")
    private Party party;

    @NotNull(message = "Election is mandatory")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "election_id", referencedColumnName = "id")
    private Election election;

    @NotBlank(message = "Status is mandatory")
    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @OneToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address wardAddress;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Column(name = "email_id", nullable = false, length = 100)
    private String emailId;

    @NotNull(message = "Date of birth is required")
    @Column(name = "dob", nullable = false)
    private Date dob;

    @Column(name = "note_for_status", length = 500)
    private String noteForStatus;

    @Column(name = "candidate_photo", length = 512)
    @NotNull(message = "candidate photo is required")
    private String candidatePhoto;
    
    @Column(name = "income_proof", length = 512)
    @NotNull(message = "Income proof is required")
    private String incomeProof;


    public String getFullName() {
        StringBuilder fullName = new StringBuilder();
        if (firstName != null && !firstName.isEmpty()) {
            fullName.append(firstName);
        }
        if (middleName != null && !middleName.isEmpty()) {
            if (fullName.length() > 0) {
                fullName.append(" ");
            }
            fullName.append(middleName);
        }
        if (lastName != null && !lastName.isEmpty()) {
            if (fullName.length() > 0) {
                fullName.append(" ");
            }
            fullName.append(lastName);
        }
        return fullName.toString();
    }
    
}
