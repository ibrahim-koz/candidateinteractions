package com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations.hibernatecandidaterepository.valueobjects

import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.CandidateId
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class CandidateIdConverter : AttributeConverter<CandidateId, String> {

    override fun convertToDatabaseColumn(attribute: CandidateId): String {
        return attribute.value
    }

    override fun convertToEntityAttribute(dbData: String): CandidateId {
        return CandidateId(dbData)
    }
}