package com.example.candidateinteractions.commands.domain.aggregates.candidate.repository

import com.example.candidateinteractions.commands.domain.aggregates.candidate.Candidate
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.CandidateId

interface CandidateRepository {
    fun getById(toCandidateId: CandidateId): Candidate
    fun save(candidate: Candidate)
}