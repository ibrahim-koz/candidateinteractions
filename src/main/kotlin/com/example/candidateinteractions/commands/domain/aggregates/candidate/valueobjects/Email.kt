package com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects

data class Email(val value: String)

fun String.toEmail() = Email(this)