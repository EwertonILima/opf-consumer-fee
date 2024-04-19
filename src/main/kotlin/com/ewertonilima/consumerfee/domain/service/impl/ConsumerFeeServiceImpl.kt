package com.ewertonilima.consumerfee.domain.service.impl

import com.ewertonilima.consumerfee.domain.entity.Fees
import com.ewertonilima.consumerfee.domain.service.ConsumerFeeService
import com.ewertonilima.consumerfee.infrastructure.ports.FeesService
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import mu.KotlinLogging

class ConsumerFeeServiceImpl(
    private val feesService: FeesService,
    private val objectMapper: ObjectMapper
) : ConsumerFeeService {
    override suspend fun processFromKafka(messageFromKafka: String) {
        val fees = convertMessageFromKafka(messageFromKafka)
        feesService.feesUpdate(fees)
        logger.info("Processing message")
    }

    private fun convertMessageFromKafka(message: String): List<Fees> {
        return try {
            objectMapper.readValue<List<Fees>>(message)
        } catch (e: JsonProcessingException) {
            logger.info("[KAFKA] Non-standard message: ${e.message}")
            emptyList()
        }
    }

    companion object {
        private val logger = KotlinLogging.logger {}
    }
}