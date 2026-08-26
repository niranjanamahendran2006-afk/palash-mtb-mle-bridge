package com.palash.mtbmle.data.repository

import kotlinx.coroutines.delay

class MockSpeechSynthesisEngine : SpeechSynthesisEngine {
    override suspend fun synthesize(santhaliText: String): SynthesisResult {
        delay(500) // simulated TTS latency — real MMS-TTS target is ~0.5-1.0s
        // Prototype does not actually play audio; UI shows a "Speaking..." state only.
        return SynthesisResult(audioAvailable = false, isDemoResult = true)
    }
}
