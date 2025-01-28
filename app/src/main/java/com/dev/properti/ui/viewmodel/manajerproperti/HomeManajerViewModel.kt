package com.dev.properti.ui.viewmodel.manajerproperti

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.dev.properti.model.ManajerProperti
import com.dev.properti.repository.ManajerRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class HomeManajerUiState {
    data class Success(val manajerProperti: List<ManajerProperti>) : HomeManajerUiState()
    object Error : HomeManajerUiState()
    object Loading : HomeManajerUiState()
}

class HomeManajerViewModel(private val manajerRepository: ManajerRepository) : ViewModel() {
    var mjrUiState: HomeManajerUiState by mutableStateOf(HomeManajerUiState.Loading)
        private set

    init {
        getMjr()
    }

    fun getMjr() {
        viewModelScope.launch {
            mjrUiState = HomeManajerUiState.Loading
            mjrUiState = try {
                HomeManajerUiState.Success(manajerRepository.getAllManajer().data)
            } catch (e: IOException) {
                HomeManajerUiState.Error
            } catch (e: HttpException) {
                HomeManajerUiState.Error
            }
        }
    }

    fun deleteMjr(id: Int) {
        viewModelScope.launch {
            try {
                manajerRepository.deleteManajer(id)
                // Refresh data after deletion
                getMjr()
            } catch (e: IOException) {
                mjrUiState = HomeManajerUiState.Error
            } catch (e: HttpException) {
                mjrUiState = HomeManajerUiState.Error
            }
        }
    }
}
