package com.example.e_com_ml.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.e_com_ml.R
import com.example.e_com_ml.model.Product
import com.example.e_com_ml.model.Review
import com.example.e_com_ml.ui.theme.*
import com.example.e_com_ml.view_model.ProductViewModel
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    clothingId: Int,
    onBack: () -> Unit,
    onReviewClick: (Int, Int) -> Unit,
    viewModel: ProductViewModel = viewModel()
) {
    val product by viewModel.selectedProduct.collectAsState()
    val reviews by viewModel.reviews.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(clothingId) {
        viewModel.loadProduct(clothingId)
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
                                text = "Product Details",
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
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary
                        )
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
                            text = error ?: "Có lỗi xảy ra"
                        )
                    }
                }

                product != null -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .background(
                                MaterialTheme.colorScheme.background
                            ),
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            end = 16.dp,
                            top = 16.dp,
                            bottom = 24.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        item {
                            ProductDetailHeader(
                                product = product!!
                            )
                        }

                        item {
                            Text(
                                text = "Customer Reviews",
                                style = MaterialTheme.typography.titleLarge,
                                color = BlueDark
                            )
                        }

                        itemsIndexed(reviews) { index, review ->
                            ReviewCard(
                                review = review,
                                onClick = {
                                    onReviewClick(
                                        clothingId,
                                        index
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


// =========================================================
// PRODUCT HEADER
// =========================================================

@Composable
private fun ProductDetailHeader(
    product: Product
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .background(ProductImageBackground),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(
                        id = R.drawable.product
                    ),
                    contentDescription = product.title,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(30.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Column(
                modifier = Modifier.padding(18.dp)
            ) {

                Text(
                    text = product.title ?: "Không có tiêu đề",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                RatingStars(
                    rating = product.rating
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                ProductInfoRow(
                    label = "Department",
                    value = product.departmentName ?: "N/A"
                )

                ProductInfoRow(
                    label = "Division",
                    value = product.divisionName ?: "N/A"
                )

                ProductInfoRow(
                    label = "Class",
                    value = product.className ?: "N/A"
                )
            }
        }
    }
}


// =========================================================
// PRODUCT INFO ROW
// =========================================================

@Composable
private fun ProductInfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = value,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


// =========================================================
// REVIEW CARD
// =========================================================

@Composable
private fun ReviewCard(
    review: Review,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "User",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                RatingStars(
                    rating = review.rating ?: 0
                )
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Age: ${review.age ?: "?"}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            if (!review.title.isNullOrBlank()) {
                Text(
                    text = review.title,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )
            }

            Text(
                text = review.reviewText
                    ?: "Không có nội dung review.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "👍 ${review.positiveFeedbackCount ?: 0}    •    Click to see prediction →",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}