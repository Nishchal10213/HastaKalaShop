package com.example.hastakalashop.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hastakalashop.data.Product
import com.example.hastakalashop.data.Sale

@Database(
    entities = [Product::class, Sale::class],
    version = 1,
    exportSchema = false
)
abstract class HastaKalaDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun saleDao(): SaleDao

    companion object {
        @Volatile
        private var INSTANCE: HastaKalaDatabase? = null

        fun getDatabase(context: Context): HastaKalaDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    HastaKalaDatabase::class.java,
                    "hastakala_shop.db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
