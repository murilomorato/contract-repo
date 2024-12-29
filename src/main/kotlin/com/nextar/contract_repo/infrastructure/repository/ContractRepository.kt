package com.nextar.contract_repo.infrastructure.repository

import com.nextar.contract_repo.domain.model.Contract
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ContractRepository : MongoRepository<Contract, String> {

    fun findTopByModelIdOrderByVersionDesc(modelId: Int): Contract?

    fun findTopByModelIdAndProviderAndConsumerOrderByVersionDesc(modelId: Int, provider: String, consumer: String): Contract?

    fun findTopByModelIdAndContractTypeOrderByVersionDesc(modelId: Int, contractType: String): Contract?

    @Aggregation(
        pipeline = [
            "{ \$sort: { modelId: 1, version: -1 } }",
            "{ \$group: { _id: \"\$modelId\", doc: { \"\$first\": \"\$\$ROOT\" } } }",
            "{ \$replaceRoot: { newRoot: \"\$doc\" } }"
        ]
    )
    fun findAllLatestVersionsOnePerModelId(): List<Contract>?

    fun findAllByModelId(modelId: Int): List<Contract>?

}