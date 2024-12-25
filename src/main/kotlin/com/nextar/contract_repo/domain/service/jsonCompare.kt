package com.nextar.contract_repo.domain.service

import net.javacrumbs.jsonunit.JsonAssert
import net.javacrumbs.jsonunit.core.Configuration

fun jsonCompare(jsonUnderTest: String, jsonParameter: String): Boolean {
    return try {
        val config = Configuration.empty()
        JsonAssert.assertJsonEquals(jsonUnderTest, jsonParameter, config)
        true
    } catch (ex: AssertionError) {
        false
    }
}