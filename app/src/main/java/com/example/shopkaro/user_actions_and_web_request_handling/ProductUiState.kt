package com.example.shopkaro.user_actions_and_web_request_handling

import com.example.shopkaro.model_class.Product

sealed interface ProductUiState {
    data object Loading : ProductUiState
    data class OnSuccess(val products: MutableList<Product>) : ProductUiState
    data class OnError(val cause: String) : ProductUiState
}