package com.tinytap.ui.screens.dashboard.state.data

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tinytap.R
import com.tinytap.model.ui_models.DashboardCardModel
import kotlinx.coroutines.launch

@Composable
fun DashboardDataStateHeader(
    currentPostOfInterest: DashboardCardModel?,
    dashboardCardModels: List<DashboardCardModel>,
    lazyListState: LazyListState,
    onNavigationToPostOfInterestError: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(modifier = Modifier.padding(start = 10.dp), onClick = {
            if (currentPostOfInterest == null) {
                onNavigationToPostOfInterestError()
                return@Button
            }
            coroutineScope.launch {
                val indexOfPostOfInterest = dashboardCardModels.indexOf(currentPostOfInterest)
                lazyListState.animateScrollToItem(indexOfPostOfInterest)
            }
        }) {
            Image(
                modifier = Modifier.size(25.dp),
                painter = painterResource(id = R.drawable.navigate_to_post_of_interest),
                contentDescription = null
            )
        }

        Text(
            text = "TinyTap",
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier)
    }
}

@Preview
@Composable
fun DashboardDataStateHeaderPreview() {
    DashboardDataStateHeader(
        null,
        emptyList(),
        rememberLazyListState(),
    ) {}
}