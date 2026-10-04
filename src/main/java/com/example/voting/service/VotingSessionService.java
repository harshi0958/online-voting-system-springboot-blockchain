package com.example.voting.service;

import org.springframework.stereotype.Service;

@Service
public class VotingSessionService {

    // static
    private static boolean votingActive = false;

    public void startVoting(){
        votingActive = true;
    }

    public void stopVoting(){
        votingActive = false;
    }

    public boolean isVotingActive(){
        return votingActive;
    }
}