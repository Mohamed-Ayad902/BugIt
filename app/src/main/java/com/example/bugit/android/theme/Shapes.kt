package com.example.bugit.android.theme

import androidx.compose.foundation.shape.RoundedCornerShape

data class AppShapes(
    val tiny: RoundedCornerShape,
    val small: RoundedCornerShape,
    val smallMedium: RoundedCornerShape,
    val medium: RoundedCornerShape,
    val mediumLarge: RoundedCornerShape,
    val large: RoundedCornerShape,
    val extraLarge: RoundedCornerShape,

    val pill: RoundedCornerShape,         // full-rounded pill
    val topRounded: RoundedCornerShape,   // only top corners rounded
    val bottomRounded: RoundedCornerShape // only bottom corners rounded
)