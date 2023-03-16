package com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects

sealed class InteractionMethod {
    object PhoneInteraction : InteractionMethod()
    object EmailInteraction : InteractionMethod()
}


fun String.toInteractionMethod() =
    when (this) {
        "PhoneInteraction" -> InteractionMethod.PhoneInteraction
        "EmailInteraction" -> InteractionMethod.EmailInteraction
        else -> throw IllegalArgumentException()
    }


fun InteractionMethod.toScalarValue(): String {
    return when (this) {
        is InteractionMethod.PhoneInteraction -> "PhoneInteraction"
        is InteractionMethod.EmailInteraction -> "EmailInteraction"
    }
}