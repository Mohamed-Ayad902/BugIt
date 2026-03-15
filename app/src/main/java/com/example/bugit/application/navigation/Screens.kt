package com.example.bugit.application.navigation

import kotlinx.serialization.Serializable

sealed interface Screens {

    @Serializable
    data object HomeScreen : Screens

    @Serializable
    data object  BugsList : Screens
}