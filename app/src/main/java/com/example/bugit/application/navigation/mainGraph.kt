package com.example.bugit.application.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.bugit.android.reusable_components.AppText
import com.example.bugit.android.theme.AppTheme.typography
import com.example.bugit.android.utils.captureComposableContentBitmap
import com.example.bugit.android.utils.findActivity
import com.example.bugit.android.utils.saveBitmapToCache
import com.example.bugit.application.SharedImageHandler
import com.example.bugit.screen.report_bug.ReportBugScreen
import kotlinx.coroutines.launch

fun NavGraphBuilder.mainGraph(navController: NavHostController, sharedImageHandler: SharedImageHandler) {

    composable<Screens.NewBug> {
        ReportBugScreen()
    }
    composable<Screens.BugsList> {
        DummyScreen(
            screenName = "Bugs List Screen",
            sharedImageHandler = sharedImageHandler,
            onScreenshotDetected = {
                navController.navigate(Screens.NewBug) {
                    popUpTo(Screens.NewBug) { inclusive = false }
                    launchSingleTop = true
                }
            }
        )
    }
}


@Composable
private fun DummyScreen(
    screenName: String,
    sharedImageHandler: SharedImageHandler,
    onScreenshotDetected: () -> Unit,
    onClick: () -> Unit = {}
) {
    val context = LocalContext.current // Don't use .applicationContext here
    val activity = context.findActivity() ?: return
    val widthPx = context.resources.displayMetrics.widthPixels
    val scope = rememberCoroutineScope()

    // visible UI stays simple
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AppText(
            text = screenName,
            style = typography.titleLarge,
            modifier = Modifier.clickable {
                onClick()

                // Move the logic inside the click listener entirely
                // to keep it out of the composition phase
                scope.launch {
                    try {
                        val bitmap = captureComposableContentBitmap(
                            activity = activity,
                            widthPx = widthPx,
                            content = {
                                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                    Text(text = screenName, style = typography.titleLarge)
                                }
                            }
                        )
                        val uri = saveBitmapToCache(context, bitmap)
                        if (uri != null) {
                            sharedImageHandler.updateSharedImage(uri.toString())
                            onScreenshotDetected()
                        }
                    } catch (e: Exception) { /* log error */ }
                }
            }
        )
    }
}


/*
@Composable
private fun DummyScreen(
    screenName: String,
    sharedImageHandler: SharedImageHandler,
    onScreenshotDetected: () -> Unit,
    onClick: () -> Unit = {}
) {
    val context = LocalContext.current.applicationContext
    val activity = context.findActivity() ?: return
    val widthPx = context.resources.displayMetrics.widthPixels
    val scope = rememberCoroutineScope()

    // 1. Define the capture logic in a reusable function
    val triggerScreenshot = {
        scope.launch {
            try {
                val bitmap = captureComposableContentBitmap(
                    activity = activity,
                    widthPx = widthPx,
                    content = {
                        // This is what gets captured in the bitmap
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(text = screenName, style = typography.titleLarge)
                        }
                    }
                )

                val uri = saveBitmapToCache(context, bitmap)
                if (uri != null) {
                    sharedImageHandler.updateSharedImage(uri.toString())
                    onScreenshotDetected() // This navigates back to NewBug
                }
            } catch (e: Exception) {
                // Log error
            }
        }
    }

    // 2. Visible UI
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AppText(
            text = screenName,
            style = typography.titleLarge,
            modifier = Modifier.clickable {
                onClick()           // Execute any passed click logic
                triggerScreenshot() // Trigger the capture
            }
        )
    }
}*/
