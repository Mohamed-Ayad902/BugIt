package com.example.bugit.screen.bugs_history.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.bugit.R
import com.example.bugit.android.reusable_components.AppText
import com.example.bugit.android.theme.AppTheme.colors
import com.example.bugit.android.theme.AppTheme.dimens
import com.example.bugit.android.theme.AppTheme.shapes
import com.example.bugit.android.theme.AppTheme.typography
import com.example.bugit.android.theme.BugItTheme
import com.example.bugit.android.theme.rememberWindowSizeClass
import com.example.bugit.common.mockup.dummyBugs
import com.example.core.feature.bug_reporting.domain.model.Bug

@Composable
fun BugHistoryItem(bug: Bug) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shapes.smallMedium)
            .background(colors.surface)
            .border(1.dp, colors.onSurface.copy(alpha = 0.1f), shapes.smallMedium)
            .padding(dimens.smallMedium)
    ) {
        AsyncImage(
            model = bug.screenshotUri,
            contentDescription = stringResource(R.string.bug_screenshot),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(dimens.massive * 2)
                .clip(shapes.small)
                .background(colors.secondary)
        )

        Spacer(Modifier.height(dimens.smallMedium))
        Chips(bug)
        Spacer(Modifier.height(dimens.extraSmall))
        AppText(
            text = bug.description,
            style = typography.bodyMedium,
            color = colors.onSurface,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(dimens.smallMedium))

        // timestamp and id
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AppText(
                text = bug.createdAt,
                style = typography.labelSmall,
                color = colors.onSurface.copy(alpha = 0.5f)
            )
            AppText(
                text = "#${bug.id.takeLast(6)}",
                style = typography.labelSmall,
                color = colors.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}


@Preview
@Composable
private fun Preview() {
    BugItTheme(rememberWindowSizeClass()) {
        Surface {
            BugHistoryItem(dummyBugs.random())
        }
    }
}