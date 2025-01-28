package com.dev.properti.ui.viewmodel.properti

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.properti.repository.PropertiRepository
import kotlinx.coroutines.launch

class DetailPropertiViewModel (
private val propertiRepository: PropertiRepository
): ViewModel(){
    var propertiUiState by mutableStateOf(DetailPropertiUiState())
        private set

    fun fetchDetailProperti(id_properti : Int) {
        viewModelScope.launch {
            propertiUiState = DetailPropertiUiState(isLoading = true)
            try {
                val properti = propertiRepository.getPropertiById(id_properti).data

                propertiUiState = DetailPropertiUiState(detailpropertiUiEvent = properti.toInsertPropertiUiEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                propertiUiState = DetailPropertiUiState(isError = true, errorMessage = "Failed to fetch details: ${e.message}")
            }
        }
    }
}

data class DetailPropertiUiState(
    val detailpropertiUiEvent: InsertPropertiUiEvent = InsertPropertiUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
){
    val isUiEventNotEmpty: Boolean
        get() = detailpropertiUiEvent != InsertPropertiUiEvent()
}
