package com.heroes.ui.screens.dashboard.list_items

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.heroes.model.ui_models.heroes_list.HeroModel

@Composable
fun HeroesListItem(model: HeroModel, onClick: (heroModel: HeroModel) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(model)
            }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(model.image),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
        )
        Text(
            text = model.name,
            color = Color.Black,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Preview
@Composable
fun HeroListItemPreview() {
    HeroesListItem(
        model = HeroModel(
            "",
            "Alon", ""
        ), onClick = {})
}


