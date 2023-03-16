package com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations.hibernatecandidaterepository

import com.example.candidateinteractions.commands.domain.aggregates.candidate.Candidate
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.CandidateNotFound
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.CandidateRepository
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations.hibernatecandidaterepository.entities.CandidateEntity
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.CandidateId
import org.hibernate.SessionFactory

class HibernateCandidateRepository(private val sessionFactory: SessionFactory) : CandidateRepository {
    override fun getById(candidateId: CandidateId): Candidate {
        val session = sessionFactory.openSession()
        val transaction = session.beginTransaction()

        val candidateEntity = session.find(CandidateEntity::class.java, candidateId) ?: throw CandidateNotFound()

        val candidate = candidateEntity.toDomain()

        transaction.commit()
        session.close()

        return candidate
    }

    override fun addNewCandidate(candidate: Candidate) {
        val session = sessionFactory.openSession()
        val transaction = session.beginTransaction()

        val candidateEntity = CandidateEntity.fromDomain(candidate)
        session.save(candidateEntity)

        transaction.commit()
        session.close()
    }

    override fun idempotentRemove(candidateId: CandidateId) {
        val session = sessionFactory.openSession()
        val transaction = session.beginTransaction()

        val candidateEntity = session.find(CandidateEntity::class.java, candidateId)
        candidateEntity?.let { session.delete(it) }

        transaction.commit()
        session.close()
    }

    override fun persistChangesOf(candidate: Candidate) {
        val session = sessionFactory.openSession()
        val transaction = session.beginTransaction()

        val candidateEntity = session.find(CandidateEntity::class.java, candidate.candidateId)
            ?: throw CandidateNotFound()

        candidateEntity.updateFromDomain(candidate)

        session.update(candidateEntity)

        transaction.commit()
        session.close()
    }
}