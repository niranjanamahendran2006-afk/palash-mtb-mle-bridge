package com.palash.mtbmle.data.model

/**
 * App-wide settings shown on the Settings screen.
 * sourceLanguage/targetLanguage are fixed to Hindi -> Santhali for this prototype,
 * but kept as fields (not hardcoded strings in the UI) so additional languages
 * (Ho, Mundari) can be added later without restructuring the screen.
 */
data class AppSettings(
    val sourceLanguage: String = "Hindi",
    val targetLanguage: String = "Santhali",
    val offlineModeEnabled: Boolean = true,
    val initialSetupCompleted: Boolean = false
)

/** Data & Model Information block shown in Settings — all "planned", none implemented yet. */
data class ModelInfo(
    val translationModel: String = "NLLB-200 (planned integration)",
    val speechRecognitionModel: String = "Vosk / Whisper-tiny (planned integration)",
    val speechSynthesisModel: String = "Meta MMS-TTS (planned integration)"
)
