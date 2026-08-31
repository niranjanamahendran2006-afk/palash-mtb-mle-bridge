package com.palash.mtbmle.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.palash.mtbmle.ui.theme.CosmicCyan
import com.palash.mtbmle.ui.theme.CosmicText

/** Large, thumb-friendly primary action button — used across all screens for consistency. */
@Composable
fun PalashPrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    accentColor: Color = CosmicCyan,
    onClick: () -> Unit
) {
    CosmicButton(text, accentColor, onClick, modifier, enabled)
}

/** Large tappable dashboard card used on the Home screen. */
@Composable
fun PalashActionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit,
    accentColor: Color = CosmicCyan
) {
    CosmicGlowCard(title, subtitle, icon, accentColor, onClick)
}

/** Reusable loading row — used for "Translating...", "Preparing worksheet...", etc. */
@Composable
fun PalashLoadingState(
    label: String,
    modifier: Modifier = Modifier,
    accentColor: Color = CosmicCyan
) {
    Column(
        modifier = modifier.padding(vertical = 16.dp),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CircularProgressIndicator(color = accentColor)
        Text(label, color = CosmicText, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun PalashSectionLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(bottom = 4.dp)
    )
}