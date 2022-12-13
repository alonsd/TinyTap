package com.tinytap.ui.screens.dashboard.screen

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import com.tinytap.ui.screens.dashboard.viewmodel.DashboardViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

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
            Log.d("defaultAppDebuger", "data: ${state.models}")
        }
        DashboardViewModel.UiState.State.Error -> {
            Log.d("defaultAppDebuger", "error: ${state.errorMessage}")
        }
        DashboardViewModel.UiState.State.Initial -> {
            Log.d("defaultAppDebuger", "initial")
        }
    }
} 