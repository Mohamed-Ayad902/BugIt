package com.example.bugit.screen.report_bug

import android.content.res.Configuration
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.ImageOnly
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bugit.R
import com.example.bugit.android.extensions.ObserveAsState
import com.example.bugit.android.messages.toUserMessageRes
import com.example.bugit.android.reusable_components.AppButton
import com.example.bugit.android.reusable_components.LocalSnackbarController
import com.example.bugit.android.reusable_components.SnackbarType
import com.example.bugit.android.theme.AppTheme.colors
import com.example.bugit.android.theme.AppTheme.dimens
import com.example.bugit.android.theme.BugItTheme
import com.example.bugit.android.theme.rememberWindowSizeClass
import com.example.bugit.screen.report_bug.components.BugForms
import com.example.bugit.screen.report_bug.components.FooterText
import com.example.bugit.screen.report_bug.components.HeaderSection
import com.example.bugit.screen.report_bug.viewmodel.ReportBugEvents
import com.example.bugit.screen.report_bug.viewmodel.ReportBugFields
import com.example.bugit.screen.report_bug.viewmodel.ReportBugFields.Description
import com.example.bugit.screen.report_bug.viewmodel.ReportBugIntents.SubmitReport
import com.example.bugit.screen.report_bug.viewmodel.ReportBugIntents.UpdateField
import com.example.bugit.screen.report_bug.viewmodel.ReportBugVM
import com.example.core.model.ReportingDestination
import com.example.core_contracts.validation.ValidationResult

@Composable
fun ReportBugScreen(
    viewmodel: ReportBugVM = hiltViewModel(),
) {
    val state by viewmodel.viewState.collectAsStateWithLifecycle()
    val snackbarController = LocalSnackbarController.current
    val context = LocalContext.current.applicationContext

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        uri?.let {
            viewmodel.sendIntent(
                UpdateField(
                    ReportBugFields.Image,
                    it.toString()
                )
            )
        }
    }

    ObserveAsState(viewmodel.eventFlow) { event ->
        when (event) {
            ReportBugEvents.Queued -> snackbarController.show(
                message = context.getString(R.string.bug_report_queued_uploading_in_background),
                type = SnackbarType.INFO
            )

            is ReportBugEvents.Failure -> snackbarController.show(
                message = context.getString(event.exception.toUserMessageRes()),
                type = SnackbarType.ERROR
            )

            is ReportBugEvents.Success -> snackbarController.show(
                message = context.getString(R.string.bug_reported_successfully),
                type = SnackbarType.SUCCESS
            )
        }
    }

    ReportBugContent(
        activeTracker = state.activeTracker,
        imageUri = state.imageUri.value,
        imageValidation = state.imageUri.result,
        description = state.description.value,
        descriptionValidation = state.description.result,
        isLoading = state.isLoading,
        isLoadingInBackground = state.isLoadingInBackground,
        onImagePickRequest = { photoPickerLauncher.launch(PickVisualMediaRequest(ImageOnly)) },
        onDescriptionChange = { viewmodel.sendIntent(UpdateField(Description, it)) },
        onSubmitClick = { viewmodel.sendIntent(SubmitReport) },
        onImageClearRequest = { viewmodel.sendIntent(UpdateField(ReportBugFields.Image, "")) }
    )
}

@Composable
private fun ReportBugContent(
    activeTracker: ReportingDestination?,
    imageUri: String,
    imageValidation: ValidationResult,
    description: String,
    descriptionValidation: ValidationResult,
    isLoading: Boolean,
    isLoadingInBackground: Boolean,
    onImagePickRequest: () -> Unit,
    onDescriptionChange: (String) -> Unit,
    onImageClearRequest: () -> Unit,
    onSubmitClick: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(horizontal = dimens.smallMedium)
            .padding(top = dimens.smallMedium)
            .verticalScroll(rememberScrollState())
            .imePadding()
    ) {
        HeaderSection(activeTracker, isLoadingInBackground)
        AnimatedVisibility(
            visible = isLoadingInBackground,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimens.smallMedium)
                    .height(dimens.extraSmall),
                color = colors.primary,
                trackColor = colors.primary.copy(alpha = 0.3f)
            )
        }

        Spacer(Modifier.height(dimens.large))
        BugForms(
            imageUri = imageUri,
            description = description,
            onImagePickRequest = onImagePickRequest,
            onDescriptionChange = onDescriptionChange,
            imageValidation = imageValidation,
            descriptionValidation = descriptionValidation,
            onImageClearRequest = onImageClearRequest
        )
        Spacer(Modifier.height(dimens.large))
        AppButton(
            text = stringResource(R.string.submit_bug_report),
            onClick = onSubmitClick,
            iconRes = R.drawable.send,
            loading = isLoading
        )
        Spacer(Modifier.height(dimens.smallMedium))
        FooterText()
        Spacer(Modifier.height(dimens.huge))
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    BugItTheme(rememberWindowSizeClass()) {
        Surface {
            ReportBugContent(
                activeTracker = ReportingDestination.GOOGLE_SHEETS,
                imageUri = "",
                imageValidation = ValidationResult.Valid,
                description = "",
                descriptionValidation = ValidationResult.Invalid.Empty,
                isLoading = false,
                isLoadingInBackground = true,
                onImagePickRequest = {},
                onDescriptionChange = {},
                onSubmitClick = {},
                onImageClearRequest = {}
            )
        }
    }
}