package com.example.bugit.android.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val extraSmall: Dp,
    val small: Dp,
    val smallMedium: Dp,
    val medium: Dp,
    val mediumLarge: Dp,
    val large: Dp,
    val extraLarge: Dp,
    val huge: Dp,
    val extraHuge: Dp,
    val massive: Dp,
    val extraMassive: Dp,
    val unexpected: Dp
)

val smallDimensions = Dimensions(
    extraSmall = 4.dp,
    small = 8.dp,
    smallMedium = 12.dp,
    medium = 16.dp,
    mediumLarge = 24.dp,
    large = 32.dp,
    extraLarge = 36.dp,
    huge = 42.dp,
    extraHuge = 50.dp,
    massive = 60.dp,
    extraMassive = 90.dp,
    unexpected = 125.dp
)

val compactDimensions = Dimensions(
    extraSmall = 4.dp,
    small = 8.dp,
    smallMedium = 16.dp,
    medium = 24.dp,
    mediumLarge = 32.dp,
    large = 40.dp,
    extraLarge = 50.dp,
    huge = 60.dp,
    extraHuge = 75.dp,
    massive = 90.dp,
    extraMassive = 150.dp,
    unexpected = 200.dp
)

val mediumDimensions = Dimensions(
    extraSmall = 8.dp,
    small = 12.dp,
    smallMedium = 18.dp,
    medium = 24.dp,
    mediumLarge = 36.dp,
    large = 48.dp,
    extraLarge = 60.dp,
    huge = 72.dp,
    extraHuge = 90.dp,
    massive = 120.dp,
    extraMassive = 200.dp,
    unexpected = 260.dp
)

val largeDimensions = Dimensions(
    extraSmall = 12.dp,
    small = 16.dp,
    smallMedium = 24.dp,
    medium = 36.dp,
    mediumLarge = 48.dp,
    large = 60.dp,
    extraLarge = 75.dp,
    huge = 90.dp,
    extraHuge = 120.dp,
    massive = 150.dp,
    extraMassive = 225.dp,
    unexpected = 300.dp
)