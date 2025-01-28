package com.dev.properti.ui.viewmodel.jenisproperti

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.properti.repository.JenisRepository
import kotlinx.coroutines.launch


class UpdateJenisViewModel(
    savedStateHandle: SavedStateHandle,
    private val jenisRepository: JenisRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertJenisUiState())
        private set

    private val _id_jenis: Int = checkNotNull(savedStateHandle["id_jenis"])

    init {
        viewModelScope.launch {
            val jenisProperti = jenisRepository.getJenisById(_id_jenis)
            uiState = InsertJenisUiState(insertJenisUiEvent = jenisProperti.data.toInsertJenisUiEvent())
        }
    }

    fun updateInsertJenisState(insertJenisUiEvent: InsertJenisUiEvent) {
        uiState = InsertJenisUiState(insertJenisUiEvent = insertJenisUiEvent)
    }

    suspend fun updateJenis() {
        viewModelScope.launch {
            try {
                jenisRepository.updateJenis(_id_jenis, uiState.insertJenisUiEvent.toJenis())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}