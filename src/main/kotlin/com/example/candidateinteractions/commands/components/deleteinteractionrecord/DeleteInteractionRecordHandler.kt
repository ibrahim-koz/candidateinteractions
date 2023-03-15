package com.example.candidateinteractions.commands.components.deleteinteractionrecord

import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.CandidateRepository
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.toCandidateId
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.toInteractionRecordId
import org.springframework.stereotype.Service

@Service
class DeleteInteractionRecordHandler(private val candidateRepository: CandidateRepository) {
    fun handle(
        scalarCandidateId: String,
        scalarInteractionRecordId: String
    ) {
        val candidate = candidateRepository.getById(scalarCandidateId.toCandidateId())
        candidate.idempotentRemoveInteractionRecord(scalarInteractionRecordId.toInteractionRecordId())
        candidateRepository.persistChangesOf(candidate)
    }
}