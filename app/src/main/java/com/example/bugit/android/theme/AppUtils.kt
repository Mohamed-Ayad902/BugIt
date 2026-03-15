package com.example.bugit.android.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp

@Composable
fun ProvideAppUtils(
    dimensions: Dimensions,
    orientation: Orientation,
    content: @Composable () -> Unit
) {
    val dimSet = remember { dimensions }
    val mOrientation = remember { orientation }
    CompositionLocalProvider(
        LocalAppDimens provides dimSet,
        LocalOrientationMode provides mOrientation,
        content = content
    )
}

object AppTheme {
    val colors: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.colorScheme

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.typography

    val dimens: Dimensions
        @Composable
        @ReadOnlyComposable
        get() = LocalAppDimens.current

    val shapes: AppShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalAppShapes.current

    val orientation: Orientation
        @Composable
        @ReadOnlyComposable
        get() = LocalOrientationMode.current
}

private val LocalAppDimens = compositionLocalOf {
    smallDimensions
}

private val LocalOrientationMode = compositionLocalOf {
    Orientation.Portrait
}

val LocalAppShapes = compositionLocalOf {
    // default fallback (won't be used in real app because theme provides it)
    AppShapes(
        tiny = RoundedCornerShape(2.dp),
        small = RoundedCornerShape(8.dp),
        smallMedium = RoundedCornerShape(12.dp),
        medium = RoundedCornerShape(16.dp),
        mediumLarge = RoundedCornerShape(24.dp),
        large = RoundedCornerShape(32.dp),
        extraLarge = RoundedCornerShape(50.dp),
        pill = RoundedCornerShape(50.dp),
        topRounded = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        bottomRounded = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
    )
}

enum class Orientation {
    Portrait, Landscape
}