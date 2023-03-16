package com.example.candidateinteractions.queries

import com.example.candidateinteractions.commands.domain.aggregates.candidate.InteractionRecordNotFound
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.CandidateNotFound
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations.hibernateimplementation.CandidateEntity
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations.hibernateimplementation.CandidateEntityRepository
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations.hibernateimplementation.InteractionRecordEntity
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.data.jpa.repository.JpaRepository


data class InteractionRecordRepresentation(
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("interactionRecordId")
    val scalarInteractionRecordId: String? = null,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("candidateId")
    val scalarCandidateId: String? = null,
    @JsonProperty("interactionMethod")
    val scalarInteractionMethod: String,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("phoneNumberOfInterviewer")
    val scalarPhoneNumberOfInterviewer: String? = null,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("emailOfInterviewer")
    var scalarMailOfInterviewer: String? = null
)


data class ContactInformationRepresentation(
    @JsonProperty("email")
    val scalarEmail: String,
    @JsonProperty("phoneNumber")
    val scalarPhoneNumber: String
)

data class CandidateRepresentation(
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("candidateId")
    val scalarCandidateId: String? = null,
    @JsonProperty("name")
    val scalarName: String,
    @JsonProperty("surname")
    val scalarSurname: String,
    @JsonProperty("contactInformation")
    val contactInformationRepresentation: ContactInformationRepresentation,
    @JsonProperty("candidateStatus")
    val scalarCandidateStatus: String
)

interface InteractionRecordEntityRepository : JpaRepository<InteractionRecordEntity, String>

fun CandidateEntity.toCandidateRepresentation(): CandidateRepresentation {
    return CandidateRepresentation(
        scalarCandidateId = this.candidateId,
        scalarName = this.name,
        scalarSurname = this.surname,
        contactInformationRepresentation = ContactInformationRepresentation(
            scalarEmail = this.contactInformation.email,
            scalarPhoneNumber = this.contactInformation.phoneNumber
        ),
        scalarCandidateStatus = this.status.name
    )
}

fun InteractionRecordEntity.toInteractionRecordRepresentation(): InteractionRecordRepresentation {
    return InteractionRecordRepresentation(
        scalarInteractionRecordId = this.interactionRecordId,
        scalarCandidateId = this.candidateId,
        scalarInteractionMethod = this.interactionMethod.name,
        scalarPhoneNumberOfInterviewer = this.phoneNumberOfInterviewer,
        scalarMailOfInterviewer = this.emailOfInterviewer
    )
}


@Service
class QueryService @Autowired constructor(
    private val candidateEntityRepository: CandidateEntityRepository,
    private val interactionRecordEntityRepository: InteractionRecordEntityRepository
) {
    fun getCandidate(scalarId: String): CandidateRepresentation {
        val candidateEntity = candidateEntityRepository.findById(scalarId).orElseThrow { CandidateNotFound() }

        return candidateEntity.toCandidateRepresentation()
    }

    fun getCandidates(): List<CandidateRepresentation> {
        val candidateEntities = candidateEntityRepository.findAll()

        return candidateEntities.map { it.toCandidateRepresentation() }
    }

    fun getCandidateInteractionRecords(candidateId: String): List<InteractionRecordRepresentation> {
        val candidateEntity = candidateEntityRepository.findById(candidateId).orElseThrow { CandidateNotFound() }

        return candidateEntity.previousInteractionRecords.map { it.toInteractionRecordRepresentation() }
    }

    fun getInteractionRecords(): List<InteractionRecordRepresentation> {
        val interactionRecordEntities = interactionRecordEntityRepository.findAll()

        return interactionRecordEntities.map { it.toInteractionRecordRepresentation() }
    }

    fun getInteractionRecord(scalarId: String): InteractionRecordRepresentation {
        val interactionRecordEntity = interactionRecordEntityRepository.findById(scalarId)
            .orElseThrow { InteractionRecordNotFound() }

        val interactionRecord = interactionRecordEntity.toDomain()

        return InteractionRecordRepresentation(
            scalarInteractionRecordId = interactionRecord.interactionRecordId.value,
            scalarCandidateId = interactionRecord.candidateId.value,
            scalarInteractionMethod = interactionRecord.interactionMethod.value,
            scalarPhoneNumberOfInterviewer = interactionRecord.phoneNumberOfInterviewer?.value,
            scalarMailOfInterviewer = interactionRecord.emailOfInterviewer?.value
        )
    }
}
