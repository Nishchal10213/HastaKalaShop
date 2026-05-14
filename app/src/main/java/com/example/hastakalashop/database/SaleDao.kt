package com.example.hastakalashop.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.hastakalashop.data.Sale
import kotlinx.coroutines.flow.Flow

@Dao
interface SaleDao {
    @Query("SELECT * FROM sales ORDER BY timestamp DESC")
    fun observeSales(): Flow<List<Sale>>

    @Insert
    suspend fun insertSale(sale: Sale)
}
