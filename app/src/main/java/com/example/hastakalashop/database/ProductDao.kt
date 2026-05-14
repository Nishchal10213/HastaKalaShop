package com.example.hastakalashop.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.hastakalashop.data.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM products ORDER BY name ASC, color ASC")
    fun observeProducts(): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE stockQuantity <= 5 ORDER BY stockQuantity ASC")
    fun observeLowStockProducts(): Flow<List<Product>>

    @Query("SELECT COUNT(*) FROM products")
    suspend fun productCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<Product>)

    @Update
    suspend fun updateProduct(product: Product)
}
