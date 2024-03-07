package com.example.shopkaro.repository.repo_for_db

import android.content.Context
import com.example.shopkaro.room_database.Product2
import kotlinx.coroutines.flow.Flow

interface ProductDbRepo {

    suspend fun updateOrInsertProduct(
        context: Context,
        product: Product2,
        msg: String
    )

    fun getProduct(): Flow<List<Product2>>

    suspend fun deleteProduct(
        product: Product2
    )
}