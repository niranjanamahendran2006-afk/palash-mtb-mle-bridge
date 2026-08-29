

/**
 * Voice Conversation screen (roadmap Sections 10-13).
 *
 * The mic button is the single interaction point. All ASR -> MT -> TTS complexity is
 * hidden behind VoiceViewModel + VoiceTranslationEngine — this file only ever renders
 * whatever VoiceProcessingStatus currently is.
 */

package com.palash.mtbmle.ui.screens.voice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.palash.mtbmle.data.model.VoiceProcessingStatus
import com.palash.mtbmle.data.repository.MockSpeechRecognitionEngine
import com.palash.mtbmle.data.repository.MockSpeechSynthesisEngine
import com.palash.mtbmle.data.repository.MockTranslationEngine
import com.palash.mtbmle.data.repository.VoiceTranslationEngine
import com.palash.mtbmle.ui.theme.PalashAmberAccent
import com.palash.mtbmle.ui.theme.PalashGreenPrimary
import com.palash.mtbmle.ui.theme.PalashTextSecondary
import com.palash.mtbmle.viewmodel.ConversationTurn
import com.palash.mtbmle.viewmodel.VoiceViewModel

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Voice Conversation", style = MaterialTheme.typography.headlineMedium)
        Text("Hindi → Santhali", style = MaterialTheme.typography.titleMedium, color = PalashTextSecondary)

        Box(contentAlignment = Alignment.Center) {
            val isRecording = uiState.status == VoiceProcessingStatus.RECORDING
            IconButton(
                onClick = viewModel::onMicTapped,
                modifier = Modifier
                    .size(120.dp)
                    .background(
                        color = if (isRecording) PalashAmberAccent else PalashGreenPrimary,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Filled.Mic,
                    contentDescription = "Tap to speak",
                    tint = Color.White,
                    modifier = Modifier.size(52.dp)
                )
            }
        }

        Text(
            text = statusLabel(uiState.status),
            style = MaterialTheme.typography.titleLarge
        )

        if (uiState.status == VoiceProcessingStatus.PROCESSING) {
            CircularProgressIndicator(color = PalashGreenPrimary)
        }

        if (uiState.status == VoiceProcessingStatus.DONE) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Recognized Hindi", style = MaterialTheme.typography.labelLarge, color = PalashTextSecondary)
                    Text(uiState.recognizedHindiText ?: "", style = MaterialTheme.typography.bodyLarge)

                    Text("Santhali (Ol Chiki)", style = MaterialTheme.typography.labelLarge, color = PalashTextSecondary)
                    Text(uiState.translatedSanthaliText ?: "", style = MaterialTheme.typography.bodyLarge)

                    Text("How to read it (Devanagari)", style = MaterialTheme.typography.labelLarge, color = PalashTextSecondary)
                    Text(uiState.translatedSanthaliDevanagari ?: "", style = MaterialTheme.typography.bodyLarge)

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

@Composable
private fun ConversationRow(turn: ConversationTurn) {
    Column {
        Text("Teacher", style = MaterialTheme.typography.labelLarge, color = PalashTextSecondary)
        Text(turn.hindiText, style = MaterialTheme.typography.bodyMedium)
        Text("Santhali (Ol Chiki)", style = MaterialTheme.typography.labelLarge, color = PalashTextSecondary)
        Text(turn.santhaliText, style = MaterialTheme.typography.bodyMedium)
        Text("How to read it", style = MaterialTheme.typography.labelLarge, color = PalashTextSecondary)
        Text(turn.santhaliDevanagari, style = MaterialTheme.typography.bodyMedium)
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