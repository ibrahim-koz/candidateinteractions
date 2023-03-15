package com.example.candidateinteractions.commands.components.deletecandidate

import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.CandidateRepository
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.toCandidateId
import org.springframework.stereotype.Service


@Service
class DeleteCandidateHandler(
    private val candidateRepository: CandidateRepository
) {
    fun handle(scalarCandidateId: String) =
        candidateRepository.idempotentRemove(scalarCandidateId.toCandidateId())
}