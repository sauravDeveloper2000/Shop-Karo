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
        product: NewProduct
    )

    @Query("SELECT * FROM newproduct")
    fun getProduct(): Flow<List<NewProduct>>

    @Delete
    suspend fun deleteProduct(
        product: NewProduct
    )
}