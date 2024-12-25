package com.nextar.contract_repo.application.controller

import com.nextar.contract_repo.application.dto.CreateContractRequest
import com.nextar.contract_repo.application.dto.GetLatestContractRequest
import com.nextar.contract_repo.domain.service.ContractService
import com.nextar.contract_repo.domain.service.jsonCompare
import com.nextar.contract_repo.domain.service.reserializeContract
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/contracts")
class ContractController(private val contractService: ContractService) {

    @PostMapping("/submit")
    fun createContract(@RequestBody @Valid request: CreateContractRequest): ResponseEntity<Any> {

        if (request.contract?.isNull == true) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf(
                "success" to false,"message" to "Contract is required"))
        }
        if (request.sentBy != request.consumer && request.sentBy != request.provider) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf(
                "success" to false,"message" to "Only consumer and provider can update contracts"))
        }

        val contract = contractService.createContractFromRequest(request)

        //val savedContract = contractService.saveContract(contract)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(mapOf(
                "success" to true,"message" to "Contract created successfully to model id ${request.modelId}",
                "data" to contract))
    }

    @GetMapping("/latest-version")
    fun getLatestVersionContract(@RequestParam("model_id") modelId: Int,
                                 @RequestParam("contractType") contractType: String): ResponseEntity<Any> {

        val contractDoc = contractService.getContractWithLatestVersion(modelId = modelId, contractType = contractType.lowercase())
        return if (contractDoc != null) {
            val formattedContract = reserializeContract(contractDoc.contract)
                val contractInDTO = GetLatestContractRequest(
                    id = contractDoc.id,
                    sentBy = contractDoc.sentBy,
                    contractType = contractDoc.contractType,
                    modelId = contractDoc.modelId,
                    provider = contractDoc.provider,
                    consumer = contractDoc.consumer,
                    contract = formattedContract,
                    version = contractDoc.version,
                    createdAt = contractDoc.createdAt
                )
            ResponseEntity.ok(contractInDTO)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(mapOf("success" to false, "message" to "No contract found for modelId=$modelId or contractType=$contractType") )
        }
    }

    @PostMapping("/can-i-deploy")
    fun canIDeploy(@RequestBody @Valid request: CreateContractRequest): ResponseEntity<Any> {

        if (request.contract?.isNull == true) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf(
                "success" to false,"message" to "Contract is required"))
        }

        val contractUnderTest = contractService.createContractFromRequest(request)
        val contractTypeToCompare = if (contractUnderTest.contractType.lowercase() == "provider") "consumer" else "provider"
        val latestContractInDb = contractService.getContractWithLatestVersion(modelId = contractUnderTest.modelId, contractType = contractTypeToCompare)

        return if (latestContractInDb != null) {
            val contractInDb = GetLatestContractRequest(
                id = latestContractInDb.id,
                modelId = latestContractInDb.modelId,
                sentBy = latestContractInDb.sentBy,
                provider = latestContractInDb.provider,
                consumer = latestContractInDb.consumer,
                contractType = latestContractInDb.contractType,
                contract = reserializeContract(latestContractInDb.contract),
                version = latestContractInDb.version,
                createdAt = latestContractInDb.createdAt
            )

            val jsonUnderTest = contractUnderTest.contract
            val jsonParameter = contractInDb.contract

            if (jsonCompare(jsonUnderTest, jsonParameter.toString())) {
                ResponseEntity.ok(mapOf("canDeploy" to true, "message" to "Contract can be deployed",
                    "jsonUnderTest" to contractUnderTest, "jsonParameter" to contractInDb))
            } else {
                ResponseEntity.ok(mapOf("canDeploy" to false, "message" to "Contract cannot be deployed",
                    "jsonUnderTest" to contractUnderTest, "jsonParameter" to contractInDb))
            }
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(mapOf("success" to false, "message" to "No contract found to compare"))
        }
    }
}