package com.example.bugit.application.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.bugit.android.theme.AppTheme.typography

fun NavGraphBuilder.mainGraph(navController: NavHostController) {

    composable<Screens.NewBug> {
        DummyScreen(screenName = "Home Screen", onClick = {
            navController.navigate(Screens.BugsList)
        })
    }
    composable<Screens.BugsList> {
        DummyScreen("Bugs List Screen")
    }
}

@Composable
private fun DummyScreen(screenName: String, onClick: () -> Unit = {}) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = screenName, style = typography.titleLarge, modifier =  Modifier.clickable { onClick() })
    }
}