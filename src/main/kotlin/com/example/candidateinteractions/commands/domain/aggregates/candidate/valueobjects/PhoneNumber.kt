package com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects

data class PhoneNumber(val value: String)

fun String.toPhoneNumber() = PhoneNumber(this)