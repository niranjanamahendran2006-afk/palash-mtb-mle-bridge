/**
 * Predefined demo worksheet templates. Replace with real output from the
 * Python/Jinja2 worksheet generator (see /worksheet-generator) once available —
 * that generator should populate Worksheet.filePath with an actual PDF/PNG.
 */
package com.palash.mtbmle.data.mock

import com.palash.mtbmle.data.model.LearningOutcome
import com.palash.mtbmle.data.model.Worksheet

object MockWorksheetData {
    val worksheets = listOf(
        Worksheet(
            id = "ws_oral_language_1",
            title = "Oral Language & Reading Decoding",
            learningOutcome = LearningOutcome.ORAL_LANGUAGE_READING,
            hindiContent = listOf(
                "बच्चों, अपनी किताब खोलो।",
                "आज हम गिनती सीखेंगे।",
                "यह कौन सा रंग है?",
                "बहुत बढ़िया, तुमने सही जवाब दिया।"
            ),
            santhaliOlChikiContent = listOf(
                "ᱜᱤᱫᱽᱨᱟᱹ ᱠᱚ, ᱟᱯᱱᱟᱨᱟᱜ ᱯᱩᱛᱷᱤ ᱡᱷᱤᱡᱽ ᱯᱮ।",
                "ᱛᱮᱦᱮᱧ ᱵᱚᱱ ᱞᱮᱠᱷᱟ ᱵᱚᱱ ᱪᱮᱫᱟ।",
                "ᱱᱚᱣᱟ ᱫᱚ ᱚᱠᱟ ᱨᱚᱝ ᱠᱟᱱᱟ?",
                "ᱟᱹᱰᱤ ᱵᱮᱥ, ᱟᱢ ᱥᱟᱹᱦᱤ ᱨᱚᱲ ᱠᱮᱫᱟ।"
            ),
            santhaliDevanagariContent = listOf(
                "गिद्रा को, अपनाराग् पुथि झिज् पे।",
                "तेहेञ् बोन् लेखा बोन् चेदा।",
                "नोवा दो ओका रंग काना?",
                "अड़ि बेस, आम् सही रोड़ केदा।"
            )
        ),
        Worksheet(
            id = "ws_letter_sound_1",
            title = "Letter Sound Recognition",
            learningOutcome = LearningOutcome.LETTER_SOUND_RECOGNITION,
            hindiContent = listOf("अ", "आ", "इ"),
            santhaliOlChikiContent = listOf("[demo]", "[demo]", "[demo]"),
            santhaliDevanagariContent = listOf("[demo]", "[demo]", "[demo]")
        ),
        Worksheet(
            id = "ws_number_1",
            title = "Basic Number Recognition",
            learningOutcome = LearningOutcome.BASIC_NUMBER_RECOGNITION,
            hindiContent = listOf("एक", "दो", "तीन"),
            santhaliOlChikiContent = listOf("[demo]", "[demo]", "[demo]"),
            santhaliDevanagariContent = listOf("[demo]", "[demo]", "[demo]")
        ),
        Worksheet(
            id = "ws_word_1",
            title = "Word Recognition",
            learningOutcome = LearningOutcome.WORD_RECOGNITION,
            hindiContent = listOf("किताब", "कलम", "पानी"),
            santhaliOlChikiContent = listOf("[demo]", "[demo]", "[demo]"),
            santhaliDevanagariContent = listOf("[demo]", "[demo]", "[demo]")
        )
    )
}
