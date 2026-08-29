package com.palash.mtbmle.ui.screens.translate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.palash.mtbmle.data.repository.MockTranslationEngine
import com.palash.mtbmle.ui.components.PalashLoadingState
import com.palash.mtbmle.ui.components.PalashPrimaryButton
import com.palash.mtbmle.ui.theme.PalashError
import com.palash.mtbmle.ui.theme.PalashGreenPrimary
import com.palash.mtbmle.ui.theme.PalashTextSecondary
import com.palash.mtbmle.viewmodel.TranslateViewModel

@Composable
fun TranslateScreen(
    viewModel: TranslateViewModel = viewModel { TranslateViewModel(MockTranslationEngine()) }
) {
    val uiState by viewModel.uiState.collectAsState()
    val clipboardManager = LocalClipboardManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Translate", style = MaterialTheme.typography.headlineMedium)
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

        PalashPrimaryButton(text = "Translate", onClick = viewModel::onTranslateClicked)

        if (uiState.isLoading) {
            PalashLoadingState(label = "Translating…")
        }

        uiState.result?.let { result ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("SOURCE · Hindi", style = MaterialTheme.typography.labelLarge, color = PalashTextSecondary)
                    Text(result.hindiText, style = MaterialTheme.typography.bodyLarge)

                    Text("TARGET · Santhali (Ol Chiki)", style = MaterialTheme.typography.labelLarge, color = PalashTextSecondary)
                    Text(result.santhaliOlChikiText, style = MaterialTheme.typography.bodyLarge)

                    Text("How to read it (Devanagari)", style = MaterialTheme.typography.labelLarge, color = PalashTextSecondary)
                    Text(result.santhaliDevanagariText, style = MaterialTheme.typography.bodyLarge)

                    if (uiState.isSpeaking) {
                        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                            CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp, color = PalashGreenPrimary)
                            Text("  Speaking…", style = MaterialTheme.typography.bodyMedium, color = PalashTextSecondary)
                        }
                    }

                    if (uiState.copiedMessage != null) {
                        Text(uiState.copiedMessage!!, style = MaterialTheme.typography.bodyMedium, color = PalashGreenPrimary)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextButton(onClick = viewModel::onPlayClicked) { Text("🔊 Play") }
                        TextButton(onClick = {
                            clipboardManager.setText(
                                AnnotatedString(
                                    "${result.hindiText}\n${result.santhaliOlChikiText}\n${result.santhaliDevanagariText}"
                                )
                            )
                            viewModel.onCopiedConfirmed()
                        }) { Text("Copy") }
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