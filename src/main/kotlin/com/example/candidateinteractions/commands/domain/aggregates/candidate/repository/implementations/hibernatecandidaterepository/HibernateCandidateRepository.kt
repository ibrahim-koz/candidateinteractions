package com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations.hibernatecandidaterepository

import com.example.candidateinteractions.commands.domain.aggregates.candidate.Candidate
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.CandidateNotFound
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.CandidateRepository
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations.hibernatecandidaterepository.entities.CandidateEntity
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.CandidateId
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository

@Primary
@Repository
class HibernateCandidateRepository @Autowired constructor(private val entityManager: EntityManager) :
    CandidateRepository {
    override fun getById(candidateId: CandidateId): Candidate {
        val candidateEntity = entityManager.find(CandidateEntity::class.java, candidateId)
            ?: throw CandidateNotFound()

        return candidateEntity.toDomain()
    }

    override fun addNewCandidate(candidate: Candidate) {
        val candidateEntity = CandidateEntity.fromDomain(candidate)
        entityManager.persist(candidateEntity)
    }

    override fun idempotentRemove(candidateId: CandidateId) {
        val candidateEntity = entityManager.find(CandidateEntity::class.java, candidateId)
        candidateEntity?.let { entityManager.remove(it) }
    }

    override fun persistChangesOf(candidate: Candidate) {
        val candidateEntity = entityManager.find(CandidateEntity::class.java, candidate.candidateId)
            ?: throw CandidateNotFound()

        candidateEntity.updateFromDomain(candidate)

        entityManager.merge(candidateEntity)
    }
}