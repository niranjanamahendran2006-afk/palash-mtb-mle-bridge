package com.palash.mtbmle.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val PalashColorScheme = lightColorScheme(
    primary = PalashGreenPrimary,
    onPrimary = PalashSurface,
    primaryContainer = PalashGreenDark,
    secondary = PalashAmberAccent,
    background = PalashBackground,
    surface = PalashSurface,
    onBackground = PalashTextPrimary,
    onSurface = PalashTextPrimary,
    error = PalashError
)

@Composable
fun PalashTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = PalashColorScheme,
        typography = PalashTypography,
        content = content
    )
}
