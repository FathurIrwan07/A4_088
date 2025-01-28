package com.dev.properti.ui.view.properti

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dev.properti.ui.viewmodel.PenyediaViewModel
import com.dev.properti.ui.viewmodel.properti.DetailPropertiViewModel

object DestinasiDetailProperti {
    const val route = "detail_properti"
    const val titleRes = "Detail Properti"
    const val IDPPR = "id_properti"
    val routeWithArgs = "$route/{$IDPPR}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPropertiView(
    id_properti: Int,
    onEditClick: (String) -> Unit = { },
    onDeleteClick: (String) -> Unit = { },
    onBackClick: () -> Unit = { },
    modifier: Modifier = Modifier,
    viewModel: DetailPropertiViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val properti = viewModel.propertiUiState.detailpropertiUiEvent

    LaunchedEffect(id_properti) {
        viewModel.fetchDetailProperti(id_properti)
    }

    val isLoading = viewModel.propertiUiState.isLoading
    val isError = viewModel.propertiUiState.isError
    val errorMessage = viewModel.propertiUiState.errorMessage

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(DestinasiDetailProperti.titleRes) },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(properti.id_properti.toString()) },
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Data")
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                else if (isError) {
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else if (viewModel.propertiUiState.isUiEventNotEmpty) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(8.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                // Use Row for each detail with label and value aligned
                                DetailRowMtg(label = "ID Properti", value = properti.id_properti.toString())
                                DetailRowMtg(label = "Nama Properti", value = properti.nama_properti.toString())
                                DetailRowMtg(label = "Deskripsi", value = properti.deskripsi_properti)
                                DetailRowMtg(label = "Lokasi", value = properti.lokasi)
                                DetailRowMtg(label = "Harga", value = properti.harga)
                                DetailRowMtg(label = "Status Properti", value = properti.status_properti)
                                DetailRowMtg(label = "nama Jenis", value = properti.id_jenis.toString())
                                DetailRowMtg(label = "Status", value = properti.id_pemilik.toString())
                                DetailRowMtg(label = "Status", value = properti.id_manajer.toString())

                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun DetailRowMtg(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = ": $value",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(2f)
        )
    }
}


