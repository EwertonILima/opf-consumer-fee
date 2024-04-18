package com.ewertonilima.consumerfee.bootloader

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["com.ewertonilima.*"])
class ConsumerfeeApplication

fun main(args: Array<String>) {
	runApplication<ConsumerfeeApplication>(*args)
}
