package com.example.candidateinteractions.queries

import com.example.candidateinteractions.commands.domain.aggregates.candidate.InteractionRecordNotFound
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.CandidateNotFound
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations.hibernatecandidaterepository.entities.CandidateEntity
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations.hibernatecandidaterepository.entities.InteractionRecordEntity
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.CandidateId
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.toInteractionRecordId
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.toScalarValue
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import jakarta.persistence.EntityManager


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

@Service
class QueryService @Autowired constructor(private val entityManager: EntityManager) {
    fun getCandidate(scalarId: String): CandidateRepresentation {
        val candidateEntity =
            entityManager.find(CandidateEntity::class.java, CandidateId(scalarId)) ?: throw CandidateNotFound()

        return candidateEntity.toCandidateRepresentation()
    }

    fun getCandidates(): List<CandidateRepresentation> {
        val candidateEntities =
            entityManager.createQuery("FROM CandidateEntity", CandidateEntity::class.java).resultList

        return candidateEntities.map { it.toCandidateRepresentation() }
    }

    fun getCandidateInteractionRecords(candidateId: String): List<InteractionRecordRepresentation> {
        val candidateEntity =
            entityManager.find(CandidateEntity::class.java, CandidateId(candidateId)) ?: throw CandidateNotFound()

        return candidateEntity.previousInteractionRecords.map { it.toInteractionRecordRepresentation() }
    }

    fun getInteractionRecords(): List<InteractionRecordRepresentation> {
        val interactionRecordEntities =
            entityManager.createQuery("FROM InteractionRecordEntity", InteractionRecordEntity::class.java).resultList

        return interactionRecordEntities.map { it.toInteractionRecordRepresentation() }
    }

    fun getInteractionRecord(scalarId: String): InteractionRecordRepresentation {
        val interactionRecordEntity =
            entityManager.find(InteractionRecordEntity::class.java, scalarId.toInteractionRecordId())
                ?: throw InteractionRecordNotFound()

        val interactionRecord = interactionRecordEntity.toDomain()

        return InteractionRecordRepresentation(
            scalarInteractionRecordId = interactionRecord.interactionRecordId.value,
            scalarCandidateId = interactionRecord.candidateId.value,
            scalarInteractionMethod = interactionRecord.interactionMethod.toScalarValue(),
            scalarPhoneNumberOfInterviewer = interactionRecord.phoneNumberOfInterviewer?.value,
            scalarMailOfInterviewer = interactionRecord.emailOfInterviewer?.value
        )
    }
}

