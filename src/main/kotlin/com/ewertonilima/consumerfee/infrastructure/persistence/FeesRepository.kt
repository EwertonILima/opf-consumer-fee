package com.ewertonilima.consumerfee.infrastructure.persistence

import mu.KotlinLogging
import org.springframework.stereotype.Repository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


@Repository
class FeesRepository(
    dynamoDbEnhancedAsyncClient: DynamoDbEnhancedAsyncClient
) {
    private var table: DynamoDbAsyncTable<FeesModel> = dynamoDbEnhancedAsyncClient.table(TABLE_NAME, tableSchema)

    fun updateFees(feesModel: FeesModel) {
        try {
            val future = table.putItemWithResponse(
                PutItemEnhancedRequest.builder(FeesModel::class.java)
                    .item(feesModel).build()
            )
            val result = future.get(10, TimeUnit.SECONDS) // Set a timeout to avoid indefinite blocking
            logger.info("Successfully saved content to DynamoDB: $result")
        } catch (e: ExecutionException) {
            // Handle specific exceptions from DynamoDB
            if (e.cause is DynamoDbException) {
                logger.error("DynamoDB specific error: ${e.message}")
            } else {
                logger.error("Error executing DynamoDB operation: ${e.cause?.message}")
            }
        } catch (e: InterruptedException) {
            // Handle if the thread was interrupted during get()
            Thread.currentThread().interrupt() // Set the interrupt flag
            logger.error("Thread was interrupted during database operation")
        } catch (e: TimeoutException) {
            logger.error("Timed out waiting for the database operation to complete")
        } catch (e: Exception) {
            // Generic exception handler as a last resort
            logger.error("Unexpected error occurred: ${e.message}")
        }
    }

    companion object {
        private val tableSchema = TableSchema.fromBean(FeesModel::class.java)
        private const val TABLE_NAME = "tb_product_fees"
        private val logger = KotlinLogging.logger {}
    }
}