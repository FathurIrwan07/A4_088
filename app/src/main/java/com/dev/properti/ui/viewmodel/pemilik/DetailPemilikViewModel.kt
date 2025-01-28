package com.dev.properti.ui.viewmodel.pemilik

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.properti.model.Pemilik
import com.dev.properti.repository.PemilikRepository
import com.dev.properti.ui.view.pemilik.DestinasiDetailPemilik
import com.dev.properti.ui.viewmodel.manajerproperti.DetailManajerUiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailPemilikUiState {
    data class Success(val pemilik: Pemilik) : DetailPemilikUiState()
    object Error : DetailPemilikUiState()
    object Loading : DetailPemilikUiState()
}

class DetailPemilikViewModel(
    savedStateHandle: SavedStateHandle,
    private val pemilikRepository: PemilikRepository
) : ViewModel() {

    var pemilikDetailState: DetailPemilikUiState by mutableStateOf(DetailPemilikUiState.Loading)
        private set

    private val _id_pemilik: Int = savedStateHandle[DestinasiDetailPemilik.IDPMK]
        ?: throw IllegalArgumentException("ID Pemilik harus disediakan dan berupa angka yang valid")

    init {
        getPemilikById()
    }

    fun getPemilikById() {
        viewModelScope.launch {
            pemilikDetailState = DetailPemilikUiState.Loading
            try {
                val response = pemilikRepository.getPemilikById(_id_pemilik)
                println("Respons dari backend: $response") // Tambahkan log
                pemilikDetailState = DetailPemilikUiState.Success(response.data)
            } catch (e: IOException) {
                println("Error jaringan: ${e.message}") // Log error
                pemilikDetailState = DetailPemilikUiState.Error
            } catch (e: HttpException) {
                println("Error HTTP: ${e.code()} - ${e.message()}") // Log error HTTP
                pemilikDetailState = DetailPemilikUiState.Error
            }
        }
    }
}