package com.dev.properti.ui.view.pemilik

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
import com.dev.properti.model.Pemilik
import com.dev.properti.ui.viewmodel.PenyediaViewModel
import com.dev.properti.ui.viewmodel.pemilik.DetailPemilikViewModel
import com.dev.properti.ui.costumwidget.TopAppBar
import com.dev.properti.ui.viewmodel.pemilik.DetailPemilikUiState

object DestinasiDetailPemilik {
    const val route = "detail_pemilik"
    const val titleRes = "Detail Pemilik"
    const val IDPMK = "id_pemilik"
    val routesWithArg = "$route/{$IDPMK}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPemilikView(
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailPemilikViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = DestinasiDetailPemilik.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getPemilikById()
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
                    contentDescription = "Edit Pemilik"
                )
            }
        }
    ) { innerPadding ->
        DetailStatusPemilik(
            modifier = Modifier.padding(innerPadding),
            detailPemilikUiState = viewModel.pemilikDetailState,
            retryAction = { viewModel.getPemilikById() }
        )
    }
}

@Composable
fun DetailStatusPemilik(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailPemilikUiState: DetailPemilikUiState
) {
    when (detailPemilikUiState) {
        is DetailPemilikUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is DetailPemilikUiState.Success -> {
            if (detailPemilikUiState.pemilik.nama_pemilik.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan.")
                }
            } else {
                ItemDetailPemilik(
                    pemilik = detailPemilikUiState.pemilik,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }

        is DetailPemilikUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailPemilik(
    modifier: Modifier = Modifier,
    pemilik: Pemilik
) {
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailPemilik(judul = "ID Pemilik", isinya = pemilik.id_pemilik.toString())
            ComponentDetailPemilik(judul = "Nama Pemilik", isinya = pemilik.nama_pemilik)
            ComponentDetailPemilik(judul = "Kontak", isinya = pemilik.kontak_pemilik)
        }
    }
}

@Composable
fun ComponentDetailPemilik(
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