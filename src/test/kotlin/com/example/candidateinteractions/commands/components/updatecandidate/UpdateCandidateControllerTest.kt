package com.example.candidateinteractions.commands.components.updatecandidate

import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.InvalidEmailException
import com.example.candidateinteractions.queries.SingleCandidateRepresentation
import com.example.candidateinteractions.queries.ContactInformationRepresentation
import com.example.candidateinteractions.queries.QueryService
import io.mockk.every
import io.mockk.mockk
import jakarta.servlet.http.HttpServletRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.web.context.request.ServletWebRequest

class UpdateCandidateControllerTest {

    private lateinit var updateCandidateHandler: UpdateCandidateHandler
    private lateinit var queryService: QueryService
    private lateinit var controller: UpdateCandidateController

    @BeforeEach
    fun setUp() {
        updateCandidateHandler = mockk(relaxed = true)
        queryService = mockk()
        controller = UpdateCandidateController(updateCandidateHandler, queryService)
    }

    @Test
    fun `update candidate successfully`() {
        val candidateId = "1"
        val request = UpdateCandidateRequest(
            name = "John",
            surname = "Doe",
            contactInformation = UpdateContactInformationRequestDTO(
                email = "john.doe@example.com",
                phoneNumber = "555-123-4567"
            ),
            candidateStatus = "active"
        )

        val singleCandidateRepresentation = SingleCandidateRepresentation(
            candidateId = candidateId,
            name = request.name,
            surname = request.surname,
            contactInformationRepresentation = ContactInformationRepresentation(
                email = request.contactInformation.email,
                phoneNumber = request.contactInformation.phoneNumber
            ),
            candidateStatus = request.candidateStatus,
            interactionRecords = listOf()
        )

        every {
            queryService.getCandidate(candidateId)
        } returns singleCandidateRepresentation

        val result = controller.handle(candidateId, request)

        assertEquals(singleCandidateRepresentation, result)
    }

    @Test
    fun `handle exception when updating candidate`() {
        val candidateId = "1"
        val request = UpdateCandidateRequest(
            name = "John",
            surname = "Doe",
            contactInformation = UpdateContactInformationRequestDTO(
                email = "john.doe@example.com",
                phoneNumber = "555-123-4567"
            ),
            candidateStatus = "active"
        )

        val exceptionValue = "Invalid email format"
        every {
            updateCandidateHandler.handle(
                UpdateCandidateParams(
                    scalarCandidateId = candidateId,
                    scalarName = request.name,
                    scalarSurname = request.surname,
                    scalarContactInformation = UpdateContactInformationDTO(
                        scalarEmail = request.contactInformation.email,
                        scalarPhoneNumber = request.contactInformation.phoneNumber
                    ),
                    scalarCandidateStatus = request.candidateStatus
                )
            )
        } throws InvalidEmailException(exceptionValue)

        val webRequest = mockk<HttpServletRequest>()
        val result = controller.handleAllExceptions(
            InvalidEmailException(exceptionValue),
            ServletWebRequest(webRequest)
        )

        assertEquals(HttpStatus.BAD_REQUEST, result.statusCode)

        val errorResponse = result.body as UpdateCandidateController.ErrorResponse
        assert(errorResponse.message.contains(exceptionValue))
    }
}
