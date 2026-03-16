package com.example.bugit.application.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.bugit.application.SharedImageHandler
import com.example.bugit.application.navigation.Screens.NewBug

@Composable
fun RootNavigation(navController: NavHostController, sharedImageHandler: SharedImageHandler) {
    val duration = 550
    val slideDuration = 750

    NavHost(
        navController = navController,
        startDestination = NewBug,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(
                    slideDuration,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(animationSpec = tween(duration))
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = tween(slideDuration, easing = FastOutSlowInEasing)
            ) + fadeOut(animationSpec = tween(duration))
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(slideDuration, easing = FastOutSlowInEasing)
            ) + fadeIn(animationSpec = tween(duration))
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(slideDuration, easing = FastOutSlowInEasing)
            ) + fadeOut(animationSpec = tween(duration))
        }
    ) {
        mainGraph(navController, sharedImageHandler)
    }
}