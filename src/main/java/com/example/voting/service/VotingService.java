package com.example.voting.service;

import com.example.voting.entity.Voter;
import com.example.voting.repository.VoterRepository;
import com.example.voting.blockchain.Blockchain;
import com.example.voting.blockchain.Block;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VotingService {

    @Autowired
    private VoterRepository voterRepository;

    @Autowired
    private VotingSessionService votingSessionService;

    private Blockchain blockchain = new Blockchain();

    // ================= LOGIN =================
    public String login(String aadhaarNo, String mobile) {

        Optional<Voter> voterOpt =
                voterRepository.findByAadhaarNoAndMobile(aadhaarNo, mobile);

        if (voterOpt.isEmpty()) {
            return "Invalid Aadhaar or Mobile";
        }

        return "Login successful";
    }

    // ================= CAST VOTE =================
    public String castVote(String aadhaarNo, String candidateName) {

        if(!votingSessionService.isVotingActive()){
            return "Voting not started";
        }

        Optional<Voter> voterOpt = voterRepository.findById(aadhaarNo);

        if (voterOpt.isEmpty()) {
            return "Voter not found";
        }

        Voter voter = voterOpt.get();

        if (voter.isHasVoted()) {
            return "You already voted";
        }

        voter.setHasVoted(true);
        voterRepository.save(voter);

        String voteData = "Voter: " + aadhaarNo + " -> Candidate: " + candidateName;
        blockchain.addBlock(voteData);

        return "Vote cast successfully";
    }

    // ================= BLOCKCHAIN =================
    public List<Block> getBlockchain(){
        return blockchain.getChain();
    }

    public boolean isBlockchainValid(){
        return blockchain.isChainValid();
    }
}