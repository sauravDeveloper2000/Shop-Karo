package com.example.shopkaro.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.shopkaro.R

@Composable
fun VerticalSpace(
    space: Int
) {
    Spacer(modifier = Modifier.height(space.dp))
}

@Composable
fun HorizontalSpace(
    space: Int
) {
    Spacer(modifier = Modifier.width(space.dp))
}

@Composable
fun LoadingComponent(
    modifier: Modifier
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_animation))
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(composition = composition, iterations = LottieConstants.IterateForever)
    }
}

@Composable
fun ErrorComponent(
    modifier: Modifier
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error_animation))
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(composition = composition, iterations = LottieConstants.IterateForever)
    }
}

/**
 * Animation shown at top app bar of home screen.
 */
@Composable
fun HomeScreenAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation1))
    LottieAnimation(
        modifier = Modifier.size(164.dp),
        composition = composition,
        iterations = LottieConstants.IterateForever,
        alignment = Alignment.TopStart,
        contentScale = ContentScale.Fit,
    )
}

fun debugLog(
    msg: String
) {
    Log.d("test", "$msg")
}

@Composable
fun CategoryComponent(
    modifier: Modifier = Modifier,
    context: Context,
    imageUrl: String,
    title: String,
    contentDescription: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .size(64.dp)
                .clip(MaterialTheme.shapes.small),
            model = ImageRequest.Builder(context)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = contentDescription,
            error = painterResource(id = R.drawable.broken_image),
            placeholder = painterResource(id = R.drawable.loading),
            contentScale = ContentScale.Crop
        )
        VerticalSpace(space = 5)
        Text(
            text = title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall
        )
    }
}