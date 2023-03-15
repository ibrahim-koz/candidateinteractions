package com.example.candidateinteractions.queries

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController


@RestController
class QueryController(private val queryService: QueryService) {
    @GetMapping("candidate/{id}")
    fun getCandidate(@PathVariable id: String) = queryService.getCandidate(scalarId = id)

    @GetMapping("candidate")
    fun getCandidates() = queryService.getCandidates()

    @GetMapping("candidate/{id}/interaction-record")
    fun getCandidateInteractionRecords(@PathVariable id: String) = queryService.getCandidateInteractionRecords(id)

    @GetMapping("interaction-record/{id}")
    fun getInteractionRecord(@PathVariable id: String) = queryService.getInteractionRecords()

    @GetMapping("interaction-record")
    fun getInteractionRecords() = queryService.getInteractionRecords()
}