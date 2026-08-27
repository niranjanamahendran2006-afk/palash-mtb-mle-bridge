
/**
 * Provides worksheets to the UI. Prototype implementation returns predefined mock templates.
 *
 * TODO(Data/Backend team): Replace the in-memory list below with output from the real
 * Python/Jinja2 worksheet generator (see /worksheet-generator in the repo root), producing
 * actual PDF/PNG files whose paths populate Worksheet.filePath.
 */
package com.palash.mtbmle.data.repository

import android.content.Context
import com.palash.mtbmle.data.mock.MockWorksheetData
import com.palash.mtbmle.data.model.LearningOutcome
import com.palash.mtbmle.data.model.Worksheet
import kotlinx.coroutines.delay

class WorksheetRepository(private val context: Context) {

    private val pdfGenerator = WorksheetPdfGenerator(context)

    fun getAllWorksheets(): List<Worksheet> = MockWorksheetData.worksheets

    suspend fun generateWorksheet(outcome: LearningOutcome): Worksheet {
        delay(900) // simulated "Preparing worksheet..." pause — the PDF itself is real

        val template = MockWorksheetData.worksheets.firstOrNull { it.learningOutcome == outcome }
            ?: MockWorksheetData.worksheets.first()

        val pdfFile = pdfGenerator.generate(template)

        return template.copy(filePath = pdfFile.absolutePath)
    }
}
