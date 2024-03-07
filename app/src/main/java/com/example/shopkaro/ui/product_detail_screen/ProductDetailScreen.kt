package com.example.shopkaro.ui.product_detail_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.shopkaro.R
import com.example.shopkaro.components.HorizontalSpace
import com.example.shopkaro.components.VerticalSpace
import com.example.shopkaro.model_class.Product
import com.example.shopkaro.room_database.Product2
import com.example.shopkaro.ui.cart_screen.CartScreenViewModel
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductDetails(
    modifier: Modifier,
    product: Product,
    cartScreenViewModel: CartScreenViewModel
) {
    val pagerState = rememberPagerState(
        pageCount = {
            product.images.size
        }
    )
    val context = LocalContext.current
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        ElevatedCard(
            modifier = Modifier.padding(8.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            )
        ) {
            /**
             * Below composable for product images
             */
            HorizontalPager(state = pagerState) { page ->
                Card(
                    Modifier
                        .padding(5.dp)
                        .graphicsLayer {
                            // Calculate the absolute offset for the current page from the
                            // scroll position. We use the absolute value which allows us to mirror
                            // any effects for both directions
                            val pageOffset = (
                                    (pagerState.currentPage - page) + pagerState
                                        .currentPageOffsetFraction
                                    ).absoluteValue

                            // We animate the alpha, between 50% and 100%
                            alpha = lerp(
                                start = 0.5f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                        }
                ) {
                    // Card content
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(.7f),
                        model = ImageRequest.Builder(context)
                            .data(product.images[page])
                            .crossfade(true)
                            .build(),
                        error = painterResource(id = R.drawable.broken_image),
                        placeholder = painterResource(id = R.drawable.loading),
                        contentDescription = null,
                        alignment = Alignment.TopCenter,
                        filterQuality = FilterQuality.High,
                        contentScale = ContentScale.Crop
                    )
                }

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(color)
                            .size(8.dp)
                    )
                }
            }
            VerticalSpace(space = 25)
            /**
             * For product title and brand
             */
            Text(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = "${product.brand}",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 22.sp
            )

            Text(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = "${product.title}",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 18.sp
            )

            VerticalSpace(space = 10)
            /**
             * For product description
             */
            Divider(
                thickness = 5.dp
            )
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = "Description",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 17.sp
            )
            VerticalSpace(space = 10)
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = "${product.description}",
                style = MaterialTheme.typography.bodyLarge
            )
            Divider(
                thickness = 5.dp
            )

            VerticalSpace(space = 10)
            /**
             * Composable for product price and discount details
             */
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = "Price Details - ",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 17.sp
            )
            VerticalSpace(space = 10)
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowDownward,
                    contentDescription = null
                )
                Text(
                    text = "${product.discountPercentage.toInt()}% Off",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    modifier = Modifier.padding(start = 10.dp),
                    text = "Rs ${product.price}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Normal
                )
            }
            Divider(
                thickness = 5.dp
            )
            VerticalSpace(space = 10)
            /**
             * Rating composable - Shows rating to user
             */
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = "Rating-",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 17.sp
            )
            VerticalSpace(space = 10)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                RatingBar(
                    value = product.rating.toFloat(),
                    style = RatingBarStyle.Fill(inActiveColor = Color.White),
                    onValueChange = {},
                    onRatingChanged = {},
                    numOfStars = 5,
                    size = 12.dp
                )
                HorizontalSpace(space = 5)
                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = "- ${product.rating}/5.0",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Normal
                )
            }

        }
        /**
         * Button to add product into cart section
         */
        Button(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .align(Alignment.CenterHorizontally),
            onClick = {
                val product2 = Product2(
                    brand = product.brand,
                    title = product.title,
                    category = product.category,
                    description = product.description,
                    discountPercentage = product.discountPercentage,
                    id = product.id,
                    price = product.price,
                    rating = product.rating,
                    thumbnail = product.thumbnail
                )
                cartScreenViewModel.addProductToDb(
                    context, product2, msg = "Product Added To Cart"
                )
            },
            shape = RoundedCornerShape(10)
        ) {
            Icon(imageVector = Icons.Filled.ShoppingCart, contentDescription = "Add to Cart")
            HorizontalSpace(space = 5)
            Text(
                text = "Add To Cart",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }

}