package com.ewertonilima.consumerfee.infrastructure.persistence

import mu.KotlinLogging
import org.springframework.stereotype.Repository
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest


@Repository
class FeesRepository(
    dynamoDbEnhancedAsyncClient: DynamoDbEnhancedAsyncClient
) {
    private var table: DynamoDbAsyncTable<FeesModel> = dynamoDbEnhancedAsyncClient.table(TABLE_NAME, tableSchema)

    fun updateFees(feesModel: FeesModel) {
        try {
            table.putItemWithResponse(PutItemEnhancedRequest.builder(FeesModel::class.java).item(feesModel).build())
                .get()
            logger.info("Saving content to DynamoDb")
        } catch (e: Exception) {
            logger.error("Unexpected error: $e")
        }
    }

    companion object {
        private val tableSchema = TableSchema.fromBean(FeesModel::class.java)
        private const val TABLE_NAME = "tb_product_fees"
        private val logger = KotlinLogging.logger {}
    }
}