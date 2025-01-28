package com.dev.properti.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.dev.properti.PropertiApplications
import com.dev.properti.ui.viewmodel.jenisproperti.DetailJenisViewModel
import com.dev.properti.ui.viewmodel.jenisproperti.HomeJenisViewModel
import com.dev.properti.ui.viewmodel.jenisproperti.InsertJenisViewModel
import com.dev.properti.ui.viewmodel.jenisproperti.UpdateJenisViewModel
import com.dev.properti.ui.viewmodel.manajerproperti.DetailManajerViewModel
import com.dev.properti.ui.viewmodel.manajerproperti.HomeManajerViewModel
import com.dev.properti.ui.viewmodel.manajerproperti.InsertManajerViewModel
import com.dev.properti.ui.viewmodel.manajerproperti.UpdateManajerViewModel
import com.dev.properti.ui.viewmodel.pemilik.DetailPemilikViewModel
import com.dev.properti.ui.viewmodel.pemilik.HomePemilikViewModel
import com.dev.properti.ui.viewmodel.pemilik.InsertPemilikViewModel
import com.dev.properti.ui.viewmodel.pemilik.UpdatePemilikViewModel
import com.dev.properti.ui.viewmodel.properti.DetailPropertiViewModel
import com.dev.properti.ui.viewmodel.properti.HomePropertiViewModel
import com.dev.properti.ui.viewmodel.properti.InsertPropertiViewModel
import com.dev.properti.ui.viewmodel.properti.UpdatePropertiViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        // pemilik
        initializer {
            HomePemilikViewModel(PropertiApplications().container.pemilikRepository)
        }
        initializer {
            InsertPemilikViewModel(PropertiApplications().container.pemilikRepository) // PemilikRepository
        }
        initializer {
            DetailPemilikViewModel(createSavedStateHandle(), PropertiApplications().container.pemilikRepository) // PemilikRepository
        }
        initializer {
            UpdatePemilikViewModel(createSavedStateHandle(), PropertiApplications().container.pemilikRepository) // PemilikRepository
        }

        // manajer
        initializer {
            HomeManajerViewModel(PropertiApplications().container.manajerRepository)
        }
        initializer {
            InsertManajerViewModel(PropertiApplications().container.manajerRepository) // ManajerRepository
        }
        initializer {
            DetailManajerViewModel(createSavedStateHandle(), PropertiApplications().container.manajerRepository) // ManajerRepository
        }
        initializer {
            UpdateManajerViewModel(createSavedStateHandle(), PropertiApplications().container.manajerRepository) // ManajerRepository
        }

        // jenis
        initializer {
            HomeJenisViewModel(PropertiApplications().container.jenisRepository)
        }
        initializer {
            InsertJenisViewModel(PropertiApplications().container.jenisRepository)
        }
        initializer {
            DetailJenisViewModel(createSavedStateHandle(), PropertiApplications().container.jenisRepository)
        }
        initializer {
            UpdateJenisViewModel(createSavedStateHandle(), PropertiApplications().container.jenisRepository)
        }

        // properti
        initializer {
            HomePropertiViewModel(PropertiApplications().container.propertiRepository)
        }
        initializer {
            InsertPropertiViewModel(
                PropertiApplications().container.propertiRepository,
                PropertiApplications().container.jenisRepository,
                PropertiApplications().container.manajerRepository,
                PropertiApplications().container.pemilikRepository
            )
        }
        initializer {
            DetailPropertiViewModel(PropertiApplications().container.propertiRepository)
        }
        initializer {
            UpdatePropertiViewModel(createSavedStateHandle(), PropertiApplications().container.propertiRepository)
        }
    }
}


fun CreationExtras.PropertiApplications() : PropertiApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PropertiApplications)