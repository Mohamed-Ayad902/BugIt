package com.example.bugit.screen.bugs_history

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.bugit.R
import com.example.bugit.android.extensions.ObserveAsState
import com.example.bugit.android.messages.toUserMessageRes
import com.example.bugit.android.reusable_components.AppText
import com.example.bugit.android.reusable_components.LocalSnackbarController
import com.example.bugit.android.reusable_components.ShimmerPlaceholder
import com.example.bugit.android.reusable_components.ShimmerRowPlaceholder
import com.example.bugit.android.reusable_components.SnackbarType
import com.example.bugit.android.reusable_components.fadeInOut
import com.example.bugit.android.reusable_components.shimmer
import com.example.bugit.android.theme.AppTheme.colors
import com.example.bugit.android.theme.AppTheme.dimens
import com.example.bugit.android.theme.AppTheme.orientation
import com.example.bugit.android.theme.AppTheme.shapes
import com.example.bugit.android.theme.AppTheme.typography
import com.example.bugit.android.theme.BugItTheme
import com.example.bugit.android.theme.Orientation
import com.example.bugit.android.theme.Orientation.Landscape
import com.example.bugit.android.theme.rememberWindowSizeClass
import com.example.bugit.common.mockup.dummyBugs
import com.example.bugit.screen.bugs_history.components.BugsGridContent
import com.example.bugit.screen.bugs_history.viewmodel.BugsHistoryEvents
import com.example.bugit.screen.bugs_history.viewmodel.BugsHistoryIntents
import com.example.bugit.screen.bugs_history.viewmodel.BugsHistoryState
import com.example.bugit.screen.bugs_history.viewmodel.BugsHistoryVM
import com.example.core.feature.bug_reporting.domain.model.Bug

@Composable
fun BugsHistoryScreen(
    viewModel: BugsHistoryVM = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    val snackbarController = LocalSnackbarController.current
    val context = LocalContext.current.applicationContext

    ObserveAsState(viewModel.eventFlow) { event ->
        when (event) {
            is BugsHistoryEvents.Failure -> snackbarController.show(
                message = context.getString(event.exception.toUserMessageRes()),
                type = SnackbarType.ERROR
            )
        }
    }

    BugsHistoryContent(
        bugs = state.bugs,
        isLoading = state.isLoading,
        onRefresh = { viewModel.sendIntent(BugsHistoryIntents.GetHistory) }
    )
}

@Composable
private fun BugsHistoryContent(
    bugs: List<Bug>,
    isLoading: Boolean,
    onRefresh: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(horizontal = dimens.smallMedium)
            .padding(top = dimens.smallMedium)
    ) {
        AppText(
            text = stringResource(R.string.bugs_history),
            style = typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = colors.onBackground,
        )

        PullToRefreshBox(
            isRefreshing = isLoading,
            onRefresh = onRefresh,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = dimens.small)
        ) {
            if (bugs.isEmpty() && !isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    AppText(
                        text = stringResource(R.string.no_bugs_reported_yet),
                        color = colors.onSurface.copy(alpha = 0.6f)
                    )
                }
            } else {
                BugsGridContent(isLoading, bugs)
            }
        }
    }
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewBugsHistoryScreenEmpty() {
    BugItTheme(rememberWindowSizeClass()) {
        Surface {
            BugsHistoryContent(
                bugs = dummyBugs,
                isLoading = false,
                onRefresh = {}
            )
        }
    }
}