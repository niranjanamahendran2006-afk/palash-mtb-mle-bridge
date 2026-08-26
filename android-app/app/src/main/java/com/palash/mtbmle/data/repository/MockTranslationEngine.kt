package com.palash.mtbmle.data.repository

import com.palash.mtbmle.data.mock.MockTranslationData
import kotlinx.coroutines.delay

/**
 * Prototype-only translation engine.
 *
 * Looks up the input sentence against the local demo dataset (data/mock/MockTranslationData.kt).
 * If no exact match is found, it returns a clearly-labelled placeholder rather than pretending
 * to have translated it — the roadmap explicitly forbids presenting mock output as validated
 * or model-generated.
 *
 * Simulates a short processing delay so the UI's loading state ("Translating...") is meaningful
 * to test, similar to what real on-device inference latency will feel like.
 */
class MockTranslationEngine : TranslationEngine {

    override suspend fun translate(hindiText: String): TranslationResult {
        delay(700) // simulated processing time — NOT a benchmark of the real pipeline

        val match = MockTranslationData.examples.firstOrNull {
            it.hindi.trim() == hindiText.trim()
        }

        return if (match != null) {
            TranslationResult(
                hindiText = match.hindi,
                santhaliText = match.santhali,
                confidence = null, // prototype: no real confidence score exists yet
                isDemoResult = true
            )
        } else {
            TranslationResult(
                hindiText = hindiText,
                santhaliText = "[Demo translation unavailable for this sentence — " +
                    "add it to MockTranslationData.kt, or connect the real NLLB engine]",
                confidence = null,
                isDemoResult = true
            )
        }
    }
}
