package com.ewertonilima.consumerfee.config

import com.ewertonilima.consumerfee.domain.service.ConsumerFeeService
import com.ewertonilima.consumerfee.domain.service.impl.ConsumerFeeServiceImpl
import com.ewertonilima.consumerfee.infrastructure.adapters.FeesServiceImpl
import com.ewertonilima.consumerfee.infrastructure.persistence.FeesRepository
import com.ewertonilima.consumerfee.infrastructure.ports.FeesService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfig {

    @Bean
    fun feesService(
        feesRepository: FeesRepository
    ): FeesService {
        return FeesServiceImpl(feesRepository)
    }

    @Bean
    fun consumerFeeService(
        feesService: FeesService,
        objectMapper: ObjectMapper
    ): ConsumerFeeService {
        return ConsumerFeeServiceImpl(
            feesService,
            objectMapper
        )
    }
}