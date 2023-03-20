package com.example.candidateinteractions.commands.components.deleteinteractionrecord

import com.example.candidateinteractions.commands.domain.aggregates.candidate.InteractionRecordNotFound
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.CandidateNotFound
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.WebRequest
import java.time.LocalDateTime

@RestController
class DeleteInteractionRecordController(private val deleteInteractionRecordHandler: DeleteInteractionRecordHandler) {
    @DeleteMapping("candidate/{candidateId}/interaction-record/{interactionRecordId}")
    fun handle(
        @PathVariable candidateId: String,
        @PathVariable interactionRecordId: String
    ): ResponseEntity<Void> {
        deleteInteractionRecordHandler.handle(
            DeleteInteractionRecordParams(
                scalarCandidateId = candidateId,
                scalarInteractionRecordId = interactionRecordId
            )
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

            is InteractionRecordNotFound -> {
                statusCode = HttpStatus.NOT_FOUND
                errorMessage = "Interaction record not found: ${ex.value}"
            }

            is InvalidEmailException -> {
                statusCode = HttpStatus.BAD_REQUEST
                errorMessage = "Invalid email format: ${ex.value}"
            }

            is InvalidNameException -> {
                statusCode = HttpStatus.BAD_REQUEST
                errorMessage = "Invalid name format: ${ex.value}"
            }

            is InvalidPhoneNumberException -> {
                statusCode = HttpStatus.BAD_REQUEST
                errorMessage = "Invalid phone number format: ${ex.value}"
            }

            is InvalidSurnameException -> {
                statusCode = HttpStatus.BAD_REQUEST
                errorMessage = "Invalid surname format: ${ex.value}"
            }

            is InvalidCandidateIdException -> {
                statusCode = HttpStatus.BAD_REQUEST
                errorMessage = "Invalid candidate ID format: ${ex.value}"
            }

            is InvalidInteractionRecordIdException -> {
                statusCode = HttpStatus.BAD_REQUEST
                errorMessage = "Invalid interaction record ID format: ${ex.value}"
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
