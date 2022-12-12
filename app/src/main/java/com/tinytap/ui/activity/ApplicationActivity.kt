package com.tinytap.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.tinytap.core.extensions.setTinyTapContent
import com.ramcosta.composedestinations.DestinationsNavHost
import com.tinytap.ui.screens.dashboard.screen.NavGraphs
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialNavigationApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@AndroidEntryPoint
class ApplicationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTinyTapContent {
            DestinationsNavHost(navGraph = NavGraphs.root)
        }
    }
}