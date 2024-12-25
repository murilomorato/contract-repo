package com.nextar.contract_repo.domain.service

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode

/** Recebe um JsonNode e modelId. Substitui os key values por placeholders que skipam a validação.
 * @return JsonNode com placeholders que pulam a validação de comparação */
fun ignoreKeyValues(modelId: Int, contractJson: JsonNode): JsonNode {
    return when (modelId) {
        1 -> modelId1ignoredValues(contractJson)
        2 -> modelId2ignoredValues(contractJson)
        12 -> modelId12ignoredValues(contractJson)
        else -> contractJson
    }
}

fun modelId1ignoredValues(contractJson: JsonNode): JsonNode {
    return contractJson
}

fun modelId2ignoredValues(contractJson: JsonNode): JsonNode {
    return contractJson
}

fun modelId12ignoredValues(contractJson: JsonNode): JsonNode {
    if (contractJson is ObjectNode) {
        contractJson.put("uid", "\${json-unit.any-not-null}")
        contractJson.put("criadoEm", "\${json-unit.any-not-null}")
    }
    return contractJson
}