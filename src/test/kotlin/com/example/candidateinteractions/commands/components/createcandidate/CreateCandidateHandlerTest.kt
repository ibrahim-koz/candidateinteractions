package com.example.candidateinteractions.commands.components.createcandidate

import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.CandidateRepository
import com.example.candidateinteractions.commands.domain.utils.IdGenerator
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class CreateCandidateHandlerTest {
    @Test
    fun `creates a new candidate`() {
        // Arrange
        val candidateRepository = mockk<CandidateRepository>(relaxed = true)

        val idGenerator = mockk<IdGenerator>()
        every { idGenerator.generateId() } returns "candidateId"

        val createCandidateHandler = CreateCandidateHandler(
            candidateRepository = candidateRepository,
            idGenerator = idGenerator
        )

        // Act
        createCandidateHandler.handle(
            scalarName = "ibrahim",
            scalarSurname = "koz",
            contactInformationDTO = ContactInformationDTO(
                scalarEmail = "ibrahimkoz@outlook.com",
                scalarPhoneNumber = "+905054536131",
            ),
            scalarCandidateStatus = "sourced"
        )

        // Assert
        verify { candidateRepository.save(any()) }

    }
}