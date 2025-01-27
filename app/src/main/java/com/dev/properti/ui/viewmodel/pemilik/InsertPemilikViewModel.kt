package com.dev.properti.ui.viewmodel.pemilik

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.properti.model.Pemilik
import com.dev.properti.repository.PemilikRepository
import kotlinx.coroutines.launch

class InsertPemilikViewModel(private val pemilikRepository: PemilikRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertPemilikUiState())
        private set

    fun updateInsertPemilikState(insertPemilikUiEvent: InsertPemilikUiEvent) {
        uiState = InsertPemilikUiState(insertPemilikUiEvent = insertPemilikUiEvent)
    }

    fun insertPemilik() {
        viewModelScope.launch {
            try {
                pemilikRepository.insertPemilik(uiState.insertPemilikUiEvent.toPemilik())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertPemilikUiState(
    val insertPemilikUiEvent: InsertPemilikUiEvent = InsertPemilikUiEvent()
)

data class InsertPemilikUiEvent(
    val id_pemilik: Int = 0,
    val nama_pemilik: String = "",
    val kontak_pemilik: String = ""
)

fun InsertPemilikUiEvent.toPemilik(): Pemilik = Pemilik(
    id_pemilik = id_pemilik,
    nama_pemilik = nama_pemilik,
    kontak_pemilik = kontak_pemilik
)

fun Pemilik.toInsertPemilikUiEvent(): InsertPemilikUiEvent = InsertPemilikUiEvent(
    id_pemilik = id_pemilik,
    nama_pemilik = nama_pemilik,
    kontak_pemilik = kontak_pemilik
)