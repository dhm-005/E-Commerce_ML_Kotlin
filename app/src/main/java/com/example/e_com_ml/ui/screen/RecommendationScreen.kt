package com.example.e_com_ml.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.e_com_ml.R
import com.example.e_com_ml.model.Recommendation
import com.example.e_com_ml.model.Review
import com.example.e_com_ml.ui.theme.*
import com.example.e_com_ml.view_model.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendationScreen(
    clothingId: Int,
    reviewIndex: Int,
    onBack: () -> Unit,
    onProductClick: (Int) -> Unit,
    viewModel: ProductViewModel = viewModel()
) {

    val prediction by viewModel.prediction.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(clothingId, reviewIndex) {
        viewModel.loadPrediction(
            clothingId = clothingId,
            reviewIndex = reviewIndex
        )
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        // Background image
        Image(
            painter = painterResource(id = R.drawable.bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Lớp phủ mờ
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color.White.copy(alpha = 0.72f)
                )
        )

        // Nội dung app

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    PrimaryStart,
                                    PrimaryEnd
                                )
                            )
                        )
                ) {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Recommendations",
                                color = Color.White
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = onBack) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Back",
                                    tint = Color.White
                                )
                            }
                        },

                        actions = {
                            Image(
                                painter = painterResource(
                                    id = R.drawable.user
                                ),
                                contentDescription = "User",
                                modifier = Modifier
                                    .padding(end = 14.dp)
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(50))
                            )
                        },

                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent,
                            scrolledContainerColor = Color.Transparent
                        )
                    )
                }
            }
        ) { paddingValues ->

            when {

                loading -> {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                error != null -> {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = error ?: "Có lỗi xảy ra",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

                prediction != null -> {

                    val result = prediction!!

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),

                        contentPadding = PaddingValues(
                            horizontal = 16.dp,
                            vertical = 20.dp
                        ),

                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        // =============================================
                        // SELECTED REVIEW
                        // =============================================

                        item {

                            Text(
                                text = "Selected Review",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        item {

                            SelectedReviewCard(
                                review = result.review
                            )
                        }


                        // =============================================
                        // RECOMMENDATION HEADER
                        // =============================================

                        item {

                            Column(
                                modifier = Modifier.padding(
                                    top = 8.dp
                                )
                            ) {

                                Text(
                                    text = "Recommendations",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(
                                    modifier = Modifier.height(4.dp)
                                )

                                Text(
                                    text = "Các Product được phân loại dựa trên kết quả dự đoán của mô hình.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }


                        // =============================================
                        // RECOMMENDED
                        // =============================================

                        item {

                            RecommendationSectionTitle(
                                title = "Recommended",
                                count = result.recommendedTotal,
                                color = RecommendedGreen
                            )
                        }

                        items(
                            result.recommendedProducts
                        ) { product ->

                            RecommendationCard(
                                product = product,
                                recommended = true,
                                onClick = {
                                    onProductClick(
                                        product.clothingId
                                    )
                                }
                            )
                        }


                        // =============================================
                        // NOT RECOMMENDED
                        // =============================================

                        item {

                            RecommendationSectionTitle(
                                title = "Not Recommended",
                                count = result.notRecommendedTotal,
                                color = NotRecommendedRed
                            )
                        }

                        items(
                            result.notRecommendedProducts
                        ) { product ->

                            RecommendationCard(
                                product = product,
                                recommended = false,
                                onClick = {
                                    onProductClick(
                                        product.clothingId
                                    )
                                }
                            )
                        }


                        // =============================================
                        // BACK
                        // =============================================

                        item {

                            OutlinedButton(
                                onClick = onBack,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = 8.dp,
                                        bottom = 16.dp
                                    )
                            ) {
                                Text(
                                    text = "← Quay lại Product"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


// =============================================================
// SELECTED REVIEW CARD
// =============================================================

@Composable
fun SelectedReviewCard(
    review: Review
) {

    Card(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            // USER + RATING

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column {

                    Text(
                        text = "User",
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(2.dp)
                    )

                    Text(
                        text = "Age: ${review.age}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Text(
                    text = "★ ${review.rating}/5",
                    fontWeight = FontWeight.Bold,
                    color = YellowText
                )
            }


            // TITLE

            if (!review.title.isNullOrBlank()) {

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text = review.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }


            // REVIEW TEXT

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = review.reviewText
                    ?: "Không có nội dung review.",

                style = MaterialTheme.typography.bodyMedium
            )


            // FOOTER

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "👍 ${review.positiveFeedbackCount}",
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                Text(
                    text = listOfNotNull(
                        review.divisionName,
                        review.departmentName,
                        review.className
                    ).joinToString(" / "),

                    style = MaterialTheme.typography.bodySmall,

                    color = MaterialTheme.colorScheme.onSurfaceVariant,

                    maxLines = 1,

                    overflow = TextOverflow.Ellipsis,

                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}


// =============================================================
// SECTION TITLE
// =============================================================

@Composable
fun RecommendationSectionTitle(
    title: String,
    count: Int,
    color: Color
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = color
        )

        Spacer(
            modifier = Modifier.width(8.dp)
        )

        Text(
            text = "($count products)",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}


// =============================================================
// RECOMMENDATION CARD
// =============================================================

@Composable
fun RecommendationCard(
    product: Recommendation,
    recommended: Boolean,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },

        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // PRODUCT IMAGE

            Image(
                painter = painterResource(
                    id = R.drawable.product
                ),

                contentDescription = "Product",

                contentScale = ContentScale.Crop,

                modifier = Modifier
                    .size(90.dp)
                    .clip(
                        RoundedCornerShape(12.dp)
                    )
                    .background(
                        ProductImageBackground
                    )
            )


            Spacer(
                modifier = Modifier.width(12.dp)
            )


            // PRODUCT INFO

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = product.title
                        ?: "Product ${product.clothingId}",

                    style = MaterialTheme.typography.titleMedium,

                    fontWeight = FontWeight.Bold,

                    maxLines = 2,

                    overflow = TextOverflow.Ellipsis
                )


                Spacer(
                    modifier = Modifier.height(6.dp)
                )


                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "★ ${product.rating ?: 0}/5",

                        style = MaterialTheme.typography.bodySmall
                    )

                    Spacer(
                        modifier = Modifier.width(10.dp)
                    )

                    Text(
                        text = "ID: ${product.clothingId}",

                        style = MaterialTheme.typography.bodySmall,

                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }


                Spacer(
                    modifier = Modifier.height(4.dp)
                )


                Text(
                    text = listOfNotNull(
                        product.divisionName,
                        product.departmentName,
                        product.className
                    ).joinToString(" / "),

                    style = MaterialTheme.typography.bodySmall,

                    color = MaterialTheme.colorScheme.onSurfaceVariant,

                    maxLines = 2,

                    overflow = TextOverflow.Ellipsis
                )


                Spacer(
                    modifier = Modifier.height(6.dp)
                )


                Text(
                    text = "Recommendation: %.1f%%".format(
                        product.recommendationScore * 100
                    ),

                    style = MaterialTheme.typography.labelMedium,

                    fontWeight = FontWeight.Bold,

                    color = if (recommended) {
                        RecommendedGreen
                    } else {
                        NotRecommendedRed
                    }
                )
            }
        }
    }
}