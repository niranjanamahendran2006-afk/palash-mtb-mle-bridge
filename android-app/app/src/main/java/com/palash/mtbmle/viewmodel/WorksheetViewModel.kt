package com.palash.mtbmle.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.palash.mtbmle.data.model.LearningOutcome
import com.palash.mtbmle.data.model.Worksheet
import com.palash.mtbmle.data.repository.WorksheetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class WorksheetUiState(
    val worksheets: List<Worksheet> = emptyList(),
    val selectedOutcome: LearningOutcome? = null,
    val isGenerating: Boolean = false,
    val generatedWorksheet: Worksheet? = null,
    val errorMessage: String? = null
)

class WorksheetViewModel(private val worksheetRepository: WorksheetRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(WorksheetUiState())
    val uiState: StateFlow<WorksheetUiState> = _uiState.asStateFlow()

    init {
        _uiState.value = _uiState.value.copy(worksheets = worksheetRepository.getAllWorksheets())
    }

    fun onOutcomeSelected(outcome: LearningOutcome) {
        _uiState.value = _uiState.value.copy(selectedOutcome = outcome)
    }

    fun onGenerateClicked() {
        val outcome = _uiState.value.selectedOutcome ?: return
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isGenerating = true, errorMessage = null)
            try {
                val worksheet = worksheetRepository.generateWorksheet(outcome)
                _uiState.value = _uiState.value.copy(isGenerating = false, generatedWorksheet = worksheet)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isGenerating = false,
                    errorMessage = "Unable to create worksheet. Please try again."
                )
            }
        }
    }
}
