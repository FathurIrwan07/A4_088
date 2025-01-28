package com.dev.properti.ui.viewmodel.manajerproperti

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.properti.model.ManajerProperti
import com.dev.properti.repository.ManajerRepository
import kotlinx.coroutines.launch

class InsertManajerViewModel(private val manajerRepository: ManajerRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertManajerUiState())
        private set

    fun updateInsertManajerState(insertManajertUiEvent: InsertManajertUiEvent) {
        uiState = InsertManajerUiState(insertManajertUiEvent = insertManajertUiEvent)
    }

    fun insertManajer() {
        viewModelScope.launch {
            try {
                manajerRepository.insertManajer(uiState.insertManajertUiEvent.toManajer())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertManajerUiState(
    val insertManajertUiEvent: InsertManajertUiEvent = InsertManajertUiEvent()
)

data class InsertManajertUiEvent(
    val id_manajer: Int = 0,
    val nama_manajer: String = "",
    val kontak_manajer: String = ""
)

fun InsertManajertUiEvent.toManajer(): ManajerProperti = ManajerProperti(
    id_manajer = id_manajer,
    nama_manajer = nama_manajer,
    kontak_manajer = kontak_manajer
)

fun ManajerProperti.toInsertManajertUiEvent(): InsertManajertUiEvent = InsertManajertUiEvent(
    id_manajer = id_manajer,
    nama_manajer = nama_manajer,
    kontak_manajer = kontak_manajer
)



