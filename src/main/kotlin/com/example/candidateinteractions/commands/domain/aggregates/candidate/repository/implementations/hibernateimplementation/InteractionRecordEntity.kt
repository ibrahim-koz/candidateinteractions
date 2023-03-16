package com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations.hibernateimplementation

import com.example.candidateinteractions.commands.domain.aggregates.candidate.entities.InteractionRecord
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.*
import jakarta.persistence.*

@Entity
@Table(name = "interaction_records")
class InteractionRecordEntity(
    @Id
    @Column(name = "interaction_record_id", nullable = false, unique = true)
    val interactionRecordId: String = "",

    @Column(name = "candidate_id", nullable = false)
    val candidateId: String = "",

    @Enumerated(EnumType.STRING)
    @Column(name = "interaction_method", nullable = false)
    var interactionMethod: InteractionMethod = InteractionMethod.PHONE_INTERACTION,

    @Column(name = "phone_number_of_interviewer")
    var phoneNumberOfInterviewer: String? = null,

    @Column(name = "email_of_interviewer")
    var emailOfInterviewer: String? = null
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", insertable = false, updatable = false)
    lateinit var candidate: CandidateEntity

    fun toDomain(): InteractionRecord {
        return InteractionRecord(
            interactionRecordId = interactionRecordId.toInteractionRecordId(),
            candidateId = candidateId.toCandidateId(),
            interactionMethod = interactionMethod,
            phoneNumberOfInterviewer = phoneNumberOfInterviewer?.toPhoneNumber(),
            emailOfInterviewer = emailOfInterviewer?.toEmail()
        )
    }

    companion object {
        fun fromDomain(interactionRecord: InteractionRecord): InteractionRecordEntity {
            return InteractionRecordEntity(
                interactionRecordId = interactionRecord.interactionRecordId.value,
                candidateId = interactionRecord.candidateId.value,
                interactionMethod = interactionRecord.interactionMethod,
                phoneNumberOfInterviewer = interactionRecord.phoneNumberOfInterviewer?.value,
                emailOfInterviewer = interactionRecord.emailOfInterviewer?.value
            )
        }
    }
}

