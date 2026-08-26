package com.palash.mtbmle.data.mock

import com.palash.mtbmle.data.model.LearningOutcome
import com.palash.mtbmle.data.model.Worksheet

/**
 * Predefined demo worksheet templates. Replace with real output from the
 * Python/Jinja2 worksheet generator (see /worksheet-generator) once available —
 * that generator should populate Worksheet.filePath with an actual PDF/PNG.
 */
object MockWorksheetData {
    val worksheets = listOf(
        Worksheet(
            id = "ws_letter_sound_1",
            title = "Letter Sound Recognition",
            learningOutcome = LearningOutcome.LETTER_SOUND_RECOGNITION,
            hindiContent = listOf("अ", "आ", "इ"),
            santhaliContent = listOf("[demo]", "[demo]", "[demo]")
        ),
        Worksheet(
            id = "ws_number_1",
            title = "Basic Number Recognition",
            learningOutcome = LearningOutcome.BASIC_NUMBER_RECOGNITION,
            hindiContent = listOf("एक", "दो", "तीन"),
            santhaliContent = listOf("[demo]", "[demo]", "[demo]")
        ),
        Worksheet(
            id = "ws_word_1",
            title = "Word Recognition",
            learningOutcome = LearningOutcome.WORD_RECOGNITION,
            hindiContent = listOf("किताब", "कलम", "पानी"),
            santhaliContent = listOf("[demo]", "[demo]", "[demo]")
        )
    )
}
