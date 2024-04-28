package com.ewertonilima.consumerfee.infrastructure.persistence

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedResponse
import java.util.concurrent.CompletableFuture

@ExtendWith(MockitoExtension::class)
class FeesRepositoryTest {

    private lateinit var feesRepository: FeesRepository

    @Mock
    private lateinit var dynamoDbEnhancedAsyncClient: DynamoDbEnhancedAsyncClient

    @Mock
    private lateinit var dynamoDbAsyncTable: DynamoDbAsyncTable<FeesModel>

    @BeforeEach
    fun setUp() {
        `when`(dynamoDbEnhancedAsyncClient.table(TABLE_NAME, tableSchema)).thenReturn(dynamoDbAsyncTable)
        feesRepository = FeesRepository(dynamoDbEnhancedAsyncClient)
    }

    private val rangesModel = RangesModel(
        range = "1",
        averageValue = "15.00",
        clientPercentage = "0.10"
    )

    private val feesModel = FeesModel(
        productCode = "2000",
        acronymCharge = "ANU",
        minimumValue = "0.00",
        maximumValue = "1000.00",
        productRanges = listOf(
            rangesModel,
            rangesModel,
            rangesModel,
            rangesModel,
        )
    )

    private fun buildPutItemEnhancedRequest(feesModel: FeesModel): PutItemEnhancedRequest<FeesModel> {
        return PutItemEnhancedRequest.builder(FeesModel::class.java).item(feesModel).build()
    }

    @Test
    fun saveFeesModelSuccess() {
        runBlocking {
            //ARRANGE

            // ACT
            doReturn(CompletableFuture.completedFuture(PutItemEnhancedResponse.builder(FeesModel::class.java).build()))
                .whenever(dynamoDbAsyncTable).putItemWithResponse(buildPutItemEnhancedRequest(feesModel))
            // ASSERT
            assertDoesNotThrow { feesRepository.updateFees(feesModel) }
        }
    }

    companion object {
        private val tableSchema = TableSchema.fromBean(FeesModel::class.java)
        private const val TABLE_NAME = "tb_product_fees"
    }
}