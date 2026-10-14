package com.example.e_com_ml.view_model

import com.example.e_com_ml.model.PredictionResponse
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_com_ml.model.Product
import com.example.e_com_ml.model.Review
import com.example.e_com_ml.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> = _selectedProduct

    private val _reviews = MutableStateFlow<List<Review>>(emptyList())
    val reviews: StateFlow<List<Review>> = _reviews

    private val _prediction =
        MutableStateFlow<PredictionResponse?>(null)

    val prediction: StateFlow<PredictionResponse?> = _prediction

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error


    fun loadProducts() {

        viewModelScope.launch {

            try {

                Log.d("API_TEST", "========== LOAD PRODUCTS ==========")
                Log.d("API_TEST", "Đang gọi GET /api/products")

                _loading.value = true
                _error.value = null

                val response =
                    RetrofitClient.api.getProducts()

                Log.d("API_TEST", "GET /api/products thành công")
                Log.d("API_TEST", "Response: $response")
                Log.d("API_TEST", "Số sản phẩm: ${response.products.size}")

                _products.value = response.products

                Log.d("API_TEST", "Đã cập nhật products")

            } catch (e: Exception) {

                Log.e("API_TEST", "========== API ERROR ==========")
                Log.e("API_TEST", "Exception type: ${e.javaClass.name}")
                Log.e("API_TEST", "Message: ${e.message}")
                Log.e("API_TEST", "Full error:", e)

                _error.value =
                    e.message ?: "Không thể tải sản phẩm"

            } finally {

                _loading.value = false

                Log.d("API_TEST", "========== LOAD PRODUCTS END ==========")
            }
        }
    }


    fun loadProduct(clothingId: Int) {

        viewModelScope.launch {

            try {

                Log.d("API_TEST", "========== LOAD PRODUCT ==========")
                Log.d(
                    "API_TEST",
                    "Đang gọi GET /api/products/$clothingId"
                )

                _loading.value = true
                _error.value = null

                val response =
                    RetrofitClient.api.getProduct(clothingId)

                Log.d(
                    "API_TEST",
                    "GET /api/products/$clothingId thành công"
                )

                Log.d(
                    "API_TEST",
                    "Product: ${response.product}"
                )

                Log.d(
                    "API_TEST",
                    "Số reviews: ${response.reviews.size}"
                )

                _selectedProduct.value =
                    response.product

                _reviews.value =
                    response.reviews

                Log.d(
                    "API_TEST",
                    "Đã cập nhật selectedProduct và reviews"
                )

            } catch (e: Exception) {

                Log.e(
                    "API_TEST",
                    "GET /api/products/$clothingId LỖI: ${e.message}",
                    e
                )

                _error.value =
                    e.message ?: "Không thể tải sản phẩm"

            } finally {

                _loading.value = false

                Log.d("API_TEST", "========== LOAD PRODUCT END ==========")
            }
        }
    }

    fun loadPrediction(
        clothingId: Int,
        reviewIndex: Int
    ) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null

                val response =
                    RetrofitClient.api.getPrediction(
                        clothingId,
                        reviewIndex
                    )

                _prediction.value = response

            } catch (e: Exception) {
                Log.e("API", "Lỗi prediction", e)
                _error.value =
                    e.message ?: "Không thể tải prediction"

            } finally {
                _loading.value = false
            }
        }
    }
}