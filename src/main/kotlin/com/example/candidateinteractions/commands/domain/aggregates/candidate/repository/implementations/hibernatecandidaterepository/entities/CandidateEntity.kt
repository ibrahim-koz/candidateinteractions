package com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations.hibernatecandidaterepository.entities

import com.example.candidateinteractions.commands.domain.aggregates.candidate.Candidate
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations.hibernatecandidaterepository.valueobjects.*
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.*
import javax.persistence.*


@Entity
@Table(name = "candidates")
class CandidateEntity(

    @Id
    @Column(name = "candidate_id")
    @Convert(converter = CandidateIdConverter::class)
    val candidateId: CandidateId,

    @Column(name = "name")
    @Convert(converter = NameConverter::class)
    var name: Name,

    @Column(name = "surname")
    @Convert(converter = SurnameConverter::class)
    var surname: Surname,

    @Embedded
    var contactInformation: ContactInformationEmbeddable,

    @Column(name = "status")
    @Convert(converter = CandidateStatusConverter::class)
    var status: CandidateStatus,

    @OneToMany(mappedBy = "candidate", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    val previousInteractionRecords: MutableSet<InteractionRecordEntity> = mutableSetOf()
) {
    fun toDomain(): Candidate {
        val candidate = Candidate(
            candidateId = candidateId,
            name = name,
            surname = surname,
            contactInformation = ContactInformation(
                email = contactInformation.email,
                phoneNumber = contactInformation.phoneNumber
            ),
            status = status
        )

        previousInteractionRecords.forEach {
            candidate.addInteractionRecord(
                it.interactionRecordId,
                it.interactionMethod,
                it.phoneNumberOfInterviewer,
                it.emailOfInterviewer
            )
        }

        return candidate
    }

    fun updateFromDomain(candidate: Candidate) {
        name = candidate.name
        surname = candidate.surname
        contactInformation = ContactInformationEmbeddable(
            email = candidate.contactInformation.email,
            phoneNumber = candidate.contactInformation.phoneNumber
        )
        status = candidate.status

        // Update the previousInteractionRecords
        val updatedInteractionRecordIds = candidate.previousInteractionRecords.keys.map { it }.toSet()
        val currentInteractionRecordIds = previousInteractionRecords.map { it.interactionRecordId }.toSet()

        // Add new InteractionRecords
        val newInteractionRecordIds = updatedInteractionRecordIds - currentInteractionRecordIds
        for (newInteractionRecordId in newInteractionRecordIds) {
            val interactionRecord =
                candidate.previousInteractionRecords[newInteractionRecordId]
            if (interactionRecord != null) {
                val interactionRecordEntity = InteractionRecordEntity(
                    interactionRecordId = interactionRecord.interactionRecordId,
                    candidateId = candidateId,
                    interactionMethod = interactionRecord.interactionMethod,
                    phoneNumberOfInterviewer = interactionRecord.phoneNumberOfInterviewer,
                    emailOfInterviewer = interactionRecord.emailOfInterviewer
                )
                previousInteractionRecords.add(interactionRecordEntity)
            }
        }

        // Update existing InteractionRecords
        val existingInteractionRecordIds = currentInteractionRecordIds.intersect(updatedInteractionRecordIds)
        for (existingInteractionRecordId in existingInteractionRecordIds) {
            val interactionRecord =
                candidate.previousInteractionRecords[existingInteractionRecordId]
            if (interactionRecord != null) {
                val interactionRecordEntity =
                    previousInteractionRecords.find { it.interactionRecordId == existingInteractionRecordId }
                if (interactionRecordEntity != null) {
                    interactionRecordEntity.interactionMethod = interactionRecord.interactionMethod
                    interactionRecordEntity.phoneNumberOfInterviewer = interactionRecord.phoneNumberOfInterviewer
                    interactionRecordEntity.emailOfInterviewer = interactionRecord.emailOfInterviewer
                }
            }
        }

        // Remove deleted InteractionRecords
        val removedInteractionRecordIds = currentInteractionRecordIds - updatedInteractionRecordIds
        previousInteractionRecords.removeIf { it.interactionRecordId in removedInteractionRecordIds }
    }

    companion object {
        fun fromDomain(candidate: Candidate): CandidateEntity {
            return CandidateEntity(
                candidateId = candidate.candidateId,
                name = candidate.name,
                surname = candidate.surname,
                contactInformation = ContactInformationEmbeddable(
                    email = candidate.contactInformation.email,
                    phoneNumber = candidate.contactInformation.phoneNumber
                ),
                status = candidate.status
            )
        }
    }
}
