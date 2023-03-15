package com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects

data class CandidateStatus(val value: String)

fun String.toCandidateStatus() = CandidateStatus(this)