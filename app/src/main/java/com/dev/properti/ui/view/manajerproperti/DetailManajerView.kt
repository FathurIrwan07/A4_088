package com.dev.properti.ui.view.manajerproperti

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
import com.dev.properti.model.ManajerProperti
import com.dev.properti.ui.viewmodel.PenyediaViewModel
import com.dev.properti.ui.costumwidget.TopAppBar
import com.dev.properti.ui.viewmodel.manajerproperti.DetailManajerUiState
import com.dev.properti.ui.viewmodel.manajerproperti.DetailManajerViewModel


object DestinasiDetailManajer {
    const val route = "detail_manajer"
    const val titleRes = "Detail Manajer"
    const val IDMJR = "id_manajer"
    val routesWithArg = "$route/{$IDMJR}"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailManajerView(
    navigateBack: () -> Unit,
    navigateToItemUpdate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailManajerViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = DestinasiDetailManajer.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getManajerById()
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
                    contentDescription = "Edit Manajer"
                )
            }
        }
    ) { innerPadding ->
        DetailStatusManajer(
            modifier = Modifier.padding(innerPadding),
            detailManajerUiState = viewModel.mjrDetailState,
            retryAction = { viewModel.getManajerById() }
        )
    }
}

@Composable
fun DetailStatusManajer(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailManajerUiState: DetailManajerUiState
) {
    when (detailManajerUiState) {
        is DetailManajerUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is DetailManajerUiState.Success -> {
            if (detailManajerUiState.manajerProperti.nama_manajer.isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan.")
                }
            } else {
                ItemDetailManajer(
                    manajerProperti = detailManajerUiState.manajerProperti,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }

        is DetailManajerUiState.Error -> com.dev.properti.ui.view.manajerproperti.OnError(
            retryAction,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Composable
fun ItemDetailManajer(
    modifier: Modifier = Modifier,
    manajerProperti: ManajerProperti
) {
    Card(
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailManajer(judul = "ID Manajer", isinya = manajerProperti.id_manajer.toString())
            ComponentDetailManajer(judul = "Nama Manajer", isinya = manajerProperti.nama_manajer)
            ComponentDetailManajer(judul = "Kontak", isinya = manajerProperti.kontak_manajer)
        }
    }
}

@Composable
fun ComponentDetailManajer(
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
