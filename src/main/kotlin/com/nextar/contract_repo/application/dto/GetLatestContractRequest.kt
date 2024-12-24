package com.nextar.contract_repo.application.dto

import com.fasterxml.jackson.databind.JsonNode
import java.time.Instant

data class GetLatestContractRequest(
    val id: String?,
    val modelId: Int,
    val provider: String,
    val consumer: String,
    val sentBy: String,
    val contract: JsonNode,
    val version: Int,
    val createdAt: Instant
)