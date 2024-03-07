package com.example.shopkaro.repository.product_repo

import com.example.shopkaro.api_service.ProductApiService
import com.example.shopkaro.model_class.ProductApiResponse
import javax.inject.Inject

class ProductRepoImpl @Inject constructor(
    private val productApiService: ProductApiService
) : ProductRepo {

    override suspend fun getAllProducts(limit: Int, skip: Int): ProductApiResponse {
        return productApiService.getAllProducts(
            limit = limit,
            skip = skip
        )
    }

    override suspend fun getProductsByCategory(category: String): ProductApiResponse {
        return productApiService.getProductsByCategory(
            category = category
        )
    }

}