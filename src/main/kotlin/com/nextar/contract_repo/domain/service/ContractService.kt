package com.nextar.contract_repo.domain.service

import com.nextar.contract_repo.application.dto.CreateContractRequest
import com.nextar.contract_repo.domain.model.Contract
import com.nextar.contract_repo.infrastructure.repository.ContractRepository
import org.springframework.stereotype.Service

@Service
class ContractService(private val contractRepository: ContractRepository) {

    fun createContractFromRequest(request: CreateContractRequest): Contract {

        val validContract = ignoreKeyValues(modelId = request.modelId!!, contractJson = request.contract!!)
        val domainContract = Contract(
            modelId = request.modelId,
            sentBy = request.sentBy!!,
            provider = request.provider!!,
            consumer = request.consumer!!,
            contract = deserializeContract(validContract),
            version = getLatestVersion(request.modelId)
        )

        return domainContract
    }

    fun saveContract(contract: Contract): Contract {
        return contractRepository.save(contract)
    }

    fun getAllContracts(): List<Contract> {
        return contractRepository.findAll()
    }

    fun getLatestVersion(modelId: Int) : Int {
        val latestContract = contractRepository.findTopByModelIdOrderByVersionDesc(modelId)
        val newVersion = if (latestContract == null) 1 else latestContract.version + 1
        return newVersion
    }

    fun getContractWithLatestVersion(modelId: Int, provider: String, consumer: String): Contract? {
        return contractRepository.findTopByModelIdAndProviderAndConsumerOrderByVersionDesc(modelId, provider, consumer)
    }
}