package com.example.shopkaro.room_database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [NewProduct::class],
    version = 1
)
abstract class ProductDatabase : RoomDatabase() {
    abstract val productDao: ProductDao
}