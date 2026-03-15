package com.example.bugit.android.reusable_components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bugit.android.theme.AppTheme.colors
import com.example.bugit.android.theme.AppTheme.dimens
import com.example.bugit.android.theme.AppTheme.shapes
import com.example.bugit.android.theme.AppTheme.typography
import com.example.bugit.android.theme.BugItTheme
import com.example.bugit.android.theme.rememberWindowSizeClass

/**
 * A highly customized text area designed specifically for long-form bug descriptions.
 * Features a character counter, custom placeholder, and scales via the Dimensions system.
 *
 * @param value The current text value.
 * @param onValueChange Callback when text is typed.
 * @param modifier Applied to the root layout.
 * @param placeholder Text shown when the field is empty.
 * @param maxLength Maximum allowed characters (drives the counter).
 */
@Composable
internal fun BugItTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    maxLength: Int = 1000
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = dimens.extraMassive)
            .background(
                color = colors.surface,
                shape = shapes.smallMedium
            )
            .border(
                width = 1.dp,
                color = colors.onSurface.copy(alpha = 0.1f),
                shape = shapes.smallMedium
            )
            .padding(dimens.medium)
    ) {
        BasicTextField(
            value = value,
            onValueChange = { if (it.length <= maxLength) onValueChange(it) },
            modifier = Modifier.fillMaxWidth(),
            textStyle = typography.bodyMedium.merge(TextStyle(color = colors.onBackground)),
            cursorBrush = SolidColor(colors.primary),
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    AppText(
                        text = placeholder,
                        color = colors.onSurface.copy(alpha = 0.5f),
                        style = typography.bodyMedium,
                    )
                }
                innerTextField()
            }
        )

        AppText(
            text = "${value.length} / $maxLength",
            modifier = Modifier.align(Alignment.BottomEnd),
            color = colors.onSurface.copy(alpha = 0.5f),
            style = typography.bodySmall,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F172A)
@Composable
private fun PreviewBugItFormFields() {
    BugItTheme(rememberWindowSizeClass()) {
        Column(
            modifier = Modifier.padding(dimens.smallMedium),
        ) {
                BugItTextField(
                    value = "",
                    onValueChange = {}
                )
        }
    }
}