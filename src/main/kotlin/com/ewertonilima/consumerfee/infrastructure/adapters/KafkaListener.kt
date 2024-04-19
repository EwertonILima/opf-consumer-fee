package com.ewertonilima.consumerfee.infrastructure.adapters

import com.ewertonilima.consumerfee.domain.service.ConsumerFeeService
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaListener(
    private val consumerFeeService: ConsumerFeeService
) {

    @KafkaListener(
        topics = ["\${topics.fee.storage.topic}"],
        groupId = "fee-storage-consumer"
    )
    fun consume(message: String) {
        runBlocking {
            consumerFeeService.processFromKafka(message)
            logger.info("Message received: $message")
        }
    }

    companion object {
        private val logger = KotlinLogging.logger {}
    }
}