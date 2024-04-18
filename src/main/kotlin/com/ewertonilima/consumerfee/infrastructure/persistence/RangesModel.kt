package com.ewertonilima.consumerfee.infrastructure.persistence

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute

data class RangesModel(
    @get:DynamoDbAttribute("range_number")
    var range: String? = null,
    @get:DynamoDbAttribute("average_value")
    var averageValue: String? = null,
    @get:DynamoDbAttribute("client_percentage")
    var clientPercentage: String? = null
)
