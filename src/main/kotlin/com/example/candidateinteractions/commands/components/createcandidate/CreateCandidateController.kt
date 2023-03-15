package com.example.candidateinteractions.commands.components.createcandidate

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
class CreateCandidateController(private val createCandidateHandler: CreateCandidateHandler) {
    @PostMapping("candidate")
    fun handle(
        @RequestBody request: CreateCandidateRequest
    ): CreateCandidateResponse {
        val interactionRecordId = createCandidateHandler.handle(
            scalarName = request.name,
            scalarSurname = request.surname,
            createContactInformationDTO = request.contactInformation,
            scalarCandidateStatus = request.candidateStatus
        )

        val location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(interactionRecordId)
            .toUri()

        return CreateCandidateResponse(location)
    }
}