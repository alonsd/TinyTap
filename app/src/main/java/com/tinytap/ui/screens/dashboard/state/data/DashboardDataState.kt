package com.tinytap.ui.screens.dashboard.state.data

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.tinytap.model.ui_models.DashboardCardModel
import com.tinytap.ui.theme.backgroundEndColor
import com.tinytap.ui.theme.backgroundStartColor

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
                DashboardCard(model)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardDataStatePreview() {
    DashboardDataState(emptyList())
}