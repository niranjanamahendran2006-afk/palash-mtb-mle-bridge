/** Worksheet list + generator screen (roadmap Sections 14-16). */
package com.palash.mtbmle.ui.screens.worksheet

import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.palash.mtbmle.data.model.LearningOutcome
import com.palash.mtbmle.data.model.Worksheet
import com.palash.mtbmle.data.repository.WorksheetRepository
import com.palash.mtbmle.ui.components.PalashLoadingState
import com.palash.mtbmle.ui.components.PalashPrimaryButton
import com.palash.mtbmle.ui.theme.PalashTextSecondary
import com.palash.mtbmle.viewmodel.WorksheetViewModel
import java.io.File

@Composable
fun WorksheetScreen() {
    val context = LocalContext.current
    val viewModel: WorksheetViewModel = viewModel {
        WorksheetViewModel(WorksheetRepository(context = context.applicationContext))
    }
    val uiState by viewModel.uiState.collectAsState()

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
                PalashPrimaryButton(
                    text = "Generate Worksheet",
                    enabled = uiState.selectedOutcome != null,
                    onClick = viewModel::onGenerateClicked
                )
            }
        }

        if (uiState.isGenerating) {
            item { PalashLoadingState(label = "Preparing worksheet…") }
        }

        uiState.generatedWorksheet?.let { ws ->
            item { WorksheetPreviewCard(ws, context) }
        }

        item { Text("Existing Worksheets", style = MaterialTheme.typography.titleLarge) }

        items(uiState.worksheets) { ws -> WorksheetListCard(ws) }
    }
}

@Composable
private fun WorksheetListCard(worksheet: Worksheet) {
    Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(worksheet.title, style = MaterialTheme.typography.titleMedium)
            Text("Hindi + Santhali", style = MaterialTheme.typography.bodyMedium, color = PalashTextSecondary)
        }
    }
}

@Composable
private fun WorksheetPreviewCard(worksheet: Worksheet, context: android.content.Context) {
    Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("PALASH — Bilingual Learning Worksheet", style = MaterialTheme.typography.titleMedium)
            Text("Learning Outcome: ${worksheet.learningOutcome.displayName}", style = MaterialTheme.typography.bodyMedium)
            worksheet.hindiContent.zip(worksheet.santhaliOlChikiContent).forEach { (hi, sat) ->
                Text("$hi   →   $sat", style = MaterialTheme.typography.bodyLarge)
            }

            if (worksheet.filePath != null) {
                Text("PDF ready ✓", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
                TextButton(onClick = { openPdf(context, worksheet.filePath) }) { Text("Open PDF") }
                TextButton(onClick = { sharePdf(context, worksheet.filePath) }) { Text("Share PDF") }
            }
        }
    }
}

private fun openPdf(context: android.content.Context, filePath: String) {
    val file = File(filePath)
    val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, "application/pdf")
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    context.startActivity(Intent.createChooser(intent, "Open worksheet PDF"))
}

private fun sharePdf(context: android.content.Context, filePath: String) {
    val file = File(filePath)
    val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "application/pdf"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    context.startActivity(Intent.createChooser(intent, "Share worksheet PDF"))
}
