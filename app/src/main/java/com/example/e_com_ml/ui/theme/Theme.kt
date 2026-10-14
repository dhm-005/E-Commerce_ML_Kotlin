package com.example.e_com_ml.ui.theme

import android.R.id.primary
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(

    primary = PrimaryStart,

    onPrimary = Color.White,

    secondary = YellowAccent,

    onSecondary = Color.Black,

    background = AppBackground,

    onBackground = TextPrimary,

    surface = Color.White,

    onSurface = TextPrimary,

    onSurfaceVariant = TextSecondary
)


private val DarkColorScheme = darkColorScheme(

    primary = BluePrimary,

    secondary = YellowAccent,

    background = androidx.compose.ui.graphics.Color(0xFF101418),

    surface = androidx.compose.ui.graphics.Color(0xFF181C20),

    onSurface = androidx.compose.ui.graphics.Color.White,

    onBackground = androidx.compose.ui.graphics.Color.White
)


@Composable
fun E_com_mlTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {

    val colorScheme =
        if (darkTheme) {
            DarkColorScheme
        } else {
            LightColorScheme
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}