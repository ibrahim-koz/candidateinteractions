package com.example.candidateinteractions.queries

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.stereotype.Service


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

@Service
class QueryService {
    fun getCandidate(scalarId: String) =
        CandidateRepresentation(
            scalarName = "ibrahim",
            scalarSurname = "koz",
            contactInformationRepresentation = ContactInformationRepresentation(
                scalarEmail = "ibrahimkoz@outlook.com",
                scalarPhoneNumber = "+905054536131"
            ),
            scalarCandidateStatus = "sourced"
        )


    fun getCandidates() = listOf(
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


    fun getCandidateInteractionRecords(
        scalarId: String
    ) = listOf(
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


    fun getInteractionRecord(
        scalarId: String
    ): InteractionRecordRepresentation =
        InteractionRecordRepresentation(
            scalarCandidateId = "candidateId",
            scalarInteractionMethod = "PhoneInteraction",
            scalarPhoneNumberOfInterviewer = "+905054536131"
        )

    fun getInteractionRecords() = listOf(
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