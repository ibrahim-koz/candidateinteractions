package com.example.candidateinteractions.commands.components.deletecandidate

import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.CandidateNotFound
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import java.time.LocalDateTime

@RestController
class DeleteCandidateController(private val deleteCandidateHandler: DeleteCandidateHandler) {

    @DeleteMapping("candidate/{id}")
    fun handle(
        @PathVariable id: String
    ): ResponseEntity<Void> {
        deleteCandidateHandler.handle(
            DeleteCandidateParams(scalarCandidateId = id)
        )
        return ResponseEntity.status(HttpStatus.OK).build()
    }

    data class ErrorResponse(val message: String, val timestamp: LocalDateTime)

    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        val statusCode: HttpStatus
        val errorMessage: String

        when (ex) {
            is CandidateNotFound -> {
                statusCode = HttpStatus.NOT_FOUND
                errorMessage = "Candidate not found: ${ex.value}"
            }

            is InvalidCandidateIdException -> {
                statusCode = HttpStatus.BAD_REQUEST
                errorMessage = "Invalid candidate ID format: ${ex.value}"
            }

            is IllegalArgumentException -> {
                statusCode = HttpStatus.BAD_REQUEST
                errorMessage = ex.message ?: "Invalid input"
            }

            else -> {
                statusCode = HttpStatus.INTERNAL_SERVER_ERROR
                errorMessage = "An error occurred"
            }
        }
        val errorDetails = if (errorMessage.isNotEmpty()) {
            ErrorResponse(errorMessage, LocalDateTime.now())
        } else {
            null
        }

        return if (errorDetails != null) {
            ResponseEntity(errorDetails, statusCode)
        } else {
            ResponseEntity(statusCode)
        }
    }
}
