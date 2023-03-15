package com.example.candidateinteractions.commands.components.updatecandidate

import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.CandidateRepository
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.*
import org.springframework.stereotype.Service

data class UpdateContactInformationDTO(
    val scalarEmail: String?,
    val scalarPhoneNumber: String?
)

fun UpdateContactInformationDTO.toContactInformation() =
    ContactInformation(
        email = this.scalarEmail?.toEmail(),
        phoneNumber = this.scalarEmail?.toPhoneNumber()
    )

data class UpdateCandidateParams(
    val scalarCandidateId: String,
    val scalarName: String,
    val scalarSurname: String,
    val scalarContactInformation: UpdateContactInformationDTO,
    val scalarCandidateStatus: String
)

@Service
class UpdateCandidateHandler(private val candidateRepository: CandidateRepository) {
    fun handle(updateCandidateParams: UpdateCandidateParams) {
        val candidate = candidateRepository.getById(updateCandidateParams.scalarCandidateId.toCandidateId())

        updateCandidateParams.let {
            candidate.apply {
                changeName(it.scalarName.toName())
                changeSurname(it.scalarSurname.toSurname())
                changeContactInformation(it.scalarContactInformation.toContactInformation())
                changeStatus(it.scalarCandidateStatus.toCandidateStatus())
            }
        }

        candidateRepository.persistChangesOf(candidate)
    }
}