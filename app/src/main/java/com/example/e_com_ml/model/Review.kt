package com.example.e_com_ml.model

import com.google.gson.annotations.SerializedName

data class Review(

    @SerializedName("Age")
    val age: Int,

    @SerializedName("Title")
    val title: String?,

    @SerializedName("Review Text")
    val reviewText: String?,

    @SerializedName("Rating")
    val rating: Int,

    @SerializedName("Recommended IND")
    val recommended: Int,

    @SerializedName("Positive Feedback Count")
    val positiveFeedbackCount: Int,

    @SerializedName("Division Name")
    val divisionName: String?,

    @SerializedName("Department Name")
    val departmentName: String?,

    @SerializedName("Class Name")
    val className: String?
)