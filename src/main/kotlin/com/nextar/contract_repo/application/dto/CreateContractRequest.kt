package com.nextar.contract_repo.application.dto

import com.fasterxml.jackson.databind.JsonNode
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CreateContractRequest(
    @field:NotNull val modelId: Int?,
    @field:NotBlank val sentBy: String?,
    @field:NotBlank val provider: String?,
    @field:NotBlank val consumer: String?,
    @field:NotNull val contract: JsonNode?
)