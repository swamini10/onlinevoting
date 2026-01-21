package com.onlinevoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.onlinevoting.model.Voting;

public interface VotingRepository extends JpaRepository<Voting, Long> {

    @Query("SELECT u FROM Voting u WHERE u.isActive = true  AND u.election.id = :electionId")
    public java.util.List<Voting> findAllByElection(Long electionId);

    @Query("SELECT v FROM Voting v WHERE v.isActive = true AND v.voter.id = :voterId")
    public java.util.List<Voting> findByVoterId(String voterId);
}
