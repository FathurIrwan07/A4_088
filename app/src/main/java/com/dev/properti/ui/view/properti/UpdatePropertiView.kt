package com.dev.properti.ui.view.properti

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dev.properti.ui.costumwidget.TopAppBar
import com.dev.properti.ui.view.manajerproperti.EntryBodyManajer
import com.dev.properti.ui.view.pemilik.InserBodyPemilik
import com.dev.properti.ui.viewmodel.PenyediaViewModel
import com.dev.properti.ui.viewmodel.manajerproperti.UpdateManajerViewModel
import com.dev.properti.ui.viewmodel.pemilik.UpdatePemilikViewModel
import com.dev.properti.ui.viewmodel.properti.UpdatePropertiViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdateProperti {
    const val route = "update_properti"
    const val titleRes = "Update Properti"
    const val IDPPR = "id_properti"
    val routesWithArg = "$route/{$IDPPR}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePropertiView(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: UpdatePropertiViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiUpdateProperti.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ) { padding ->
        EntryBodyProperti(
            modifier = Modifier.padding(padding),
            insertPropertiUiState = viewModel.uiState,
            onPropertiValueChange = viewModel::updateInsertPropertiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateProperti()
                    delay(600)
                    withContext(Dispatchers.Main) {
                        onNavigate()
                    }
                }
            }
        )
    }
}