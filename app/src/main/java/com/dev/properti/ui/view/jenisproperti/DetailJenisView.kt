package com.dev.properti.ui.view.jenisproperti

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dev.properti.model.JenisProperti
import com.dev.properti.model.Pemilik
import com.dev.properti.ui.viewmodel.PenyediaViewModel
import com.dev.properti.ui.viewmodel.pemilik.DetailPemilikViewModel
import com.dev.properti.ui.costumwidget.TopAppBar
import com.dev.properti.ui.viewmodel.jenisproperti.DetailJenisUiState
import com.dev.properti.ui.viewmodel.jenisproperti.DetailJenisViewModel
import com.dev.properti.ui.viewmodel.pemilik.DetailPemilikUiState

object DestinasiDetailJenis {
    const val route = "detail_jenis"
    const val titleRes = "Detail Jenis"
    const val IDJNS = "id_jenis"
    val routesWithArg = "$route/{$IDJNS}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailJenisView(
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailJenisViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = DestinasiDetailJenis.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getJenisById()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemUpdate,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Jenis"
                )
            }
        }
    ) { innerPadding ->
        DetailStatusJenis(
            modifier = Modifier.padding(innerPadding),
            detailJenisUiState = viewModel.jenisDetailState,
            retryAction = { viewModel.getJenisById() }
        )
    }
}

@Composable
fun DetailStatusJenis(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailJenisUiState: DetailJenisUiState
) {
    when (detailJenisUiState) {
        is DetailJenisUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is DetailJenisUiState.Success -> {
            if (detailJenisUiState.jenisProperti.nama_jenis.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan.")
                }
            } else {
                ItemDetailJenis(
                    jenisProperti = detailJenisUiState.jenisProperti,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }

        is DetailJenisUiState.Error -> OnError(
            retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun ItemDetailJenis(
    modifier: Modifier = Modifier,
    jenisProperti: JenisProperti
) {
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailJenis(judul = "ID Jenis", isinya = jenisProperti.id_jenis.toString())
            ComponentDetailJenis(judul = "Nama Jenis", isinya = jenisProperti.nama_jenis)
            ComponentDetailJenis(judul = "Deskripsi", isinya = jenisProperti.deskripsi_jenis)
        }
    }
}

@Composable
fun ComponentDetailJenis(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}