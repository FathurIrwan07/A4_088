package com.dev.properti.ui.viewmodel.pemilik

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.dev.properti.model.Pemilik
import com.dev.properti.repository.PemilikRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomePemilikUiState {
    data class Success(val pemilik: List<Pemilik>) : HomePemilikUiState()
    object Error : HomePemilikUiState()
    object Loading : HomePemilikUiState()
}

class HomePemilikViewModel(private val pemilikRepository: PemilikRepository) : ViewModel() {
    var pemiliktUiState: HomePemilikUiState by mutableStateOf(HomePemilikUiState.Loading)
        private set

    init {
        getPemilik()
    }

    fun getPemilik() {
        viewModelScope.launch {
            pemiliktUiState = HomePemilikUiState.Loading
            pemiliktUiState = try {
                HomePemilikUiState.Success(pemilikRepository.getAllPemilik().data)
            } catch (e: IOException) {
                HomePemilikUiState.Error
            } catch (e: HttpException) {
                HomePemilikUiState.Error
            }
        }
    }

    fun deletePemilik(id: Int) {
        viewModelScope.launch {
            try {
                pemilikRepository.deletePemilik(id)
                // Refresh data after deletion
                getPemilik()
            } catch (e: IOException) {
                pemiliktUiState = HomePemilikUiState.Error
            } catch (e: HttpException) {
                pemiliktUiState = HomePemilikUiState.Error
            }
        }
    }
}