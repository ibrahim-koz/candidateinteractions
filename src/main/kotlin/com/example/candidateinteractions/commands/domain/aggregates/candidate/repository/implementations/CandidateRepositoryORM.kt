package com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations

import com.example.candidateinteractions.commands.domain.aggregates.candidate.Candidate
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.CandidateRepository
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.CandidateId

class CandidateRepositoryORM: CandidateRepository {
    override fun getById(toCandidateId: CandidateId): Candidate {
        TODO("Not yet implemented")
    }

    override fun save(candidate: Candidate) {
        TODO("Not yet implemented")
    }
}