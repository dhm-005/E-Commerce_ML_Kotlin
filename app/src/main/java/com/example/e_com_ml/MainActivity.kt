package com.example.e_com_ml

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.e_com_ml.ui.screen.ProductDetailScreen
import com.example.e_com_ml.ui.screen.ProductListScreen
import com.example.e_com_ml.ui.screen.RecommendationScreen
import com.example.e_com_ml.ui.theme.E_com_mlTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        setContent {

            E_com_mlTheme {

                val navController =
                    rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "products"
                ) {

                    // =========================================
                    // PRODUCT LIST
                    // =========================================

                    composable(
                        route = "products"
                    ) {

                        ProductListScreen(

                            onProductClick = { clothingId ->

                                navController.navigate(
                                    "product/$clothingId"
                                )
                            }
                        )
                    }


                    // =========================================
                    // PRODUCT DETAIL
                    // =========================================

                    composable(
                        route = "product/{clothingId}"
                    ) { backStackEntry ->

                        val clothingId =
                            backStackEntry
                                .arguments
                                ?.getInt("clothingId")
                                ?: return@composable

                        ProductDetailScreen(

                            clothingId = clothingId,

                            onBack = {
                                navController.popBackStack()
                            },

                            onReviewClick = { clothingId, reviewIndex ->
                                navController.navigate(
                                    "recommendation/$clothingId/$reviewIndex"
                                )
                            }
                        )
                    }


                    // =========================================
                    // RECOMMENDATIONS
                    // =========================================

                    composable(
                        route = "recommendation/{clothingId}/{reviewIndex}"
                    ) { backStackEntry ->

                        val clothingId =
                            backStackEntry.arguments
                                ?.getString("clothingId")
                                ?.toIntOrNull() ?: return@composable

                        val reviewIndex =
                            backStackEntry.arguments
                                ?.getString("reviewIndex")
                                ?.toIntOrNull() ?: return@composable

                        RecommendationScreen(
                            clothingId = clothingId,
                            reviewIndex = reviewIndex,
                            onBack = {
                                navController.popBackStack()
                            },
                            onProductClick = { productId ->
                                navController.navigate(
                                    "product/$productId"
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}