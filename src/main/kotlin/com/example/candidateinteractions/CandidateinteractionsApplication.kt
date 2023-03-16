package com.example.candidateinteractions

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(basePackages = ["com.example.candidateinteractions.commands.domain.aggregates.candidate.repository.implementations.hibernatecandidaterepository.HibernateCandidateRepository"])
class CandidateinteractionsApplication

fun main(args: Array<String>) {
    runApplication<CandidateinteractionsApplication>(*args)
}
