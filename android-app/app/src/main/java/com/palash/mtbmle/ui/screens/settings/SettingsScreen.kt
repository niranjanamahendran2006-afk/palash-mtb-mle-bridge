package com.palash.mtbmle.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.palash.mtbmle.data.model.ModelInfo
import com.palash.mtbmle.ui.components.PalashSectionLabel
import com.palash.mtbmle.ui.theme.PalashTextSecondary
import com.palash.mtbmle.ui.components.CosmicBackground
import com.palash.mtbmle.ui.components.CosmicPanel
import com.palash.mtbmle.ui.theme.CosmicIndigo
import com.palash.mtbmle.ui.theme.CosmicViolet
import com.palash.mtbmle.ui.theme.CosmicBlue
import com.palash.mtbmle.ui.theme.CosmicText

/** Settings screen (roadmap Section 17). Prototype values are static/local — no backend. */
@Composable
fun SettingsScreen() {
    var offlineModeEnabled by remember { mutableStateOf(true) }
    val modelInfo = remember { ModelInfo() }

    CosmicBackground(
        accentColor = CosmicIndigo,
        secondaryAccent = CosmicViolet,
        tertiaryAccent = CosmicBlue,
        intensity = 0.65f
    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Settings", color = CosmicText, style = MaterialTheme.typography.headlineMedium)

        PalashSectionLabel("LANGUAGE")
        SettingsRow(label = "Source", value = "Hindi")
        SettingsRow(label = "Target", value = "Santhali")

        Divider(color = CosmicIndigo.copy(alpha = 0.22f))

        PalashSectionLabel("APPLICATION")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Offline Mode", style = MaterialTheme.typography.bodyLarge)
            Switch(checked = offlineModeEnabled, onCheckedChange = { offlineModeEnabled = it })
        }

        Divider(color = CosmicIndigo.copy(alpha = 0.22f))

        PalashSectionLabel("ABOUT")
        SettingsRow(label = "PALASH MTB-MLE", value = "")
        SettingsRow(label = "Version", value = "Prototype 1.0")

        Divider(color = CosmicIndigo.copy(alpha = 0.22f))

        PalashSectionLabel("DATA & MODEL INFORMATION")
        Text(
            "The items below are planned integrations. This prototype uses mock " +
                "engines only — see the project README for details.",
            style = MaterialTheme.typography.bodyMedium,
            color = PalashTextSecondary
        )
        SettingsRow(label = "Translation", value = modelInfo.translationModel)
        SettingsRow(label = "Speech Recognition", value = modelInfo.speechRecognitionModel)
        SettingsRow(label = "Speech Output", value = modelInfo.speechSynthesisModel)
    }
    }
}

@Composable
private fun SettingsRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyLarge)
        if (value.isNotEmpty()) {
            Text(value, style = MaterialTheme.typography.bodyMedium, color = PalashTextSecondary)
        }
    }
}