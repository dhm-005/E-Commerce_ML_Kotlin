package com.example.e_com_ml.network

import com.example.e_com_ml.model.ProductDetailResponse
import com.example.e_com_ml.model.ProductResponse
import com.example.e_com_ml.model.PredictionResponse

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("api/products")
    suspend fun getProducts(): ProductResponse


    @GET("api/products/{id}")
    suspend fun getProduct(
        @Path("id") clothingId: Int
    ): ProductDetailResponse

    @GET("api/predict/{clothingId}/{reviewIndex}")
    suspend fun getPrediction(
        @Path("clothingId") clothingId: Int,
        @Path("reviewIndex") reviewIndex: Int
    ): PredictionResponse
}