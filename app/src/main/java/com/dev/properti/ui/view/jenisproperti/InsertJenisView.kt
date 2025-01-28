package com.dev.properti.ui.view.jenisproperti

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
import com.dev.properti.ui.costumwidget.TopAppBar
import com.dev.properti.ui.viewmodel.jenisproperti.InsertJenisUiEvent
import com.dev.properti.ui.viewmodel.jenisproperti.InsertJenisUiState
import com.dev.properti.ui.viewmodel.jenisproperti.InsertJenisViewModel
import kotlinx.coroutines.launch

object DestinasiInsertJenis : DestinasiNavigasi {
    override val route = "insert_jenis"
    override val titleRes = "Insert Jenis"
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertJenisView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertJenisViewModel = viewModel(factory = PenyediaViewModel.Factory)
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
                title = DestinasiInsertJenis.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        InserBodyJenis(
            insertJenisUiState = viewModel.uiState,
            onJenisValueChange = viewModel::updateInsertJenisState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertJenis()
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
fun InserBodyJenis(
    insertJenisUiState: InsertJenisUiState,
    onJenisValueChange: (InsertJenisUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputJenis(
            insertJenisUiEvent = insertJenisUiState.insertJenisUiEvent,
            onValueChange = onJenisValueChange,
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
fun FormInputJenis(
    insertJenisUiEvent: InsertJenisUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertJenisUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertJenisUiEvent.nama_jenis,
            onValueChange = { onValueChange(insertJenisUiEvent.copy(nama_jenis = it)) },
            label = { Text("Nama Jenis") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertJenisUiEvent.deskripsi_jenis,
            onValueChange = { onValueChange(insertJenisUiEvent.copy(deskripsi_jenis = it)) },
            label = { Text("Deskripsi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
           // keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
    }
}