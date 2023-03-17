# API Documentation

This API is designed to manage candidates and their interaction records.

## Table of Contents

-   [Create Candidate](#create-candidate)
-   [Delete Candidate](#delete-candidate)
-   [Update Candidate](#update-candidate)
-   [List Candidates](#list-candidates)
-   [Get Candidate](#get-candidate)
-   [Add Interaction Record](#add-interaction-record)
-   [Delete Interaction Record](#delete-interaction-record)
-   [Update Interaction Record](#update-interaction-record)
-   [Appendix: Architecture Patterns in the Candidate Interactions Project](#appendix--architecture-patterns-in-the-candidate-interactions-project)

## Create Candidate

**POST** `/candidate`

Creates a new candidate.

**Request Body**

```json
{
  "name": "John",
  "surname": "Doe",
  "contactInformation": {
    "email": "john.doe@example.com",
    "phoneNumber": "555-123-4567"
  },
  "candidateStatus": "active"
}
```

**Response**

```json
{
  "candidateId": "1",
  "name": "John",
  "surname": "Doe",
  "contactInformation": {
    "email": "john.doe@example.com",
    "phoneNumber": "555-123-4567"
  },
  "candidateStatus": "active"
}
```

## Delete Candidate

**DELETE** `/candidate/{id}`

Deletes an existing candidate.

**Path Parameters**

-   `id`: The ID of the candidate to delete.

**Response**

-   No response body. A `200 OK` status code is returned if the operation is successful.

## Update Candidate

**PUT** `/candidate/{id}`

Updates an existing candidate.

**Path Parameters**

-   `id`: The ID of the candidate to update.

**Request Body**

```json
{
  "name": "John",
  "surname": "Doe",
  "contactInformation": {
    "email": "john.doe@example.com",
    "phoneNumber": "555-123-4567"
  },
  "candidateStatus": "active"
}
```

**Response**

```json
{
  "candidateId": "1",
  "name": "John",
  "surname": "Doe",
  "contactInformation": {
    "email": "john.doe@example.com",
    "phoneNumber": "555-123-4567"
  },
  "candidateStatus": "active"
}
```

## List Candidates

**GET** `/candidates`

Retrieves a list of all candidates.

**Response**

```json
{
  "candidates": [
    {
      "candidateId": "1",
      "name": "John",
      "surname": "Doe",
      "contactInformation": {
        "email": "john.doe@example.com",
        "phoneNumber": "555-123-4567"
      },
      "candidateStatus": "active"
    }
  ]
}
```

## Get Candidate

**GET** `/candidate/{id}`

Retrieves a candidate and their interaction records.

**Path Parameters**

-   `id`: The ID of the candidate to retrieve.

**Response**

```json
{
  "candidateId": "1",
  "name": "John",
  "surname": "Doe",
  "contactInformation": {
    "email": "john.doe@example.com",
    "phoneNumber": "555-123-4567"
  },
  "candidateStatus": "active",
  "interactionRecords": [
    {
      "interactionRecordId": "1",
      "interactionMethod": "call",
      "phoneNumberOfInterviewer": "555-321-7654",
      "emailOfInterviewer": "interviewer@example.com"
    }
  ]
}
```

## Add Interaction Record

**POST** `/candidate/{candidateId}/interaction-record`

Adds an interaction record to a candidate.

**Path Parameters**

-   `candidateId`: The ID of the candidate to add an interaction record to.

**Request Body**

```json
{
  "interactionMethod": "call",
  "phoneNumberOfInterviewer": "555-321-7654",
  "emailOfInterviewer": "interviewer@example.com"
}
```

**Response**

```json
{
  "interactionRecordId": "1",
  "interactionMethod": "call",
  "phoneNumberOfInterviewer": "555-321-7654",
  "emailOfInterviewer": "interviewer@example.com"
}
```

## Delete Interaction Record

**DELETE** `/candidate/{candidateId}/interaction-record/{interactionRecordId}`

Deletes an interaction record from a candidate.

**Path Parameters**

-   `candidateId`: The ID of the candidate to remove an interaction record from.
-   `interactionRecordId`: The ID of the interaction record to delete.

**Response**

-   No response body. A `200 OK` status code is returned if the operation is successful.

## Update Interaction Record

**PUT** `/candidate/{candidateId}/interaction-record/{interactionRecordId}`

Updates an interaction record for a candidate.

**Path Parameters**

-   `candidateId`: The ID of the candidate to update an interaction record for.
-   `interactionRecordId`: The ID of the interaction record to update.

**Request Body**

```json
{
  "interactionMethod": "email",
  "phoneNumberOfInterviewer": "555-321-7654",
  "emailOfInterviewer": "interviewer@example.com"
}
```

**Response**

```json
{
  "interactionRecordId": "1",
  "interactionMethod": "email",
  "phoneNumberOfInterviewer": "555-321-7654",
  "emailOfInterviewer": "interviewer@example.com"
}
```

# How to Run

To run this project, follow these steps:

To run this project, follow these steps:

1. Make the `start.sh` file executable:
    ```bash
    chmod +x start.sh
    ```
2. Run the `start.sh` script to build the Docker image and run the container:
    ```bash
    ./start.sh
    ```
    This script will execute the following commands:
    ```bash
    docker build . -t candidateinteractions
    docker run -p 8080:8080 candidateinteractions
    ```
3. The API should now be available at `http://localhost:8080`. You can use a tool like [Postman](https://www.postman.com/) or [curl](https://curl.se/) to send requests to the API.

## Appendix: Architecture Patterns in the Candidate Interactions Project

This project incorporates several software architecture patterns, including Command Query Responsibility Segregation (CQRS), Domain-Driven Design (DDD), Hexagonal Architecture (Ports and Adapters), and a touch of London-style Test-Driven Development (TDD) to explore interfaces using a top-down approach. The project is also designed to be completely persistence layer ignorant, allowing for greater flexibility and adaptability.

### 1. CQRS

-   Separates read (query) and write (command) operations
-   Enhances scalability and maintainability
-   Facilitates eventual consistency and event sourcing

### 2. DDD

-   Focuses on modeling the problem domain
-   Centralizes domain knowledge in code
-   Improves communication between developers and domain experts

### 3. Hexagonal Architecture

-   Decouples core logic from external dependencies
-   Increases maintainability and testability
-   Allows for easier adaptation to changing dependencies
-   Enables persistence layer ignorance

### 4. London-style TDD

-   Explores interfaces using a top-down approach
-   Guides design of system components
-   Ensures consistent and well-defined interactions between components

By combining these patterns and emphasizing persistence layer ignorance, the Candidate Interactions Project achieves a robust, maintainable, and scalable system, providing a solid foundation for future development and growth. The design allows for seamless integration with various persistence layers, increasing adaptability to different storage technologies as requirements evolve.