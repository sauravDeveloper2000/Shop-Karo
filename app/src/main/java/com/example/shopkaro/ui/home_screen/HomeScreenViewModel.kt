package com.example.shopkaro.ui.home_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopkaro.components.debugLog
import com.example.shopkaro.model_class.Product
import com.example.shopkaro.repository.product_repo.ProductRepo
import com.example.shopkaro.user_actions_and_web_request_handling.ProductUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val productRepo: ProductRepo
) : ViewModel() {

    var productUiState = MutableStateFlow<ProductUiState>(ProductUiState.Loading)
        private set
    private var listOfProducts = mutableListOf<Product>()
    var isProductFinished = mutableStateOf(false)
    private var limit = 30
    private var skip = 0


    init {
        products()
    }

    /**
     * products() - In this we are making our first network request and getting first 30 products
     */
    fun products() {
        viewModelScope.launch {
            productUiState.value = ProductUiState.Loading
            productUiState.value = try {
                val response = productRepo.getAllProducts(
                    limit = limit,
                    skip = skip
                )
                if (response.products.isNotEmpty()) {
                    response.products.forEach { product ->
                        listOfProducts.add(product)
                    }
                }
                debugLog(msg = "from products() - $listOfProducts")
                ProductUiState.OnSuccess(products = listOfProducts)
            } catch (e: Exception) {
                ProductUiState.OnError(cause = e.message.toString())
            }
        }
    }

    /**
     * getMoreProducts() - This function invokes by user when user clicks on load more button. To get more products.
     */
    fun getMoreProducts() {
        viewModelScope.launch {
            productUiState.value = ProductUiState.Loading
            productUiState.value = try {
                skip += 30
                val response = productRepo.getAllProducts(
                    limit = 30,
                    skip = skip
                )
                isProductFinished.value = response.products.isEmpty()
                if (response.products.isNotEmpty()) {
                    response.products.forEach { product ->
                        listOfProducts.add(product)
                    }
                }
                debugLog(msg = listOfProducts.toString())
                ProductUiState.OnSuccess(products = listOfProducts)
            } catch (e: Exception) {
                ProductUiState.OnError(cause = e.message.toString())
            }
        }
    }

    /**
     * This below function gives product based upon the id
     */

}