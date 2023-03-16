package com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations.hibernatecandidaterepository.valueobjects

import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.Name
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class NameConverter : AttributeConverter<Name, String> {

    override fun convertToDatabaseColumn(attribute: Name): String {
        return attribute.value
    }

    override fun convertToEntityAttribute(dbData: String): Name {
        return Name(dbData)
    }
}