package com.palash.mtbmle.data.repository

import com.palash.mtbmle.data.mock.MockVoiceData
import kotlinx.coroutines.delay

class MockSpeechRecognitionEngine : SpeechRecognitionEngine {
    override suspend fun recognize(audio: AudioInput): String {
        delay(600) // simulated ASR latency — real Vosk/Whisper target is ~0.5-1.0s
        return MockVoiceData.demoHindiPhrases.random()
    }
}
