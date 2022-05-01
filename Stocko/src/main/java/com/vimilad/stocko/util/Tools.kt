package com.vimilad.stocko.util

import java.time.DayOfWeek
import java.time.LocalDateTime

object Tools {
    fun getChartDayName(): String {
        val dayOfWeek = LocalDateTime.now().minusDays(1).dayOfWeek
        val isSaturday = dayOfWeek == DayOfWeek.SATURDAY
        val isSunday = dayOfWeek == DayOfWeek.SUNDAY

        val daysBefore: Long = when {
            isSaturday -> 2
            isSunday -> 3
            else -> 1
        }

        return LocalDateTime.now().minusDays(daysBefore).dayOfWeek.name
    }
}