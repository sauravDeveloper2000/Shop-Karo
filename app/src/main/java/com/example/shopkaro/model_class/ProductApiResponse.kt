package com.example.shopkaro.model_class

import kotlinx.serialization.Serializable

/**
 * The type of response we are getting by making networking request to https://dummyjson.com/products
 */

@Serializable
data class ProductApiResponse(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
)