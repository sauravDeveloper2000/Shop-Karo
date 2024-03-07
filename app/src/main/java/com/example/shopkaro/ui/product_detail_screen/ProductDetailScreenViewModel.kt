package com.example.shopkaro.ui.product_detail_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.shopkaro.model_class.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductDetailScreenViewModel @Inject constructor() : ViewModel() {

    var desiredProduct by mutableStateOf<Product?>(null)
        private set

    fun setDesiredProductField(
        product: Product
    ) {
        desiredProduct = product
    }
}