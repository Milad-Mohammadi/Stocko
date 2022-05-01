package com.vimilad.stocko.data.repository

import com.vimilad.stocko.data.csv.CSVParser
import com.vimilad.stocko.data.local.StockoDatabase
import com.vimilad.stocko.data.mapper.toCompanyInfo
import com.vimilad.stocko.data.mapper.toCompanyListing
import com.vimilad.stocko.data.mapper.toCompanyListingEntity
import com.vimilad.stocko.data.remote.StockoApi
import com.vimilad.stocko.domain.model.CompanyInfo
import com.vimilad.stocko.domain.model.CompanyListing
import com.vimilad.stocko.domain.model.IntradayInfo
import com.vimilad.stocko.domain.repository.StockoRepository
import com.vimilad.stocko.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockoRepositoryImpl @Inject constructor(
    private val api: StockoApi,
    private val db: StockoDatabase,
    private val companyListingsParser: CSVParser<CompanyListing>,
    private val intradayInfoParser: CSVParser<IntradayInfo>
): StockoRepository {

    private val dao = db.dao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading())
            val localListings = dao.searchCompanyListing(query)
            emit(Resource.Success(data = localListings.map { it.toCompanyListing() }))

            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteListings = try {
                val response = api.getListings()
                companyListingsParser.parse(response.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data because:\n${e.message}"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data because:\n${e.message}"))
                null
            }

            remoteListings?.let { listings ->
                dao.clearCompanyListings()
                dao.insertCompanyListings(
                    listings.map { it.toCompanyListingEntity() }
                )
                emit(Resource.Success(
                    data = dao
                        .searchCompanyListing("")
                        .map { it.toCompanyListing() }
                ))
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getIntradayInfo(symbol: String): Resource<List<IntradayInfo>> {
        return try {
            val response = api.getIntradayInfo(symbol)
            val result = intradayInfoParser.parse(response.byteStream())
            Resource.Success(result)
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error("Couldn't load intraday info because:\n${e.message}")
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error("Couldn't load intraday info because:\n${e.message}")
        }
    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try {
            val response = api.getCompanyInfo(symbol)
            Resource.Success(response.toCompanyInfo())
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error("Couldn't load company info because:\n${e.message}")
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error("Couldn't load company info because:\n${e.message}")
        }
    }

}