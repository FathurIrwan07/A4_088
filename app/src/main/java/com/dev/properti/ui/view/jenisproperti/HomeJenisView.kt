package com.dev.properti.ui.view.jenisproperti

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dev.properti.R
import com.dev.properti.model.JenisProperti
import com.dev.properti.model.Pemilik
import com.dev.properti.navigation.DestinasiNavigasi
import com.dev.properti.ui.viewmodel.PenyediaViewModel
import com.dev.properti.ui.viewmodel.pemilik.HomePemilikViewModel
import com.dev.properti.ui.costumwidget.TopAppBar
import com.dev.properti.ui.viewmodel.jenisproperti.HomeJenisUiState
import com.dev.properti.ui.viewmodel.jenisproperti.HomeJenisViewModel
import com.dev.properti.ui.viewmodel.pemilik.HomePemilikUiState


object DestinasiHomeJenis : DestinasiNavigasi {
    override val route = "home_jenis"
    override val titleRes = "Home Jenis"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeJenisView(
    navigateToItemEntry: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeJenisViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiHomeJenis.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getJenis()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp),
                containerColor = MaterialTheme.colorScheme.primary // Menggunakan warna utama untuk FAB
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Jenis")
            }
        },
    ) { innerPadding ->
        HomeStatusJenis(
            homeJenisUiState = viewModel.jenisUiState,
            retryAction = { viewModel.getJenis() },
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = 30.dp), // Menambahkan padding untuk memberi ruang pada TopAppBar
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteJenis(it.id_jenis)
                viewModel.getJenis()
            }
        )
    }
}

@Composable
fun HomeStatusJenis(
    homeJenisUiState: HomeJenisUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (JenisProperti) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (homeJenisUiState) {
        is HomeJenisUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeJenisUiState.Success -> {
            if (homeJenisUiState.jenisProperti.isEmpty()) {
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak Ada Data Jenis", color = MaterialTheme.colorScheme.onBackground)
                }
            } else {
                JenisLayout(
                    jenisProperti = homeJenisUiState.jenisProperti, modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_jenis.toString()) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is HomeJenisUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.connection_error), contentDescription = "")
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp), color = MaterialTheme.colorScheme.onBackground)
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun JenisLayout(
    jenisProperti: List<JenisProperti>,
    modifier: Modifier = Modifier,
    onDetailClick: (JenisProperti) -> Unit,
    onDeleteClick: (JenisProperti) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(jenisProperti) { jenisProperti ->
            JenisCard(
                jenisProperti = jenisProperti,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(jenisProperti) },
                onDeleteClick = { onDeleteClick(jenisProperti) }
            )
        }
    }
}

@Composable
fun JenisCard(
    jenisProperti: JenisProperti,
    modifier: Modifier = Modifier,
    onDeleteClick: (JenisProperti) -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                showDialog = false
                onDeleteClick(jenisProperti)
            },
            onDeleteCancel = { showDialog = false }
        )
    }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFF2196F3), Color(0xFF64B5F6)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = jenisProperti.nama_jenis,
                        style = MaterialTheme.typography.titleLarge.copy(color = Color.White),
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = { showDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error // Warna ikon delete sesuai dengan tema error
                        )
                    }
                }
                Text(
                    text = jenisProperti.deskripsi_jenis,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
                )
            }
        }
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes")
            }
        }
    )
}