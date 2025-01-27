package com.dev.properti.ui.view.pemilik

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dev.properti.navigation.DestinasiNavigasi
import com.dev.properti.ui.viewmodel.PenyediaViewModel
import com.dev.properti.ui.viewmodel.pemilik.InsertPemilikViewModel
import com.dev.properti.ui.costumwidget.TopAppBar
import com.dev.properti.ui.viewmodel.pemilik.InsertPemilikUiEvent
import com.dev.properti.ui.viewmodel.pemilik.InsertPemilikUiState
import kotlinx.coroutines.launch

object DestinasiInsertPemilik : DestinasiNavigasi {
    override val route = "insert_pemilik"
    override val titleRes = "Insert Pemilik"
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertPemilikView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPemilikViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {

    Button(onClick = { navigateBack() }) {
        Text("Kembali")
    }

    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiInsertPemilik.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        InserBodyPemilik(
            insertPemilikUiState = viewModel.uiState,
            onPemilikValueChange = viewModel::updateInsertPemilikState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPemilik()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun InserBodyPemilik(
    insertPemilikUiState: InsertPemilikUiState,
    onPemilikValueChange: (InsertPemilikUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputPemilik(
            insertPemilikUiEvent = insertPemilikUiState.insertPemilikUiEvent,
            onValueChange = onPemilikValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@Composable
fun FormInputPemilik(
    insertPemilikUiEvent: InsertPemilikUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertPemilikUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertPemilikUiEvent.nama_pemilik,
            onValueChange = { onValueChange(insertPemilikUiEvent.copy(nama_pemilik = it)) },
            label = { Text("Nama Pemilik") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertPemilikUiEvent.kontak_pemilik,
            onValueChange = { onValueChange(insertPemilikUiEvent.copy(kontak_pemilik = it)) },
            label = { Text("Kontak Pemilik") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
    }
}