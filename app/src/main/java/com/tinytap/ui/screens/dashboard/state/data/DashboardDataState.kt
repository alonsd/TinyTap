@file:OptIn(ExperimentalFoundationApi::class)

package com.tinytap.ui.screens.dashboard.state.data

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.tinytap.core.ui.SwipeToDismissVertical
import com.tinytap.model.ui_models.DashboardCardModel
import com.tinytap.ui.screens.dashboard.viewmodel.DashboardViewModel.UiEvent.UserSwipedList.SwipeDirection
import com.tinytap.ui.theme.backgroundEndColor
import com.tinytap.ui.theme.backgroundStartColor

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DashboardDataState(
    dashboardCardModels: List<DashboardCardModel>,
    onCardSwiped: (direction: SwipeDirection, model: DashboardCardModel) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        backgroundStartColor,
                        backgroundEndColor
                    )
                )
            ),
        verticalArrangement = Arrangement.Center
    ) {
        LazyRow(modifier = Modifier.wrapContentSize()) {
            items(dashboardCardModels, key = { it.id }) { model ->

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
                        DashboardCard(modifier = Modifier.animateItemPlacement(), model = model)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.TABLET, widthDp = 720, heightDp = 360)
@Composable
fun DashboardDataStatePreview() {
    DashboardDataState(
        listOf(
            DashboardCardModel("", "url", "TinyTap", "TinyTap"),
            DashboardCardModel("", "url", "TinyTap2", "TinyTap2"),
            DashboardCardModel("", "url", "TinyTap3", "TinyTap3"),
            DashboardCardModel("", "url", "TinyTap4", "TinyTap4"),
            DashboardCardModel("", "url", "TinyTap5", "TinyTap5"),
            DashboardCardModel("", "url", "TinyTap5", "TinyTap5"),
        )
    ) { _, _ ->
    }
}