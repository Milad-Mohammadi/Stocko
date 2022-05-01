package com.vimilad.stocko.presentation.company_info

import com.vimilad.stocko.domain.model.CompanyInfo
import com.vimilad.stocko.domain.model.IntradayInfo

data class CompanyInfoState (
    val stockInfos: List<IntradayInfo> = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)