package com.tinytap.model.ui_models

data class DashboardCardModel(
    val id : String,
    val thumbnailUrl: String,
    val title: String,
    val author: String,
    val isCurrentPostOfInterest : Boolean = false
) {
    var showDefaultThumbnail = thumbnailUrl.startsWith("https://")
}
