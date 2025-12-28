package com.onlinevoting.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "election")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Election extends AuditDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "election_id")
    private Long electionId;

    @NotBlank(message = "Election name is mandatory")
    @Column(name = "election_name", nullable = false, length = 255)
    private String electionName;

    @NotNull(message = "Election date is mandatory")
    @Column(name = "election_date", nullable = false)
    private LocalDate electionDate;

    @NotNull(message = "Result date is mandatory")
    @Column(name = "result_date")
    private LocalDate resultDate;

    @NotNull(message = "Result date is mandatory")
    @Column(name = "form_end_date")
    private LocalDate formEndDate;

    @NotNull(message = "Country is mandatory")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;

    @NotNull(message = "State is mandatory")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id", referencedColumnName = "id")
    private State state;

    @NotNull(message = "City is mandatory")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @NotNull(message = "Officer is mandatory")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "officer_id", referencedColumnName = "id")
    private UserDetail officer;

    @NotBlank(message = "Status is mandatory")
    @Column(name = "status", nullable = false, length = 50)
    private String status;
}
