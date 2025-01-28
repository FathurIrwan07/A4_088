package com.dev.properti.ui.viewmodel.jenisproperti

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.dev.properti.model.JenisProperti
import com.dev.properti.repository.JenisRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeJenisUiState {
    data class Success(val jenisProperti: List<JenisProperti>) : HomeJenisUiState()
    object Error : HomeJenisUiState()
    object Loading : HomeJenisUiState()
}

class HomeJenisViewModel(private val jenisRepository: JenisRepository) : ViewModel() {
    var jenisUiState: HomeJenisUiState by mutableStateOf(HomeJenisUiState.Loading)
        private set

    init {
        getJenis()
    }

    fun getJenis() {
        viewModelScope.launch {
            jenisUiState = HomeJenisUiState.Loading
            jenisUiState = try {
                HomeJenisUiState.Success(jenisRepository.getAllJenis().data)
            } catch (e: IOException) {
                HomeJenisUiState.Error
            } catch (e: HttpException) {
                HomeJenisUiState.Error
            }
        }
    }

    fun deleteJenis(id: Int) {
        viewModelScope.launch {
            try {
                jenisRepository.deleteJenis(id)
                // Refresh data after deletion
                getJenis()
            } catch (e: IOException) {
                jenisUiState = HomeJenisUiState.Error
            } catch (e: HttpException) {
                jenisUiState = HomeJenisUiState.Error
            }
        }
    }
}