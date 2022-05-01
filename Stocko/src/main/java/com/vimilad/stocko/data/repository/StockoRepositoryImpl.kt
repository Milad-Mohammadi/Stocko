package com.vimilad.stocko.data.repository

import com.vimilad.stocko.data.local.StockoDatabase
import com.vimilad.stocko.data.mapper.toCompanyListing
import com.vimilad.stocko.data.remote.StockoApi
import com.vimilad.stocko.domain.model.CompanyListing
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
    val api: StockoApi,
    val db: StockoDatabase
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
                TODO("Parse CSV data")
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data because:\n${e.message}"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data because:\n${e.message}"))
            }
        }
    }

}