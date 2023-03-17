package com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects

class InvalidNameException(val value: String) : IllegalArgumentException()

data class Name(val value: String) {
    init {
        requireValidName(value)
    }

    private fun requireValidName(name: String) {
        if (!isValidName(name)) {
            throw InvalidNameException(name)
        }
    }

    private fun isValidName(name: String): Boolean {
        val namePattern = "^[\\p{L} .'-]+$".toRegex()
        return namePattern.matches(name)
    }
}

fun String.toName() = Name(this)