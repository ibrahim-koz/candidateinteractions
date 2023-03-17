package com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects

class InvalidCandidateIdException(val value: String) : IllegalArgumentException()

data class CandidateId(val value: String) {
    init {
        requireValidCandidateId(value)
    }

    private fun requireValidCandidateId(candidateId: String) {
        if (!isValidCandidateId(candidateId)) {
            throw InvalidCandidateIdException(candidateId)
        }
    }

    private fun isValidCandidateId(candidateId: String): Boolean {
        val candidateIdPattern = "^[a-zA-Z0-9-]+$".toRegex()
        return candidateIdPattern.matches(candidateId)
    }

    override fun toString(): String {
        return value
    }
}

fun String.toCandidateId() = CandidateId(this)