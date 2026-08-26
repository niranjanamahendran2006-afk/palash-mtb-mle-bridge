package com.palash.mtbmle.data.repository

import com.palash.mtbmle.data.model.VoiceProcessingStatus
import com.palash.mtbmle.data.model.VoiceTranslationResult

/**
 * Orchestrates the full voice pipeline:
 *   Microphone -> ASR -> Hindi text -> Translation -> Santhali text -> TTS -> Santhali audio
 *
 * This class composes the three engine interfaces above so the ViewModel (and UI) only ever
 * calls ONE function and gets ONE result back — it never touches ASR/MT/TTS individually.
 * This mirrors exactly the "Model Integration Contract" from the team roadmap (Section 29).
 */
class VoiceTranslationEngine(
    private val asr: SpeechRecognitionEngine,
    private val translator: TranslationEngine,
    private val tts: SpeechSynthesisEngine
) {
    suspend fun translateVoice(audio: AudioInput): VoiceTranslationResult {
        val startTime = System.currentTimeMillis()

        val hindiText = asr.recognize(audio)
        val translation = translator.translate(hindiText)
        tts.synthesize(translation.santhaliText)

        val elapsed = System.currentTimeMillis() - startTime

        return VoiceTranslationResult(
            recognizedHindiText = hindiText,
            translatedSanthaliText = translation.santhaliText,
            processingTimeMillis = elapsed,
            status = VoiceProcessingStatus.DONE,
            isDemoResult = true
        )
    }
}
