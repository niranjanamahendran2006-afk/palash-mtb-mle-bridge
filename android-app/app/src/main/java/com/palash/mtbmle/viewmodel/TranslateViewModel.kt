package com.palash.mtbmle.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palash.mtbmle.data.repository.TranslationEngine
import com.palash.mtbmle.data.repository.TranslationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TranslateUiState(
    val inputText: String = "",
    val isLoading: Boolean = false,
    val result: TranslationResult? = null,
    val errorMessage: String? = null
)

/**
 * The Android developer only depends on the TranslationEngine INTERFACE here.
 * Swapping MockTranslationEngine -> NllbTranslationEngine at the call site
 * (see PalashApp.kt where this ViewModel is constructed) is the only change
 * ever needed to go from prototype to real ML.
 */
class TranslateViewModel(private val translationEngine: TranslationEngine) : ViewModel() {

    private val _uiState = MutableStateFlow(TranslateUiState())
    val uiState: StateFlow<TranslateUiState> = _uiState.asStateFlow()

    fun onInputChanged(text: String) {
        _uiState.value = _uiState.value.copy(inputText = text, errorMessage = null)
    }

    fun onTranslateClicked() {
        val text = _uiState.value.inputText.trim()
        if (text.isEmpty()) {
            _uiState.value = _uiState.value.copy(errorMessage = "Please enter a Hindi sentence.")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            try {
                val result = translationEngine.translate(text)
                _uiState.value = _uiState.value.copy(isLoading = false, result = result)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Translation could not be completed. Please try again."
                )
            }
        }
    }

    fun onClear() {
        _uiState.value = TranslateUiState()
    }
}
