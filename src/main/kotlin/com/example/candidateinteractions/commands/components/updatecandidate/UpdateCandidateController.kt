package com.example.candidateinteractions.commands.components.updatecandidate;

import com.example.candidateinteractions.queries.CandidateRepresentation
import com.example.candidateinteractions.queries.QueryService
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController;

data class UpdateContactInformationRequestDTO(
    val email: String,
    val phoneNumber: String
)

data class UpdateCandidateRequest(
    val name: String?,
    val surname: String?,
    val contactInformation: UpdateContactInformationRequestDTO,
    val candidateStatus: String?
)


@RestController
class UpdateCandidateController(
    private val updateCandidateHandler: UpdateCandidateHandler,
    private val queryService: QueryService
) {
    @PatchMapping("candidate/{id}")
    fun handle(
        @PathVariable id: String,
        @RequestBody request: UpdateCandidateRequest
    ): CandidateRepresentation {
        updateCandidateHandler.handle(
            UpdateCandidateParams(
                scalarCandidateId = id,
                scalarName = request.name,
                scalarSurname = request.surname,
                scalarContactInformation = UpdateContactInformationDTO(
                    scalarEmail = request.contactInformation.email,
                    scalarPhoneNumber = request.contactInformation.phoneNumber
                ),
                scalarCandidateStatus = request.candidateStatus
            )
        )

        return queryService.getCandidate(scalarId = id)
    }
}
