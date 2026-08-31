package com.palash.mtbmle.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.palash.mtbmle.data.model.OfflineStatus
import com.palash.mtbmle.ui.components.OfflineStatusBadge
import com.palash.mtbmle.ui.components.PalashActionCard
import com.palash.mtbmle.ui.theme.PalashTextSecondary
import com.palash.mtbmle.ui.components.CosmicBackground
import com.palash.mtbmle.ui.theme.CosmicBlue
import com.palash.mtbmle.ui.theme.CosmicViolet
import com.palash.mtbmle.ui.theme.CosmicCyan
import com.palash.mtbmle.ui.theme.CosmicText
import com.palash.mtbmle.ui.theme.CosmicTextMuted


/**
 * Teacher dashboard — the app's entry screen, and the "home region" of the PALASH galaxy
 * (blue + violet + cyan nebula). Deliberately uncrowded (roadmap Section 6): app identity,
 * current language pair, three large action cards, and an offline indicator.
 */
@Composable
fun HomeScreen(
    onNavigateToTranslate: () -> Unit,
    onNavigateToVoice: () -> Unit,
    onNavigateToWorksheet: () -> Unit
) {
    CosmicBackground(accentColor = CosmicBlue, secondaryAccent = CosmicViolet, tertiaryAccent = CosmicCyan) {
    Column(
        modifier = Modifier.fillMaxSize().padding(PaddingValues(20.dp)),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Hey, Explorer! 🚀", color = CosmicText, style = MaterialTheme.typography.headlineLarge)
        Text(
            "Your learning galaxy is ready",
            style = MaterialTheme.typography.titleMedium,
            color = CosmicTextMuted
        )

        Spacer(Modifier.height(4.dp))

        Text(
            "Hindi → Santhali",
            style = MaterialTheme.typography.titleLarge,
            color = CosmicText
        )

        OfflineStatusBadge(status = OfflineStatus.OFFLINE_READY)

        Spacer(Modifier.height(8.dp))

        PalashActionCard(
            title = "Translate Text",
            subtitle = "Turn a Hindi classroom sentence into Santhali",
            icon = Icons.Filled.Translate,
            onClick = onNavigateToTranslate,
            accentColor = CosmicViolet
        )
        PalashActionCard(
            title = "Voice Conversation",
            subtitle = "Speak Hindi, hear it translated into Santhali",
            icon = Icons.Filled.Mic,
            onClick = onNavigateToVoice,
            accentColor = CosmicCyan
        )
        PalashActionCard(
            title = "Worksheets",
            subtitle = "Bilingual worksheets for your class",
            icon = Icons.Filled.MenuBook,
            onClick = onNavigateToWorksheet,
            accentColor = CosmicBlue
        )

        Spacer(Modifier.height(8.dp))
        Text(
            "Designed for classroom use",
            style = MaterialTheme.typography.bodyMedium,
            color = PalashTextSecondary
        )
    }
    }
}