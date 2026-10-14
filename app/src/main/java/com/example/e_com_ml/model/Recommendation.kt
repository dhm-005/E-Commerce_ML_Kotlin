package com.example.e_com_ml.model

import com.google.gson.annotations.SerializedName

data class Recommendation(
    @SerializedName("Clothing ID")
    val clothingId: Int,

    @SerializedName("Title")
    val title: String?,

    @SerializedName("Rating")
    val rating: Int?,

    @SerializedName("recommendation_score")
    val recommendationScore: Double,

    @SerializedName("recommended_reviews")
    val recommendedReviews: Int,

    @SerializedName("total_reviews")
    val totalReviews: Int,

    @SerializedName("recommended_ratio")
    val recommendedRatio: Double,

    @SerializedName("Division Name")
    val divisionName: String?,

    @SerializedName("Department Name")
    val departmentName: String?,

    @SerializedName("Class Name")
    val className: String?
)