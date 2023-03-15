package com.example.candidateinteractions.commands.components.updateinteractionrecord

import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.CandidateRepository
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.*
import org.springframework.stereotype.Service

data class UpdateInteractionRecordParams(
    val scalarCandidateId: String,
    val scalarInteractionRecordId: String,
    val scalarInteractionMethod: String,
    val scalarPhoneNumberOfInterviewer: String? = null,
    val scalarEmailOfInterviewer: String? = null
)

@Service
class UpdateInteractionRecordHandler(
    private val candidateRepository: CandidateRepository
) {
    fun handle(
        updateInteractionRecordParams: UpdateInteractionRecordParams
    ) {
        val candidate = candidateRepository.getById(updateInteractionRecordParams.scalarCandidateId.toCandidateId())
        updateInteractionRecordParams.run {
            candidate.updateInteractionRecord(
                interactionRecordId = scalarInteractionRecordId.toInteractionRecordId(),
                interactionMethod = scalarInteractionMethod.toInteractionMethod(),
                phoneNumberOfInterviewer = scalarPhoneNumberOfInterviewer?.toPhoneNumber(),
                emailOfInterviewer = scalarEmailOfInterviewer?.toEmail()
            )
        }
        candidateRepository.persistChangesOf(candidate)
    }
}