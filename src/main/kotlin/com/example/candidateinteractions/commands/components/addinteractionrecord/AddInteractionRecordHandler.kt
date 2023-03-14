package com.example.candidateinteractions.commands.components.addinteractionrecord

import com.example.candidateinteractions.commands.domain.aggregates.candidate.Candidate
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.CandidateRepository
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.*
import com.example.candidateinteractions.commands.domain.utils.IdGenerator


class AddInteractionRecordHandler(
    private val candidateRepository: CandidateRepository,
    private val idGenerator: IdGenerator
) {
    fun handle(
        candidateId: String,
        interactionMethod: String,
        phoneNumberOfInterviewer: String? = null,
        emailOfInterviewer: String? = null
    ) {
        val candidate: Candidate = candidateRepository.getById(candidateId.toCandidateId())
        val id = idGenerator.generateId().toInteractionRecordId()

        candidate.addInteractionRecord(
            interactionRecordId = id,
            interactionMethod = interactionMethod.toInteractionMethod(),
            phoneNumberOfInterviewer = phoneNumberOfInterviewer?.toPhoneNumber(),
            emailOfInterviewer = emailOfInterviewer?.toEmail()
        )

        candidateRepository.save(candidate)
    }
}