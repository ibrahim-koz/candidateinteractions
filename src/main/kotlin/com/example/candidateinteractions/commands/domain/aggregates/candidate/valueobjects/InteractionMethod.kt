package com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects

enum class InteractionMethod(val value: String) {
    PHONE_INTERACTION("PhoneInteraction"),
    EMAIL_INTERACTION("EmailInteraction");

    companion object {
        private val valueMap = values().associateBy(InteractionMethod::value)

        fun fromValue(value: String): InteractionMethod =
            valueMap[value] ?: throw IllegalArgumentException("Invalid interaction method value: $value")
    }
}

fun String.toInteractionMethod(): InteractionMethod {
    return InteractionMethod.fromValue(this)
}