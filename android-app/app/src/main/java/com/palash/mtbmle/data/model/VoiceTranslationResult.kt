

/**
 * The result contract for one voice-translation turn.
 *
 * This is the exact shape the ML/NLP lead's real pipeline (Vosk/Whisper -> NLLB -> MMS-TTS)
 * must eventually return. The Android app depends ONLY on this data class — it never
 * needs to know whether the values came from MockVoiceTranslationEngine or the real one.
 */
package com.palash.mtbmle.data.model

data class VoiceTranslationResult(
    val recognizedHindiText: String,
    val translatedSanthaliText: String,
    val translatedSanthaliDevanagari: String,
    val processingTimeMillis: Long,
    val status: VoiceProcessingStatus,
    val isDemoResult: Boolean = true
)

enum class VoiceProcessingStatus {
    IDLE,
    RECORDING,
    PROCESSING,
    SPEAKING,
    DONE,
    ERROR
}
