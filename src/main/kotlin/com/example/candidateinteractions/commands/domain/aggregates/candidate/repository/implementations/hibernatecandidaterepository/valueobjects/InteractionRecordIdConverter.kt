package com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations.hibernatecandidaterepository.valueobjects

import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.InteractionRecordId
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class InteractionRecordIdConverter : AttributeConverter<InteractionRecordId, String> {

    override fun convertToDatabaseColumn(attribute: InteractionRecordId): String {
        return attribute.value
    }

    override fun convertToEntityAttribute(dbData: String): InteractionRecordId {
        return InteractionRecordId(dbData)
    }
}