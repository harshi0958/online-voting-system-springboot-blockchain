package com.example.voting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.voting.entity.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
