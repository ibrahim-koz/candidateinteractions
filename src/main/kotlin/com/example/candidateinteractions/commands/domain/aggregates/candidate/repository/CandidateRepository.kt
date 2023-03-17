package com.example.candidateinteractions.commands.domain.aggregates.candidate.repository

import com.example.candidateinteractions.commands.domain.aggregates.candidate.Candidate
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.CandidateId
import java.lang.Exception

class CandidateNotFound(val value: CandidateId) : Exception()

interface CandidateRepository {
    fun getById(candidateId: CandidateId): Candidate
    fun addNewCandidate(candidate: Candidate)
    fun idempotentRemove(candidateId: CandidateId)
    fun persistChangesOf(candidate: Candidate)
}