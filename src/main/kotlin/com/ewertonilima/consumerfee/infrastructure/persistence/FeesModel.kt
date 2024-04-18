package com.ewertonilima.consumerfee.infrastructure.persistence

import com.ewertonilima.consumerfee.infrastructure.mapper.RangesModelConverter
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbConvertedBy
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey

@DynamoDbBean
data class FeesModel(
    @get:DynamoDbPartitionKey
    @get:DynamoDbAttribute("product_code")
    var productCode: String? = null,
    @get:DynamoDbAttribute("date_update")
    var dateUpdate: String? = null,
    @get:DynamoDbAttribute("acronym_charge")
    var acronymCharge: String? = null,
    @get:DynamoDbAttribute("minimum_value")
    var minimumValue: String? = null,
    @get:DynamoDbAttribute("maximum_value")
    var maximumValue: String? = null,
    @get:DynamoDbConvertedBy(RangesModelConverter.ToList::class)
    @get:DynamoDbAttribute("product_ranges")
    var productRanges: List<RangesModel>? = null
)
