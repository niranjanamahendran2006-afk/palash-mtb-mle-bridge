package com.palash.mtbmle.ui.screens.translate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.palash.mtbmle.data.repository.MockTranslationEngine
import com.palash.mtbmle.ui.components.PalashLoadingState
import com.palash.mtbmle.ui.components.PalashPrimaryButton
import com.palash.mtbmle.ui.theme.PalashError
import com.palash.mtbmle.ui.theme.PalashTextSecondary
import com.palash.mtbmle.ui.components.CosmicBackground
import com.palash.mtbmle.ui.components.CosmicPanel
import com.palash.mtbmle.ui.theme.CosmicPink
import com.palash.mtbmle.ui.theme.CosmicMagenta
import com.palash.mtbmle.ui.theme.CosmicViolet
import com.palash.mtbmle.ui.theme.CosmicText
import com.palash.mtbmle.viewmodel.TranslateViewModel

/**
 * Core translation screen (roadmap Sections 7-9).
 *
 * NOTE ON VIEWMODEL CONSTRUCTION:
 * For prototype simplicity this screen builds its own ViewModel with MockTranslationEngine
 * inline below. In a production build this construction moves to a small factory/DI setup
 * in PalashApp.kt so the SAME screen code can receive NllbTranslationEngine instead —
 * no change to this file is required for that swap.
 */
@Composable
fun TranslateScreen(
    viewModel: TranslateViewModel = viewModel { TranslateViewModel(MockTranslationEngine()) }
) {
    val uiState by viewModel.uiState.collectAsState()

    CosmicBackground(accentColor = CosmicPink, secondaryAccent = CosmicMagenta, tertiaryAccent = CosmicViolet) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Decode a sentence", color = CosmicText, style = MaterialTheme.typography.headlineMedium)
        Text("Hindi → Santhali", style = MaterialTheme.typography.titleMedium, color = PalashTextSecondary)

        Text("Enter Hindi sentence", style = MaterialTheme.typography.labelLarge)
        OutlinedTextField(
            value = uiState.inputText,
            onValueChange = viewModel::onInputChanged,
            placeholder = { Text("Type a classroom sentence…  e.g. बच्चों, अपनी किताब खोलो।") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        if (uiState.errorMessage != null) {
            Text(uiState.errorMessage!!, color = PalashError, style = MaterialTheme.typography.bodyMedium)
        }

        PalashPrimaryButton(text = "Translate", accentColor = CosmicPink, onClick = viewModel::onTranslateClicked)

        if (uiState.isLoading) {
            PalashLoadingState(label = "Translating…", accentColor = CosmicPink)
        }

        uiState.result?.let { result ->
            CosmicPanel(accentColor = CosmicMagenta) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("SOURCE · Hindi", style = MaterialTheme.typography.labelLarge, color = PalashTextSecondary)
                    Text(result.hindiText, style = MaterialTheme.typography.bodyLarge)

                    Text("TARGET · Santhali", style = MaterialTheme.typography.labelLarge, color = PalashTextSecondary)
                    Text(result.santhaliText, style = MaterialTheme.typography.bodyLarge)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextButton(onClick = { /* TODO: wire to real TTS playback */ }) { Text("🔊 Play") }
                        TextButton(onClick = { /* TODO: copy to clipboard */ }) { Text("Copy") }
                        TextButton(onClick = viewModel::onClear) { Text("Clear") }
                    }

                    Text(
                        text = if (result.isDemoResult) "Prototype result — confidence unavailable in prototype"
                               else "Translation confidence",
                        style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
                        color = PalashTextSecondary
                    )
                }
            }
        }
    }
    }
}