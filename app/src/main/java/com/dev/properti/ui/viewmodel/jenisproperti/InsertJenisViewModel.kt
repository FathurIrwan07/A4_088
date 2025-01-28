package com.dev.properti.ui.viewmodel.jenisproperti


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.properti.model.JenisProperti
import com.dev.properti.repository.JenisRepository
import kotlinx.coroutines.launch

class InsertJenisViewModel(
    private val jenisRepository: JenisRepository
) : ViewModel() {

    // State untuk menyimpan data form
    var uiState by mutableStateOf(InsertJenisUiState())
        private set

    // Update state berdasarkan input dari pengguna
    fun updateInsertJenisState(event: InsertJenisUiEvent) {
        uiState = uiState.copy(insertJenisUiEvent = event)
    }

    // Simpan data ke repository
    fun insertJenis() {
        viewModelScope.launch {
            try {
                val jenisProperti = uiState.insertJenisUiEvent.toJenis()
                jenisRepository.insertJenis(jenisProperti)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


// State untuk UI form InsertJenis
data class InsertJenisUiState(
    val insertJenisUiEvent: InsertJenisUiEvent = InsertJenisUiEvent()
)

// Representasi data yang dimasukkan pengguna
data class InsertJenisUiEvent(
    val id_jenis: Int = 0,
    val nama_jenis: String = "",
    val deskripsi_jenis: String = ""
)

// Konversi dari InsertJenisUiEvent ke JenisProperti
fun InsertJenisUiEvent.toJenis(): JenisProperti = JenisProperti(
    id_jenis = id_jenis,
    nama_jenis = nama_jenis,
    deskripsi_jenis = deskripsi_jenis
)

// Konversi dari JenisProperti ke InsertJenisUiEvent
fun JenisProperti.toInsertJenisUiEvent(): InsertJenisUiEvent = InsertJenisUiEvent(
    id_jenis = id_jenis,
    nama_jenis = nama_jenis,
    deskripsi_jenis = deskripsi_jenis
)

