package com.example.bugit.screen.bugs_history.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.bugit.android.theme.AppTheme.dimens
import com.example.bugit.android.theme.AppTheme.orientation
import com.example.bugit.android.theme.BugItTheme
import com.example.bugit.android.theme.Orientation.Landscape
import com.example.bugit.android.theme.rememberWindowSizeClass
import com.example.bugit.common.mockup.dummyBugs
import com.example.core.feature.bug_reporting.domain.model.Bug

@Composable
fun BugsGridContent(isLoading: Boolean, bugs: List<Bug>, modifier: Modifier = Modifier) {
    val isLandscape = orientation == Landscape
    val columnCount = if (isLandscape) 2 else 1
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(columnCount),
        verticalItemSpacing = dimens.medium,
        horizontalArrangement = Arrangement.spacedBy(dimens.medium),
        modifier = modifier.fillMaxSize()
    ) {
        if (isLoading && bugs.isEmpty()) {
            items(6) { BugHistoryShimmerItem() }
        }

        items(bugs, key = { it.id }) { bug ->
            BugHistoryItem(bug = bug)
        }

        // some bottom padding for nav bar
        item(span = StaggeredGridItemSpan.FullLine) {
            Spacer(modifier = Modifier.height(dimens.extraHuge))
        }
    }
}


@Preview
@Composable
private fun Preview() {
    BugItTheme(rememberWindowSizeClass()) {
        Surface {
            BugsGridContent(true, dummyBugs)
        }
    }
}