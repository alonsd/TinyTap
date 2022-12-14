package com.tinytap.ui.screens.dashboard.state.data

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tinytap.model.ui_models.DashboardCardModel
import com.tinytap.ui.theme.backgroundEndColor
import com.tinytap.ui.theme.backgroundStartColor

@Composable
fun DashboardCard(
    modifier: Modifier = Modifier,
    model: DashboardCardModel
) {
    Column(
        modifier = modifier
            .size(230.dp, 300.dp)
            .background(Color.Transparent)
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        if (model.showDefaultThumbnail) {
            AsyncImage(
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(10.dp)),
                model = model.thumbnailUrl, contentDescription = ""
            )
        } else {
            Image(
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(10.dp)),
                painter = painterResource(id = com.tinytap.R.drawable.tinytap),
                contentDescription = ""
            )
        }
        Text(
            modifier = Modifier.padding(top = 10.dp),
            fontSize = 16.sp,
            color = Color.White,
            text = model.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = model.subtitle,
            fontSize = 12.sp,
            color = Color.LightGray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardCardPreview() {
    DashboardCard(
        modifier = Modifier.background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    backgroundStartColor,
                    backgroundEndColor
                )
            )
        ),
        model = DashboardCardModel(
            "", "image", "TinyTap", "TinyTap Games"
        )
    )
}