package com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations.hibernateimplementation

import com.example.candidateinteractions.commands.domain.aggregates.candidate.Candidate
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.CandidateNotFound
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.CandidateRepository
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.CandidateId
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class CandidateRepositoryAdapter(
    private val candidateEntityRepository: CandidateEntityRepository,
) : CandidateRepository {

    override fun getById(candidateId: CandidateId): Candidate {
        val candidateEntity = candidateEntityRepository.findById(candidateId.value).orElse(null)
        return candidateEntity?.toDomain() ?: throw CandidateNotFound()
    }

    override fun addNewCandidate(candidate: Candidate) {
        val candidateEntity = CandidateEntity.fromDomain(candidate)
        candidateEntityRepository.save(candidateEntity)
    }

    override fun idempotentRemove(candidateId: CandidateId) {
        candidateEntityRepository.deleteById(candidateId.value)
    }

    override fun persistChangesOf(candidate: Candidate) {
        val candidateEntity = CandidateEntity.fromDomain(candidate)
        candidateEntityRepository.save(candidateEntity)
    }
}
