package com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects

class InvalidInteractionRecordIdException(val value: String) :
    IllegalArgumentException()

data class InteractionRecordId(val value: String) {
    init {
        requireValidInteractionRecordId(value)
    }

    private fun requireValidInteractionRecordId(interactionRecordId: String) {
        if (!isValidInteractionRecordId(interactionRecordId)) {
            throw InvalidInteractionRecordIdException(interactionRecordId)
        }
    }

    private fun isValidInteractionRecordId(interactionRecordId: String): Boolean {
        val interactionRecordIdPattern = "^[a-zA-Z0-9-]+$".toRegex()
        return interactionRecordIdPattern.matches(interactionRecordId)
    }
}


fun String.toInteractionRecordId() = InteractionRecordId(this)