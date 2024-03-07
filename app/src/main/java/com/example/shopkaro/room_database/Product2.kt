package com.example.shopkaro.room_database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product2(
    @PrimaryKey
    val id: Int,
    val brand: String,
    val category: String,
    val description: String,
    val discountPercentage: Double,
    val price: Int,
    val rating: Double,
    val thumbnail: String,
    val title: String,
    var quantity: Int = 1
)
