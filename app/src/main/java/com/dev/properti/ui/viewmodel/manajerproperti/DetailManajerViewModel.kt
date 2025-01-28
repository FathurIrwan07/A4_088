package com.dev.properti.ui.viewmodel.manajerproperti

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.properti.model.ManajerProperti
import com.dev.properti.repository.ManajerRepository
import com.dev.properti.ui.view.manajerproperti.DestinasiDetailManajer
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailManajerUiState {
    data class Success(val manajerProperti: ManajerProperti) : DetailManajerUiState()
    object Error : DetailManajerUiState()
    object Loading : DetailManajerUiState()
}

class DetailManajerViewModel(
    savedStateHandle: SavedStateHandle,
    private val manajerRepository: ManajerRepository
) : ViewModel() {

    var mjrDetailState: DetailManajerUiState by mutableStateOf(DetailManajerUiState.Loading)
        private set

    private val _id_manajer: Int = savedStateHandle[DestinasiDetailManajer.IDMJR]
        ?: throw IllegalArgumentException("ID Manajer harus disediakan dan berupa angka yang valid")

    init {
        getManajerById()
    }

    fun getManajerById() {
        viewModelScope.launch {
            mjrDetailState = DetailManajerUiState.Loading
            try {
                val response = manajerRepository.getManajerById(_id_manajer)
                println("Respons dari backend: $response") // Tambahkan log
                mjrDetailState = DetailManajerUiState.Success(response.data)
            } catch (e: IOException) {
                println("Error jaringan: ${e.message}") // Log error
                mjrDetailState = DetailManajerUiState.Error
            } catch (e: HttpException) {
                println("Error HTTP: ${e.code()} - ${e.message()}") // Log error HTTP
                mjrDetailState = DetailManajerUiState.Error
            }
        }
    }

}
