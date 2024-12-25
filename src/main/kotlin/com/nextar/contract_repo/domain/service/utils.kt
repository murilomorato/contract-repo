package com.nextar.contract_repo.domain.service

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper

/** Converte string de json em JsonNode */
fun reserializeContract(contractString: String): JsonNode {
    val mapper = ObjectMapper()
    val contract = mapper.readTree(contractString)
    return contract
}

/** Converte JsonNode em String que pode ser reserializada */
fun deserializeContract(contractJson: JsonNode): String {
    val mapper = ObjectMapper()
    val contract = mapper.writeValueAsString(contractJson)
    return contract
}