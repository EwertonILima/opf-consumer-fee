package com.ewertonilima.consumerfee.domain.service

interface ConsumerFeeService {
    suspend fun processFromKafka(messageFromKafka: String)
}