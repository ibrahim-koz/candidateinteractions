package com.example.candidateinteractions.queries

import com.example.candidateinteractions.commands.domain.aggregates.candidate.InteractionRecordNotFound
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.CandidateNotFound
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.context.request.WebRequest
import java.time.LocalDateTime


@RestController
class QueryController(private val queryService: QueryService) {
    @GetMapping("candidate/{id}")
    fun getCandidate(@PathVariable id: String) = queryService.getCandidate(scalarId = id)

    @GetMapping("candidate")
    fun getCandidates() = queryService.getCandidates()

    @GetMapping("candidate/{id}/interaction-record")
    fun getCandidateInteractionRecords(@PathVariable id: String) = queryService.getCandidateInteractionRecords(id)

    @GetMapping("interaction-record/{id}")
    fun getInteractionRecord(@PathVariable id: String) = queryService.getInteractionRecords()

    @GetMapping("interaction-record")
    fun getInteractionRecords() = queryService.getInteractionRecords()

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

            else -> {
                statusCode = HttpStatus.INTERNAL_SERVER_ERROR
                errorMessage = "An error occurred"
            }
        }

        val errorDetails = ErrorResponse(errorMessage, LocalDateTime.now())
        return ResponseEntity(errorDetails, statusCode)
    }
}