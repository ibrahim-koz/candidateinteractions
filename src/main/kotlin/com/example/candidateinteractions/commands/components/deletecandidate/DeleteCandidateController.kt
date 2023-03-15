package com.example.candidateinteractions.commands.components.deletecandidate

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class DeleteCandidateController(private val deleteCandidateHandler: DeleteCandidateHandler) {
    @DeleteMapping("candidate/{id}")
    fun handle(
        @PathVariable id: String
    ): ResponseEntity<Void> {
        deleteCandidateHandler.handle(id)
        return ResponseEntity.status(HttpStatus.OK).build()
    }
}