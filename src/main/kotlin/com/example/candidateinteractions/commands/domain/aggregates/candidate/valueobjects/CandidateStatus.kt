package com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects

class InvalidCandidateStatusException(val value: String) : IllegalArgumentException()

enum class CandidateStatus(val value: String) {
    SOURCED("sourced"),
    INTERVIEWING("interviewing"),
    OFFER_SENT("offer sent"),
    HIRED("hired");

    companion object {
        private val valueMap = values().associateBy(CandidateStatus::value)

        fun fromValue(value: String): CandidateStatus =
            valueMap[value] ?: throw InvalidCandidateStatusException(value)
    }
}

fun String.toCandidateStatus(): CandidateStatus {
    return CandidateStatus.fromValue(this)
}