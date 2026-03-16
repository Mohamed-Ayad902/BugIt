package com.example.bugit.android.reusable_components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import com.example.bugit.android.messages.toUserMessageRes
import com.example.bugit.android.theme.AppTheme.colors
import com.example.bugit.android.theme.AppTheme.dimens
import com.example.bugit.android.theme.AppTheme.typography
import com.example.core_contracts.validation.ValidationResult

/**
 * A reusable wrapper around [Text] to enforce the application's design system typography.
 *
 * @param text The text content.
 * @param color Text color. Defaults to [MaterialTheme.colorScheme.onBackground].
 * @param style Text style (font, size, weight). Defaults to [MaterialTheme.typography.bodyMedium].
 * @param fontWeight Optional font weight override (e.g., FontWeight.Bold).
 * @param textAlign Text alignment within its container.
 * @param modifier The modifier for layout adjustments.
 */
@Composable
internal fun AppText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    fontWeight: FontWeight? = null,
    fontSize: TextUnit? = null,
    useSystemFont: Boolean = false, // new flag to bypass Emprint font
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    fontStyle: FontStyle = FontStyle.Normal,
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = fontSize ?: style.fontSize,
        style = style.copy(
            fontFamily = if (useSystemFont) FontFamily.Default else style.fontFamily,
            fontWeight = fontWeight ?: style.fontWeight
        ),
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow
    )
}

/** * Helper composable to cleanly animate and display validation errors.
 */
@Composable
fun ErrorText(validationResult: ValidationResult) {
    val errorMessage = validationResult.toUserMessageRes()
    AnimatedVisibility(visible = errorMessage != null) {
        if (errorMessage != null) {
            AppText(
                text = stringResource(errorMessage),
                color = colors.error,
                style = typography.labelSmall,
                modifier = Modifier.padding(top = dimens.extraSmall, start = dimens.extraSmall)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppTextPreview() {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(dimens.small)
    ) {
        AppText("Standard Text",)
        AppText("Bold Text", fontWeight = FontWeight.Bold,)
        AppText("Header Text", style = MaterialTheme.typography.titleMedium,)
        AppText("System default text", useSystemFont = true,)
        AppText("System bold", fontWeight = FontWeight.Bold, useSystemFont = true,)
    }
}