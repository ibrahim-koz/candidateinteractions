package com.example.candidateinteractions.commands.components.addinteractionrecord

import com.example.candidateinteractions.commands.domain.aggregates.candidate.Candidate
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.CandidateNotFound
import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.CandidateRepository
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.*
import com.example.candidateinteractions.commands.domain.utils.IdGenerator
import io.mockk.every
import org.junit.jupiter.api.Test
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.assertThrows

class AddInteractionRecordHandlerTest {
    @Test
    fun `adds a new phone interaction record to the candidate given whose id is valid`() {
        // Arrange
        val candidate = mockk<Candidate>(relaxed = true)
        val candidateRepository = mockk<CandidateRepository>(relaxed = true)
        every { candidateRepository.getById("candidateId".toCandidateId()) } returns candidate

        val idGenerator = mockk<IdGenerator>()
        every { idGenerator.generateId() } returns "interactionRecordId"
        val addInteractionRecordHandler = AddInteractionRecordHandler(candidateRepository, idGenerator)

        // Act
        addInteractionRecordHandler.handle(
            scalarCandidateId = "candidateId",
            scalarInteractionMethod = "PhoneInteraction",
            scalarPhoneNumberOfInterviewer = "+905054536131"
        )

        // Assert
        verify { candidateRepository.getById("candidateId".toCandidateId()) }
        verify {
            candidate.addInteractionRecord(
                interactionRecordId = "interactionRecordId".toInteractionRecordId(),
                interactionMethod = "PhoneInteraction".toInteractionMethod(),
                phoneNumberOfInterviewer = "+905054536131".toPhoneNumber(),
            )
        }
        verify { candidateRepository.addNewCandidate(candidate) }
    }

    @Test
    fun `fails to add new phone interaction record to the candidate whose id isn't exist`() {
        // Arrange
        val candidateRepository = mockk<CandidateRepository>(relaxed = true)
        every { candidateRepository.getById("candidateId".toCandidateId()) } throws CandidateNotFound()

        val idGenerator = mockk<IdGenerator>()
        every { idGenerator.generateId() } returns "interactionRecordId"
        val addInteractionRecordHandler = AddInteractionRecordHandler(candidateRepository, idGenerator)

        // Act && Assert
        assertThrows<CandidateNotFound> {
            addInteractionRecordHandler.handle(
                scalarCandidateId = "candidateId",
                scalarInteractionMethod = "PhoneInteraction",
                scalarPhoneNumberOfInterviewer = "+905054536131"
            )
        }
    }
}