package com.palash.mtbmle.ui.screens.worksheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.palash.mtbmle.data.model.LearningOutcome
import com.palash.mtbmle.data.model.Worksheet
import com.palash.mtbmle.data.repository.WorksheetRepository
import com.palash.mtbmle.ui.components.PalashLoadingState
import com.palash.mtbmle.ui.components.PalashPrimaryButton
import com.palash.mtbmle.ui.theme.PalashTextSecondary
import com.palash.mtbmle.ui.components.CosmicBackground
import com.palash.mtbmle.ui.components.CosmicPanel
import com.palash.mtbmle.ui.theme.CosmicCoral
import com.palash.mtbmle.ui.theme.CosmicOrange
import com.palash.mtbmle.ui.theme.CosmicMagenta
import com.palash.mtbmle.viewmodel.WorksheetViewModel

/** Worksheet list + generator screen (roadmap Sections 14-16). */
@Composable
fun WorksheetScreen(
    viewModel: WorksheetViewModel = viewModel { WorksheetViewModel(WorksheetRepository()) }
) {
    val uiState by viewModel.uiState.collectAsState()

    CosmicBackground(accentColor = CosmicCoral, secondaryAccent = CosmicOrange, tertiaryAccent = CosmicMagenta) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("Worksheets", style = MaterialTheme.typography.headlineMedium)
            Text(
                "Bilingual worksheets aligned to NIPUN Bharat outcomes",
                style = MaterialTheme.typography.bodyMedium,
                color = PalashTextSecondary
            )
        }

        item { Text("Create Worksheet", style = MaterialTheme.typography.titleLarge) }

        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Learning Outcome", style = MaterialTheme.typography.labelLarge)
                LearningOutcome.entries.forEach { outcome ->
                    FilterChip(
                        selected = uiState.selectedOutcome == outcome,
                        onClick = { viewModel.onOutcomeSelected(outcome) },
                        label = { Text(outcome.displayName) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Text("Language: Hindi → Santhali", style = MaterialTheme.typography.bodyMedium, color = PalashTextSecondary)
                PalashPrimaryButton(
                    text = "Generate Worksheet",
                    enabled = uiState.selectedOutcome != null,
                    accentColor = CosmicOrange,
                    onClick = viewModel::onGenerateClicked
                )
            }
        }

        if (uiState.isGenerating) {
            item { PalashLoadingState(label = "Preparing worksheet…", accentColor = CosmicCoral) }
        }

        uiState.generatedWorksheet?.let { ws ->
            item { WorksheetPreviewCard(ws) }
        }

        item { Text("Existing Worksheets", style = MaterialTheme.typography.titleLarge) }

        items(uiState.worksheets) { ws -> WorksheetListCard(ws) }
    }
    }
}

@Composable
private fun WorksheetListCard(worksheet: Worksheet) {
    CosmicPanel(accentColor = CosmicOrange) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(worksheet.title, style = MaterialTheme.typography.titleMedium)
            Text("Hindi + Santhali", style = MaterialTheme.typography.bodyMedium, color = PalashTextSecondary)
            TextButton(onClick = { /* TODO: navigate to full WorksheetPreviewScreen with this worksheet id */ }) {
                Text("Open")
            }
        }
    }
}

@Composable
private fun WorksheetPreviewCard(worksheet: Worksheet) {
    CosmicPanel(accentColor = CosmicCoral) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("PALASH — Bilingual Learning Worksheet", style = MaterialTheme.typography.titleMedium)
            Text("Learning Outcome: ${worksheet.learningOutcome.displayName}", style = MaterialTheme.typography.bodyMedium)
            worksheet.hindiContent.zip(worksheet.santhaliContent).forEach { (hi, sat) ->
                Text("$hi   →   $sat", style = MaterialTheme.typography.bodyLarge)
            }
            Text(
                "Match the picture with the correct word.",
                style = MaterialTheme.typography.bodyMedium,
                color = PalashTextSecondary
            )
        }
    }
}