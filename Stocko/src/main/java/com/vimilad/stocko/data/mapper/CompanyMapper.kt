package com.vimilad.stocko.data.mapper

import com.vimilad.stocko.data.local.CompanyListingEntity
import com.vimilad.stocko.data.remote.dto.CompanyInfoDto
import com.vimilad.stocko.data.remote.dto.IntradayInfoDto
import com.vimilad.stocko.domain.model.CompanyInfo
import com.vimilad.stocko.domain.model.CompanyListing
import com.vimilad.stocko.domain.model.IntradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun CompanyListingEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {
    return CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
        symbol = symbol ?: "",
        description = description ?: "",
        name = name ?: "",
        country = country ?: "",
        industry = industry ?: "",
    )
}