package com.example.hastakalashop.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sales")
data class Sale(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productName: String,
    val color: String,
    val quantity: Int,
    val price: Double,
    val totalAmount: Double,
    val timestamp: Long = System.currentTimeMillis()
)
