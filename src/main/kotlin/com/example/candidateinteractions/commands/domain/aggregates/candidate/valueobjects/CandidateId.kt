package com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects

data class CandidateId(val value: String)

fun String.toCandidateId() = CandidateId(this)