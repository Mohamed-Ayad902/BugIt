package com.example.bugit.application.navigation

import android.app.Activity
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bugit.R
import com.example.bugit.android.theme.AppTheme.colors
import com.example.bugit.android.theme.AppTheme.dimens
import com.example.bugit.android.utils.captureCurrentScreen
import com.example.bugit.android.utils.saveBitmapToCache
import com.example.bugit.application.SharedImageHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun BugReportFAB(
    navController: NavController,
    sharedImageHandler: SharedImageHandler,
    scope: CoroutineScope,
    context: Context,
    activity: Activity?
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val isVisible by remember(navBackStackEntry) {
        derivedStateOf {
            val currentRoute = navBackStackEntry?.destination?.route
            currentRoute?.contains("NewBug") == false
        }
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = scaleIn() + fadeIn(),
        exit = scaleOut() + fadeOut()
    ) {
        FloatingActionButton(
            onClick = {
                activity?.let { act ->
                    scope.launch(Dispatchers.Main.immediate) {
                        val bitmap = captureCurrentScreen(act)
                        if (bitmap != null) {
                            val uri = saveBitmapToCache(context, bitmap)
                            if (uri != null) {
                                sharedImageHandler.updateSharedImage(uri.toString())
                                navController.navigate(Screens.NewBug) {
                                    popUpTo(Screens.NewBug) { inclusive = false }
                                    launchSingleTop = true
                                }
                            }
                        }
                    }
                }
            },
            shape = CircleShape,
            containerColor = colors.primary,
            contentColor = colors.onPrimary
        ) {
            Icon(
                painter = painterResource(R.drawable.bug),
                contentDescription = stringResource(R.string.report_bug),
                modifier = Modifier.size(dimens.medium)
            )
        }
    }
}