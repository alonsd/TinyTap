package com.tinytap.ui.screens.dashboard.state.data

import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.tinytap.core.ui.SwipeToDismissVertical
import com.tinytap.model.ui_models.DashboardCardModel
import com.tinytap.ui.screens.dashboard.viewmodel.DashboardViewModel.UiEvent.UserSwipedCard.SwipeDirection

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun DashboardSwipeToDismissRow(
    model: DashboardCardModel,
    onCardSwiped: (
        direction: SwipeDirection,
        model: DashboardCardModel
    ) -> Unit,
    onCardClicked: (model: DashboardCardModel) -> Unit
) {
    val dismissState = rememberDismissState()

    LaunchedEffect(
        key1 = dismissState.isDismissed(DismissDirection.EndToStart)
    ) {
        dismissState.reset()
    }
    when {
        dismissState.isDismissed(DismissDirection.EndToStart) -> {
            onCardSwiped(SwipeDirection.UP_TO_DOWN, model)
        }
        dismissState.isDismissed(DismissDirection.StartToEnd) -> {
            onCardSwiped(SwipeDirection.DOWN_TO_UP, model)
        }
    }

    SwipeToDismissVertical(
        state = dismissState,
        background = {},
        dismissContent = {
            DashboardCard(
                model = model,
                onCardClicked = onCardClicked
            )
        }
    )
}