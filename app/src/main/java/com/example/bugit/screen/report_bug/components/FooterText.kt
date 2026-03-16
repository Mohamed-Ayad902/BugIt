package com.example.bugit.screen.report_bug.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import com.example.bugit.R
import com.example.bugit.android.reusable_components.AppText
import com.example.bugit.android.theme.AppTheme.colors
import com.example.bugit.android.theme.AppTheme.typography

@Composable
fun FooterText() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        AppText(
            text = stringResource(R.string.reporting_to),
            style = typography.bodySmall,
            color = colors.onSurface.copy(alpha = 0.5f)
        )
        AppText(
            text = stringResource(R.string.project_bugit_tracker_v1),
            style = typography.bodySmall.copy(textDecoration = TextDecoration.Underline),
            color = colors.onSurface.copy(alpha = 0.6f),
        )
    }
}