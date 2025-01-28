package com.dev.properti.ui.viewmodel.properti

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.properti.model.JenisProperti
import com.dev.properti.model.JenisPropertiResponse
import com.dev.properti.model.ManajerProperti
import com.dev.properti.model.ManajerPropertiResponse
import com.dev.properti.model.Pemilik
import com.dev.properti.model.PemilikResponse
import com.dev.properti.model.Properti
import com.dev.properti.repository.JenisRepository
import com.dev.properti.repository.ManajerRepository
import com.dev.properti.repository.PemilikRepository
import com.dev.properti.repository.PropertiRepository
import kotlinx.coroutines.launch

class InsertPropertiViewModel(
    private val propertiRepository: PropertiRepository,
    private val jenisRepository: JenisRepository,
    private val mjr: ManajerRepository, // Ini untuk mengambil data pemilik
    private val pmk: PemilikRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertPropertiUiState())
        private set


    init {
        loadDropdownData()
    }

    private fun loadDropdownData() {
        viewModelScope.launch {
            try {

                val jenisPropertiResponse: JenisPropertiResponse = jenisRepository.getAllJenis()
                val jenisPropertiList: List<JenisProperti> = jenisPropertiResponse.data

                val pemilikResponse: PemilikResponse = pmk.getAllPemilik()
                val pemilikList: List<Pemilik> = pemilikResponse.data

                val manajerPropertiResponse: ManajerPropertiResponse = mjr.getAllManajer()
                val manajerList: List<ManajerProperti> = manajerPropertiResponse.data

                uiState = uiState.copy(
                    jenisOption = jenisPropertiList.map {
                        it.toDropdownOptionJenis()
                    },
                    pemilikOption = pemilikList.map {
                        it.toDropdownOptionPemilik() // Memperbaiki penulisan method ini
                    },
                    manajerOption = manajerList.map {
                        it.toDropdownOptionManajer()
                    }
                )


            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateInsertPropertiState(insertPropertiUiEvent: InsertPropertiUiEvent) {
        uiState = InsertPropertiUiState(insertPropertiUiEvent = insertPropertiUiEvent)
    }

    fun insertProperti() {
        viewModelScope.launch {
            try {
                propertiRepository.insertProperti(uiState.insertPropertiUiEvent.toProperti())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertPropertiUiState(
    val insertPropertiUiEvent: InsertPropertiUiEvent = InsertPropertiUiEvent(),
    val jenisOption: List<DropdownOptionJenis> = emptyList(),
    val pemilikOption: List<DropdownOptionPemilik> = emptyList(),
    val manajerOption: List<DropdownOptionManajer> = emptyList()

)

data class InsertPropertiUiEvent(
    val id_properti: Int = 0,
    val nama_properti: String = "",
    val deskripsi_properti: String = "",
    val lokasi: String = "",
    val harga: String = "",
    val status_properti: String = "",
    val id_jenis: Int? = null,
    val id_pemilik: Int? = null,
    val id_manajer: Int? = null
)

fun InsertPropertiUiEvent.toProperti(): Properti = Properti(
    id_properti = id_properti,
    nama_properti = nama_properti,
    deskripsi_properti = deskripsi_properti,
    lokasi = lokasi,
    harga = harga,
    status_properti = status_properti,
    id_jenis = id_jenis,
    id_pemilik = id_pemilik,
    id_manajer = id_manajer
)

fun Properti.toInsertPropertiUiEvent(): InsertPropertiUiEvent = InsertPropertiUiEvent(
    id_properti = id_properti,
    nama_properti = nama_properti,
    deskripsi_properti = deskripsi_properti,
    lokasi = lokasi,
    harga = harga,
    status_properti = status_properti,
    id_jenis = id_jenis,
    id_pemilik = id_pemilik,
    id_manajer = id_manajer
)


//
data class DropdownOptionJenis(
    val id_jenis: String,
    val label: String
)

data class DropdownOptionPemilik(
    val id_pemilik: String,
    val label: String
)

data class DropdownOptionManajer(
    val id_manajer: String,
    val label: String
)

fun JenisProperti.toDropdownOptionJenis() = DropdownOptionJenis(
    id_jenis = id_jenis.toString(),
    label = nama_jenis
)

fun Pemilik.toDropdownOptionPemilik() = DropdownOptionPemilik(
    id_pemilik = id_pemilik.toString(),
    label = nama_pemilik
)

fun ManajerProperti.toDropdownOptionManajer() = DropdownOptionManajer(
    id_manajer = id_manajer.toString(),
    label = nama_manajer
)
