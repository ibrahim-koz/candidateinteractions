import com.example.candidateinteractions.commands.components.createcandidate.CreateCandidateController
import com.example.candidateinteractions.commands.components.createcandidate.CreateCandidateHandler
import com.example.candidateinteractions.commands.components.createcandidate.CreateCandidateRequest
import com.example.candidateinteractions.commands.components.createcandidate.CreateContactInformationDTO
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.InvalidEmailException
import com.example.candidateinteractions.queries.ContactInformationRepresentation
import com.example.candidateinteractions.queries.QueryService
import com.example.candidateinteractions.queries.SingleCandidateRepresentation
import io.mockk.every
import io.mockk.mockk
import jakarta.servlet.http.HttpServletRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.web.context.request.ServletWebRequest

class CreateCandidateControllerTest {

    private lateinit var createCandidateHandler: CreateCandidateHandler
    private lateinit var queryService: QueryService
    private lateinit var controller: CreateCandidateController

    @BeforeEach
    fun setUp() {
        createCandidateHandler = mockk()
        queryService = mockk()
        controller = CreateCandidateController(createCandidateHandler, queryService)
    }

    @Test
    fun `create candidate successfully`() {
        val request = CreateCandidateRequest(
            name = "John",
            surname = "Doe",
            contactInformation = CreateContactInformationDTO(
                scalarEmail = "john.doe@example.com",
                scalarPhoneNumber = "555-123-4567"
            ),
            candidateStatus = "active"
        )

        val candidateId = "1"
        val singleCandidateRepresentation = SingleCandidateRepresentation(
            candidateId = candidateId,
            name = request.name,
            surname = request.surname,
            contactInformationRepresentation = ContactInformationRepresentation(
                email = request.contactInformation.scalarEmail,
                phoneNumber = request.contactInformation.scalarPhoneNumber
            ),
            candidateStatus = request.candidateStatus,
            interactionRecords = listOf()
        )

        every {
            createCandidateHandler.handle(any())
        } returns candidateId

        every {
            queryService.getCandidate(candidateId)
        } returns singleCandidateRepresentation

        val result = controller.handle(request)

        assertEquals(HttpStatus.CREATED, result.statusCode)
        assertEquals(singleCandidateRepresentation, result.body)
    }

    @Test
    fun `handle exception when creating candidate`() {
        val request = CreateCandidateRequest(
            name = "John",
            surname = "Doe",
            contactInformation = CreateContactInformationDTO(
                scalarEmail = "john.doe@example.com",
                scalarPhoneNumber = "555-123-4567"
            ),
            candidateStatus = "active"
        )

        val exceptionValue = "Invalid email format"
        every {
            createCandidateHandler.handle(match {
                it.scalarName == request.name &&
                        it.scalarSurname == request.surname &&
                        it.createContactInformationDTO == request.contactInformation &&
                        it.scalarCandidateStatus == request.candidateStatus
            })
        } throws InvalidEmailException(exceptionValue)

        val webRequest = mockk<HttpServletRequest>()
        val result = controller.handleAllExceptions(
            InvalidEmailException(exceptionValue),
            ServletWebRequest(webRequest)
        )

        assertEquals(HttpStatus.BAD_REQUEST, result.statusCode)

        val errorResponse = result.body as CreateCandidateController.ErrorResponse
        assert(errorResponse.message.contains(exceptionValue))
    }
}
