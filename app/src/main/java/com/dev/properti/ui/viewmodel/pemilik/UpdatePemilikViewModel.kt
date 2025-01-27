package com.dev.properti.ui.viewmodel.pemilik

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.properti.repository.PemilikRepository
import kotlinx.coroutines.launch

class UpdatePemilikViewModel(
    savedStateHandle: SavedStateHandle,
    private val pemilikRepository: PemilikRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertPemilikUiState())
        private set

    private val _id_pemilik: Int = checkNotNull(savedStateHandle["id_pemilik"])

    init {
        viewModelScope.launch {
            val pemilik = pemilikRepository.getPemilikById(_id_pemilik)
            uiState = InsertPemilikUiState(insertPemilikUiEvent = pemilik.toInsertPemilikUiEvent())
        }
    }

    fun updateInsertPemilikState(insertPemilikUiEvent: InsertPemilikUiEvent) {
        uiState = InsertPemilikUiState(insertPemilikUiEvent = insertPemilikUiEvent)
    }

    suspend fun updatePemilik() {
        viewModelScope.launch {
            try {
                pemilikRepository.updatePemilik(_id_pemilik, uiState.insertPemilikUiEvent.toPemilik())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}