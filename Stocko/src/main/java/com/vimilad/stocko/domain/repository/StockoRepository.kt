package com.vimilad.stocko.domain.repository

import com.vimilad.stocko.domain.model.CompanyListing
import com.vimilad.stocko.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockoRepository {
    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>
}