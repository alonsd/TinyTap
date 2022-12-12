package com.heroes.ui.screens.dashboard.screen

import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.heroes.ui.screens.dashboard.state.data.DashboardDataState
import com.heroes.ui.screens.dashboard.state.loading.DashboardScreenLoadingState
import com.heroes.ui.screens.dashboard.viewmodel.DashboardViewModel
import com.heroes.ui.screens.destinations.HeroDetailsScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@ExperimentalComposeUiApi
@Destination
@Composable
fun DashboardScreen(
    navigator: DestinationsNavigator,
    viewModel : DashboardViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = "") {
        viewModel.uiAction.collect { uiAction ->
            when (uiAction) {
                is DashboardViewModel.UiAction.NavigateToHeroesDetails -> {
                    navigator.navigate(HeroDetailsScreenDestination(uiAction.heroModel))
                }
            }
        }
    }


    when (uiState.state) {
        DashboardViewModel.UiState.State.Data -> {
            DashboardDataState(uiState.heroesList,
                uiState.searchState,
                onSearchQueryChanged = { text ->
                    viewModel.submitEvent(DashboardViewModel.UiEvent.SearchQueryChanged(text))
                },
                onSearchFocusChange = { focused ->
                    viewModel.submitEvent(DashboardViewModel.UiEvent.SearchBarFocusChanged(focused))
                },
                onClearQueryClicked = {
                    viewModel.submitEvent(DashboardViewModel.UiEvent.ClearQueryClicked)
                },
                onListScrolling = { isScrollInProgress ->
                    viewModel.submitEvent(DashboardViewModel.UiEvent.ListIsScrolling(isScrollInProgress))
                },
                onListItemClicked = { model ->
                    viewModel.submitEvent(DashboardViewModel.UiEvent.ListItemClicked(model))
                })
        }
        DashboardViewModel.UiState.State.Error -> {
            Toast.makeText(LocalContext.current, uiState.errorMessage, Toast.LENGTH_SHORT).show()
        }
        DashboardViewModel.UiState.State.Initial -> {
            DashboardScreenLoadingState()
        }
    }
}