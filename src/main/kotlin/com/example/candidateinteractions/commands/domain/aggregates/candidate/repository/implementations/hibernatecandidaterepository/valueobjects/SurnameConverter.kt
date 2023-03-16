package com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations.hibernatecandidaterepository.valueobjects

import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.Surname
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class SurnameConverter : AttributeConverter<Surname, String> {

    override fun convertToDatabaseColumn(attribute: Surname): String {
        return attribute.value
    }

    override fun convertToEntityAttribute(dbData: String): Surname {
        return Surname(dbData)
    }
}