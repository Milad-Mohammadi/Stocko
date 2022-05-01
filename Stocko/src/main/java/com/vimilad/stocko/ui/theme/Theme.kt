package com.vimilad.stocko.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = Orange,
    onPrimary = Color.DarkGray,
    background = DarkBlue,
    onBackground = White,
)

private val LightColorPalette = lightColors(
    primary = Orange,
    onPrimary = Color.LightGray,
    background = White,
    onBackground = DarkBlue
)

@Composable
fun StockMarketAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !darkTheme
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette
    val systembarColor = if (darkTheme) DarkBlue else White

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = systembarColor,
            darkIcons = useDarkIcons
        )
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}