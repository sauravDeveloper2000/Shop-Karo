package com.example.shopkaro.api_service

import com.example.shopkaro.model_class.ProductApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ProductApiService {

    /**
     * getAllProducts:- This gives us limited products and to get limited products we passed limit and skip query param.
     */
    @GET("products")
    suspend fun getAllProducts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): ProductApiResponse

    /**
     * getProductsByCategory - This api gives us products by category. And category is the query which being passed by user.
     */
    @GET("products/category/{category}")
    suspend fun getProductsByCategory(
        @Path("category") category: String
    ): ProductApiResponse
}