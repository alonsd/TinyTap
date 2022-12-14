package com.tinytap.ui.screens.dashboard.screen

import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.tinytap.ui.screens.dashboard.viewmodel.DashboardViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.tinytap.R
import com.tinytap.core.ui.GeneralLoadingState
import com.tinytap.ui.screens.dashboard.actions.DashboardExtraInformationDialog
import com.tinytap.ui.screens.dashboard.state.data.DashboardDataState
import com.tinytap.ui.screens.dashboard.viewmodel.DashboardViewModel.UiAction.NoAction
import com.tinytap.ui.screens.dashboard.viewmodel.DashboardViewModel.UiEvent.UserSwipedCard

@RootNavGraph(start = true)
@ExperimentalComposeUiApi
@Destination
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel()
) {

    val state by viewModel.uiState.collectAsState()
    val action by viewModel.uiAction.collectAsState(initial = NoAction)
    val context = LocalContext.current
    val postOfInterestError = stringResource(R.string.dashboard_screen_navgiation_to_post_of_interest_error)

    when (action) {
        NoAction -> Unit
        is DashboardViewModel.UiAction.ShowExtraInformationDialog -> {
            val extraInformationModel = state.extraInformationCardModel ?: return
            DashboardExtraInformationDialog(extraInformationModel) {
                viewModel.submitEvent(DashboardViewModel.UiEvent.ExtraInformationDialogConfirmButtonClicked)
            }
        }
    }

    when (state.state) {
        DashboardViewModel.UiState.State.Data -> {
            DashboardDataState(
                currentPostOfInterest = state.currentPostOfInterest,
                dashboardCardModels = state.models,
                onCardSwiped = { swipeDirection, modelId ->
                    viewModel.submitEvent(UserSwipedCard(swipeDirection, modelId))
                },
                onCardClicked = { model ->
                    viewModel.submitEvent(DashboardViewModel.UiEvent.UserClickedOnCard(model))
                },
                onNavigationToPostOfInterestError = {
                    Toast.makeText(context, postOfInterestError, Toast.LENGTH_SHORT).show()
                }
            )
        }
        DashboardViewModel.UiState.State.Error -> {
            Toast.makeText(LocalContext.current, state.errorMessage, Toast.LENGTH_SHORT).show()
        }
        DashboardViewModel.UiState.State.Initial -> {
            GeneralLoadingState()
        }
    }
}