package com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects

class InvalidSurnameException(val value: String) : IllegalArgumentException()

data class Surname(val value: String) {
    init {
        requireValidSurname(value)
    }

    private fun requireValidSurname(surname: String) {
        if (!isValidSurname(surname)) {
            throw InvalidSurnameException(surname)
        }
    }

    private fun isValidSurname(surname: String): Boolean {
        val surnamePattern = "^[\\p{L} .'-]+$".toRegex()
        return surnamePattern.matches(surname)
    }
}

fun String.toSurname() = Surname(this)