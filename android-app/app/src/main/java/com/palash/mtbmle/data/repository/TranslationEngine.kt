package com.palash.mtbmle.data.repository

/**
 * TRANSLATION ENGINE CONTRACT
 * ---------------------------
 * This is the single boundary between the Android app and the ML team's work.
 * The UI/ViewModel layer talks ONLY to this interface — never to a concrete engine.
 *
 * Prototype implementation: MockTranslationEngine (below / see .kt file of same name)
 *
 * TODO(ML team): Replace MockTranslationEngine with NllbTranslationEngine, e.g.:
 *   class NllbTranslationEngine(context: Context) : TranslationEngine {
 *       // load quantized NLLB-200 .tflite/.onnx model from assets/models/
 *       // run on-device inference, no network calls
 *   }
 * No other file in the app needs to change when that swap happens.
 */
interface TranslationEngine {
    suspend fun translate(hindiText: String): TranslationResult
}

data class TranslationResult(
    val hindiText: String,
    val santhaliText: String,
    /** Null in the prototype — a real model may return an actual quality/confidence signal. */
    val confidence: Float? = null,
    val isDemoResult: Boolean = true
)
