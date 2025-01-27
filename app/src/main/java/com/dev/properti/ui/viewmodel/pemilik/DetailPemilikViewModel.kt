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
                val pemilik = pemilikRepository.getPemilikById(_id_pemilik)
                pemilikDetailState = DetailPemilikUiState.Success(pemilik)
            } catch (e: IOException) {
                // Tangani error jaringan atau masalah I/O
                pemilikDetailState = DetailPemilikUiState.Error
            } catch (e: HttpException) {
                // Tangani error terkait HTTP (misalnya 404, 500, dll)
                pemilikDetailState = DetailPemilikUiState.Error
            }
        }
    }
}