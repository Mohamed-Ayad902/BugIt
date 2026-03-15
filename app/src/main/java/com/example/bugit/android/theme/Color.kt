package com.example.bugit.android.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

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

val DarkColorScheme = darkColorScheme(
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

val LightColorScheme = lightColorScheme(
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