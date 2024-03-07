package com.example.shopkaro.room_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface ProductDao {

    @Upsert
    suspend fun updateOrInsertProduct(
        product: Product2
    )

    @Query("SELECT * FROM product2")
    fun getProduct(): Flow<List<Product2>>

    @Delete
    suspend fun deleteProduct(
        product: Product2
    )
}