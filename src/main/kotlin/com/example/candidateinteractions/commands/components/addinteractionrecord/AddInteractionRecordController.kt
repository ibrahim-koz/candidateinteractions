package com.example.candidateinteractions.commands.components.addinteractionrecord

import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.CandidateNotFound
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.*
import com.example.candidateinteractions.queries.InteractionRecordRepresentation
import com.example.candidateinteractions.queries.QueryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.WebRequest
import java.time.LocalDateTime

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

            is InvalidInteractionMethodException -> {
                statusCode = HttpStatus.BAD_REQUEST
                errorMessage = "Invalid interaction method format: ${ex.value}"
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
