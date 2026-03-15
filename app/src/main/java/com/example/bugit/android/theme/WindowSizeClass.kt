package com.example.bugit.android.theme


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp

 sealed class WindowSize(val size: Int) {
    data class Small(val smallSize: Int) : WindowSize(smallSize)
    data class Compact(val compactSize: Int) : WindowSize(compactSize)
    data class Medium(val mediumSize: Int) : WindowSize(mediumSize)
    data class Large(val largeSize: Int) : WindowSize(largeSize)
}

 data class WindowSizeClass(
    val width: WindowSize,
    val height: WindowSize
)

@Composable
 fun rememberWindowSizeClass(): WindowSizeClass {
    val config = LocalWindowInfo.current.containerSize
    val density = LocalDensity.current

    val widthDp: Dp = with(density) { config.width.toDp() }
    val heightDp: Dp = with(density) { config.height.toDp() }

    val width by remember(widthDp) {
        mutableIntStateOf(widthDp.value.toInt())
    }

    val height by remember(heightDp) {
        mutableIntStateOf(heightDp.value.toInt())
    }

    val windowWidthClass = when {
        width <= 360 -> WindowSize.Small(width)
        width in 361..480 -> WindowSize.Compact(width)
        width in 481..720 -> WindowSize.Medium(width)
        else -> WindowSize.Large(width)
    }

    val windowHeightClass = when {
        height <= 360 -> WindowSize.Small(height)
        height in 361..480 -> WindowSize.Compact(height)
        height in 481..720 -> WindowSize.Medium(height)
        else -> WindowSize.Large(height)
    }

    return WindowSizeClass(windowWidthClass, windowHeightClass)
}