package com.dev.properti.ui.viewmodel.manajerproperti

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.properti.repository.ManajerRepository
import kotlinx.coroutines.launch

class UpdateManajerViewModel(
    savedStateHandle: SavedStateHandle,
    private val manajerRepository: ManajerRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertManajerUiState())
        private set

    private val _id_manajer: Int = checkNotNull(savedStateHandle["id_manajer"])

    init {
        viewModelScope.launch {
            val manajerProperti = manajerRepository.getManajerById(_id_manajer)
            uiState = InsertManajerUiState(insertManajertUiEvent = manajerProperti.data.toInsertManajertUiEvent())
        }
    }

    fun updateInsertMjrState(insertManajertUiEvent: InsertManajertUiEvent) {
        uiState = InsertManajerUiState(insertManajertUiEvent = insertManajertUiEvent)
    }

    suspend fun updateMjr() {
        viewModelScope.launch {
            try {
                manajerRepository.updateManajer(_id_manajer, uiState.insertManajertUiEvent.toManajer())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
