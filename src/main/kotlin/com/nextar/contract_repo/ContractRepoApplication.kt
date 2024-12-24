package com.nextar.contract_repo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ContractRepoApplication

fun main(args: Array<String>) {
	runApplication<ContractRepoApplication>(*args)
		System.out.println("Server running")

}
