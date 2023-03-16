package com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations.hibernatecandidaterepository.entities

import com.example.candidateinteractions.commands.domain.aggregates.candidate.entities.InteractionRecord
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations.hibernatecandidaterepository.valueobjects.*
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.*
import com.example.candidateinteractions.queries.InteractionRecordRepresentation
import javax.persistence.*

@Entity
@Table(name = "interaction_records")
class InteractionRecordEntity(

    @Id
    @Column(name = "interaction_record_id")
    @Convert(converter = InteractionRecordIdConverter::class)
    val interactionRecordId: InteractionRecordId,

    @Column(name = "candidate_id")
    @Convert(converter = CandidateIdConverter::class)
    val candidateId: CandidateId,

    @Column(name = "interaction_method")
    @Convert(converter = InteractionMethodConverter::class)
    var interactionMethod: InteractionMethod,

    @Column(name = "phone_number_of_interviewer")
    @Convert(converter = PhoneNumberConverter::class)
    var phoneNumberOfInterviewer: PhoneNumber?,

    @Column(name = "email_of_interviewer")
    @Convert(converter = EmailConverter::class)
    var emailOfInterviewer: Email?
) {
    fun toDomain(): InteractionRecord {
        return InteractionRecord(
            interactionRecordId,
            candidateId,
            interactionMethod,
            phoneNumberOfInterviewer,
            emailOfInterviewer
        )
    }

    companion object {
        fun fromDomain(interactionRecord: InteractionRecord): InteractionRecordEntity {
            return InteractionRecordEntity(
                interactionRecordId = interactionRecord.interactionRecordId,
                candidateId = interactionRecord.candidateId,
                interactionMethod = interactionRecord.interactionMethod,
                phoneNumberOfInterviewer = interactionRecord.phoneNumberOfInterviewer,
                emailOfInterviewer = interactionRecord.emailOfInterviewer
            )
        }
    }

    fun toInteractionRecordRepresentation(): InteractionRecordRepresentation {
        return InteractionRecordRepresentation(
            scalarInteractionRecordId = interactionRecordId.value,
            scalarCandidateId = candidateId.value,
            scalarInteractionMethod = interactionMethod.toString(),
            scalarPhoneNumberOfInterviewer = phoneNumberOfInterviewer?.value,
            scalarMailOfInterviewer = emailOfInterviewer?.value
        )
    }

}


