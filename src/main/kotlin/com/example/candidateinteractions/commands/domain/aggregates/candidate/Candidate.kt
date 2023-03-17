package com.example.candidateinteractions.commands.domain.aggregates.candidate

import com.example.candidateinteractions.commands.domain.aggregates.candidate.entities.AbstractEntity
import com.example.candidateinteractions.commands.domain.aggregates.candidate.entities.InteractionRecord
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.*
import com.example.candidateinteractions.commands.domain.utils.requireNotNullOrThrow

class InteractionRecordNotFound(val value: InteractionRecordId) : Exception()

class Candidate(
    val candidateId: CandidateId,
    var name: Name,
    var surname: Surname,
    var contactInformation: ContactInformation,
    var status: CandidateStatus
) : AbstractEntity<CandidateId>(candidateId) {
    val previousInteractionRecords = mutableMapOf<InteractionRecordId, InteractionRecord>()


    fun changeName(name: Name) {
        this.name = name
    }

    fun changeSurname(surname: Surname) {
        this.surname = surname
    }

    fun changeContactInformation(contactInformation: ContactInformation) {
        this.contactInformation = contactInformation
    }

    fun changeStatus(status: CandidateStatus) {
        this.status = status
    }

    fun addInteractionRecord(
        interactionRecordId: InteractionRecordId,
        interactionMethod: InteractionMethod,
        phoneNumberOfInterviewer: PhoneNumber? = null,
        emailOfInterviewer: Email? = null
    ) {
        previousInteractionRecords[interactionRecordId] =
            InteractionRecord(
                interactionRecordId = interactionRecordId,
                candidateId = this.candidateId,
                interactionMethod = interactionMethod,
                phoneNumberOfInterviewer = phoneNumberOfInterviewer,
                emailOfInterviewer = emailOfInterviewer
            )
    }

    fun idempotentRemoveInteractionRecord(interactionRecordId: InteractionRecordId) {
        previousInteractionRecords.remove(interactionRecordId)
    }

    fun updateInteractionRecord(
        interactionRecordId: InteractionRecordId,
        interactionMethod: InteractionMethod,
        phoneNumberOfInterviewer: PhoneNumber? = null,
        emailOfInterviewer: Email? = null
    ) {
        val interactionRecord = previousInteractionRecords[interactionRecordId]
        requireNotNullOrThrow(interactionRecord) { InteractionRecordNotFound(interactionRecordId) }

        interactionRecord.update(
            interactionMethod = interactionMethod,
            phoneNumberOfInterviewer = phoneNumberOfInterviewer,
            emailOfInterviewer = emailOfInterviewer
        )
    }
}

