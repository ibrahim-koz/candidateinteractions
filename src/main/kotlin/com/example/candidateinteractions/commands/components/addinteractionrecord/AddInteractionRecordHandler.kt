package com.example.candidateinteractions.commands.components.addinteractionrecord

import com.example.candidateinteractions.commands.domain.aggregates.candidate.Candidate
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.CandidateRepository
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.*
import com.example.candidateinteractions.commands.domain.utils.IdGenerator
import org.springframework.stereotype.Service

@Service
class AddInteractionRecordHandler(
    private val candidateRepository: CandidateRepository,
    private val idGenerator: IdGenerator
) {
    fun handle(
        scalarCandidateId: String,
        scalarInteractionMethod: String,
        scalarPhoneNumberOfInterviewer: String? = null,
        scalarEmailOfInterviewer: String? = null
    ): String {
        val candidate: Candidate = candidateRepository.getById(scalarCandidateId.toCandidateId())
        val id = idGenerator.generateId()

        candidate.addInteractionRecord(
            interactionRecordId = id.toInteractionRecordId(),
            interactionMethod = scalarInteractionMethod.toInteractionMethod(),
            phoneNumberOfInterviewer = scalarPhoneNumberOfInterviewer?.toPhoneNumber(),
            emailOfInterviewer = scalarEmailOfInterviewer?.toEmail()
        )

        candidateRepository.addNewCandidate(candidate)
        return id
    }
}