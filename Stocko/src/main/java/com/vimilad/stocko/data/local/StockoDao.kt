package com.vimilad.stocko.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StockoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyListings(companyListingEntities: List<CompanyListingEntity>)

    @Query("DELETE FROM CompanyListingEntity")
    suspend fun clearCompanyListings()

    @Query(
        """
            SELECT * FROM CompanyListingEntity
            WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR 
            UPPER(:query) == symbol
        """
    )
    suspend fun searchCompanyListing(query: String): List<CompanyListingEntity>
}