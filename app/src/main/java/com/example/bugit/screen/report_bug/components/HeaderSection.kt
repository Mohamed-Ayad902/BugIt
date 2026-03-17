package com.example.bugit.screen.report_bug.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bugit.R
import com.example.bugit.android.reusable_components.AppText
import com.example.bugit.android.theme.AppTheme.colors
import com.example.bugit.android.theme.AppTheme.dimens
import com.example.bugit.android.theme.AppTheme.typography
import com.example.core.model.ReportingDestination
import com.example.core.model.ReportingDestination.GOOGLE_SHEETS
import com.example.core.model.ReportingDestination.NOTION

@Composable
fun HeaderSection(activeTracker: ReportingDestination?, isSyncing: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.bug),
            contentDescription = stringResource(R.string.bugit_logo),
            tint = colors.primary,
            modifier = Modifier.size(dimens.mediumLarge)
        )
        AppText(
            text = stringResource(R.string.bug_it),
            color = colors.onBackground,
            style = typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = dimens.small)
        )
        Spacer(modifier = Modifier.weight(1f))
        if (activeTracker != null) TrackerConnectionPill(activeTracker, isSyncing)
    }
}

@Composable
private fun TrackerConnectionPill(activeTracker: ReportingDestination, isSyncing: Boolean) {
    val tracker = if (isSyncing) stringResource(R.string.syncing_report) else when (activeTracker) {
        GOOGLE_SHEETS -> stringResource(R.string.google_sheets_connected)
        NOTION -> stringResource(R.string.notion_connected)
    }
    val dotColor = if (isSyncing) colors.secondary else colors.tertiary
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(colors.surface, CircleShape)
            .border(
                width = 1.dp,
                color = colors.onSurface.copy(alpha = 0.1f),
                shape = CircleShape
            )
            .padding(
                horizontal = dimens.smallMedium,
                vertical = dimens.small.minus(dimens.extraSmall / 2)
            )
    ) {
        PulsingDot(dotColor)
        AppText(
            modifier = Modifier.padding(start = dimens.small),
            text = tracker,
            color = colors.onSurface.copy(alpha = 0.7f),
            style = typography.bodySmall,
            fontWeight = FontWeight.Medium
        )
    }
}