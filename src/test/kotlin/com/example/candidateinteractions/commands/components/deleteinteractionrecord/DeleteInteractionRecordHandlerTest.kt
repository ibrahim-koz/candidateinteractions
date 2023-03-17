import com.example.candidateinteractions.commands.components.deleteinteractionrecord.DeleteInteractionRecordController
import com.example.candidateinteractions.commands.components.deleteinteractionrecord.DeleteInteractionRecordHandler
import com.example.candidateinteractions.commands.components.deleteinteractionrecord.DeleteInteractionRecordParams
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

class DeleteInteractionRecordHandlerTest {

    private lateinit var deleteInteractionRecordHandler: DeleteInteractionRecordHandler
    private lateinit var controller: DeleteInteractionRecordController

    @BeforeEach
    fun setUp() {
        deleteInteractionRecordHandler = mockk(relaxed = true)
        controller = DeleteInteractionRecordController(deleteInteractionRecordHandler)
    }

    @Test
    fun `delete interaction record successfully`() {
        val candidateId = "1"
        val interactionRecordId = "1"

        controller.handle(candidateId, interactionRecordId)

        verify { deleteInteractionRecordHandler.handle(
            DeleteInteractionRecordParams(
                scalarCandidateId = candidateId,
                scalarInteractionRecordId = interactionRecordId
            )
        ) }
    }

    @Test
    fun `handle exception when deleting interaction record`() {
        val candidateId = "1"
        val interactionRecordId = "1"
        val exceptionValue = "Candidate not found: ${candidateId.toCandidateId()}"

        every {
            deleteInteractionRecordHandler.handle(
                DeleteInteractionRecordParams(
                    scalarCandidateId = candidateId,
                    scalarInteractionRecordId = interactionRecordId
                )
            )
        } throws CandidateNotFound(candidateId.toCandidateId())

        val webRequest = mockk<HttpServletRequest>()
        val result = controller.handleAllExceptions(
            CandidateNotFound(candidateId.toCandidateId()),
            ServletWebRequest(webRequest)
        )

        assertEquals(HttpStatus.NOT_FOUND, result.statusCode)

        val errorResponse = result.body as DeleteInteractionRecordController.ErrorResponse
        assert(errorResponse.message.contains(exceptionValue))
    }

}
