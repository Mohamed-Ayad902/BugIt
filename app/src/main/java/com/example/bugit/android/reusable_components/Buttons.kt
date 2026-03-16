package com.example.bugit.android.reusable_components

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.bugit.R
import com.example.bugit.android.theme.AppTheme.colors
import com.example.bugit.android.theme.AppTheme.dimens
import com.example.bugit.android.theme.AppTheme.shapes
import com.example.bugit.android.theme.AppTheme.typography
import com.example.bugit.android.theme.BugItTheme
import com.example.bugit.android.theme.rememberWindowSizeClass

/**
 * A primary action button used across the application.
 *
 * This button handles [loading] states by showing a [CircularProgressIndicator]
 * and automatically manages [enabled] logic to prevent multiple clicks. It scales dynamically
 * based on the active device dimensions (Compact, Medium, Large).
 *
 * @param modifier Applied to the root Button layout.
 * @param text The label text to display.
 * @param iconRes Optional drawable resource ID to display a leading icon.
 * @param onClick Triggered when the button is pressed (ignored if [loading] or not [enabled]).
 * @param enabled Controls the touch interactivity and visual state.
 * @param loading When true, hides the text/icon and shows a progress spinner.
 * @param containerColor The background color when the button is active.
 * @param contentColor The text/icon color when the button is active.
 * @param disabledContainerColor The background color when disabled or loading.
 * @param disabledContentColor The text/icon color when disabled or loading.
 * @param fontWeight Optional [FontWeight] to override the default [textStyle].
 * @param textStyle The typography style for the button label.
 * @param progressColor The color of the loading spinner.
 */
@Composable
internal fun AppButton(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes iconRes: Int? = null,
    onClick: () -> Unit,
    enabled: Boolean = true,
    loading: Boolean = false,
    containerColor: Color = colors.primary,
    contentColor: Color = colors.onPrimary,
    disabledContainerColor: Color = colors.onSurface.copy(alpha = 0.12f),
    disabledContentColor: Color = colors.onSurface.copy(alpha = 0.38f),
    fontWeight: FontWeight = FontWeight.Bold,
    textStyle: TextStyle = typography.bodyLarge,
    progressColor: Color = colors.onPrimary,
) {
    val isButtonEnabled = enabled && !loading

    Button(
        shape = shapes.smallMedium,
        modifier = modifier
            .fillMaxWidth()
            .height(dimens.extraLarge + dimens.extraSmall),
        onClick = onClick,
        enabled = isButtonEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = disabledContainerColor,
            disabledContentColor = disabledContentColor
        ),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.graphicsLayer { alpha = if (loading) 0f else 1f }
            ) {
                if (iconRes != null) {
                    Icon(
                        painter = painterResource(id = iconRes),
                        contentDescription = null,
                        modifier = Modifier.size(dimens.medium)
                    )
                    Spacer(modifier = Modifier.width(dimens.small))
                }

                AppText(
                    text = text,
                    color = if (isButtonEnabled) contentColor else disabledContentColor,
                    style = textStyle,
                    fontWeight = fontWeight,
                )
            }

            if (loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(dimens.medium),
                    color = progressColor,
                    strokeWidth = dimens.extraSmall / 2
                )
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES,)
@Composable
private fun PreviewAppButtonAllStates() {
    BugItTheme(rememberWindowSizeClass()) {
        Surface {
            Column(
                modifier = Modifier.padding(dimens.smallMedium),
                verticalArrangement = Arrangement.spacedBy(dimens.smallMedium)
            ) {
                AppButton(
                    text = stringResource(R.string.submit_bug_report),
                    onClick = {}
                )

                AppButton(
                    text = stringResource(R.string.submit_bug_report),
                    iconRes = R.drawable.send,
                    onClick = {}
                )

                AppButton(
                    text = stringResource(R.string.submit_bug_report),
                    loading = true,
                    onClick = {}
                )

                AppButton(
                    text = stringResource(R.string.submit_bug_report),
                    enabled = false,
                    onClick = {}
                )
            }
        }
    }
}