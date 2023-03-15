package com.example.candidateinteractions.commands.components.addinteractionrecord

import com.example.candidateinteractions.commands.domain.aggregates.candidate.Candidate
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.CandidateRepository
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.*
import com.example.candidateinteractions.commands.domain.utils.IdGenerator
import org.springframework.stereotype.Service

data class AddInteractionRecordParams(
    val scalarCandidateId: String,
    val scalarInteractionMethod: String,
    val scalarPhoneNumberOfInterviewer: String? = null,
    val scalarEmailOfInterviewer: String? = null
)

@Service
class AddInteractionRecordHandler(
    private val candidateRepository: CandidateRepository,
    private val idGenerator: IdGenerator
) {
    fun handle(
        addInteractionRecordParams: AddInteractionRecordParams
    ): String {
        val candidate: Candidate =
            candidateRepository.getById(addInteractionRecordParams.scalarCandidateId.toCandidateId())
        val id = idGenerator.generateId()

        candidate.addInteractionRecord(
            interactionRecordId = id.toInteractionRecordId(),
            interactionMethod = addInteractionRecordParams.scalarInteractionMethod.toInteractionMethod(),
            phoneNumberOfInterviewer = addInteractionRecordParams.scalarPhoneNumberOfInterviewer?.toPhoneNumber(),
            emailOfInterviewer = addInteractionRecordParams.scalarEmailOfInterviewer?.toEmail()
        )

        candidateRepository.addNewCandidate(candidate)
        return id
    }
}