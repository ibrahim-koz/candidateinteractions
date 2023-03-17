##### Candidate Interactions API Documentation
The Candidate Interactions API allows you to manage candidates and their interaction records.

##### Running the Project
Ensure you have Docker installed on your system.
Clone the project repository.
In the root directory of the project, run the following commands:
bash
Copy code
docker build . -t candidateinteractions
docker run -p 8080:8080 candidateinteractions
The API should now be accessible on http://localhost:8080.

Endpoints
Create Candidate
POST /candidate

Creates a new candidate.

Request Body
```json
{
    "name": "John",
    "surname": "Doe",
    "contactInformation": {
        "scalarEmail": "john.doe@example.com",
        "scalarPhoneNumber": "555-123-4567"
    },
    "candidateStatus": "active"
}
```
Response

json
Copy code
{
"candidateId": "1",
"name": "John",
"surname": "Doe",
"contactInformationRepresentation": {
"email": "john.doe@example.com",
"phoneNumber": "555-123-4567"
},
"candidateStatus": "active"
}
Update Candidate
PUT /candidate/{id}

Updates an existing candidate.

Path Parameters

id: The ID of the candidate to update.
Request Body

json
Copy code
{
"name": "John",
"surname": "Doe",
"contactInformation": {
"email": "john.doe@example.com",
"phoneNumber": "555-123-4567"
},
"candidateStatus": "active"
}
Response

json
Copy code
{
"candidateId": "1",
"name": "John",
"surname": "Doe",
"contactInformationRepresentation": {
"email": "john.doe@example.com",
"phoneNumber": "555-123-4567"
},
"candidateStatus": "active"
}
Delete Candidate
DELETE /candidate/{id}

Deletes an existing candidate.

Path Parameters

id: The ID of the candidate to delete.
Response

No response body. A 204 No Content status code is returned if the operation is successful.
Create Interaction Record
POST /candidate/{id}/interaction

Creates a new interaction record for a candidate.

Path Parameters

id: The ID of the candidate to create the interaction record for.
Request Body

json
Copy code
{
"interactionDate": "2023-03-01T14:30:00Z",
"interactionType": "call",
"comments": "Positive interaction."
}
Response

json
Copy code
{
"interactionRecordId": "1",
"interactionDate": "2023-03-01T14:30:00Z",
"interactionType": "call",
"comments": "Positive interaction."
}
Update Interaction Record
PUT /candidate/{candidateId}/interaction/{interactionRecordId}

Updates an existing interaction record for a candidate.

Path Parameters

candidateId: The ID of the candidate the interaction record belongs to.
interactionRecordId: The ID of the interaction record to update.
Request Body

json
Copy code
{
"interactionDate": "2023-03-01T14:30:00Z",
"interactionType": "call",
"comments": "Positive interaction."
}
Response