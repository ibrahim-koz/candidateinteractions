package com.example.candidateinteractions.queries

import com.example.candidateinteractions.commands.domain.aggregates.candidate.InteractionRecordNotFound
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.CandidateNotFound
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations.hibernateimplementation.CandidateEntity
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations.hibernateimplementation.CandidateEntityRepository
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations.hibernateimplementation.InteractionRecordEntity
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.toCandidateId
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service


data class InteractionRecordRepresentation(
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("interactionRecordId")
    val interactionRecordId: String? = null,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("candidateId")
    val candidateId: String? = null,
    @JsonProperty("interactionMethod")
    val interactionMethod: String,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("phoneNumberOfInterviewer")
    val phoneNumberOfInterviewer: String? = null,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("emailOfInterviewer")
    var emailOfInterviewer: String? = null
)


data class ContactInformationRepresentation(
    @JsonProperty("email")
    val email: String,
    @JsonProperty("phoneNumber")
    val phoneNumber: String
)

data class CandidateRepresentation(
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("candidateId")
    val candidateId: String? = null,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("surname")
    val surname: String,
    @JsonProperty("contactInformation")
    val contactInformationRepresentation: ContactInformationRepresentation,
    @JsonProperty("candidateStatus")
    val candidateStatus: String
)

interface InteractionRecordEntityRepository : JpaRepository<InteractionRecordEntity, String>

fun CandidateEntity.toCandidateRepresentation(): CandidateRepresentation {
    return CandidateRepresentation(
        candidateId = this.candidateId,
        name = this.name,
        surname = this.surname,
        contactInformationRepresentation = ContactInformationRepresentation(
            email = this.contactInformation.email,
            phoneNumber = this.contactInformation.phoneNumber
        ),
        candidateStatus = this.status.name
    )
}

fun InteractionRecordEntity.toInteractionRecordRepresentation(): InteractionRecordRepresentation {
    return InteractionRecordRepresentation(
        interactionRecordId = this.interactionRecordId,
        candidateId = this.candidateId,
        interactionMethod = this.interactionMethod.name,
        phoneNumberOfInterviewer = this.phoneNumberOfInterviewer,
        emailOfInterviewer = this.emailOfInterviewer
    )
}


@Service
class QueryService @Autowired constructor(
    private val candidateEntityRepository: CandidateEntityRepository,
    private val interactionRecordEntityRepository: InteractionRecordEntityRepository
) {
    fun getCandidate(scalarId: String): CandidateRepresentation {
        val candidateEntity =
            candidateEntityRepository.findById(scalarId).orElseThrow { CandidateNotFound(scalarId.toCandidateId()) }

        return candidateEntity.toCandidateRepresentation()
    }

    fun getCandidates(): List<CandidateRepresentation> {
        val candidateEntities = candidateEntityRepository.findAll()

        return candidateEntities.map { it.toCandidateRepresentation() }
    }

    fun getCandidateInteractionRecords(candidateId: String): List<InteractionRecordRepresentation> {
        val candidateEntity = candidateEntityRepository.findById(candidateId)
            .orElseThrow { CandidateNotFound(candidateId.toCandidateId()) }

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
            interactionRecordId = interactionRecord.interactionRecordId.value,
            candidateId = interactionRecord.candidateId.value,
            interactionMethod = interactionRecord.interactionMethod.value,
            phoneNumberOfInterviewer = interactionRecord.phoneNumberOfInterviewer?.value,
            emailOfInterviewer = interactionRecord.emailOfInterviewer?.value
        )
    }
}
