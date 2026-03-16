package com.example.bugit.android.reusable_components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import com.example.bugit.android.theme.AppTheme.colors
import com.example.bugit.android.theme.AppTheme.dimens
import com.example.bugit.android.theme.AppTheme.shapes
import kotlin.random.Random

@Composable
fun ShimmerPlaceholder(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.background(
            color = colors.secondary.copy(alpha = 0.5f),
            shape = shapes.small
        )
    )
}

@Composable
fun ShimmerRowPlaceholder(
    labelWidthRange: ClosedFloatingPointRange<Float> = 0.3f..0.5f,
    valueWidthRange: ClosedFloatingPointRange<Float> = 0.2f..0.4f,
) {
    val labelWidth = remember {
        Random.nextDouble(labelWidthRange.start.toDouble(), labelWidthRange.endInclusive.toDouble()).toFloat()
    }
    val valueWidth = remember {
        Random.nextDouble(valueWidthRange.start.toDouble(), valueWidthRange.endInclusive.toDouble()).toFloat()
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ShimmerPlaceholder(
            modifier = Modifier
                .height(dimens.smallMedium)
                .fillMaxWidth(labelWidth)
        )
        ShimmerPlaceholder(
            modifier = Modifier
                .height(dimens.smallMedium)
                .fillMaxWidth(valueWidth)
        )
    }
}

@Composable
fun Modifier.shimmer(
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 270f,
    durationMillis: Int = 1000,
): Modifier {
    val shimmerColors = listOf(
        colors.surface,
        colors.secondary.copy(alpha = 0.5f),
        colors.surface,
    )
    val transition = rememberInfiniteTransition(label = "")

    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "Shimmer loading animation",
    )
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0f),
        end = Offset(x = translateAnimation.value, y = angleOfAxisY),
    )
    return this.background(brush)
}

@Composable
fun Modifier.fadeInOut(durationMillis: Int = 600): Modifier {
    val transition = rememberInfiniteTransition(label = "")
    val alphaAnimation = transition.animateFloat(
        initialValue = 0.2f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "Fade in/out animation",
    )
    return this.alpha(alphaAnimation.value)
}