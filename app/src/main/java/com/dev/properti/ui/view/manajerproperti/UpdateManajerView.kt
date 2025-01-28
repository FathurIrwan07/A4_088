package com.dev.properti.ui.view.manajerproperti

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
import com.dev.properti.ui.view.pemilik.InserBodyPemilik
import com.dev.properti.ui.viewmodel.PenyediaViewModel
import com.dev.properti.ui.viewmodel.manajerproperti.UpdateManajerViewModel
import com.dev.properti.ui.viewmodel.pemilik.UpdatePemilikViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdateManajer {
    const val route = "update_manajer"
    const val titleRes = "Update Manajer"
    const val IDMJR = "id_manajer"
    val routesWithArg = "$route/{$IDMJR}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateManajerView(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: UpdateManajerViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = DestinasiUpdateManajer.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ) { padding ->
        EntryBodyManajer(
            modifier = Modifier.padding(padding),
            insertManajerUiState = viewModel.uiState,
            onManajerValueChange = viewModel::updateInsertMjrState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateMjr()
                    delay(600)
                    withContext(Dispatchers.Main) {
                        onNavigate()
                    }
                }
            }
        )
    }
}