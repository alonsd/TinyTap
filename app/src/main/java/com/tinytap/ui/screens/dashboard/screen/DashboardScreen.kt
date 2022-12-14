package com.tinytap.ui.screens.dashboard.screen

import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.tinytap.ui.screens.dashboard.viewmodel.DashboardViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.tinytap.core.ui.GeneralLoadingState
import com.tinytap.ui.screens.dashboard.state.data.DashboardDataState

@RootNavGraph(start = true)
@ExperimentalComposeUiApi
@Destination
@Composable
fun DashboardScreen(
    navigator: DestinationsNavigator,
    viewModel: DashboardViewModel = hiltViewModel()
) {

    val state by viewModel.uiState.collectAsState()
    when (state.state) {
        DashboardViewModel.UiState.State.Data -> {
            DashboardDataState(state.models) { swipeDirection, modelId ->
                viewModel.submitEvent(DashboardViewModel.UiEvent.UserSwipedList(swipeDirection, modelId))
            }
        }
        DashboardViewModel.UiState.State.Error -> {
            Toast.makeText(LocalContext.current, state.errorMessage, Toast.LENGTH_SHORT).show()
        }
        DashboardViewModel.UiState.State.Initial -> {
            GeneralLoadingState()
        }
    }
}