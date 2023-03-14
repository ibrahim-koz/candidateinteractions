package com.example.candidateinteractions.commands.domain.aggregates.candidate

import com.example.candidateinteractions.commands.domain.aggregates.candidate.entities.AbstractEntity
import com.example.candidateinteractions.commands.domain.aggregates.candidate.entities.InteractionRecord
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.*

class Candidate(
    val candidateId: CandidateId,
    var name: Name,
    var surname: Surname,
    var contactInformation: ContactInformation,
    var candidateStatus: CandidateStatus
) : AbstractEntity<CandidateId>(candidateId) {
    private val previousInteractionRecords = mutableListOf<InteractionRecord>()

    fun changeName(name: Name) {}

    fun changeSurname(surname: Surname) {}

    fun changeContactInformation(contactInformation: ContactInformation) {}

    fun addInteractionRecord(
        interactionRecordId: InteractionRecordId,
        interactionMethod: InteractionMethod,
        phoneNumberOfInterviewer: PhoneNumber? = null,
        emailOfInterviewer: Email? = null
    ) {
        previousInteractionRecords.add(
            InteractionRecord(
                interactionRecordId = interactionRecordId,
                candidateId = this.candidateId,
                interactionMethod = interactionMethod,
                phoneNumberOfInterviewer = phoneNumberOfInterviewer,
                emailOfInterviewer = emailOfInterviewer
            )
        )
    }

    fun deleteInteractionRecord(interactionRecordId: InteractionRecordId) {}

    fun updateInteractionRecord() {}
}

