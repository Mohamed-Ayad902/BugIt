package com.example.bugit.android.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.bugit.R

val interFontFamily = FontFamily(
    Font(R.font.inter_regular, FontWeight.Normal),
    Font(R.font.inter_medium, FontWeight.SemiBold),
    Font(R.font.inter_bold, FontWeight.Bold)
)

private fun adaptiveLetterSpacing(fontSize: Float): Float {
    return when {
        fontSize <= 14 -> 0.02f
        fontSize <= 18 -> 0.01f
        fontSize <= 24 -> 0.0f
        else -> -0.01f
    }
}

private const val FONT_SCALE = 0.93f

private fun createTextStyle(
    size: Float,
    weight: FontWeight = FontWeight.Normal,
    family: FontFamily? = interFontFamily
) = TextStyle(
    fontFamily = family,
    fontWeight = weight,
    fontSize = (size * FONT_SCALE).sp,
    lineHeight = (size * 1.45f * FONT_SCALE).sp,
    letterSpacing = adaptiveLetterSpacing(size * FONT_SCALE).sp
)

// BASE SCALE (Small)
val typographySmall = Typography(
    bodySmall = createTextStyle(14f),
    bodyMedium = createTextStyle(16f),
    bodyLarge = createTextStyle(18f),
    titleSmall = createTextStyle(18f, FontWeight.Bold),
    titleMedium = createTextStyle(20f, FontWeight.Bold),
    titleLarge = createTextStyle(23f, FontWeight.Bold),
    headlineSmall = createTextStyle(26f, FontWeight.Bold),
    headlineMedium = createTextStyle(28f, FontWeight.Bold),
    headlineLarge = createTextStyle(30f, FontWeight.Bold)
)

// COMPACT SCALE (Standard Mobile)
val typographyCompact = Typography(
    bodySmall = createTextStyle(15f),
    bodyMedium = createTextStyle(16f),
    bodyLarge = createTextStyle(19f),
    titleSmall = createTextStyle(21f, FontWeight.Bold),
    titleMedium = createTextStyle(22f, FontWeight.Bold),
    titleLarge = createTextStyle(26f, FontWeight.Bold),
    headlineSmall = createTextStyle(28f, FontWeight.Bold),
    headlineMedium = createTextStyle(32f, FontWeight.Bold),
    headlineLarge = createTextStyle(35f, FontWeight.Bold)
)

// MEDIUM SCALE (Small Tablets / Large Phones)
val typographyMedium = Typography(
    bodySmall = createTextStyle(16f),
    bodyMedium = createTextStyle(20f),
    bodyLarge = createTextStyle(22f),
    titleSmall = createTextStyle(24f, FontWeight.Bold),
    titleMedium = createTextStyle(27f, FontWeight.Bold),
    titleLarge = createTextStyle(30f, FontWeight.Bold),
    headlineSmall = createTextStyle(32f, FontWeight.Bold),
    headlineMedium = createTextStyle(35f, FontWeight.Bold),
    headlineLarge = createTextStyle(38f, FontWeight.Bold)
)

// BIG SCALE (Large Tablets)
val typographyBig = Typography(
    bodySmall = createTextStyle(20f),
    bodyMedium = createTextStyle(29f),
    bodyLarge = createTextStyle(32f),
    titleSmall = createTextStyle(32f, FontWeight.Bold),
    titleMedium = createTextStyle(36f, FontWeight.Bold),
    titleLarge = createTextStyle(39f, FontWeight.Bold),
    headlineSmall = createTextStyle(42f, FontWeight.Bold),
    headlineMedium = createTextStyle(45f, FontWeight.Bold),
    headlineLarge = createTextStyle(50f, FontWeight.Bold)
)