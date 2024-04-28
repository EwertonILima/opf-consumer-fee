package com.ewertonilima.consumerfee.infrastructure.adapter

import com.ewertonilima.consumerfee.domain.service.ConsumerFeeService
import com.ewertonilima.consumerfee.infrastructure.adapters.KafkaListener
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class KafkaListenerTest {

    private lateinit var kafkaListener: KafkaListener

    @Mock
    private lateinit var consumerFeeService: ConsumerFeeService

    @BeforeEach
    fun setUp() {
        kafkaListener = KafkaListener(consumerFeeService)
    }

    private val message = "[{productCode:2000," +
            "acronymCharge:ANU," +
            "minimumValue:0.00," +
            "maximumValue:1000.00," +
            "productRanges:" +
            "[{range:1,averageValue:15,000,clientPercentage:0.0100}," +
            "{range:2,averageValue:50,000,clientPercentage:0.1200}," +
            "{range:3,averageValue:75,000,clientPercentage:0.1100}," +
            "{range:4,averageValue:1000,000,clientPercentage:0.7100}]}}"

    @Test
    fun receiveMessageSuccessTest() {
        runBlocking {
            // ARRANGE

            // ACT
            kafkaListener.consume(message)
            // ASSERT
            verify(consumerFeeService).processFromKafka(message)
            assertDoesNotThrow { kafkaListener.consume(message) }
        }
    }

    @Test
    fun errorReceiveMessageTest() {
        runBlocking {
            // ARRANGE
            val errorMessage = "Unexpected error event processing"
            whenever(consumerFeeService.processFromKafka(message)).doThrow(
                RuntimeException(errorMessage)
            )
            // ACT
            val result = assertThrows<RuntimeException> {
                kafkaListener.consume(message)
            }
            // ASSERT
            assert(result.message == errorMessage)
        }
    }
}