package com.example.candidateinteractions.commands.components.addinteractionrecord

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

data class AddInteractionRecordRequest(
    val interactionMethod: String,
    val phoneNumberOfInterviewer: String? = null,
    val emailOfInterviewer: String? = null
)

data class AddInteractionRecordResponse(
    @JsonProperty("location")
    val location: URI
)

@RestController
class AddInteractionRecordController(private val addInteractionRecordHandler: AddInteractionRecordHandler) {
    @PostMapping("candidate/{id}/interaction-record")
    fun handle(
        @PathVariable id: String,
        @RequestBody request: AddInteractionRecordRequest
    ): AddInteractionRecordResponse {
        val interactionRecordId = addInteractionRecordHandler.handle(
            AddInteractionRecordParams(
                scalarCandidateId = id,
                scalarInteractionMethod = request.interactionMethod,
                scalarPhoneNumberOfInterviewer = request.phoneNumberOfInterviewer,
                scalarEmailOfInterviewer = request.emailOfInterviewer
            )
        )

        val location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(interactionRecordId)
            .toUri()

        return AddInteractionRecordResponse(location)
    }
}