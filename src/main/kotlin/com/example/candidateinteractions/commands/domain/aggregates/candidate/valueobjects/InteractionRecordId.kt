package com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects

data class InteractionRecordId(val value: String)

fun String.toInteractionRecordId() = InteractionRecordId(this)