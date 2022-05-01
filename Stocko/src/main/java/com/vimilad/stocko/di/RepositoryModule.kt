package com.vimilad.stocko.di

import com.vimilad.stocko.data.csv.CSVParser
import com.vimilad.stocko.data.csv.CompanyListingsParser
import com.vimilad.stocko.data.csv.IntradayInfoParser
import com.vimilad.stocko.data.repository.StockoRepositoryImpl
import com.vimilad.stocko.domain.model.CompanyListing
import com.vimilad.stocko.domain.model.IntradayInfo
import com.vimilad.stocko.domain.repository.StockoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(companyListingsParser: CompanyListingsParser): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindIntradayInfoParser(intradayInfoParser: IntradayInfoParser): CSVParser<IntradayInfo>

    @Binds
    @Singleton
    abstract fun bindCStockoRepository(stockoRepositoryImpl: StockoRepositoryImpl): StockoRepository
}