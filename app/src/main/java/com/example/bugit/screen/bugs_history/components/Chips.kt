package com.example.bugit.screen.bugs_history.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
fun Chips(bug: Bug, modifier: Modifier = Modifier) {
    if (bug.dynamicFields.isNotEmpty()) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(dimens.extraSmall),
            verticalArrangement = Arrangement.spacedBy(dimens.extraSmall),
            modifier = modifier.fillMaxWidth()
        ) {
            bug.dynamicFields.forEach { (key, value) ->

                val chipColor = when {
                    key.equals("Severity", ignoreCase = true) && value.equals("High", ignoreCase = true) -> colors.error
                    key.equals("Severity", ignoreCase = true) && value.equals("Medium", ignoreCase = true) -> Color(0xFFF57C00)
                    key.equals("Severity", ignoreCase = true) && value.equals("Low", ignoreCase = true) -> colors.primary
                    else -> colors.onSurface.copy(alpha = 0.7f)
                }

                Box(
                    modifier = Modifier
                        .background(chipColor.copy(alpha = 0.1f), shapes.small)
                        .border(1.dp, chipColor.copy(alpha = 0.3f), shapes.small)
                        .padding(horizontal = dimens.small, vertical = dimens.extraSmall / 2)
                ) {
                    AppText(
                        text = "$key: $value",
                        style = typography.labelSmall,
                        color = chipColor,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
        Spacer(Modifier.height(dimens.small))
    }
}


@Preview
@Composable
private fun Preview() {
    BugItTheme(rememberWindowSizeClass()) {
        Surface {
            Chips(dummyBugs.random())
        }
    }
}