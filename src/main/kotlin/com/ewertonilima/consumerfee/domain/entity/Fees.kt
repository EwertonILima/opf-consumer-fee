package com.ewertonilima.consumerfee.domain.entity

data class Fees(
    val productCode: String? = null,
    val acronymCharge: String? = null,
    val minimumValue: String? = null,
    val maximumValue: String? = null,
    var productRanges: List<Ranges>? = null
)