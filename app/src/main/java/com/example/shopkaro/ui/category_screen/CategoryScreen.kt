package com.example.shopkaro.ui.category_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopkaro.components.CategoryComponent
import com.example.shopkaro.components.HorizontalSpace
import com.example.shopkaro.components.VerticalSpace


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoryScreen(
    modifier: Modifier,
    getProductsByCategory: (String) -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .padding(10.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        /**
         * Mens Section.
         */
        Text(
            text = "Mens Section",
            style = MaterialTheme.typography.titleMedium,
            fontSize = 20.sp
        )
        VerticalSpace(space = 20)
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            mensCategory.forEach { category ->
                CategoryComponent(
                    modifier = Modifier.clickable {
                        getProductsByCategory(category.endPoint)
                    },
                    context = context,
                    imageUrl = category.image,
                    title = category.title,
                    contentDescription = category.contentDescription
                )
                HorizontalSpace(space = 25)
            }
        }

        VerticalSpace(space = 10)
        Divider(
            color = MaterialTheme.colorScheme.primary,
            thickness = 2.dp
        )
        VerticalSpace(space = 10)

        /**
         * Women section
         */
        Text(
            text = "Women Section",
            style = MaterialTheme.typography.titleMedium,
            fontSize = 20.sp
        )
        VerticalSpace(space = 20)
        FlowRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            womenCategory.forEach { category ->
                CategoryComponent(
                    modifier = Modifier.clickable {
                        getProductsByCategory(category.endPoint)
                    },
                    context = context,
                    imageUrl = category.image,
                    title = category.title,
                    contentDescription = category.contentDescription
                )
                HorizontalSpace(space = 25)
            }
        }
        VerticalSpace(space = 10)
        Divider(
            color = MaterialTheme.colorScheme.primary,
            thickness = 2.dp
        )
        VerticalSpace(space = 10)

        /**
         * Electronics and Mobile Phone
         */
        Text(
            text = "Mobile & Electronics",
            style = MaterialTheme.typography.titleMedium,
            fontSize = 20.sp
        )
        VerticalSpace(space = 20)
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            gadgets.forEach { category ->
                CategoryComponent(
                    modifier = Modifier.clickable {
                        getProductsByCategory(category.endPoint)
                    },
                    context = context,
                    imageUrl = category.image,
                    title = category.title,
                    contentDescription = category.contentDescription
                )
                HorizontalSpace(space = 25)
            }
        }

        VerticalSpace(space = 10)
        Divider(
            color = MaterialTheme.colorScheme.primary,
            thickness = 2.dp
        )
        VerticalSpace(space = 10)

        /**
         * Personal Care
         */
        Text(
            text = "Personal Care",
            style = MaterialTheme.typography.titleMedium,
            fontSize = 20.sp
        )
        VerticalSpace(space = 20)
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            personalCare.forEach { category ->
                CategoryComponent(
                    modifier = Modifier.clickable {
                        getProductsByCategory(category.endPoint)
                    },
                    context = context,
                    imageUrl = category.image,
                    title = category.title,
                    contentDescription = category.contentDescription
                )
                HorizontalSpace(space = 25)
            }
        }

        VerticalSpace(space = 10)
        Divider(
            color = MaterialTheme.colorScheme.primary,
            thickness = 2.dp
        )
        VerticalSpace(space = 10)

        /**
         * Home Needs
         */
        Text(
            text = "Home Needs",
            style = MaterialTheme.typography.titleMedium,
            fontSize = 20.sp
        )
        VerticalSpace(space = 20)
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            homeNeed.forEach { category ->
                CategoryComponent(
                    modifier = Modifier.clickable {
                        getProductsByCategory(category.endPoint)
                    },
                    context = context,
                    imageUrl = category.image,
                    title = category.title,
                    contentDescription = category.contentDescription
                )
                HorizontalSpace(space = 25)
            }
        }

        VerticalSpace(space = 10)
        Divider(
            color = MaterialTheme.colorScheme.primary,
            thickness = 2.dp
        )
        VerticalSpace(space = 10)

        /**
         * Automotive & Motorcycle
         */
        Text(
            text = "Automotive & Motorcycle",
            style = MaterialTheme.typography.titleMedium,
            fontSize = 20.sp
        )
        VerticalSpace(space = 20)
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            vehicle.forEach { category ->
                CategoryComponent(
                    modifier = Modifier.clickable {
                        getProductsByCategory(category.endPoint)
                    },
                    context = context,
                    imageUrl = category.image,
                    title = category.title,
                    contentDescription = category.contentDescription
                )
                HorizontalSpace(space = 25)
            }
        }
    }
}