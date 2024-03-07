package com.example.shopkaro.ui.home_screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.shopkaro.R
import com.example.shopkaro.components.ErrorComponent
import com.example.shopkaro.components.HorizontalSpace
import com.example.shopkaro.components.LoadingComponent
import com.example.shopkaro.components.VerticalSpace
import com.example.shopkaro.model_class.Product
import com.example.shopkaro.user_actions_and_web_request_handling.ProductUiState
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle


@Composable
fun HomeScreen(
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    getToProductDetail: (Product) -> Unit
) {
    val productUiState by homeScreenViewModel.productUiState.collectAsState()

    when (productUiState) {
        ProductUiState.Loading -> {
            LoadingComponent(modifier = Modifier.fillMaxSize())
        }

        is ProductUiState.OnError -> {
            ErrorComponent(
                modifier = Modifier.fillMaxSize()
            )
        }

        is ProductUiState.OnSuccess -> {

            ProductsScreen(
                modifier = Modifier.fillMaxSize(),
                products = (productUiState as ProductUiState.OnSuccess).products,
                loadMoreProducts = {
                    homeScreenViewModel.getMoreProducts()
                },
                isProductFinished = homeScreenViewModel.isProductFinished.value,
                onClickOnProductItem = { product ->
                    getToProductDetail(product)
                }
            )
        }
    }
}

@Composable
fun ProductsScreen(
    modifier: Modifier = Modifier,
    products: List<Product>,
    loadMoreProducts: () -> Unit,
    isProductFinished: Boolean,
    onClickOnProductItem: (Product) -> Unit
) {

    val context = LocalContext.current
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
                        onClickOnProductItem(product)
                    }
            )
        }
        item {
            Button(
                onClick = {
                    loadMoreProducts()
                    if (isProductFinished) {
                        Toast.makeText(context, "Products Finished", Toast.LENGTH_LONG).show()
                    }
                },
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "Load More",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

/**
 * Represents single product item.
 */
@Composable
fun SingleProductItem(
    product: Product,
    modifier: Modifier
) {
    val context = LocalContext.current
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Row {
            AsyncImage(
                modifier = Modifier
                    .size(164.dp),
                model = ImageRequest.Builder(context)
                    .data(product.thumbnail)
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.broken_image),
                placeholder = painterResource(id = R.drawable.loading),
                contentDescription = product.description,
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                /**
                 * Product Brand
                 */
                Text(
                    text = product.brand,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 18.sp
                )
                VerticalSpace(space = 8)
                /**
                 * Product Name as title
                 */
                Text(
                    text = product.title,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 15.sp
                )
                VerticalSpace(space = 8)
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDownward,
                        contentDescription = null
                    )
                    Text(
                        text = "${product.discountPercentage.toInt()}%Off",
                        fontWeight = FontWeight.Normal
                    )
                    HorizontalSpace(space = 10)
                    Text(
                        text = "${product.price}₨",
                        fontWeight = FontWeight.Normal
                    )
                }
                VerticalSpace(space = 8)
                /**
                 * Rating composable - Shows rating to user
                 */
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingBar(
                        value = product.rating.toFloat(),
                        style = RatingBarStyle.Fill(inActiveColor = Color.White),
                        onValueChange = {},
                        onRatingChanged = {},
                        numOfStars = 5,
                        size = 12.dp
                    )
                    HorizontalSpace(space = 8)
                    Text(text = "${product.rating}/5.0")
                }
            }
        }
    }
}