package com.palash.mtbmle.data.repository

/**
 * SPEECH SYNTHESIS (TTS) ENGINE CONTRACT
 * ----------------------------------------
 * Input: Santhali text.
 * Output: playable audio (represented as a simple result marker for the prototype).
 *
 * TODO(ML team): Replace MockSpeechSynthesisEngine with an MmsTtsEngine implementation,
 * subject to Meta MMS checkpoint availability for Santhali — verify this before committing
 * engineering time (see roadmap Section: "Tech Stack & System Architecture").
 */
interface SpeechSynthesisEngine {
    suspend fun synthesize(santhaliText: String): SynthesisResult
}

data class SynthesisResult(
    val audioAvailable: Boolean,
    val isDemoResult: Boolean = true
)
