package com.nextar.contract_repo.domain.service

import com.nextar.contract_repo.application.dto.CreateContractRequest
import com.nextar.contract_repo.application.events.ContractCreatedModelEvent
import com.nextar.contract_repo.domain.model.Contract
import com.nextar.contract_repo.infrastructure.repository.ContractRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ContractService(private val contractRepository: ContractRepository, private val eventPublisher: ApplicationEventPublisher) {

    /** Função que converte uma request de contrato em objeto de domínio com os campos ignoráveis formatados.
     * @param request objeto tipado no DTO da request de entrada da api /contract/submit
     * @return objeto de domínio de contrato */
    fun createContractFromRequest(request: CreateContractRequest): Contract {

        val validContract = ignoreKeyValues(modelId = request.modelId!!, contractJson = request.contract!!)
        val contractType = if (request.sentBy == request.provider) "provider" else "consumer"
        val domainContract = Contract(
            modelId = request.modelId,
            sentBy = request.sentBy!!,
            contractType = contractType,
            provider = request.provider!!,
            consumer = request.consumer!!,
            contract = deserializeContract(validContract),
            version = getLatestVersionNumber(modelId = request.modelId, provider = request.provider, consumer = request.consumer)
        )

        val savedContract = saveContract(domainContract)
        return savedContract
    }

    fun saveContract(contract: Contract): Contract {
        val savedContract = contractRepository.save(contract)
        eventPublisher.publishEvent(ContractCreatedModelEvent(
            contractId = savedContract.id ?: "n/a",
            modelId = savedContract.modelId,
            version = savedContract.version,
            contractType = savedContract.contractType,
            createdAt = Instant.now()
        ))
        return savedContract
    }

    fun getAllContracts(): List<Contract> {
        return contractRepository.findAll()
    }

    /** Busca a última versão do contrato(consumer ou provider) e retorna versão+1. */
    fun getLatestVersionNumber(modelId: Int, provider: String, consumer: String) : Int {
        val latestContract = contractRepository.findTopByModelIdAndProviderAndConsumerOrderByVersionDesc(modelId, provider, consumer)
        val newVersion = if (latestContract == null) 1 else latestContract.version + 1
        return newVersion
    }

    /** Retorna a última versão do contrato de acordo com o parâmetro (modelId, consumer/provider). */
    fun getContractWithLatestVersion(modelId: Int, contractType: String): Contract? {
        return contractRepository.findTopByModelIdAndContractTypeOrderByVersionDesc(modelId, contractType)
    }

    /** Retorna a última versão de todos os contratos cadastrados. */
    fun getAllContractsLatestVersions(): List<Contract>? {
        return contractRepository.findAllLatestVersionsOnePerModelId()
    }

    /** Retorna todas versões de um determinado contrato. */
    fun getAllVersionsOfContract(modelId: Int): List<Contract>? {
        return contractRepository.findAllByModelId(modelId)
    }

}