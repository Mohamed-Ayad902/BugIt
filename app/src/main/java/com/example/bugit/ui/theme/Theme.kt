package com.example.bugit.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Shared Brand Colors
val IndigoPrimary = Color(0xFF6366F1)
val RoseError = Color(0xFFF43F5E)
val MintSuccess = Color(0xFF10B981)

// Dark Mode Palette
val SlateBackgroundDark = Color(0xFF0F172A)
val SlateSurfaceDark = Color(0xFF1E293B)
val IndigoSecondaryDark = Color(0xFF94A3B8)

// Light Mode Palette
val SlateBackgroundLight = Color(0xFFF8FAFC)
val SlateSurfaceLight = Color(0xFFFFFFFF)
val IndigoSecondaryLight = Color(0xFF64748B)

private val DarkColorScheme = darkColorScheme(
    primary = IndigoPrimary,
    onPrimary = Color.White,
    secondary = IndigoSecondaryDark,
    tertiary = MintSuccess,
    error = RoseError,
    background = SlateBackgroundDark,
    surface = SlateSurfaceDark,
    onBackground = Color.White,
    onSurface = Color(0xFFCBD5E1)
)

private val LightColorScheme = lightColorScheme(
    primary = IndigoPrimary,
    onPrimary = Color.White,
    secondary = IndigoSecondaryLight,
    tertiary = MintSuccess,
    error = RoseError,
    background = SlateBackgroundLight,
    surface = SlateSurfaceLight,
    onBackground = Color(0xFF0F172A),
    onSurface = Color(0xFF1E293B)
)

@Composable
fun BugItTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}