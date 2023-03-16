package com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations.hibernatecandidaterepository.valueobjects

import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.PhoneNumber
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class PhoneNumberConverter : AttributeConverter<PhoneNumber, String> {

    override fun convertToDatabaseColumn(attribute: PhoneNumber): String {
        return attribute.value
    }

    override fun convertToEntityAttribute(dbData: String): PhoneNumber {
        return PhoneNumber(dbData)
    }
}
