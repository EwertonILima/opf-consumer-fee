package com.ewertonilima.consumerfee.infrastructure.ports

import com.ewertonilima.consumerfee.domain.entity.Fees

interface FeesService {
    suspend fun feesUpdate(fees: List<Fees>)
}