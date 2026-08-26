package com.palash.mtbmle.data.repository

/**
 * SPEECH RECOGNITION (ASR) ENGINE CONTRACT
 * -----------------------------------------
 * Input: raw microphone audio (represented here as a simple marker type for the
 * prototype, since no real audio capture/model is wired in yet).
 * Output: recognized Hindi text.
 *
 * TODO(ML team): Replace MockSpeechRecognitionEngine with VoskSpeechRecognitionEngine
 * or a Whisper-tiny based implementation, loading a quantized model from assets/models/.
 */
interface SpeechRecognitionEngine {
    suspend fun recognize(audio: AudioInput): String
}

/** Placeholder audio representation for the prototype — replace with real PCM/WAV buffer later. */
class AudioInput(val durationMillis: Long)
