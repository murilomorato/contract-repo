package com.nextar.contract_repo.domain.service

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper

fun reserializeContract(contractString: String): JsonNode {
    val mapper = ObjectMapper()
    val contract = mapper.readTree(contractString)
    return contract
}

fun deserializeContract(contractJson: JsonNode): String {
    val mapper = ObjectMapper()
    val contract = mapper.writeValueAsString(contractJson)
    return contract
}