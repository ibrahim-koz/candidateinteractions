import com.example.candidateinteractions.commands.components.deletecandidate.DeleteCandidateController
import com.example.candidateinteractions.commands.components.deletecandidate.DeleteCandidateHandler
import com.example.candidateinteractions.commands.components.deletecandidate.DeleteCandidateParams
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.CandidateNotFound
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.toCandidateId
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakarta.servlet.http.HttpServletRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.web.context.request.ServletWebRequest

class DeleteCandidateControllerTest {

    private lateinit var deleteCandidateHandler: DeleteCandidateHandler
    private lateinit var controller: DeleteCandidateController

    @BeforeEach
    fun setUp() {
        deleteCandidateHandler = mockk(relaxed = true)
        controller = DeleteCandidateController(deleteCandidateHandler)
    }

    @Test
    fun `delete candidate successfully`() {
        val candidateId = "1"

        controller.handle(candidateId)

        verify {
            deleteCandidateHandler.handle(
                DeleteCandidateParams(candidateId)
            )
        }
    }

    @Test
    fun `handle exception when deleting candidate`() {
        val candidateId = "1"
        val exceptionValue = "Candidate not found"

        every {
            deleteCandidateHandler.handle(
                DeleteCandidateParams(candidateId)
            )
        } throws CandidateNotFound(candidateId.toCandidateId())

        val webRequest = mockk<HttpServletRequest>()
        val result = controller.handleAllExceptions(
            CandidateNotFound(candidateId.toCandidateId()),
            ServletWebRequest(webRequest)
        )

        assertEquals(HttpStatus.NOT_FOUND, result.statusCode)

        val errorResponse = result.body as DeleteCandidateController.ErrorResponse
        assert(errorResponse.message.contains(exceptionValue))
    }
}
