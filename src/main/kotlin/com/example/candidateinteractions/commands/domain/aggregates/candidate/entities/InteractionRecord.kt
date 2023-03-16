package com.example.candidateinteractions.commands.domain.aggregates.candidate.entities

import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.*

class InteractionRecord(
    val interactionRecordId: InteractionRecordId,
    val candidateId: CandidateId,
    var interactionMethod: InteractionMethod,
    var phoneNumberOfInterviewer: PhoneNumber?,
    var emailOfInterviewer: Email?
) : AbstractEntity<InteractionRecordId>(interactionRecordId) {
    fun update(
        interactionMethod: InteractionMethod? = null,
        phoneNumberOfInterviewer: PhoneNumber? = null,
        emailOfInterviewer: Email? = null,
    ) {
        if (interactionMethod != null) {
            when (interactionMethod) {
                InteractionMethod.PHONE_INTERACTION -> {
                    requireNotNull(phoneNumberOfInterviewer)
                    require(emailOfInterviewer == null)
                    this.interactionMethod = interactionMethod
                    this.phoneNumberOfInterviewer = phoneNumberOfInterviewer
                    this.emailOfInterviewer = null
                }

                InteractionMethod.EMAIL_INTERACTION -> {
                    requireNotNull(emailOfInterviewer)
                    require(phoneNumberOfInterviewer == null)
                    this.interactionMethod = interactionMethod
                    this.phoneNumberOfInterviewer = null
                    this.emailOfInterviewer = emailOfInterviewer
                }
            }
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
