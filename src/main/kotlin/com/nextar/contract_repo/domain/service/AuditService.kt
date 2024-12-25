package com.nextar.contract_repo.domain.service
import com.nextar.contract_repo.application.events.AuditLog
import com.nextar.contract_repo.application.events.ContractCreatedModelEvent
import com.nextar.contract_repo.infrastructure.repository.AuditRepository

import org.springframework.stereotype.Service

@Service
class AuditService(private val auditRepository: AuditRepository) {


    fun registerEvent(event: ContractCreatedModelEvent) {

        val auditLog = AuditLog(
            action = if (event.version == 1) "CREATE_CONTRACT" else "UPDATE_CONTRACT",
            contractId = event.contractId,
            modelId = event.modelId,
            contractType = event.contractType,
            version = event.version,
            createdAt = event.createdAt
        )
        auditRepository.save(auditLog)
    }

}