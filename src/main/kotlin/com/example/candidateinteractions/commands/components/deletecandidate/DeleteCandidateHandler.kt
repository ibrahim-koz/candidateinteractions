package com.example.candidateinteractions.commands.components.deletecandidate

import com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.CandidateRepository
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.toCandidateId
import org.springframework.stereotype.Service

data class DeleteCandidateParams(
    val scalarCandidateId: String
)

@Service
class DeleteCandidateHandler(
    private val candidateRepository: CandidateRepository
) {
    fun handle(deleteCandidateParams: DeleteCandidateParams) =
        candidateRepository.idempotentRemove(deleteCandidateParams.scalarCandidateId.toCandidateId())
}