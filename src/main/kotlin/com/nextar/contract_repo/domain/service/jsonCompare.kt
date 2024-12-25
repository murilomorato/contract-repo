package com.nextar.contract_repo.domain.service

import net.javacrumbs.jsonunit.JsonAssert
import net.javacrumbs.jsonunit.core.Configuration

/** Compara duas strings de json. Ignora placeholders adicionados em ignoredKeyValues.kt para o modelId.
 * @param jsonUnderTest json da build que está sendo testada
 * @param jsonParameter json que já está armazenado no banco de dados*/
fun jsonCompare(jsonUnderTest: String, jsonParameter: String): Boolean {
    return try {
        val config = Configuration.empty()
        JsonAssert.assertJsonEquals(jsonUnderTest, jsonParameter, config)
        true
    } catch (ex: AssertionError) {
        false
    }
}