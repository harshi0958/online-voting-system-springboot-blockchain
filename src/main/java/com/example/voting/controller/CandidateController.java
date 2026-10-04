package com.example.voting.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.voting.entity.Candidate;
import com.example.voting.repository.CandidateRepository;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CandidateRepository candidateRepo;

    // add candidate
    @PostMapping("/add")
    public Candidate addCandidate(@RequestBody Candidate c) {
        return candidateRepo.save(c);
    }

    // get all candidates
    @GetMapping("/results")
    public List<Candidate> getAll() {
        return candidateRepo.findAll();
    }
}

