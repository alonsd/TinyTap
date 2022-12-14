package com.tinytap.ui.screens.dashboard.state.data

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.tinytap.model.ui_models.DashboardCardModel
import com.tinytap.ui.theme.backgroundEndColor
import com.tinytap.ui.theme.backgroundStartColor

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DashboardDataState(dashboardCardModels: List<DashboardCardModel>) {
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
            items(dashboardCardModels) { model ->

                val dismissState = rememberDismissState()

                LaunchedEffect(
                    key1 = dismissState.isDismissed(DismissDirection.EndToStart),
                    key2 = dismissState.isDismissed(DismissDirection.StartToEnd)
                ) {
                    dismissState.reset()
                }
                when {
                    dismissState.isDismissed(DismissDirection.EndToStart) -> {
                        Toast.makeText(LocalContext.current, "end to start", Toast.LENGTH_SHORT).show()
                        //TODO - implement delete
                    }
                    dismissState.isDismissed(DismissDirection.StartToEnd) -> {
                        Toast.makeText(LocalContext.current, "start to end", Toast.LENGTH_SHORT).show()
                        //TODO - implement extra details
                    }
                }

                SwipeToDismissVertical(
                    state = dismissState,
                    background = {
                        val color by animateColorAsState(
                            when {
                                dismissState.targetValue == DismissValue.Default-> Color.Transparent
                                dismissState.targetValue == DismissValue.DismissedToEnd -> Color.Blue
                                dismissState.targetValue == DismissValue.DismissedToStart -> Color.Red
                                else -> Color.Transparent
                            }
                        )
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(color),
                        )
                    },
                    dismissContent = {
                        DashboardCard(model = model)
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
    )
}