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


/**
 * Teacher dashboard — the app's entry screen. Deliberately uncrowded (roadmap Section 6):
 * app identity, current language pair, three large action cards, and an offline indicator.
 */
@Composable
fun HomeScreen(
    onNavigateToTranslate: () -> Unit,
    onNavigateToVoice: () -> Unit,
    onNavigateToWorksheet: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(PaddingValues(20.dp)),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("PALASH", style = MaterialTheme.typography.headlineLarge)
        Text(
            "Mother Tongue Learning Bridge",
            style = MaterialTheme.typography.titleMedium,
            color = PalashTextSecondary
        )

        Spacer(Modifier.height(4.dp))

        Text(
            "Hindi → Santhali",
            style = MaterialTheme.typography.titleLarge
        )

        OfflineStatusBadge(status = OfflineStatus.OFFLINE_READY)

        Spacer(Modifier.height(8.dp))

        PalashActionCard(
            title = "Translate Text",
            subtitle = "Turn a Hindi classroom sentence into Santhali",
            icon = Icons.Filled.Translate,
            onClick = onNavigateToTranslate
        )
        PalashActionCard(
            title = "Voice Conversation",
            subtitle = "Speak Hindi, hear it translated into Santhali",
            icon = Icons.Filled.Mic,
            onClick = onNavigateToVoice
        )
        PalashActionCard(
            title = "Worksheets",
            subtitle = "Bilingual worksheets for your class",
            icon = Icons.Filled.MenuBook,
            onClick = onNavigateToWorksheet
        )

        Spacer(Modifier.height(8.dp))
        Text(
            "Designed for classroom use",
            style = MaterialTheme.typography.bodyMedium,
            color = PalashTextSecondary
        )
    }
}

