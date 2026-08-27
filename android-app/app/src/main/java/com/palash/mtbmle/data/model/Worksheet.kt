/**
 * A bilingual worksheet, aligned to a NIPUN Bharat FLN learning outcome.
 *
 * `filePath` is null in the prototype (no real PDF/image is generated yet).
 * When the Python/Jinja2 worksheet generator (Role C) is wired in, it should
 * populate filePath with the exported PDF/PNG so WorksheetPreviewScreen can
 * render/share/print the real file instead of the in-app mock preview.
 */
package com.palash.mtbmle.data.model

data class Worksheet(
    val id: String,
    val title: String,
    val learningOutcome: LearningOutcome,
    val hindiContent: List<String>,
    val santhaliOlChikiContent: List<String>,
    val santhaliDevanagariContent: List<String>,
    val filePath: String? = null,
    val isDemoContent: Boolean = true
)

enum class LearningOutcome(val displayName: String) {
    LETTER_SOUND_RECOGNITION("Letter Sound Recognition"),
    BASIC_NUMBER_RECOGNITION("Basic Number Recognition"),
    WORD_RECOGNITION("Word Recognition"),
    ORAL_LANGUAGE_READING("Oral Language & Reading Decoding")
}