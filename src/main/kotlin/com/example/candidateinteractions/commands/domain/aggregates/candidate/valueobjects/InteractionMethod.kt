package com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects

data class InteractionMethod(val value: String)

fun String.toInteractionMethod() = InteractionMethod(this)