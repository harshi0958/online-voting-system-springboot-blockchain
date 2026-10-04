package com.example.voting.repository;

import java.util.Optional;

import com.example.voting.entity.Voter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoterRepository extends JpaRepository<Voter, String> {

    // Count voters who voted
    long countByHasVotedTrue();

    // Find voter by Aadhaar number
    Optional<Voter> findByAadhaarNo(String aadhaarNo);
    Optional<Voter> findByEmail(String email);
    Optional<Voter> findByAadhaarNoAndMobile(String aadhaarNo, String mobile);

}