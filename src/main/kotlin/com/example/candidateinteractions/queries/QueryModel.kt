package com.example.candidateinteractions.queries

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

data class InteractionRecordRepresentation(
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("interactionRecordId")
    val scalarInteractionRecordId: String? = null,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("candidateId")
    val scalarCandidateId: String? = null,
    @JsonProperty("interactionMethod")
    val scalarInteractionMethod: String,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("phoneNumberOfInterviewer")
    val scalarPhoneNumberOfInterviewer: String? = null,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("emailOfInterviewer")
    var scalarMailOfInterviewer: String? = null
)


data class ContactInformationRepresentation(
    @JsonProperty("email")
    val scalarEmail: String,
    @JsonProperty("phoneNumber")
    val scalarPhoneNumber: String
)

data class CandidateRepresentation(
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("candidateId")
    val scalarCandidateId: String? = null,
    @JsonProperty("name")
    val scalarName: String,
    @JsonProperty("surname")
    val scalarSurname: String,
    @JsonProperty("contactInformation")
    val contactInformationRepresentation: ContactInformationRepresentation,
    @JsonProperty("candidateStatus")
    val scalarCandidateStatus: String
)

@RestController
class QueryModel {
    @GetMapping("candidate/{id}")
    fun getCandidate(
        @PathVariable id: String
    ): CandidateRepresentation {
        return CandidateRepresentation(
            scalarName = "ibrahim",
            scalarSurname = "koz",
            contactInformationRepresentation = ContactInformationRepresentation(
                scalarEmail = "ibrahimkoz@outlook.com",
                scalarPhoneNumber = "+905054536131"
            ),
            scalarCandidateStatus = "sourced"
        )
    }

    @GetMapping("candidate")
    fun getCandidates(
    ): List<CandidateRepresentation> {
        return listOf(
            CandidateRepresentation(
                scalarCandidateId = "candidateId",
                scalarName = "ibrahim",
                scalarSurname = "koz",
                contactInformationRepresentation = ContactInformationRepresentation(
                    scalarEmail = "ibrahimkoz@outlook.com",
                    scalarPhoneNumber = "+905054536131"
                ),
                scalarCandidateStatus = "sourced"
            ),
            CandidateRepresentation(
                scalarCandidateId = "candidateId2",
                scalarName = "ibrahim",
                scalarSurname = "koz",
                contactInformationRepresentation = ContactInformationRepresentation(
                    scalarEmail = "ibrahimkoz@outlook.com",
                    scalarPhoneNumber = "+905054536131"
                ),
                scalarCandidateStatus = "sourced"
            ),
            CandidateRepresentation(
                scalarCandidateId = "candidateId3",
                scalarName = "ibrahim",
                scalarSurname = "koz",
                contactInformationRepresentation = ContactInformationRepresentation(
                    scalarEmail = "ibrahimkoz@outlook.com",
                    scalarPhoneNumber = "+905054536131"
                ),
                scalarCandidateStatus = "sourced"
            )
        )
    }

    @GetMapping("candidate/{id}/interaction-record")
    fun getCandidateInteractionRecords(
        @PathVariable id: String
    ): List<InteractionRecordRepresentation> {
        return listOf(
            InteractionRecordRepresentation(
                scalarInteractionRecordId = "1",
                scalarInteractionMethod = "PhoneInteraction",
                scalarPhoneNumberOfInterviewer = "+905054536131"
            ),
            InteractionRecordRepresentation(
                scalarInteractionRecordId = "2",
                scalarInteractionMethod = "PhoneInteraction",
                scalarPhoneNumberOfInterviewer = "+905054536131"
            ),
            InteractionRecordRepresentation(
                scalarInteractionRecordId = "3",
                scalarInteractionMethod = "EmailInteraction",
                scalarMailOfInterviewer = "ibrahimkoz@outlook.com"
            )
        )
    }

    @GetMapping("interaction-record/{id}")
    fun getInteractionRecord(
        @PathVariable id: String
    ): InteractionRecordRepresentation {
        return InteractionRecordRepresentation(
            scalarCandidateId = "candidateId",
            scalarInteractionMethod = "PhoneInteraction",
            scalarPhoneNumberOfInterviewer = "+905054536131"
        )
    }

    @GetMapping("interaction-record")
    fun getInteractionRecords(
    ): List<InteractionRecordRepresentation> {
        return listOf(
            InteractionRecordRepresentation(
                scalarInteractionRecordId = "1",
                scalarCandidateId = "candidateId",
                scalarInteractionMethod = "PhoneInteraction",
                scalarPhoneNumberOfInterviewer = "+905054536131"
            ),
            InteractionRecordRepresentation(
                scalarInteractionRecordId = "2",
                scalarCandidateId = "candidateId",
                scalarInteractionMethod = "PhoneInteraction",
                scalarPhoneNumberOfInterviewer = "+905054536131"
            ),
            InteractionRecordRepresentation(
                scalarInteractionRecordId = "3",
                scalarCandidateId = "candidateId",
                scalarInteractionMethod = "EmailInteraction",
                scalarMailOfInterviewer = "ibrahimkoz@outlook.com"
            )
        )
    }
}