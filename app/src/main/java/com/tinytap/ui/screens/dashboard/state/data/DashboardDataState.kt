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

                var dismissState = rememberDismissState()

                when {
                    dismissState.isDismissed(DismissDirection.EndToStart) ->{
                        //TODO - implement extra info
                        dismissState = DismissState(DismissValue.Default)
                    }
                    dismissState.isDismissed(DismissDirection.StartToEnd) ->{
                        //TODO - implement delete
                        dismissState = DismissState(DismissValue.Default)
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
                            }
                        )
                        val alignment = Alignment.CenterEnd
                        val icon = Icons.Default.Delete

                        val scale by animateFloatAsState(
                            if (dismissState.targetValue == DismissValue.Default) 0.75f else 1f
                        )

                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = Dp(20f)),
                            contentAlignment = alignment
                        ) {
                            Icon(
                                icon,
                                contentDescription = "Delete Icon",
                                modifier = Modifier.scale(scale)
                            )
                        }
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