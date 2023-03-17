package com.example.candidateinteractions.commands.domain.aggregates.candidate.entities

import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.*

class InteractionRecord(
    val interactionRecordId: InteractionRecordId,
    val candidateId: CandidateId,
    interactionMethod: InteractionMethod,
    phoneNumberOfInterviewer: PhoneNumber?,
    emailOfInterviewer: Email?
) : AbstractEntity<InteractionRecordId>(interactionRecordId) {

    lateinit var interactionMethod: InteractionMethod
    var phoneNumberOfInterviewer: PhoneNumber? = null
    var emailOfInterviewer: Email? = null

    init {
        validateAndUpdateInteractionDetails(interactionMethod, phoneNumberOfInterviewer, emailOfInterviewer)
    }

    private fun validateAndUpdateInteractionDetails(
        interactionMethod: InteractionMethod,
        phoneNumberOfInterviewer: PhoneNumber?,
        emailOfInterviewer: Email?
    ) {
        when (interactionMethod) {
            InteractionMethod.PHONE_INTERACTION -> {
                requireNotNull(phoneNumberOfInterviewer)
                require(emailOfInterviewer == null)
            }

            InteractionMethod.EMAIL_INTERACTION -> {
                requireNotNull(emailOfInterviewer)
                require(phoneNumberOfInterviewer == null)
            }
        }
        this.interactionMethod = interactionMethod
        this.phoneNumberOfInterviewer = phoneNumberOfInterviewer
        this.emailOfInterviewer = emailOfInterviewer
    }

    fun update(
        interactionMethod: InteractionMethod? = null,
        phoneNumberOfInterviewer: PhoneNumber? = null,
        emailOfInterviewer: Email? = null,
    ) {
        if (interactionMethod != null) {
            validateAndUpdateInteractionDetails(interactionMethod, phoneNumberOfInterviewer, emailOfInterviewer)
        } else {
            if (phoneNumberOfInterviewer != null && this.interactionMethod == InteractionMethod.PHONE_INTERACTION) {
                this.phoneNumberOfInterviewer = phoneNumberOfInterviewer
            }

            if (emailOfInterviewer != null && this.interactionMethod == InteractionMethod.EMAIL_INTERACTION) {
                this.emailOfInterviewer = emailOfInterviewer
            }
        }
    }
}
