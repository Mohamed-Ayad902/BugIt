package com.example.bugit.android.reusable_components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.bugit.R
import com.example.bugit.android.theme.AppTheme.colors
import com.example.bugit.android.theme.AppTheme.dimens
import com.example.bugit.android.theme.AppTheme.shapes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

enum class SnackbarType { SUCCESS, ERROR, INFO }

data class AppSnackbarVisuals(
    override val message: String,
    override val actionLabel: String? = null,
    override val duration: SnackbarDuration = SnackbarDuration.Long,
    override val withDismissAction: Boolean = true,
    val type: SnackbarType = SnackbarType.INFO,
) : SnackbarVisuals

@Composable
fun AppSnackBar(data: SnackbarData) {
    val visuals = data.visuals as? AppSnackbarVisuals
    val (backgroundColor, icon) = when (visuals?.type) {
        SnackbarType.ERROR -> colors.errorContainer to R.drawable.ic_error
        SnackbarType.SUCCESS -> colors.tertiaryContainer to R.drawable.success
        else -> colors.surfaceVariant to R.drawable.ic_warning
    }

    Snackbar(
        modifier = Modifier.padding(dimens.small.plus(dimens.extraSmall)),
        containerColor = backgroundColor,
        contentColor = contentColorFor(backgroundColor),
        shape = shapes.smallMedium,
        action = {
            data.visuals.actionLabel?.let { label ->
                TextButton(
                    onClick = { data.performAction() }
                ) {
                    AppText(label,)
                }
            }
        }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(icon), null)
            Spacer(Modifier.width(dimens.small.plus(dimens.extraSmall)))
            AppText(data.visuals.message,)
        }
    }
}


val LocalSnackbarController = staticCompositionLocalOf<SnackbarController> {
    error("No SnackbarController provided")
}

class SnackbarController(
    val hostState: SnackbarHostState,
    private val scope: CoroutineScope,
) {
    fun show(
        message: String,
        actionLabel: String? = null,
        duration: SnackbarDuration = SnackbarDuration.Short,
        withDismissAction: Boolean = true,
        type: SnackbarType = SnackbarType.INFO,
        onAction: (() -> Unit)? = null
    ) {
        scope.launch {
            // dismiss current snackbar immediately if a new one is triggered
            hostState.currentSnackbarData?.dismiss()

            val result = hostState.showSnackbar(
                AppSnackbarVisuals(
                    message = message,
                    type = type,
                    actionLabel = actionLabel,
                    duration = duration,
                    withDismissAction = withDismissAction
                )
            )
            if (result == SnackbarResult.ActionPerformed) onAction?.invoke()
        }
    }
}