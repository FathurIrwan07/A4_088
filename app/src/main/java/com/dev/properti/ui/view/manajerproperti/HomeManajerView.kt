package com.dev.properti.ui.view.manajerproperti

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
import com.dev.properti.model.ManajerProperti
import com.dev.properti.model.Pemilik
import com.dev.properti.navigation.DestinasiNavigasi
import com.dev.properti.ui.viewmodel.PenyediaViewModel
import com.dev.properti.ui.viewmodel.pemilik.HomePemilikViewModel
import com.dev.properti.ui.costumwidget.TopAppBar
import com.dev.properti.ui.viewmodel.manajerproperti.HomeManajerUiState
import com.dev.properti.ui.viewmodel.manajerproperti.HomeManajerViewModel
import com.dev.properti.ui.viewmodel.pemilik.HomePemilikUiState


object DestinasiHomeManajer : DestinasiNavigasi {
    override val route = "home_manajer"
    override val titleRes = "Home Manajer"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeManajerView(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeManajerViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiHomeManajer.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getMjr()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Manajer")
            }
        },
    ) { innerPadding ->
        HomeStatusManajer(
            homeManajerUiState = viewModel.mjrUiState,
            retryAction = { viewModel.getMjr() },
            modifier = Modifier
                .padding(innerPadding)
                .padding(top = 30.dp),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteMjr(it.id_manajer)
                viewModel.getMjr()
            }
        )
    }
}

@Composable
fun HomeStatusManajer(
    homeManajerUiState: HomeManajerUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (ManajerProperti) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (homeManajerUiState) {
        is HomeManajerUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeManajerUiState.Success -> {
            if (homeManajerUiState.manajerProperti.isEmpty()) {
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak Ada Data Manajer", color = MaterialTheme.colorScheme.onBackground)
                }
            } else {
                ManajerLayout(
                    manajerProperti = homeManajerUiState.manajerProperti, modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_manajer.toString()) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is HomeManajerUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
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
fun ManajerLayout(
    manajerProperti: List<ManajerProperti>,
    modifier: Modifier = Modifier,
    onDetailClick: (ManajerProperti) -> Unit,
    onDeleteClick: (ManajerProperti) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(manajerProperti) { manajerProperti ->
            ManajerCard(
                manajerProperti = manajerProperti,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(manajerProperti) },
                onDeleteClick = { onDeleteClick(manajerProperti) }
            )
        }
    }
}

@Composable
fun ManajerCard(
    manajerProperti: ManajerProperti,
    modifier: Modifier = Modifier,
    onDeleteClick: (ManajerProperti) -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                showDialog = false
                onDeleteClick(manajerProperti)
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
                            Color(0xFF9C27B0),
                            Color(0xFFBA68C8)
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
                        text = manajerProperti.nama_manajer,
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
                    text = manajerProperti.kontak_manajer,
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