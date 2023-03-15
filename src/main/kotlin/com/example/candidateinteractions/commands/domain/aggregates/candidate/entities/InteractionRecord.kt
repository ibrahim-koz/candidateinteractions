package com.example.candidateinteractions.commands.domain.aggregates.candidate.entities

import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.*
import com.example.candidateinteractions.commands.domain.utils.requireNull

class InteractionRecord(
    private val interactionRecordId: InteractionRecordId,
    private val candidateId: CandidateId,
    private var interactionMethod: InteractionMethod,
    private var phoneNumberOfInterviewer: PhoneNumber?,
    private var emailOfInterviewer: Email?
) : AbstractEntity<InteractionRecordId>(interactionRecordId) {
    fun updateInteractionMethod(
        interactionMethod: InteractionMethod,
        phoneNumberOfInterviewer: PhoneNumber? = null,
        emailOfInterviewer: Email? = null,
    ) {
        when (interactionMethod) {
            is InteractionMethod.PhoneInteraction -> {
                requireNotNull(phoneNumberOfInterviewer)
                requireNull(emailOfInterviewer)
                this.interactionMethod = interactionMethod
                this.phoneNumberOfInterviewer = phoneNumberOfInterviewer
                this.emailOfInterviewer = null
            }

            is InteractionMethod.EmailInteraction -> {
                requireNotNull(emailOfInterviewer)
                requireNull(phoneNumberOfInterviewer)
                this.interactionMethod = interactionMethod
                this.phoneNumberOfInterviewer = null
                this.emailOfInterviewer = emailOfInterviewer
            }
        }
    }
}

