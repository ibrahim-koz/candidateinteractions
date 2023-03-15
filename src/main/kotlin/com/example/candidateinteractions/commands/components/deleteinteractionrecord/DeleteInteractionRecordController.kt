package com.example.candidateinteractions.commands.components.deleteinteractionrecord

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class DeleteInteractionRecordController(private val deleteInteractionRecordHandler: DeleteInteractionRecordHandler) {
    @DeleteMapping("candidate/{candidateId}/interaction-record/{interactionRecordId}")
    fun handle(
        @PathVariable candidateId: String,
        @PathVariable interactionRecordId: String
    ): ResponseEntity<Void> {
        deleteInteractionRecordHandler.handle(
            scalarCandidateId = candidateId,
            scalarInteractionRecordId = interactionRecordId
        )
        return ResponseEntity.status(HttpStatus.OK).build()
    }
}