package com.example.shopkaro.ui.cart_screen

import android.content.Context
import androidx.compose.foundation.background
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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.shopkaro.R
import com.example.shopkaro.components.HorizontalSpace
import com.example.shopkaro.components.VerticalSpace
import com.example.shopkaro.room_database.NewProduct
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    modifier: Modifier,
    backToHomeScreen: () -> Unit,
    cartScreenViewModel: CartScreenViewModel = hiltViewModel()
) {
    var products = cartScreenViewModel.products.collectAsState(initial = emptyList()).value
    var context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Cart Section",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 20.sp
                )
            })
        }
    ) { innerPadding ->
        if (products.isNotEmpty()) {
            LazyColumn(
                modifier = modifier.padding(innerPadding)
            ) {
                items(
                    items = products,
                    key = {
                        it.id
                    }
                ) { product ->
                    SingleProductItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        newProduct = product,
                        removeProduct = {
                            cartScreenViewModel.deleteProduct(it)
                        },
                        onQuantityChange = {
                            val newProduct = product.copy(
                                quantity = it
                            )
                            cartScreenViewModel.addProductToDb(
                                context = context,
                                product = newProduct,
                                msg = "Quantity has been changed to $it"
                            )
                        },
                        context = context
                    )
                    VerticalSpace(space = 10)
                }
                item {
                    TotalPrice(modifier = Modifier, products = products)
                }
            }
        } else {
            EmptyCartScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                backToShopping = {
                    backToHomeScreen()
                }
            )
        }
    }
}

/**
 * Represents single product item added into cart.
 */

@Composable
fun SingleProductItem(
    modifier: Modifier,
    newProduct: NewProduct,
    context: Context,
    removeProduct: (NewProduct) -> Unit,
    onQuantityChange: (Int) -> Unit
) {
    // Content for drop down menu
    var quantity = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    var selectedQuantity by remember {
        mutableIntStateOf(quantity[0])
    }

    Card(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                modifier = Modifier
                    .padding(5.dp)
                    .clip(RoundedCornerShape(10.dp)),
                model = ImageRequest.Builder(context)
                    .data(newProduct.thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = newProduct.title,
                error = painterResource(id = R.drawable.broken_image),
                placeholder = painterResource(id = R.drawable.loading),
                alignment = Alignment.Center,
                filterQuality = FilterQuality.High,
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(5.dp)
            ) {
                Text(
                    text = newProduct.brand,
                    style = MaterialTheme.typography.titleMedium
                )
                VerticalSpace(space = 5)
                Text(
                    text = newProduct.title,
                    style = MaterialTheme.typography.bodyMedium
                )
                VerticalSpace(space = 5)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Filled.ArrowDownward,
                        contentDescription = null,
                    )
                    Text(
                        text = "${newProduct.discountPercentage.toInt()}off",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    HorizontalSpace(space = 10)
                    Text(
                        text = "₨-${newProduct.price * selectedQuantity}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                VerticalSpace(space = 5)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    /**
                     * Rating composable - Shows rating to user
                     */
                    RatingBar(
                        value = newProduct.rating.toFloat(),
                        style = RatingBarStyle.Fill(inActiveColor = Color.White),
                        onValueChange = {},
                        onRatingChanged = {},
                        numOfStars = 5,
                        size = 12.dp
                    )
                    HorizontalSpace(space = 10)
                    Text(
                        text = "${newProduct.rating}/5.0",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
        /**
         * 2 Button - 1. for remove product from cart.
         *            2. for select quantity of added product.
         */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            /**
             * Remove product from cart
             */
            Button(
                onClick = {
                    removeProduct(newProduct)
                },
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(
                    text = "Remove",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
            }
            HorizontalSpace(space = 8)
            /**
             * Select quantity of added product
             */
            DropDownMenuForQuantity(
                quantity = quantity,
                selectedQuantity = selectedQuantity,
                onQuantityClick = {
                    selectedQuantity = it
                    onQuantityChange(it)
                }
            )
        }
    }
}

/**
 * Exposed drop down menu composable
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenuForQuantity(
    quantity: List<Int>,
    selectedQuantity: Int,
    onQuantityClick: (Int) -> Unit
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    //Exposed drop down menu
    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = {
            isExpanded = it
        }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .background(color = Color.Transparent)
                .menuAnchor(),
            value = selectedQuantity.toString(),
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            textStyle = MaterialTheme.typography.bodyMedium
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = {
                isExpanded = false
            }
        ) {
            quantity.forEach {
                DropdownMenuItem(
                    text = {
                        Text(text = it.toString())
                    },
                    onClick = {
                        onQuantityClick(it)
                        isExpanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

/**
 * TotalPrice - This composable gives total price.
 */

@Composable
fun TotalPrice(
    modifier: Modifier,
    products: List<NewProduct>
) {
    var totalPrice = 0
    Card(
        modifier = modifier.padding(8.dp),
        shape = RoundedCornerShape(5.dp)
    ) {
        if (products.isNotEmpty()) {
            products.forEach { product ->
                totalPrice += (product.price * product.quantity)
            }
            Text(
                modifier = modifier.padding(5.dp),
                text = "Total Price = $totalPrice",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}