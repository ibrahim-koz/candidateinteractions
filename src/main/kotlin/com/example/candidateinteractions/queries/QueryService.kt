package com.example.candidateinteractions.queries

import com.example.candidateinteractions.commands.domain.aggregates.candidate.InteractionRecordNotFound
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.CandidateNotFound
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations.hibernateimplementation.CandidateEntity
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations.hibernateimplementation.CandidateEntityRepository
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations.hibernateimplementation.InteractionRecordEntity
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.toCandidateId
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.toInteractionRecordId
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

data class SingleCandidateRepresentation(
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
    val candidateStatus: String,
    @JsonProperty("interactionRecords")
    val interactionRecords: List<InteractionRecordRepresentation>
)

data class CandidateListRepresentation(
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


fun CandidateEntity.toCandidateListRepresentation(): CandidateListRepresentation {
    return CandidateListRepresentation(
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


interface InteractionRecordEntityRepository : JpaRepository<InteractionRecordEntity, String>

fun CandidateEntity.singleCandidateRepresentation(): SingleCandidateRepresentation {
    val interactionRecords = this.previousInteractionRecords.map { it.toInteractionRecordRepresentation() }
    return SingleCandidateRepresentation(
        candidateId = this.candidateId,
        name = this.name,
        surname = this.surname,
        contactInformationRepresentation = ContactInformationRepresentation(
            email = this.contactInformation.email,
            phoneNumber = this.contactInformation.phoneNumber
        ),
        candidateStatus = this.status.name,
        interactionRecords = interactionRecords
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
    fun getCandidate(scalarId: String): SingleCandidateRepresentation {
        val candidateEntity =
            candidateEntityRepository.findById(scalarId).orElseThrow { CandidateNotFound(scalarId.toCandidateId()) }

        return candidateEntity.singleCandidateRepresentation()
    }

    fun getCandidates(): List<CandidateListRepresentation> {
        val candidateEntities = candidateEntityRepository.findAll()

        return candidateEntities.map { it.toCandidateListRepresentation() }
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
            .orElseThrow { InteractionRecordNotFound(scalarId.toInteractionRecordId()) }

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
