package com.palash.mtbmle.ui.screens.voice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.palash.mtbmle.data.model.VoiceProcessingStatus
import com.palash.mtbmle.data.repository.MockSpeechRecognitionEngine
import com.palash.mtbmle.data.repository.MockSpeechSynthesisEngine
import com.palash.mtbmle.data.repository.MockTranslationEngine
import com.palash.mtbmle.data.repository.VoiceTranslationEngine
import com.palash.mtbmle.ui.theme.PalashTextSecondary
import com.palash.mtbmle.ui.components.CosmicBackground
import com.palash.mtbmle.ui.components.CosmicMicButton
import com.palash.mtbmle.ui.components.CosmicPanel
import com.palash.mtbmle.ui.theme.CosmicMint
import com.palash.mtbmle.ui.theme.CosmicTeal
import com.palash.mtbmle.ui.theme.CosmicCyan
import com.palash.mtbmle.ui.theme.CosmicText
import com.palash.mtbmle.viewmodel.ConversationTurn
import com.palash.mtbmle.viewmodel.VoiceViewModel

/**
 * Voice Conversation screen (roadmap Sections 10-13) — green/teal/cyan region of the galaxy.
 *
 * The mic button is the single interaction point. All ASR -> MT -> TTS complexity is
 * hidden behind VoiceViewModel + VoiceTranslationEngine — this file only ever renders
 * whatever VoiceProcessingStatus currently is.
 */
@Composable
fun VoiceScreen(
    viewModel: VoiceViewModel = viewModel {
        VoiceViewModel(
            VoiceTranslationEngine(
                asr = MockSpeechRecognitionEngine(),
                translator = MockTranslationEngine(),
                tts = MockSpeechSynthesisEngine()
            )
        )
    }
) {
    val uiState by viewModel.uiState.collectAsState()

    CosmicBackground(accentColor = CosmicMint, secondaryAccent = CosmicTeal, tertiaryAccent = CosmicCyan) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Voice Mission", color = CosmicText, style = MaterialTheme.typography.headlineMedium)
        Text("Hindi → Santhali", style = MaterialTheme.typography.titleMedium, color = PalashTextSecondary)

        Box(contentAlignment = Alignment.Center) {
            val isRecording = uiState.status == VoiceProcessingStatus.RECORDING
            CosmicMicButton(
                icon = Icons.Filled.Mic,
                isListening = isRecording,
                accentColor = CosmicTeal,
                listeningColor = CosmicMint,
                onClick = viewModel::onMicTapped
            )
        }

        Text(
            text = statusLabel(uiState.status),
            style = MaterialTheme.typography.titleLarge
        )

        if (uiState.status == VoiceProcessingStatus.PROCESSING) {
            CircularProgressIndicator(color = CosmicTeal)
        }

        if (uiState.status == VoiceProcessingStatus.DONE) {
            CosmicPanel(accentColor = CosmicMint) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Recognized Hindi", style = MaterialTheme.typography.labelLarge, color = PalashTextSecondary)
                    Text(uiState.recognizedHindiText ?: "", style = MaterialTheme.typography.bodyLarge)

                    Text("Santhali", style = MaterialTheme.typography.labelLarge, color = PalashTextSecondary)
                    Text(uiState.translatedSanthaliText ?: "", style = MaterialTheme.typography.bodyLarge)

                    Text(
                        "Demo processing time: ${(uiState.processingTimeMillis ?: 0) / 1000.0} sec",
                        style = MaterialTheme.typography.bodyMedium,
                        color = PalashTextSecondary
                    )
                }
            }
        }

        if (uiState.errorMessage != null) {
            Text(uiState.errorMessage!!, color = MaterialTheme.colorScheme.error)
        }

        if (uiState.history.isNotEmpty()) {
            Text("Conversation", style = MaterialTheme.typography.titleMedium)
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                uiState.history.takeLast(5).forEach { turn -> ConversationRow(turn) }
            }
            TextButton(onClick = viewModel::onClearConversation) {
                Text("Clear Conversation")
            }
        }
    }
    }
}

@Composable
private fun ConversationRow(turn: ConversationTurn) {
    Column {
        Text("Teacher", style = MaterialTheme.typography.labelLarge, color = PalashTextSecondary)
        Text(turn.hindiText, style = MaterialTheme.typography.bodyMedium)
        Text("Santhali", style = MaterialTheme.typography.labelLarge, color = PalashTextSecondary)
        Text(turn.santhaliText, style = MaterialTheme.typography.bodyMedium)
    }
}

private fun statusLabel(status: VoiceProcessingStatus): String = when (status) {
    VoiceProcessingStatus.IDLE -> "Tap to Speak"
    VoiceProcessingStatus.RECORDING -> "Listening…"
    VoiceProcessingStatus.PROCESSING -> "Translating…"
    VoiceProcessingStatus.SPEAKING -> "Speaking…"
    VoiceProcessingStatus.DONE -> "Done"
    VoiceProcessingStatus.ERROR -> "Something went wrong"
}