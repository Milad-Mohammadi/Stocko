package com.vimilad.stocko.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CompanyListingEntity::class],
    version = 1
)
abstract class StockoDatabase: RoomDatabase() {
    abstract val dao: StockoDao
}