package com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects

class InvalidPhoneNumberException(val value: String) : IllegalArgumentException()

data class PhoneNumber(val value: String) {
    init {
        requireValidPhoneNumber(value)
    }

    private fun requireValidPhoneNumber(phoneNumber: String) {
        if (!isValidPhoneNumber(phoneNumber)) {
            throw InvalidPhoneNumberException(phoneNumber)
        }
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val phoneNumberPattern = "^\\+?[1-9]\\d{1,14}$".toRegex()
        return phoneNumberPattern.matches(phoneNumber)
    }
}

fun String.toPhoneNumber() = PhoneNumber(this)