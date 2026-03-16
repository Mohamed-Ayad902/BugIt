package com.example.bugit.application

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxDefaults
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.bugit.android.reusable_components.AppSnackBar
import com.example.bugit.android.reusable_components.LocalSnackbarController
import com.example.bugit.android.reusable_components.SnackbarController
import com.example.bugit.android.theme.BugItTheme
import com.example.bugit.android.theme.rememberWindowSizeClass
import com.example.bugit.application.navigation.BottomNavBar
import com.example.bugit.application.navigation.BugReportFAB
import com.example.bugit.application.navigation.RootNavigation
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sharedImageHandler: SharedImageHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            handleIntent(intent)
        }
        enableEdgeToEdge()
        setContent {
            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()
            val snackbarController = remember { SnackbarController(snackbarHostState, scope) }
            val navController = rememberNavController()

            CompositionLocalProvider(LocalSnackbarController provides snackbarController) {
                BugItTheme(rememberWindowSizeClass()) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        snackbarHost = {
                            SnackbarHost(hostState = snackbarHostState) { data ->
                                val dismissState = rememberSwipeToDismissBoxState(
                                    initialValue = SwipeToDismissBoxValue.Settled,
                                    positionalThreshold = SwipeToDismissBoxDefaults.positionalThreshold
                                )

                                SwipeToDismissBox(
                                    state = dismissState,
                                    backgroundContent = { /* kept empty for no background color on swipe */ },
                                    content = { AppSnackBar(data = data) }
                                )
                            }
                        },
                        bottomBar = { BottomNavBar(navController = navController) },
                        floatingActionButton = {
                            BugReportFAB(
                                navController = navController,
                                sharedImageHandler = sharedImageHandler,
                                scope = scope,
                                context = this,
                                activity = (this as? Activity)
                            )
                        }
                    ) { innerPadding ->
                        Column(
                            Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {
                            RootNavigation(navController)
                        }
                    }
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        if (intent?.action == Intent.ACTION_SEND && intent.type?.startsWith("image/") == true) {
            val imageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent.getParcelableExtra(Intent.EXTRA_STREAM)
            }

            imageUri?.let {
                sharedImageHandler.updateSharedImage(it.toString())
                intent.removeExtra(Intent.EXTRA_STREAM)
                intent.action = Intent.ACTION_MAIN
            }
        }
    }
}