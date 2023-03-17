package com.example.candidateinteractions.commands.components.updateinteractionrecord

import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.InvalidEmailException
import com.example.candidateinteractions.queries.InteractionRecordRepresentation
import com.example.candidateinteractions.queries.QueryService
import io.mockk.every
import io.mockk.mockk
import jakarta.servlet.http.HttpServletRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.web.context.request.ServletWebRequest

class UpdateInteractionRecordControllerTest {

    private lateinit var updateInteractionRecordHandler: UpdateInteractionRecordHandler
    private lateinit var queryService: QueryService
    private lateinit var controller: UpdateInteractionRecordController

    @BeforeEach
    fun setUp() {
        updateInteractionRecordHandler = mockk(relaxed = true)
        queryService = mockk()
        controller = UpdateInteractionRecordController(updateInteractionRecordHandler, queryService)
    }

    @Test
    fun `update interaction record successfully`() {
        val candidateId = "1"
        val interactionRecordId = "1"
        val request = UpdateInteractionRecordRequest(
            interactionMethod = "phone",
            phoneNumberOfInterviewer = "555-555-1234",
            emailOfInterviewer = "test@example.com"
        )

        val interactionRecordRepresentation = InteractionRecordRepresentation(
            candidateId = candidateId,
            interactionRecordId = interactionRecordId,
            interactionMethod = request.interactionMethod,
            phoneNumberOfInterviewer = request.phoneNumberOfInterviewer,
            emailOfInterviewer = request.emailOfInterviewer
        )

        every {
            queryService.getInteractionRecord(interactionRecordId)
        } returns interactionRecordRepresentation

        val result = controller.handle(candidateId, interactionRecordId, request)

        assertEquals(interactionRecordRepresentation, result)
    }

    @Test
    fun `handle exception when updating interaction record`() {
        val candidateId = "1"
        val interactionRecordId = "1"
        val request = UpdateInteractionRecordRequest(
            interactionMethod = "phone",
            phoneNumberOfInterviewer = "555-555-1234",
            emailOfInterviewer = "test@example.com"
        )

        val exceptionValue = "Invalid email format"
        every {
            updateInteractionRecordHandler.handle(
                UpdateInteractionRecordParams(
                    scalarCandidateId = candidateId,
                    scalarInteractionRecordId = interactionRecordId,
                    scalarInteractionMethod = request.interactionMethod,
                    scalarPhoneNumberOfInterviewer = request.phoneNumberOfInterviewer,
                )
            )
        } throws InvalidEmailException(exceptionValue)

        val webRequest = mockk<HttpServletRequest>()
        val result = controller.handleAllExceptions(
            InvalidEmailException(exceptionValue),
            ServletWebRequest(webRequest)
        )

        assertEquals(HttpStatus.BAD_REQUEST, result.statusCode)

        val errorResponse = result.body as UpdateInteractionRecordController.ErrorResponse
        assert(errorResponse.message.contains(exceptionValue))
    }
}
