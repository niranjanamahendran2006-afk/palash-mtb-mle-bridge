
/**
 * Orchestrates the mic-tap -> Listening -> Translating -> Speaking -> Done flow.
 * Depends only on VoiceTranslationEngine, which itself composes the three swappable
 * engine interfaces (ASR / MT / TTS) — see data/repository/VoiceTranslationEngine.kt.
 */

package com.palash.mtbmle.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palash.mtbmle.data.model.VoiceProcessingStatus
import com.palash.mtbmle.data.repository.AudioInput
import com.palash.mtbmle.data.repository.VoiceTranslationEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ConversationTurn(
    val hindiText: String,
    val santhaliText: String,
    val santhaliDevanagari: String
)

data class VoiceUiState(
    val status: VoiceProcessingStatus = VoiceProcessingStatus.IDLE,
    val recognizedHindiText: String? = null,
    val translatedSanthaliText: String? = null,
    val translatedSanthaliDevanagari: String? = null,
    val processingTimeMillis: Long? = null,
    val history: List<ConversationTurn> = emptyList(),
    val errorMessage: String? = null
)

class VoiceViewModel(private val voiceTranslationEngine: VoiceTranslationEngine) : ViewModel() {

    private val _uiState = MutableStateFlow(VoiceUiState())
    val uiState: StateFlow<VoiceUiState> = _uiState.asStateFlow()

    fun onMicTapped() {
        if (_uiState.value.status == VoiceProcessingStatus.RECORDING) return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                status = VoiceProcessingStatus.RECORDING,
                errorMessage = null
            )

            kotlinx.coroutines.delay(1200)

            _uiState.value = _uiState.value.copy(status = VoiceProcessingStatus.PROCESSING)

            try {
                val result = voiceTranslationEngine.translateVoice(AudioInput(durationMillis = 1200))

                _uiState.value = _uiState.value.copy(status = VoiceProcessingStatus.SPEAKING)
                kotlinx.coroutines.delay(400)

                _uiState.value = _uiState.value.copy(
                    status = VoiceProcessingStatus.DONE,
                    recognizedHindiText = result.recognizedHindiText,
                    translatedSanthaliText = result.translatedSanthaliText,
                    translatedSanthaliDevanagari = result.translatedSanthaliDevanagari,
                    processingTimeMillis = result.processingTimeMillis,
                    history = _uiState.value.history + ConversationTurn(
                        hindiText = result.recognizedHindiText,
                        santhaliText = result.translatedSanthaliText,
                        santhaliDevanagari = result.translatedSanthaliDevanagari
                    )
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    status = VoiceProcessingStatus.ERROR,
                    errorMessage = "Translation could not be completed. Please try again."
                )
            }
        }
    }

    fun onClearConversation() {
        _uiState.value = _uiState.value.copy(history = emptyList())
    }
}
