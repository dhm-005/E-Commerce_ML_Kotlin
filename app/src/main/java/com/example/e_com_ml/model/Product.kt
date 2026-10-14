package com.example.e_com_ml.model
import com.google.gson.annotations.SerializedName

data class Product(

    @SerializedName("Clothing ID")
    val clothingId: Int,

    @SerializedName("Title")
    val title: String?,

    @SerializedName("Rating")
    val rating: Int,

    @SerializedName("Division Name")
    val divisionName: String?,

    @SerializedName("Department Name")
    val departmentName: String?,

    @SerializedName("Class Name")
    val className: String?
)