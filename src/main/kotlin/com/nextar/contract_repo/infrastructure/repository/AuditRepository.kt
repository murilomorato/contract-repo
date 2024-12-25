package com.nextar.contract_repo.infrastructure.repository

import com.nextar.contract_repo.application.events.AuditLog
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface AuditRepository : MongoRepository<AuditLog, String> {

    //fun findTopByModelIdOrderByVersionDesc(modelId: Int): ContractCreatedModelEvent?


}
