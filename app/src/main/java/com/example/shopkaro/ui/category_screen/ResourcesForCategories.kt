package com.example.shopkaro.ui.category_screen

data class Category(
    val image: String,
    val title: String,
    val contentDescription: String,
    val endPoint: String
)

val mensCategory = listOf(
    Category(
        image = "https://cdn.dummyjson.com/product-images/51/2.jpg",
        title = "Shirts",
        contentDescription = "Mens Shirts",
        endPoint = "mens-shirts"
    ),
    Category(
        image = "https://cdn.dummyjson.com/product-images/56/2.jpg",
        title = "shoes",
        contentDescription = "Mens Shoes",
        endPoint = "mens-shoes"
    ),
    Category(
        image = "https://cdn.dummyjson.com/product-images/62/2.jpg",
        title = "Watches",
        contentDescription = "Mens Watches",
        endPoint = "mens-watches"
    )
)

val womenCategory = listOf(
    Category(
        image = "https://cdn.dummyjson.com/product-images/41/1.jpg",
        title = "Dresses",
        contentDescription = "Women Dresses",
        endPoint = "womens-dresses"
    ),
    Category(
        image = "https://cdn.dummyjson.com/product-images/47/thumbnail.jpeg",
        title = "Shoes",
        contentDescription = "Women Shoes",
        endPoint = "womens-shoes"
    ),
    Category(
        image = "https://cdn.dummyjson.com/product-images/66/thumbnail.jpg",
        title = "Watches",
        contentDescription = "Women Watches",
        endPoint = "womens-watches"
    ),
    Category(
        image = "https://cdn.dummyjson.com/product-images/72/thumbnail.webp",
        title = "Bags",
        contentDescription = "Women Bags",
        endPoint = "womens-bags"
    ),
    Category(
        image = "https://cdn.dummyjson.com/product-images/76/2.jpg",
        title = "Jewellery",
        contentDescription = "Women Jewellery",
        endPoint = "womens-jewellery"
    ),
    Category(
        image = "https://cdn.dummyjson.com/product-images/36/1.jpg",
        title = "Tops",
        contentDescription = "Women Tops",
        endPoint = "tops"
    )
)
val gadgets = listOf(
    Category(
        image = "https://cdn.dummyjson.com/product-images/2/1.jpg",
        title = "Mobiles",
        contentDescription = "Mobile Phones",
        endPoint = "smartphones"
    ),
    Category(
        image = "https://cdn.dummyjson.com/product-images/6/1.png",
        title = "Laptop",
        contentDescription = "Laptop",
        endPoint = "laptops"
    ),
    Category(
        image = "https://cdn.dummyjson.com/product-images/96/1.jpg",
        title = "Lighting",
        contentDescription = "Lighting",
        endPoint = "lighting"
    )
)

val personalCare = listOf(
    Category(
        image = "https://cdn.dummyjson.com/product-images/12/1.jpg",
        title = "Fragrances",
        contentDescription = "Fragrances",
        endPoint = "fragrances"
    ),
    Category(
        image = "https://cdn.dummyjson.com/product-images/16/2.webp",
        title = "Skincare",
        contentDescription = "Skincare",
        endPoint = "skincare"
    ),
    Category(
        image = "https://cdn.dummyjson.com/product-images/81/2.jpg",
        title = "Sunglasses",
        contentDescription = "Sunglasses",
        endPoint = "sunglasses"
    )
)

val homeNeed = listOf(
    Category(
        image = "https://cdn.dummyjson.com/product-images/26/1.jpg",
        title = "Home\nDecoration",
        contentDescription = "Home Decoration",
        endPoint = "home-decoration"
    ),
    Category(
        image = "https://cdn.dummyjson.com/product-images/31/1.jpg",
        title = "Furniture",
        contentDescription = "Furniture",
        endPoint = "furniture"
    ),
    Category(
        image = "https://cdn.dummyjson.com/product-images/21/1.png",
        title = "Groceries",
        contentDescription = "Groceries",
        endPoint = "groceries"
    )
)


val vehicle = listOf(
    Category(
        image = "https://cdn.dummyjson.com/product-images/92/1.jpg",
        title = "Motorcycle",
        contentDescription = "Motorcycle",
        endPoint = "motorcycle"
    ),
    Category(
        image = "https://cdn.dummyjson.com/product-images/87/1.jpg",
        title = "Automotive",
        contentDescription = "Automotive",
        endPoint = "automotive"
    )
)