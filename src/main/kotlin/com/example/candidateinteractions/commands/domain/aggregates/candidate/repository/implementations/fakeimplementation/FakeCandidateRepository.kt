package com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations.fakeimplementation

import com.example.candidateinteractions.commands.domain.aggregates.candidate.Candidate
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.CandidateNotFound
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.CandidateRepository
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.CandidateId
import org.springframework.stereotype.Repository

@Repository
class FakeCandidateRepository : CandidateRepository {
    val candidates = mutableMapOf<CandidateId, Candidate>()
    override fun getById(candidateId: CandidateId): Candidate =
        candidates[candidateId] ?: throw CandidateNotFound()


    override fun addNewCandidate(candidate: Candidate) {
        if (!candidates.containsKey(candidate.candidateId)) {
            candidates[candidate.candidateId] = candidate
        }
    }

    override fun idempotentRemove(candidateId: CandidateId) {
        candidates.remove(candidateId)
    }

    override fun persistChangesOf(candidate: Candidate) {}
}