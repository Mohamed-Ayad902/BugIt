package com.example.bugit.android.reusable_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.bugit.android.theme.AppTheme.colors
import com.example.bugit.android.theme.AppTheme.shapes

@Composable
 fun Modifier.appCardStyle(): Modifier = composed {
    this
        .clip(shapes.smallMedium)
        .background(colors.background)
        .border(.5.dp, colors.outlineVariant, shapes.smallMedium)
}

inline fun Modifier.conditional(
    condition: Boolean,
    modifier: Modifier.() -> Modifier
): Modifier = if (condition) then(modifier(Modifier)) else this

fun Modifier.dashedBorder(color: Color, strokeWidth: Dp, cornerRadius: Dp) = this.drawBehind {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    drawRoundRect(
        color = color,
        style = Stroke(width = strokeWidth.toPx(), pathEffect = pathEffect),
        cornerRadius = CornerRadius(cornerRadius.toPx())
    )
}