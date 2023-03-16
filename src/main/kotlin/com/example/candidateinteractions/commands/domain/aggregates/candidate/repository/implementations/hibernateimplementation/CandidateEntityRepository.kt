package com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations.hibernateimplementation

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CandidateEntityRepository : JpaRepository<CandidateEntity, String>