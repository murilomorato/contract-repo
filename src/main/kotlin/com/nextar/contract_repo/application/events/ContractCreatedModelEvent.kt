package com.nextar.contract_repo.application.events

import java.time.Instant

data class ContractCreatedModelEvent(
    val contractId: String,
    val modelId: Int,
    val version: Int,
    val contractType: String,
    val createdAt: Instant
)