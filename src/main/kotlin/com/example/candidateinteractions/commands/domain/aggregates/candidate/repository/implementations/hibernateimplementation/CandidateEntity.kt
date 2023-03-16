package com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations.hibernateimplementation

import com.example.candidateinteractions.commands.domain.aggregates.candidate.Candidate
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.*
import jakarta.persistence.*

@Entity
@Table(name = "candidates")
class CandidateEntity(
    @Id
    @Column(name = "candidate_id", nullable = false, unique = true)
    val candidateId: String = "",

    @Column(name = "name", nullable = false)
    var name: String = "",

    @Column(name = "surname", nullable = false)
    var surname: String = "",

    @Embedded
    var contactInformation: ContactInformationEntity = ContactInformationEntity(),

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: CandidateStatus = CandidateStatus.SOURCED,

    @OneToMany(mappedBy = "candidate", cascade = [CascadeType.ALL], orphanRemoval = true)
    val previousInteractionRecords: MutableSet<InteractionRecordEntity> = mutableSetOf()
) {
    fun toDomain(): Candidate {
        val candidate = Candidate(
            candidateId = CandidateId(candidateId),
            name = Name(name),
            surname = Surname(surname),
            contactInformation = contactInformation.toDomain(),
            status = status
        )

        previousInteractionRecords.map { it.toDomain() }.forEach {
            candidate.addInteractionRecord(
                interactionRecordId = it.interactionRecordId,
                interactionMethod = it.interactionMethod,
                phoneNumberOfInterviewer = it.phoneNumberOfInterviewer,
                emailOfInterviewer = it.emailOfInterviewer
            )
        }

        return candidate
    }

    companion object {
        fun fromDomain(candidate: Candidate): CandidateEntity {
            val interactionRecordEntities =
                candidate.previousInteractionRecords.values.map { InteractionRecordEntity.fromDomain(it) }
                    .toMutableSet()
            return CandidateEntity(
                candidateId = candidate.candidateId.value,
                name = candidate.name.value,
                surname = candidate.surname.value,
                contactInformation = ContactInformationEntity.fromDomain(candidate.contactInformation),
                status = candidate.status,
                previousInteractionRecords = interactionRecordEntities
            ).apply {
                interactionRecordEntities.forEach { it.candidate = this }
            }
        }
    }
}


@Embeddable
class ContactInformationEntity(
    @Column(name = "email")
    var email: String = "",

    @Column(name = "phone_number")
    var phoneNumber: String = ""
) {
    fun toDomain(): ContactInformation {
        return ContactInformation(
            email = email.toEmail(),
            phoneNumber = phoneNumber.toPhoneNumber()
        )
    }

    companion object {
        fun fromDomain(contactInformation: ContactInformation): ContactInformationEntity {
            return ContactInformationEntity(
                email = contactInformation.email.value,
                phoneNumber = contactInformation.phoneNumber.value
            )
        }
    }
}
