package com.vimilad.stocko.data.csv

import com.opencsv.CSVReader
import com.vimilad.stocko.data.mapper.toIntradayInfo
import com.vimilad.stocko.data.remote.dto.IntradayInfoDto
import com.vimilad.stocko.domain.model.IntradayInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.DayOfWeek
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntradayInfoParser @Inject constructor(): CSVParser<IntradayInfo> {

    override suspend fun parse(stream: InputStream): List<IntradayInfo> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1) // Drop the titles row from CSV file
                .mapNotNull { line ->
                    val timestamp = line.getOrNull(0) ?: return@mapNotNull null
                    val close = line.getOrNull(4) ?: return@mapNotNull null
                    val dto = IntradayInfoDto(timestamp, close.toDouble())
                    dto.toIntradayInfo()

                }.filter {
                    // Stock Market is closed on Saturday & Sunday, the filter works based on this.

                    val dayOfWeek = LocalDateTime.now().minusDays(1).dayOfWeek
                    val isSaturday = dayOfWeek == DayOfWeek.SATURDAY
                    val isSunday = dayOfWeek == DayOfWeek.SUNDAY

                    val daysBefore: Long = when {
                        isSaturday -> 2
                        isSunday -> 3
                        else -> 1
                    }

                    it.date.dayOfMonth == LocalDateTime.now().minusDays(daysBefore).dayOfMonth
                }.sortedBy {
                    it.date.hour
                }.also {
                    csvReader.close()
                }
        }
    }
}