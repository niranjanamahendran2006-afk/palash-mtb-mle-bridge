package com.palash.mtbmle.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val PalashColorScheme = darkColorScheme(
    primary = CosmicCyan,
    onPrimary = CosmicMidnight,
    primaryContainer = CosmicNavy,
    secondary = CosmicViolet,
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