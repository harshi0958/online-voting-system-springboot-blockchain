package com.example.voting.entity;

import jakarta.persistence.*;

@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long voterId;

    private Long candidateId;

    // Constructor
    public Vote() {}

    public Vote(Long voterId, Long candidateId) {
        this.voterId = voterId;
        this.candidateId = candidateId;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public Long getVoterId() {
        return voterId;
    }

    public void setVoterId(Long voterId) {
        this.voterId = voterId;
    }

    public Long getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }
}
