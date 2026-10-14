package com.example.e_com_ml.model

import com.google.gson.annotations.SerializedName

data class PredictionResponse(
    val product: Product,
    val review: Review,
    @SerializedName("recommended_products")
    val recommendedProducts: List<Recommendation>,

    @SerializedName("not_recommended_products")
    val notRecommendedProducts: List<Recommendation>,

    @SerializedName("recommended_total")
    val recommendedTotal: Int,

    @SerializedName("not_recommended_total")
    val notRecommendedTotal: Int
)