package com.example.bugit.application.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.bugit.android.reusable_components.AppText
import com.example.bugit.android.theme.AppTheme.typography
import com.example.bugit.screen.bugs_history.BugsHistoryScreen
import com.example.bugit.screen.report_bug.ReportBugScreen

fun NavGraphBuilder.mainGraph(navController: NavHostController) {

    composable<Screens.NewBug> {
        ReportBugScreen()
    }
    composable<Screens.BugsList> {
        BugsHistoryScreen()
    }
}


@Composable
private fun DummyScreen(screenName: String, onClick: () -> Unit = {}) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AppText(
            text = screenName,
            style = typography.titleLarge,
            modifier = Modifier.clickable { onClick() }
        )
    }
}