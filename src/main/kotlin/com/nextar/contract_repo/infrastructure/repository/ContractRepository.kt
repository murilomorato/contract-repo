package com.nextar.contract_repo.infrastructure.repository

import com.nextar.contract_repo.domain.model.Contract
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ContractRepository : MongoRepository<Contract, String> {

    fun findTopByModelIdOrderByVersionDesc(modelId: Int): Contract?

    fun findTopByModelIdAndProviderAndConsumerOrderByVersionDesc(modelId: Int, provider: String, consumer: String): Contract?

}