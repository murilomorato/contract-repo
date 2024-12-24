package com.nextar.contract_repo.domain.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "contracts")
data class Contract (
    @Id
    val id: String? = null,
    val modelId: Int,
    val sentBy: String,
    val provider: String,
    val consumer: String,
    val contractType: String,
    val contract: String,
    val version: Int,
    val createdAt: Instant = Instant.now()
)