package com.example.candidateinteractions.commands.components.createcandidate

import com.example.candidateinteractions.queries.CandidateRepresentation
import com.example.candidateinteractions.queries.QueryService
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

data class CreateCandidateRequest(
    val name: String,
    val surname: String,
    val contactInformation: CreateContactInformationDTO,
    val candidateStatus: String
)

data class CreateCandidateResponse(
    @JsonProperty("location")
    val location: URI
)

@RestController
class CreateCandidateController(
    private val createCandidateHandler: CreateCandidateHandler,
    private val queryService: QueryService
) {
    @PostMapping("candidate")
    fun handle(
        @RequestBody request: CreateCandidateRequest
    ): CandidateRepresentation {
        val candidateId = createCandidateHandler.handle(
            CreateCandidateParams(
                scalarName = request.name,
                scalarSurname = request.surname,
                createContactInformationDTO = request.contactInformation,
                scalarCandidateStatus = request.candidateStatus
            )
        )

        return queryService.getCandidate(candidateId)
    }
}