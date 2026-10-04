package com.example.voting.controller;

import com.example.voting.blockchain.Blockchain;
import com.example.voting.entity.Candidate;
import com.example.voting.entity.Voter;
import com.example.voting.repository.CandidateRepository;
import com.example.voting.repository.VoterRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vote")
@CrossOrigin
public class VotingController {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private VoterRepository voterRepository;

    private Blockchain blockchain = new Blockchain();

    // ✅ CAST VOTE
    @PostMapping("/cast")
    public String castVote(
            @RequestParam int candidateId,
            HttpSession session
    ) {

        // 1️⃣ Check login
        String aadhaarNo = (String) session.getAttribute("aadhaarNo");

        if (aadhaarNo == null) {
            return "Please login first!";
        }

        // 2️⃣ Check voter exists
        Optional<Voter> optionalVoter =
                voterRepository.findByAadhaarNo(aadhaarNo);

        if (optionalVoter.isEmpty()) {
            return "Voter not found!";
        }

        Voter voter = optionalVoter.get();

        // 3️⃣ Check already voted
        if (voter.isHasVoted()) {
            return "You have already voted!";
        }

        // 4️⃣ Check candidate exists
        Optional<Candidate> optionalCandidate =
                candidateRepository.findById(candidateId);

        if (optionalCandidate.isEmpty()) {
            return "Candidate not found!";
        }

        Candidate candidate = optionalCandidate.get();

        // 5️⃣ Increase vote count
        candidate.setVoteCount(candidate.getVoteCount() + 1);
        candidateRepository.save(candidate);

        // 6️⃣ Mark voter as voted
        voter.setHasVoted(true);
        voterRepository.save(voter);

        // 7️⃣ Add to blockchain
        blockchain.addBlock(
                "Voter " + aadhaarNo + " voted for " + candidate.getName()
        );

        return "Vote successfully casted!";
    }

    // ✅ RESULTS
    @GetMapping("/results")
    public List<Candidate> getResults() {
        return candidateRepository.findAll();
    }

    // ✅ BLOCKCHAIN VIEW
    @GetMapping("/blockchain")
    public Object getBlockchain() {
        return blockchain.getChain();
    }
}