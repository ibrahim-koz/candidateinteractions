package com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects

data class Surname(val value: String)

fun String.toSurname() = Surname(this)