package com.palash.mtbmle.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.palash.mtbmle.data.model.OfflineStatus
import com.palash.mtbmle.ui.theme.CosmicMint
import com.palash.mtbmle.ui.theme.CosmicTextMuted

/** Small "● Offline Ready" style indicator (roadmap Section 6 & 20). */
@Composable
fun OfflineStatusBadge(status: OfflineStatus, modifier: Modifier = Modifier) {
    val (label, color) = when (status) {
        OfflineStatus.OFFLINE_READY -> "Offline Ready" to CosmicMint
        OfflineStatus.OFFLINE -> "Offline Mode" to CosmicTextMuted
        OfflineStatus.SYNCING -> "Syncing…" to CosmicTextMuted
        OfflineStatus.ONLINE -> "Online" to CosmicTextMuted
    }
    Row(
        modifier = modifier
            .background(color.copy(alpha = 0.12f), RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("● ", color = color, style = MaterialTheme.typography.labelLarge)
        Text(label, color = color, style = MaterialTheme.typography.labelLarge)
    }
}

// Convenience overload used where a plain Boolean is more natural than the enum.
@Composable
fun OfflineStatusBadge(isReady: Boolean, modifier: Modifier = Modifier) {
    OfflineStatusBadge(
        status = if (isReady) OfflineStatus.OFFLINE_READY else OfflineStatus.OFFLINE,
        modifier = modifier
    )
}