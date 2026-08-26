package com.palash.mtbmle.data.repository

import com.palash.mtbmle.data.mock.MockWorksheetData
import com.palash.mtbmle.data.model.LearningOutcome
import com.palash.mtbmle.data.model.Worksheet
import kotlinx.coroutines.delay

/**
 * Provides worksheets to the UI. Prototype implementation returns predefined mock templates.
 *
 * TODO(Data/Backend team): Replace the in-memory list below with output from the real
 * Python/Jinja2 worksheet generator (see /worksheet-generator in the repo root), producing
 * actual PDF/PNG files whose paths populate Worksheet.filePath.
 */
class WorksheetRepository {

    fun getAllWorksheets(): List<Worksheet> = MockWorksheetData.worksheets

    suspend fun generateWorksheet(outcome: LearningOutcome): Worksheet {
        delay(900) // simulated "Preparing worksheet..." generation time
        return MockWorksheetData.worksheets.firstOrNull { it.learningOutcome == outcome }
            ?: MockWorksheetData.worksheets.first()
    }
}
