package com.example.shopkaro.repository.repo_for_db

import android.content.Context
import com.example.shopkaro.room_database.NewProduct
import kotlinx.coroutines.flow.Flow

interface ProductDbRepo {

    suspend fun updateOrInsertProduct(
        context: Context,
        product: NewProduct,
        msg: String
    )

    fun getProduct(): Flow<List<NewProduct>>

    suspend fun deleteProduct(
        product: NewProduct
    )
}