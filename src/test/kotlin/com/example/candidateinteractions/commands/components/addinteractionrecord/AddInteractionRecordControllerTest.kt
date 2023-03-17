package com.example.candidateinteractions.commands.components.addinteractionrecord

import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.InvalidEmailException
import com.example.candidateinteractions.queries.InteractionRecordRepresentation
import com.example.candidateinteractions.queries.QueryService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakarta.servlet.http.HttpServletRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.web.context.request.ServletWebRequest

class AddInteractionRecordControllerTest {

    private val addInteractionRecordHandler = mockk<AddInteractionRecordHandler>()
    private val queryService = mockk<QueryService>()
    private val controller = AddInteractionRecordController(addInteractionRecordHandler, queryService)

    @Test
    fun `add interaction record successfully`() {
        val candidateId = "1"
        val interactionRecordId = "101"
        val request = AddInteractionRecordRequest(
            interactionMethod = "phone",
            phoneNumberOfInterviewer = "555-555-1234",
            emailOfInterviewer = "test@example.com"
        )

        every {
            addInteractionRecordHandler.handle(any())
        } returns interactionRecordId

        every {
            queryService.getInteractionRecord(interactionRecordId)
        } returns InteractionRecordRepresentation(
            interactionRecordId = interactionRecordId,
            interactionMethod = request.interactionMethod,
            phoneNumberOfInterviewer = request.phoneNumberOfInterviewer,
            emailOfInterviewer = request.emailOfInterviewer
        )

        val result = controller.handle(candidateId, request)

        assertEquals(request.interactionMethod, result.interactionMethod)
        assertEquals(request.phoneNumberOfInterviewer, result.phoneNumberOfInterviewer)
        assertEquals(request.emailOfInterviewer, result.emailOfInterviewer)

        verify { addInteractionRecordHandler.handle(any()) }
        verify { queryService.getInteractionRecord(interactionRecordId) }
    }

    @Test
    fun `handle exception when adding interaction record`() {
        val candidateId = "1"
        val request = AddInteractionRecordRequest(
            interactionMethod = "phone",
            phoneNumberOfInterviewer = "555-555-1234",
            emailOfInterviewer = "test@example.com"
        )

        val exceptionValue = "Invalid email format"
        every {
            addInteractionRecordHandler.handle(
                AddInteractionRecordParams(
                    scalarCandidateId = candidateId,
                    scalarInteractionMethod = request.interactionMethod,
                    scalarPhoneNumberOfInterviewer = request.phoneNumberOfInterviewer,
                    scalarEmailOfInterviewer = request.emailOfInterviewer
                )
            )
        } throws InvalidEmailException(exceptionValue)

        val webRequest = mockk<HttpServletRequest>()
        val result = controller.handleAllExceptions(
            InvalidEmailException(exceptionValue),
            ServletWebRequest(webRequest)
        )

        assertEquals(HttpStatus.BAD_REQUEST, result.statusCode)

        val errorResponse = result.body as AddInteractionRecordController.ErrorResponse
        assert(errorResponse.message.contains(exceptionValue))
    }
}
