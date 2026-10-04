package com.example.voting.entity;

import jakarta.persistence.*;

@Entity
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String partyName;   // ✅ ADDED

    private int voteCount = 0;  // default 0

    // ================= GETTERS & SETTERS =================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPartyName() {     // ✅ ADDED
        return partyName;
    }

    public void setPartyName(String partyName) {   // ✅ ADDED
        this.partyName = partyName;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}