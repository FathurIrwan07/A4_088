package com.dev.properti.ui.viewmodel.properti

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.properti.repository.PemilikRepository
import com.dev.properti.repository.PropertiRepository
import com.dev.properti.ui.view.properti.DestinasiUpdateProperti
import kotlinx.coroutines.launch

class UpdatePropertiViewModel(
    savedStateHandle: SavedStateHandle,
    private val propertiRepository: PropertiRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertPropertiUiState())
        private set

    private val _id_properti: String = checkNotNull(savedStateHandle[DestinasiUpdateProperti.IDPPR])

    init {
        loadPropertiData()
    }

    private fun loadPropertiData() {
        viewModelScope.launch {
            try {
                val properti = propertiRepository.getPropertiById(_id_properti.toInt()).data
                uiState = uiState.copy(
                    insertPropertiUiEvent = properti?.toInsertPropertiUiEvent() ?: InsertPropertiUiEvent()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateInsertPropertiState(insertPropertiUiEvent: InsertPropertiUiEvent) {
        uiState = uiState.copy(insertPropertiUiEvent = insertPropertiUiEvent)
    }

    suspend fun updateProperti() {
        viewModelScope.launch {
            try {
                propertiRepository.updateProperti(_id_properti.toInt(), uiState.insertPropertiUiEvent.toProperti())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
