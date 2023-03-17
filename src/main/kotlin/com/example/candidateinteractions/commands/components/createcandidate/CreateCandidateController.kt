package com.example.candidateinteractions.commands.components.createcandidate

import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.CandidateNotFound
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.*
import com.example.candidateinteractions.queries.CandidateRepresentation
import com.example.candidateinteractions.queries.QueryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.WebRequest
import java.time.LocalDateTime

data class CreateCandidateRequest(
    val name: String,
    val surname: String,
    val contactInformation: CreateContactInformationDTO,
    val candidateStatus: String
)

@RestController
class CreateCandidateController(
    private val createCandidateHandler: CreateCandidateHandler,
    private val queryService: QueryService
) {
    @PostMapping("candidate")
    fun handle(
        @RequestBody request: CreateCandidateRequest
    ): ResponseEntity<CandidateRepresentation> {
        val candidateId = createCandidateHandler.handle(
            CreateCandidateParams(
                scalarName = request.name,
                scalarSurname = request.surname,
                createContactInformationDTO = request.contactInformation,
                scalarCandidateStatus = request.candidateStatus
            )
        )

        val candidateRepresentation = queryService.getCandidate(candidateId)
        return ResponseEntity(candidateRepresentation, HttpStatus.CREATED)
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

            is InvalidCandidateStatusException -> {
                statusCode = HttpStatus.BAD_REQUEST
                errorMessage = "Invalid candidate status format: ${ex.value}"
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
