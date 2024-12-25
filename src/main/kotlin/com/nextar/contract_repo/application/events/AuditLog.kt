package com.nextar.contract_repo.application.events

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "auditLog")
data class AuditLog(
    @Id
    val id: String? = null,
    val action: String,
    val contractId: String,
    val modelId: Int,
    val contractType: String,
    val version: Int,
    val createdAt: Instant = Instant.now()
)