package com.tinytap.model.ui_models

data class DashboardCardModel(
    val id : String,
    val thumbnailUrl: String,
    val title: String,
    val subtitle: String
) {
    var showDefaultThumbnail = thumbnailUrl.startsWith("https://")
}
