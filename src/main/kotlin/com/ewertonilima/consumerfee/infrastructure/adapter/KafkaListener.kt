package com.ewertonilima.consumerfee.infrastructure.adapter

import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaListener {

    @KafkaListener(
        topics = ["\${topics.fee.storage.topic}"],
        groupId = "fee-storage-consumer"
    )
    fun consume(message: String) {
        logger.info("Message received: $message")
    }

    companion object {
        private val logger = KotlinLogging.logger {}
    }
}