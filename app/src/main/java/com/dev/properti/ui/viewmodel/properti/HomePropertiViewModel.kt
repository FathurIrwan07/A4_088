package com.dev.properti.ui.viewmodel.properti

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.dev.properti.model.Properti
import com.dev.properti.repository.PropertiRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomePropertiUiState {
    data class Success(val properti: List<Properti>) : HomePropertiUiState()
    object Error : HomePropertiUiState()
    object Loading : HomePropertiUiState()
}

class HomePropertiViewModel(private val propertiRepository: PropertiRepository) : ViewModel() {
    var propertitUiState: HomePropertiUiState by mutableStateOf(HomePropertiUiState.Loading)
        private set

    init {
        getProperti()
    }

    fun getProperti() {
        viewModelScope.launch {
            propertitUiState = HomePropertiUiState.Loading
            propertitUiState = try {
                HomePropertiUiState.Success(propertiRepository.getAllProperti().data)
            } catch (e: IOException) {
                HomePropertiUiState.Error
            } catch (e: HttpException) {
                HomePropertiUiState.Error
            }
        }
    }

    fun deleteProperti(id: Int) {
        viewModelScope.launch {
            try {
                propertiRepository.deleteProperti(id)
                // Refresh data after deletion
                getProperti()
            } catch (e: IOException) {
                propertitUiState = HomePropertiUiState.Error
            } catch (e: HttpException) {
                propertitUiState = HomePropertiUiState.Error
            }
        }
    }
}