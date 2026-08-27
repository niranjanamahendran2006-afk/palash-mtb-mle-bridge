

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
package com.palash.mtbmle.data.repository

import com.palash.mtbmle.data.mock.MockTranslationData
import kotlinx.coroutines.delay

class MockTranslationEngine : TranslationEngine {

    override suspend fun translate(hindiText: String): TranslationResult {
        delay(700) // simulated processing time — NOT a benchmark of the real pipeline

        val match = MockTranslationData.examples.firstOrNull {
            it.hindi.trim() == hindiText.trim()
        }

        return if (match != null) {
            TranslationResult(
                hindiText = match.hindi,
                santhaliOlChikiText = match.santhaliOlChiki,
                santhaliDevanagariText = match.santhaliDevanagari,
                confidence = null,
                isDemoResult = true
            )
        } else {
            TranslationResult(
                hindiText = hindiText,
                santhaliOlChikiText = "[Demo translation unavailable for this sentence]",
                santhaliDevanagariText = "[unavailable]",
                confidence = null,
                isDemoResult = true
            )
        }
    }
}
