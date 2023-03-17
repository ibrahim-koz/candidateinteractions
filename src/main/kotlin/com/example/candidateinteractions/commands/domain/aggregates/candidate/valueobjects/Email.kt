package com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects

class InvalidEmailException(val value: String) : IllegalArgumentException()


data class Email(val value: String) {
    init {
        requireValidEmail(value)
    }

    private fun requireValidEmail(email: String) {
        if (!isValidEmail(email)) {
            throw InvalidEmailException(value)
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern =
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z]{2,6}$".toRegex(RegexOption.IGNORE_CASE)
        return emailPattern.matches(email)
    }
}

fun String.toEmail() = Email(this)