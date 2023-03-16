package com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations.hibernatecandidaterepository.valueobjects

import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.CandidateStatus
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class CandidateStatusConverter : AttributeConverter<CandidateStatus, String> {

    override fun convertToDatabaseColumn(attribute: CandidateStatus): String {
        return attribute.value
    }

    override fun convertToEntityAttribute(dbData: String): CandidateStatus {
        return CandidateStatus(dbData)
    }
}