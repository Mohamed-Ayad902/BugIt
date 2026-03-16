package com.example.bugit.screen.report_bug.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.bugit.R
import com.example.bugit.android.reusable_components.AppText
import com.example.bugit.android.reusable_components.BugItTextField
import com.example.bugit.android.reusable_components.ErrorText
import com.example.bugit.android.reusable_components.dashedBorder
import com.example.bugit.android.theme.AppTheme.colors
import com.example.bugit.android.theme.AppTheme.dimens
import com.example.bugit.android.theme.AppTheme.orientation
import com.example.bugit.android.theme.AppTheme.shapes
import com.example.bugit.android.theme.AppTheme.typography
import com.example.bugit.android.theme.BugItTheme
import com.example.bugit.android.theme.Orientation
import com.example.bugit.android.theme.rememberWindowSizeClass
import com.example.core_contracts.validation.ValidationResult

@Composable
fun BugForms(
    imageUri: String,
    imageValidation: ValidationResult,
    description: String,
    descriptionValidation: ValidationResult,
    onImagePickRequest: () -> Unit,
    onDescriptionChange: (String) -> Unit,
    onImageClearRequest: () -> Unit
) {
    val isLandscape = orientation == Orientation.Landscape

    Column(Modifier.fillMaxWidth()) {

        if (isLandscape) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(dimens.large)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    ImagePickerSection(
                        imageUri = imageUri,
                        validationResult = imageValidation,
                        onImagePickRequest = onImagePickRequest,
                        onImageClearRequest = onImageClearRequest
                    )
                }
                Box(modifier = Modifier.weight(1f)) {
                    DescriptionSection(
                        description = description,
                        validationResult = descriptionValidation,
                        onDescriptionChange = onDescriptionChange
                    )
                }
            }
        } else {
            ImagePickerSection(
                imageUri = imageUri,
                validationResult = imageValidation,
                onImagePickRequest = onImagePickRequest,
                onImageClearRequest = onImageClearRequest
            )
            Spacer(Modifier.height(dimens.large))
            DescriptionSection(
                description = description,
                validationResult = descriptionValidation,
                onDescriptionChange = onDescriptionChange
            )
        }

        Spacer(Modifier.height(dimens.mediumLarge))
        DropDowns()
    }
}


@Composable
private fun ImagePickerSection(
    imageUri: String,
    validationResult: ValidationResult,
    onImagePickRequest: () -> Unit,
    onImageClearRequest: () -> Unit
) {
    Column(Modifier.fillMaxWidth()) {
        AppText(
            text = stringResource(R.string.visual_evidence),
            style = typography.bodySmall,
            color = colors.onSurface.copy(alpha = 0.6f),
            fontWeight = FontWeight.Bold,
        )

        val isError = validationResult is ValidationResult.Invalid
        val borderColor = if (isError) colors.error else colors.onSurface.copy(alpha = 0.5f)

        val baseModifier = Modifier
            .padding(top = dimens.small)
            .fillMaxWidth()
            .height(dimens.huge * 3)
            .clip(shapes.smallMedium)
            .background(colors.surface)

        val interactiveModifier = if (imageUri.isEmpty())
            baseModifier.clickable { onImagePickRequest() }
         else baseModifier

        Column(
            modifier = interactiveModifier.dashedBorder(
                color = borderColor,
                strokeWidth = dimens.extraSmall / 2,
                cornerRadius = dimens.smallMedium
            ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (imageUri.isNotEmpty()) {
                Box(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = stringResource(R.string.visual_evidence),
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    Row(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(dimens.small),
                        horizontalArrangement = Arrangement.spacedBy(dimens.small)
                    ) {
                        // Edit Button
                        Box(
                            modifier = Modifier
                                .size(dimens.mediumLarge)
                                .background(colors.surface.copy(alpha = 0.85f), CircleShape)
                                .clickable { onImagePickRequest() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = android.R.drawable.ic_menu_edit),
                                contentDescription = "Change Image",
                                tint = colors.primary,
                                modifier = Modifier.size(dimens.medium)
                            )
                        }

                        // Delete Button
                        Box(
                            modifier = Modifier
                                .size(dimens.mediumLarge)
                                .background(colors.surface.copy(alpha = 0.85f), CircleShape)
                                .clickable { onImageClearRequest() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = android.R.drawable.ic_menu_delete),
                                contentDescription = "Remove Image",
                                tint = colors.error,
                                modifier = Modifier.size(dimens.medium)
                            )
                        }
                    }
                }
            } else {
                // Placeholder UI remains exactly the same
                Box(
                    Modifier
                        .background(colors.primary.copy(alpha = 0.15f), CircleShape)
                        .padding(dimens.medium),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.gallery),
                        contentDescription = stringResource(R.string.upload_screenshot),
                        tint = colors.primary,
                        modifier = Modifier.size(dimens.mediumLarge)
                    )
                }

                Spacer(modifier = Modifier.height(dimens.medium))

                AppText(
                    text = stringResource(R.string.upload_screenshot),
                    color = colors.onBackground,
                    style = typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(4.dp))

                AppText(
                    text = stringResource(R.string.attach_an_image_of_the_bug_you_encountered),
                    color = colors.onSurface.copy(alpha = 0.5f),
                    style = typography.bodyMedium
                )
            }
        }

        ErrorText(validationResult)
    }
}

@Composable
private fun DescriptionSection(
    description: String,
    validationResult: ValidationResult,
    onDescriptionChange: (String) -> Unit
) {
    Column(Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppText(
                text = stringResource(R.string.bug_description),
                style = typography.bodySmall,
                color = colors.onSurface.copy(alpha = 0.6f),
                fontWeight = FontWeight.Bold,
            )
            AppText(
                text = stringResource(R.string.required),
                style = typography.bodySmall,
                color = colors.onSurface.copy(alpha = 0.4f),
                fontStyle = FontStyle.Italic
            )
        }

        BugItTextField(
            value = description,
            onValueChange = onDescriptionChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimens.small),
            placeholder = stringResource(R.string.provide_details_about_what_went_wrong_steps_to_reproduce_and_expected_behavior)
        )

        ErrorText(validationResult)
    }
}

/** Static UI for severity and category dropdowns */
@Composable
fun DropDowns() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimens.medium)
    ) {
        // severity dropdown (Static UI)
        Column(Modifier.weight(1f)) {
            AppText(
                text = "Severity",
                style = typography.bodySmall,
                color = colors.onSurface.copy(alpha = 0.6f),
                fontWeight = FontWeight.SemiBold
            )
            OutlinedTextField(
                value = "Low",
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .padding(top = dimens.small)
                    .fillMaxWidth(),
                textStyle = typography.bodyMedium.copy(color = colors.onBackground),
                shape = shapes.small,
                trailingIcon = { Icon(painterResource(R.drawable.arrow_down), contentDescription = null, tint = colors.onSurface) },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = colors.surface.copy(alpha = 0.3f),
                    unfocusedBorderColor = colors.onSurface.copy(alpha = 0.1f)
                )
            )
        }

        // category dropdown (Static UI)
        Column(Modifier.weight(1f)) {
            AppText(
                text = "Category",
                style = typography.bodySmall,
                color = colors.onSurface.copy(alpha = 0.6f),
                fontWeight = FontWeight.SemiBold
            )
            OutlinedTextField(
                value = "UI/UX",
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .padding(top = dimens.small)
                    .fillMaxWidth(),
                textStyle = typography.bodyMedium.copy(color = colors.onBackground),
                shape = shapes.small,
                trailingIcon = { Icon(painterResource(R.drawable.arrow_down), contentDescription = null, tint = colors.onSurface) },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = colors.surface.copy(alpha = 0.3f),
                    unfocusedBorderColor = colors.onSurface.copy(alpha = 0.1f)
                )
            )
        }
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
private fun BugFormsPreview() {
    BugItTheme(rememberWindowSizeClass()) {
        Surface(color = MaterialTheme.colorScheme.background) {
            BugForms(
                imageUri = "",
                imageValidation = ValidationResult.Invalid.ImageTooLarge(6),
                description = "This is a sample bug description.",
                descriptionValidation = ValidationResult.Valid,
                onImagePickRequest = {},
                onDescriptionChange = {},
                onImageClearRequest = {}
            )
        }
    }
}