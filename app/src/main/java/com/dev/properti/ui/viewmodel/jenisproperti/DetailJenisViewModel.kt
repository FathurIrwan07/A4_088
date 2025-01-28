package com.dev.properti.ui.viewmodel.jenisproperti

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.properti.model.JenisProperti
import com.dev.properti.model.JenisPropertiResponseDetail
import com.dev.properti.model.Pemilik
import com.dev.properti.repository.JenisRepository
import com.dev.properti.ui.view.jenisproperti.DestinasiDetailJenis
import com.dev.properti.ui.view.pemilik.DestinasiDetailPemilik
import com.dev.properti.ui.viewmodel.manajerproperti.DetailManajerUiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailJenisUiState {
    data class Success(val jenisProperti: JenisProperti) : DetailJenisUiState()
    object Error : DetailJenisUiState()
    object Loading : DetailJenisUiState()
}

class DetailJenisViewModel(
    savedStateHandle: SavedStateHandle,
    private val jenisRepository: JenisRepository
) : ViewModel() {

    var jenisDetailState: DetailJenisUiState by mutableStateOf(DetailJenisUiState.Loading)
        private set

    private val _id_jenis: Int = savedStateHandle[DestinasiDetailJenis.IDJNS]
        ?: throw IllegalArgumentException("ID Jenis harus disediakan dan berupa angka yang valid")

    init {
        getJenisById()
    }

    fun getJenisById() {
        viewModelScope.launch {
            jenisDetailState = DetailJenisUiState.Loading
            try {
                val response = jenisRepository.getJenisById(_id_jenis)
                println("Respons dari backend: $response") // Tambahkan log
                jenisDetailState = DetailJenisUiState.Success(response.data)
            } catch (e: IOException) {
                println("Error jaringan: ${e.message}") // Log error
                jenisDetailState = DetailJenisUiState.Error
            } catch (e: HttpException) {
                println("Error HTTP: ${e.code()} - ${e.message()}") // Log error HTTP
                jenisDetailState = DetailJenisUiState.Error
            }
        }
    }
}
