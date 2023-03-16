package com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations.hibernatecandidaterepository.valueobjects

import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.Email
import javax.persistence.AttributeConverter
import javax.persistence.Converter


@Converter(autoApply = true)
class EmailConverter : AttributeConverter<Email, String> {

    override fun convertToDatabaseColumn(attribute: Email): String {
        return attribute.value
    }

    override fun convertToEntityAttribute(dbData: String): Email {
        return Email(dbData)
    }
}