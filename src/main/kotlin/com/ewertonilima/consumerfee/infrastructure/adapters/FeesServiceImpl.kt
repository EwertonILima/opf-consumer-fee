package com.ewertonilima.consumerfee.infrastructure.adapters

import com.ewertonilima.consumerfee.domain.entity.Fees
import com.ewertonilima.consumerfee.infrastructure.persistence.FeesModel
import com.ewertonilima.consumerfee.infrastructure.persistence.FeesRepository
import com.ewertonilima.consumerfee.infrastructure.persistence.RangesModel
import com.ewertonilima.consumerfee.infrastructure.ports.FeesService
import org.springframework.beans.BeanUtils
import java.time.Instant

class FeesServiceImpl(
    private val feesRepository: FeesRepository
) : FeesService {
    override suspend fun feesUpdate(fees: List<Fees>) {
        fees.forEach {
            feesRepository.updateFees(feesToFeesModel(it))
        }
    }

    private fun feesToFeesModel(fees: Fees): FeesModel {
        val feesModel = FeesModel()
        BeanUtils.copyProperties(fees, feesModel)
        val rangesModels = fees.productRanges?.map { range ->
            val rangesModel = RangesModel()
            BeanUtils.copyProperties(range, rangesModel)
            rangesModel
        }
        feesModel.productRanges = rangesModels
        feesModel.dateUpdate = Instant.now().toString()
        return feesModel
    }
}