package com.example.candidateinteractions.commands.components.updatecandidate

import org.springframework.stereotype.Service

data class UpdateContactInformationDTO(
    val scalarEmail: String,
    val scalarPhoneNumber: String
)

data class UpdateCandidateParams(
    val scalarCandidateId: String,
    val scalarName: String?,
    val scalarSurname: String?,
    val scalarContactInformation: UpdateContactInformationDTO?,
    val scalarCandidateStatus: String?
)

@Service
class UpdateCandidateHandler {
    fun handle(updateCandidateParams: UpdateCandidateParams) {

    }
}