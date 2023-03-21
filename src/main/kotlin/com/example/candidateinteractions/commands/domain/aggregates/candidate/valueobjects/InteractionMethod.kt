package com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects

class InvalidInteractionMethodException(val value: String) : IllegalArgumentException()

enum class InteractionMethod(val value: String) {
    PHONE_INTERACTION("phoneInteraction"),
    EMAIL_INTERACTION("emailInteraction");

    companion object {
        private val valueMap = values().associateBy(InteractionMethod::value)

        fun fromValue(value: String): InteractionMethod =
            valueMap[value.lowercase()] ?: throw InvalidInteractionMethodException(value)
    }

    override fun toString(): String {
        return value
    }
}

fun String.toInteractionMethod(): InteractionMethod {
    return InteractionMethod.fromValue(this)
}