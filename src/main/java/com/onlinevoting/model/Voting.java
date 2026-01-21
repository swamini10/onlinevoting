package com.onlinevoting.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "voting")
@Data
public class Voting extends AuditDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "election_id", nullable = false)
    @ManyToOne
    @NotNull(message = "Election cannot be null")
    private Election election;

    @JoinColumn(name = "voter_id", nullable = false)
    @ManyToOne
    @NotNull(message = "Voter cannot be null")
    private UserDetail voter;

    @Column(name = "candidate_id", nullable = true)
    private String candidateId;

     @Column(name = "lattitude", nullable = true)
    private String lattitude;

     @Column(name = "longitude", nullable = true)
    private String longitude;

    @Column(name="client_name",nullable = true)
    private String clientName;

    @Column(name="election_start_date_time",nullable = true)
    private LocalDateTime electionStartDateTime;

    @Column(name="election_end_date_time",nullable = true)
    private LocalDateTime electionEndDateTime;
}
