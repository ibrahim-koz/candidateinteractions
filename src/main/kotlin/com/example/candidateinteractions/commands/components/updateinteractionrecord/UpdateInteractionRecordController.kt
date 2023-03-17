package com.example.candidateinteractions.commands.components.updateinteractionrecord

import com.example.candidateinteractions.commands.domain.aggregates.candidate.InteractionRecordNotFound
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.CandidateNotFound
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.*
import com.example.candidateinteractions.queries.InteractionRecordRepresentation
import com.example.candidateinteractions.queries.QueryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.WebRequest
import java.time.LocalDateTime

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

            is InvalidInteractionMethodException -> {
                statusCode = HttpStatus.BAD_REQUEST
                errorMessage = "Invalid interaction method: ${ex.value}"
            }

            is InvalidPhoneNumberException -> {
                statusCode = HttpStatus.BAD_REQUEST
                errorMessage = "Invalid phone number format: ${ex.value}"
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
        val errorDetails = ErrorResponse(errorMessage, LocalDateTime.now())
        return ResponseEntity(errorDetails, statusCode)
    }
}
