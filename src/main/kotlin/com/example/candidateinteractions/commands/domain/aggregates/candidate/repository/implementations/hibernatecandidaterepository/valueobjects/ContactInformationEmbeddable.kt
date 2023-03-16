package com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations.hibernatecandidaterepository.valueobjects

import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.Email
import com.example.candidateinteractions.commands.domain.aggregates.candidate.valueobjects.PhoneNumber
import javax.persistence.Column
import javax.persistence.Convert
import javax.persistence.Embeddable


@Embeddable
class ContactInformationEmbeddable(
    @Column(name = "email")
    @Convert(converter = EmailConverter::class)
    var email: Email?,

    @Column(name = "phone_number")
    @Convert(converter = PhoneNumberConverter::class)
    var phoneNumber: PhoneNumber?
)