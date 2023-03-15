package com.example.candidateinteractions.commands.components.addinteractionrecord

import com.example.candidateinteractions.queries.InteractionRecordRepresentation
import com.example.candidateinteractions.queries.QueryService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

data class AddInteractionRecordRequest(
    val interactionMethod: String,
    val phoneNumberOfInterviewer: String? = null,
    val emailOfInterviewer: String? = null
)

@RestController
class AddInteractionRecordController(
    private val addInteractionRecordHandler: AddInteractionRecordHandler,
    private val queryService: QueryService
) {
    @PostMapping("candidate/{id}/interaction-record")
    fun handle(
        @PathVariable id: String,
        @RequestBody request: AddInteractionRecordRequest
    ): InteractionRecordRepresentation {
        val interactionRecordId = addInteractionRecordHandler.handle(
            AddInteractionRecordParams(
                scalarCandidateId = id,
                scalarInteractionMethod = request.interactionMethod,
                scalarPhoneNumberOfInterviewer = request.phoneNumberOfInterviewer,
                scalarEmailOfInterviewer = request.emailOfInterviewer
            )
        )

        return queryService.getInteractionRecord(interactionRecordId)
    }
}