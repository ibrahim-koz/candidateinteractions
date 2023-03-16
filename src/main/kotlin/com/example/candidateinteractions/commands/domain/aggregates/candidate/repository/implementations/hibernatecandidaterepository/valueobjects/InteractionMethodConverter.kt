package com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations.hibernatecandidaterepository.valueobjects

import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.InteractionMethod
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class InteractionMethodConverter : AttributeConverter<InteractionMethod, String> {

    override fun convertToDatabaseColumn(attribute: InteractionMethod): String {
        return when (attribute) {
            is InteractionMethod.PhoneInteraction -> "PhoneInteraction"
            is InteractionMethod.EmailInteraction -> "EmailInteraction"
        }
    }

    override fun convertToEntityAttribute(dbData: String): InteractionMethod {
        return when (dbData) {
            "PhoneInteraction" -> InteractionMethod.PhoneInteraction
            "EmailInteraction" -> InteractionMethod.EmailInteraction
            else -> throw IllegalArgumentException("Unknown value: $dbData")
        }
    }
}