package com.tinytap.core.extensions

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.tinytap.ui.theme.TinyTapTheme

fun ComponentActivity.setTinyTapContent(content: @Composable () -> Unit){
    setContent {
        TinyTapTheme {
            content()
        }
    }
}