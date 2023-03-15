package com.example.candidateinteractions.commands.domain.utils

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
inline fun <T : Any> requireNotNullOrThrow(value: T?, exception: () -> Throwable): T {
    contract {
        returns() implies (value != null)
    }

    if (value == null) {
        throw exception()
    }
    return value
}

@OptIn(ExperimentalContracts::class)
fun <T : Any> requireNull(value: T?) {
    contract {
        returns() implies (value == null)
    }

    if (value != null) {
        throw IllegalArgumentException()
    }
}