package com.example.candidateinteractions.commands.components.updateinteractionrecord

import com.example.candidateinteractions.queries.InteractionRecordRepresentation
import com.example.candidateinteractions.queries.QueryService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

data class UpdateInteractionRecordRequest(
    val interactionMethod: String,
    val phoneNumberOfInterviewer: String? = null,
    val emailOfInterviewer: String? = null
)

@RestController
class UpdateInteractionRecordController(
    private val updateInteractionRecordHandler: UpdateInteractionRecordHandler,
    private val queryService: QueryService
) {
    @PutMapping("candidate/{candidateId}/interaction-record/{interactionRecordId}")
    fun handle(
        @PathVariable candidateId: String,
        @PathVariable interactionRecordId: String,
        @RequestBody request: UpdateInteractionRecordRequest
    ): InteractionRecordRepresentation {
        updateInteractionRecordHandler.handle(
            UpdateInteractionRecordParams(
                scalarCandidateId = candidateId,
                scalarInteractionRecordId = interactionRecordId,
                scalarInteractionMethod = request.interactionMethod,
                scalarPhoneNumberOfInterviewer = request.phoneNumberOfInterviewer,
                scalarEmailOfInterviewer = request.emailOfInterviewer
            )
        )
        return queryService.getInteractionRecord(scalarId = interactionRecordId)
    }
}