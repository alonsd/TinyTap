package com.tinytap.ui.screens.dashboard.state.data

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
    model: DashboardCardModel,
    onCardClicked: (model: DashboardCardModel) -> Unit
) {

    Column(
        modifier = modifier
            .wrapContentHeight()
            .width(200.dp)
            .background(Color.Transparent)
            .padding(16.dp)
            .clickable {
                onCardClicked(model)
            },
    ) {
        val painter = painterResource(id = com.tinytap.R.drawable.tinytap)
        if (model.showDefaultThumbnail) {
            AsyncImage(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 5.dp)
                    .size(130.dp)
                    .clip(RoundedCornerShape(10.dp)),
                alignment = Alignment.TopStart,
                contentScale = ContentScale.FillBounds,
                model = model.thumbnailUrl, contentDescription = ""
            )
        } else {
            Image(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 5.dp)
                    .size(130.dp)
                    .clip(RoundedCornerShape(10.dp)),
                alignment = Alignment.TopStart,
                painter = painterResource(id = com.tinytap.R.drawable.tinytap),
                contentDescription = ""
            )
        }
        Image(
            modifier = Modifier
                .size(25.dp)
                .padding(top = 10.dp, start = 16.dp)
                .alpha(if (model.isCurrentPostOfInterest) 1f else 0f),
            painter = painterResource(id = com.tinytap.R.drawable.post_of_interest),
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(top = 10.dp, start = 16.dp),
            fontSize = 16.sp,
            color = Color.White,
            text = model.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = Modifier.padding(start = 16.dp, bottom = 5.dp),
            text = model.author,
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
            "",
            "image",
            "TinyTap",
            "TinyTap Games",
            true
        )
    ) {}
}