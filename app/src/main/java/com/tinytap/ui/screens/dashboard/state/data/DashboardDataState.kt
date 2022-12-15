package com.tinytap.ui.screens.dashboard.state.data

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tinytap.model.ui_models.DashboardCardModel
import com.tinytap.ui.screens.dashboard.viewmodel.DashboardViewModel.UiEvent.UserSwipedCard.SwipeDirection
import com.tinytap.ui.theme.backgroundEndColor
import com.tinytap.ui.theme.backgroundStartColor

@Composable
fun DashboardDataState(
    currentPostOfInterest: DashboardCardModel? = null,
    dashboardCardModels: List<DashboardCardModel>,
    onCardSwiped: (direction: SwipeDirection, model: DashboardCardModel) -> Unit,
    onCardClicked: (model: DashboardCardModel) -> Unit,
    onNavigationToPostOfInterestError: () -> Unit
) {

    val lazyListState = rememberLazyListState()

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
        DashboardDataStateHeader(
            currentPostOfInterest,
            dashboardCardModels,
            lazyListState,
            onNavigationToPostOfInterestError
        )
        LazyRow(
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 10.dp),
            state = lazyListState
        ) {
            items(dashboardCardModels, key = { it.id }) { model ->
                DashboardSwipeToDismissRow(model, onCardSwiped, onCardClicked)
            }
        }
    }
}


@Preview(showBackground = true, device = Devices.TABLET, widthDp = 720, heightDp = 360)
@Composable
fun DashboardDataStatePreview() {
    DashboardDataState(
        dashboardCardModels = listOf(
            DashboardCardModel("", "url", "TinyTap", "TinyTap"),
            DashboardCardModel("", "url", "TinyTap2", "TinyTap2"),
            DashboardCardModel("", "url", "TinyTap3", "TinyTap3"),
            DashboardCardModel("", "url", "TinyTap4", "TinyTap4"),
            DashboardCardModel("", "url", "TinyTap5", "TinyTap5"),
            DashboardCardModel("", "url", "TinyTap5", "TinyTap5"),
        ),
        onCardSwiped = { _, _ -> },
        onCardClicked = {},
        onNavigationToPostOfInterestError = {}
    )
}