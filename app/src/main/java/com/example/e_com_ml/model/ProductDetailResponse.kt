package com.example.e_com_ml.model

data class ProductDetailResponse(

    val product: Product,

    val reviews: List<Review>
)