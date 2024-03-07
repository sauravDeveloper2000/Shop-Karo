package com.example.shopkaro.repository.product_repo

import com.example.shopkaro.model_class.ProductApiResponse

interface ProductRepo {
    suspend fun getAllProducts(
        limit: Int,
        skip: Int
    ): ProductApiResponse

    suspend fun getProductsByCategory(
        category: String
    ): ProductApiResponse
}