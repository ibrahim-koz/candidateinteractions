package com.example.candidateinteractions.commands.components.addinteractionrecord

import com.example.candidateinteractions.queries.InteractionRecordRepresentation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

data class AddInteractionRecordRequest(
    val candidateId: String,
    val interactionMethod: String,
    val phoneNumberOfInterviewer: String? = null,
    val emailOfInterviewer: String? = null
)


@RestController
class AddInteractionRecordController(private val addInteractionRecordHandler: AddInteractionRecordHandler) {
    @PostMapping("candidate/{id}/interaction-record")
    fun handle(
        @PathVariable id: Int,
        @RequestBody request: AddInteractionRecordRequest
    ): ResponseEntity<InteractionRecordRepresentation> {
        val interactionRecordId = addInteractionRecordHandler.handle(
            scalarCandidateId = request.candidateId,
            scalarInteractionMethod = request.interactionMethod,
            scalarPhoneNumberOfInterviewer = request.phoneNumberOfInterviewer,
            scalarEmailOfInterviewer = request.emailOfInterviewer
        )

        val location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(interactionRecordId)
            .toUri()

        return ResponseEntity.created(location).build()
    }
}