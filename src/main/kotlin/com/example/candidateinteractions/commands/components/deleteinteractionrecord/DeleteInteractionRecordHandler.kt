package com.example.candidateinteractions.commands.components.deleteinteractionrecord

import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.CandidateRepository
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.toCandidateId
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.toInteractionRecordId
import org.springframework.stereotype.Service

data class DeleteInteractionRecordParams(
    val scalarCandidateId: String,
    val scalarInteractionRecordId: String
)

@Service
class DeleteInteractionRecordHandler(private val candidateRepository: CandidateRepository) {
    fun handle(
        deleteInteractionRecordParams: DeleteInteractionRecordParams
    ) {
        val candidate = candidateRepository.getById(deleteInteractionRecordParams.scalarCandidateId.toCandidateId())
        candidate.idempotentRemoveInteractionRecord(deleteInteractionRecordParams.scalarInteractionRecordId.toInteractionRecordId())
        candidateRepository.persistChangesOf(candidate)
    }
}