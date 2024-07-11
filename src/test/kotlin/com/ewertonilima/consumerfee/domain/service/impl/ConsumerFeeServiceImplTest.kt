package com.ewertonilima.consumerfee.domain.service.impl

import com.ewertonilima.consumerfee.domain.service.ConsumerFeeService
import com.ewertonilima.consumerfee.infrastructure.ports.FeesService
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension


@ExtendWith(MockitoExtension::class)
class ConsumerFeeServiceImplTest {

    private lateinit var consumerFeeService: ConsumerFeeService

    @Mock
    private lateinit var feesService: FeesService

    @Mock
    private lateinit var objectMapper: ObjectMapper


    @BeforeEach
    fun setUp() {
        consumerFeeService = ConsumerFeeServiceImpl(feesService, objectMapper)
    }


    @Test
    fun processFromKafkaTest() {
        runBlocking {
            assertDoesNotThrow {
                consumerFeeService.processFromKafka(
                    "[{productCode:2000," +
                            "acronymCharge:ANU," +
                            "minimumValue:0.00," +
                            "maximumValue:1000.00," +
                            "productRanges:" +
                            "[{range:1,averageValue:15,000,clientPercentage:0.0100}," +
                            "{range:2,averageValue:50,000,clientPercentage:0.1200}," +
                            "{range:3,averageValue:75,000,clientPercentage:0.1100}," +
                            "{range:4,averageValue:1000,000,clientPercentage:0.7100}]}}"
                )
            }
        }
    }
}