package com.dev.properti.ui.view.manajerproperti

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
import com.dev.properti.ui.costumwidget.TopAppBar
import com.dev.properti.navigation.DestinasiNavigasi
import com.dev.properti.ui.viewmodel.manajerproperti.InsertManajertUiEvent
import com.dev.properti.ui.viewmodel.manajerproperti.InsertManajerUiState
import com.dev.properti.ui.viewmodel.manajerproperti.InsertManajerViewModel
import com.dev.properti.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiInsertManajer : DestinasiNavigasi {
    override val route = "insert_manajer"
    override val titleRes = "Insert Manajer"
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertManajerView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertManajerViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiInsertManajer.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyManajer(
            insertManajerUiState = viewModel.uiState,
            onManajerValueChange = viewModel::updateInsertManajerState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertManajer()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        )
    }
}

@Composable
fun EntryBodyManajer(
    insertManajerUiState: InsertManajerUiState,
    onManajerValueChange: (InsertManajertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputManajer(
            insertManajerUiEvent = insertManajerUiState.insertManajertUiEvent,
            onValueChange = onManajerValueChange,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputManajer(
    insertManajerUiEvent: InsertManajertUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertManajertUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertManajerUiEvent.nama_manajer,
            onValueChange = { onValueChange(insertManajerUiEvent.copy(nama_manajer = it)) },
            label = { Text("Nama Manajer") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertManajerUiEvent.kontak_manajer,
            onValueChange = { onValueChange(insertManajerUiEvent.copy(kontak_manajer = it)) },
            label = { Text("Kontak Manajer") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        if (enabled) {
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(20.dp)
        )
    }
}
