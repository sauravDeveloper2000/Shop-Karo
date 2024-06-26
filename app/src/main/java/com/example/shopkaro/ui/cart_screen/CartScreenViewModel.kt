package com.example.shopkaro.ui.cart_screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopkaro.repository.repo_for_db.ProductDbRepo
import com.example.shopkaro.room_database.NewProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartScreenViewModel @Inject constructor(
    private val productDbRepo: ProductDbRepo
) : ViewModel() {

    var products = productDbRepo.getProduct()
        private set

    fun addProductToDb(
        context: Context,
        product: NewProduct,
        msg: String
    ) {
        viewModelScope.launch {
            productDbRepo.updateOrInsertProduct(
                context,
                product,
                msg = msg
            )
        }
    }

    fun deleteProduct(
        product: NewProduct
    ) {
        viewModelScope.launch {
            productDbRepo.deleteProduct(product)
        }
    }
}