package com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects

data class Name(val value: String)

fun String.toName() = Name(this)