package com.example.shopkaro.ui.category_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopkaro.components.debugLog
import com.example.shopkaro.repository.product_repo.ProductRepo
import com.example.shopkaro.user_actions_and_web_request_handling.ProductUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductByCategoryViewModel @Inject constructor(
    private val productRepo: ProductRepo
) : ViewModel() {

    var productsByCategoryUiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
        private set

    fun givesProductsByCategory(
        category: String
    ) {
        viewModelScope.launch {

            productsByCategoryUiState.value = ProductUiState.Loading
            productsByCategoryUiState.value = try {
                val response = productRepo.getProductsByCategory(category = category)
                debugLog(msg = response.products.toString())
                ProductUiState.OnSuccess(products = response.products.toMutableList())
            } catch (e: Exception) {
                ProductUiState.OnError(cause = e.message.toString())
            }

        }
    }
}