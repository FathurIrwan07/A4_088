package com.dev.properti.ui.view.properti

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dev.properti.ui.costumwidget.TopAppBar
import com.dev.properti.navigation.DestinasiNavigasi
import com.dev.properti.ui.viewmodel.properti.InsertPropertiUiEvent
import com.dev.properti.ui.viewmodel.properti.InsertPropertiUiState
import com.dev.properti.ui.viewmodel.properti.InsertPropertiViewModel
import com.dev.properti.ui.viewmodel.properti.DropdownOptionJenis
import com.dev.properti.ui.viewmodel.properti.DropdownOptionPemilik
import com.dev.properti.ui.viewmodel.properti.DropdownOptionManajer
import kotlinx.coroutines.launch

object DestinasiInsertProperti : DestinasiNavigasi {
    override val route = "insert_properti"
    override val titleRes = "Insert Properti"
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertPropertiView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPropertiViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiInsertProperti.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyProperti(
            insertPropertiUiState = viewModel.uiState,
            onPropertiValueChange = viewModel::updateInsertPropertiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertProperti()
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
fun EntryBodyProperti(
    insertPropertiUiState: InsertPropertiUiState,
    onPropertiValueChange: (InsertPropertiUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputProperti(
            insertPropertiUiEvent = insertPropertiUiState.insertPropertiUiEvent,
            jenisOptions = insertPropertiUiState.jenisOption,
            pemilikOptions = insertPropertiUiState.pemilikOption,
            manajerOptions = insertPropertiUiState.manajerOption,
            onValueChange = onPropertiValueChange,
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
fun FormInputProperti(
    insertPropertiUiEvent: InsertPropertiUiEvent,
    jenisOptions: List<DropdownOptionJenis>,
    pemilikOptions: List<DropdownOptionPemilik>,
    manajerOptions: List<DropdownOptionManajer>,
    modifier: Modifier = Modifier,
    onValueChange: (InsertPropertiUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    val statusProperti = listOf("Tersedia", "Tersewa", "Dijual")

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        InputField(
            value = insertPropertiUiEvent.nama_properti,
            label = "Nama Properti",
            onValueChange = { onValueChange(insertPropertiUiEvent.copy(nama_properti = it)) },
            enabled = enabled
        )

        InputField(
            value = insertPropertiUiEvent.deskripsi_properti,
            label = "Deskripsi Properti",
            onValueChange = { onValueChange(insertPropertiUiEvent.copy(deskripsi_properti = it)) },
            enabled = enabled
        )

        InputField(
            value = insertPropertiUiEvent.lokasi,
            label = "Lokasi",
            onValueChange = { onValueChange(insertPropertiUiEvent.copy(lokasi = it)) },
            enabled = enabled
        )

        InputField(
            value = insertPropertiUiEvent.harga,
            label = "Harga",
            keyboardType = KeyboardType.Number,
            onValueChange = { onValueChange(insertPropertiUiEvent.copy(harga = it)) },
            enabled = enabled
        )

        DropDownField(
            title = "Pilih Jenis Properti",
            options = jenisOptions.map { it.label },
            selectedOption = jenisOptions.find { it.id_jenis.toIntOrNull() == insertPropertiUiEvent.id_jenis }?.label
                ?: "",
            onOptionSelected = { label ->
                val selectedJenis = jenisOptions.find { it.label == label }
                onValueChange(insertPropertiUiEvent.copy(id_jenis = selectedJenis?.id_jenis?.toInt()))
            }
        )

        DropDownField(
            title = "Pilih Pemilik Properti",
            options = pemilikOptions.map { it.label },
            selectedOption = pemilikOptions.find { it.id_pemilik.toIntOrNull() == insertPropertiUiEvent.id_pemilik }?.label
                ?: "",
            onOptionSelected = { label ->
                val selectedPemilik = pemilikOptions.find { it.label == label }
                onValueChange(insertPropertiUiEvent.copy(id_pemilik = selectedPemilik?.id_pemilik?.toInt()))
            }
        )

        DropDownField(
            title = "Pilih Manajer Properti",
            options = manajerOptions.map { it.label },
            selectedOption = manajerOptions.find { it.id_manajer.toIntOrNull() == insertPropertiUiEvent.id_manajer }?.label
                ?: "",
            onOptionSelected = { label ->
                val selectedManajer = manajerOptions.find { it.label == label }
                onValueChange(insertPropertiUiEvent.copy(id_manajer = selectedManajer?.id_manajer?.toInt()))
            }
        )

        Text(text = "Status Properti")
        statusProperti.forEach { status ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = insertPropertiUiEvent.status_properti == status,
                    onClick = { onValueChange(insertPropertiUiEvent.copy(status_properti = status)) }
                )
                Text(text = status)
            }
        }
    }
}

@Composable
fun InputField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

@Composable
fun DropDownField(
    title: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var currentSelected by remember { mutableStateOf(selectedOption) }

    Column {
        OutlinedTextField(
            value = currentSelected,
            onValueChange = {},
            readOnly = true,
            label = { Text(text = title) },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        onOptionSelected(option)
                        currentSelected = option
                        expanded = false
                    }
                )
            }
        }
    }
}
