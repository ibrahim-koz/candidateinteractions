package com.example.candidateinteractions.commands.domain.utils

import org.springframework.stereotype.Component
import java.util.UUID

@Component
class IdGenerator {
    fun generateId(): String {
        return UUID.randomUUID().toString()
    }
}
