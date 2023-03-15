package com.example.candidateinteractions.commands.components.createcandidate

import com.example.candidateinteractions.commands.domain.aggregates.candidate.Candidate
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.CandidateRepository
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.*
import com.example.candidateinteractions.commands.domain.utils.IdGenerator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.stereotype.Service

data class CreateContactInformationDTO(
    @JsonProperty("email")
    val scalarEmail: String,
    @JsonProperty("phoneNumber")
    val scalarPhoneNumber: String
)

fun CreateContactInformationDTO.toContactInformation() = ContactInformation(
    email = this.scalarEmail.toEmail(),
    phoneNumber = this.scalarPhoneNumber.toPhoneNumber()
)

@Service
class CreateCandidateHandler(
    private val candidateRepository: CandidateRepository,
    private val idGenerator: IdGenerator
) {
    fun handle(
        scalarName: String,
        scalarSurname: String,
        createContactInformationDTO: CreateContactInformationDTO,
        scalarCandidateStatus: String
    ): String {
        val id = idGenerator.generateId()
        val candidate = Candidate(
            candidateId = id.toCandidateId(),
            name = scalarName.toName(),
            surname = scalarSurname.toSurname(),
            contactInformation = createContactInformationDTO.toContactInformation(),
            candidateStatus = scalarCandidateStatus.toCandidateStatus()
        )

        candidateRepository.addNewCandidate(candidate)
        return id
    }
}