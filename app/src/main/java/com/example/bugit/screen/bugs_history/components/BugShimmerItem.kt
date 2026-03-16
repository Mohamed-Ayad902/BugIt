package com.example.bugit.screen.bugs_history.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bugit.android.reusable_components.ShimmerPlaceholder
import com.example.bugit.android.reusable_components.ShimmerRowPlaceholder
import com.example.bugit.android.reusable_components.fadeInOut
import com.example.bugit.android.reusable_components.shimmer
import com.example.bugit.android.theme.AppTheme.colors
import com.example.bugit.android.theme.AppTheme.dimens
import com.example.bugit.android.theme.AppTheme.shapes
import com.example.bugit.android.theme.BugItTheme
import com.example.bugit.android.theme.rememberWindowSizeClass


@Composable
fun BugHistoryShimmerItem() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shapes.smallMedium)
            .background(colors.surface)
            .border(1.dp, colors.onSurface.copy(alpha = 0.1f), shapes.smallMedium)
            .shimmer()
            .fadeInOut()
            .padding(dimens.smallMedium),
        verticalArrangement = Arrangement.spacedBy(dimens.smallMedium)
    ) {
        // image
        ShimmerPlaceholder(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimens.massive * 2)
        )

        // dynamic fields chips
        Row(horizontalArrangement = Arrangement.spacedBy(dimens.extraSmall)) {
            ShimmerPlaceholder(modifier = Modifier
                .fillMaxWidth(0.3f)
                .height(dimens.small))
            ShimmerPlaceholder(modifier = Modifier
                .fillMaxWidth(0.2f)
                .height(dimens.small))
        }

        // description
        ShimmerPlaceholder(
            modifier = Modifier
                .fillMaxWidth(0.2f)
                .height(dimens.small)
        )
        Column(verticalArrangement = Arrangement.spacedBy(dimens.extraSmall)) {
            ShimmerPlaceholder(modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(dimens.small))
            ShimmerPlaceholder(modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(dimens.small))
        }

        // timestamp
        ShimmerRowPlaceholder()
    }
}

@Preview
@Composable
private fun Preview() {
    BugItTheme(rememberWindowSizeClass()) {
        Surface {
            BugHistoryShimmerItem()
        }
    }
}