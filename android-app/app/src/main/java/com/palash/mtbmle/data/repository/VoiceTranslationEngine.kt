package com.palash.mtbmle.data.repository

import com.palash.mtbmle.data.model.VoiceProcessingStatus
import com.palash.mtbmle.data.model.VoiceTranslationResult

class VoiceTranslationEngine(
    private val asr: SpeechRecognitionEngine,
    private val translator: TranslationEngine,
    private val tts: SpeechSynthesisEngine
) {
    suspend fun translateVoice(audio: AudioInput): VoiceTranslationResult {
        val startTime = System.currentTimeMillis()

        val hindiText = asr.recognize(audio)
        val translation = translator.translate(hindiText)
        tts.synthesize(translation.santhaliOlChikiText)

        val elapsed = System.currentTimeMillis() - startTime

        return VoiceTranslationResult(
            recognizedHindiText = hindiText,
            translatedSanthaliText = translation.santhaliOlChikiText,
            translatedSanthaliDevanagari = translation.santhaliDevanagariText,
            processingTimeMillis = elapsed,
            status = VoiceProcessingStatus.DONE,
            isDemoResult = true
        )
    }
}