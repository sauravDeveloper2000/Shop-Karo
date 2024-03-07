package com.example.shopkaro.ui.category_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.shopkaro.components.ErrorComponent
import com.example.shopkaro.components.LoadingComponent
import com.example.shopkaro.model_class.Product
import com.example.shopkaro.ui.home_screen.SingleProductItem
import com.example.shopkaro.user_actions_and_web_request_handling.ProductUiState

@Composable
fun ProductsByCategory(
    modifier: Modifier = Modifier,
    productsByCategoryState: ProductUiState,
    productInDetails: (Product) -> Unit
) {
    when (productsByCategoryState) {
        ProductUiState.Loading -> {
            LoadingComponent(modifier = modifier)
        }

        is ProductUiState.OnError -> {
            ErrorComponent(
                modifier = modifier
            )
        }

        is ProductUiState.OnSuccess -> {
            ProductsByCategoryScreen(
                modifier = modifier,
                products = productsByCategoryState.products,
                getProductInDetails = {
                    productInDetails(it)
                }
            )
        }
    }
}

@Composable
fun ProductsByCategoryScreen(
    modifier: Modifier,
    products: List<Product>,
    getProductInDetails: (Product) -> Unit
) {

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            items = products,
            key = { it.id }
        ) { product ->
            SingleProductItem(
                product = product,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 5.dp,
                        vertical = 5.dp
                    )
                    .clickable {
                        getProductInDetails(product)
                    }
            )
        }
    }
}