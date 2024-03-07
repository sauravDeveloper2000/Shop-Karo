package com.example.shopkaro.model_class

import kotlinx.serialization.Serializable

/**
 * Represents single product item fetched from networking request.
 */

@Serializable
data class Product(
    val brand: String,
    val category: String,
    val description: String,
    val discountPercentage: Double,
    val id: Int,
    val images: List<String>,
    val price: Int,
    val rating: Double,
    val stock: Int,
    val thumbnail: String,
    val title: String
)