package com.tinytap.ui.screens.dashboard.state.data

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
        key1 = dismissState.isDismissed(DismissDirection.StartToEnd)
    ) {
        dismissState.reset()
    }
    when {
        dismissState.isDismissed(DismissDirection.EndToStart) -> {
            onCardSwiped(SwipeDirection.DOWN_TO_UP, model)
        }
        dismissState.isDismissed(DismissDirection.StartToEnd) -> {
            onCardSwiped(SwipeDirection.UP_TO_DOWN, model)
        }
    }

    SwipeToDismissVertical(
        state = dismissState,
        background = {
            val color by animateColorAsState(
                when (dismissState.targetValue) {
                    DismissValue.Default -> Color.Transparent
                    DismissValue.DismissedToEnd -> Color.Blue
                    DismissValue.DismissedToStart -> Color.Red
                    else -> Color.Transparent
                }
            )
            Box(
                Modifier
                    .fillMaxSize()
                    .background(color)
            )
        },
        dismissContent = {
            DashboardCard(
                model = model,
                onCardClicked = onCardClicked
            )
        }
    )
}