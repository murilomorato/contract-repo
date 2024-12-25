package com.nextar.contract_repo.application.events

import com.nextar.contract_repo.infrastructure.repository.AuditRepository
import com.nextar.contract_repo.domain.service.AuditService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class AuditListener(private val auditService: AuditService) {

    @EventListener
    fun handleContractCreatedEvent(event: ContractCreatedModelEvent) {
        val auditEvent = auditService.registerEvent(event)
    }
}