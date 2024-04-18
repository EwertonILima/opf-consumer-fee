package com.ewertonilima.consumerfee.infrastructure.mapper

import com.ewertonilima.consumerfee.infrastructure.persistence.RangesModel
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

object RangesModelConverter {

    class ToList : AttributeConverter<List<RangesModel>> {
        private val objectMapper = jacksonObjectMapper()

        override fun transformFrom(input: List<RangesModel>): AttributeValue {
            return AttributeValue.builder().s(objectMapper.writeValueAsString(input)).build()
        }

        override fun transformTo(input: AttributeValue): List<RangesModel> {
            return objectMapper.readValue(
                input.s(),
                objectMapper.typeFactory.constructCollectionType(List::class.java, RangesModel::class.java)
            )
        }

        override fun type(): EnhancedType<List<RangesModel>> {
            return EnhancedType.listOf(RangesModel::class.java)
        }

        override fun attributeValueType(): AttributeValueType {
            return AttributeValueType.S
        }

    }
}