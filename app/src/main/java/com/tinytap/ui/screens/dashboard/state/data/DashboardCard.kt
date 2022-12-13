package com.tinytap.ui.screens.dashboard.state.data

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tinytap.model.ui_models.DashboardCardModel

@Composable
fun DashboardCard(model: DashboardCardModel) {
    Card(
        modifier = Modifier
            .size(200.dp)
            .padding(16.dp),
        shape = RoundedCornerShape(30.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (model.showDefaultThumbnail) {
                AsyncImage(
                    modifier = Modifier
                        .size(100.dp)
                        .padding(10.dp),
                    model = model.thumbnailUrl, contentDescription = ""
                )
            } else {
                Image(
                    modifier = Modifier.padding(10.dp),
                    painter = painterResource(id = com.tinytap.R.drawable.tinytap),
                    contentDescription = ""
                )
            }

            Text(
                modifier = Modifier.padding(10.dp),
                fontSize = 16.sp,
                color = Color.Black,
                text = model.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier.padding(5.dp),
                text = model.subtitle,
                fontSize = 12.sp,
                color = Color.LightGray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DashboardCardPreview() {
    DashboardCard(DashboardCardModel("image", "TinyTap", "TinyTap Games"))
}