package com.example.candidateinteractions.commands.domain.aggregates.candidate.entities

import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.*

class InteractionRecord(
    val interactionRecordId: InteractionRecordId,
    val candidateId: CandidateId,
    val interactionMethod: InteractionMethod,
    var phoneNumberOfInterviewer: PhoneNumber?,
    var emailOfInterviewer: Email?
) :
    AbstractEntity<InteractionRecordId>(interactionRecordId)
