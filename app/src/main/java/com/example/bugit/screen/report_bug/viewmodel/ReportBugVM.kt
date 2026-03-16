package com.example.bugit.screen.report_bug.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.bugit.android.base.BaseViewModel
import com.example.bugit.application.SharedImageHandler
import com.example.bugit.screen.report_bug.viewmodel.ReportBugFields.Description
import com.example.bugit.screen.report_bug.viewmodel.ReportBugFields.Image
import com.example.core.feature.bug_reporting.domain.model.BugReportRequest
import com.example.core.feature.bug_reporting.domain.usecase.GetActiveTrackerUC
import com.example.core.feature.bug_reporting.domain.usecase.ReportBugUC
import com.example.core_contracts.utils.Resource
import com.example.core_contracts.validation.FormField
import com.example.core_contracts.validation.ValidationResult.Valid
import com.example.core_contracts.validation.validateDescription
import com.example.core_contracts.validation.validateImageUri
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportBugVM @Inject constructor(
    private val sharedImageHandler: SharedImageHandler,
    private val getActiveTrackerUC: GetActiveTrackerUC,
    private val submitBugReportUC: ReportBugUC
) : BaseViewModel<ReportBugState, ReportBugIntents, ReportBugEvents>(ReportBugState()) {

    init {
        sendIntent(ReportBugIntents.GetActiveTracker)
        viewModelScope.launch {
            sharedImageHandler.sharedImageUri.collect { sharedUri ->
                if (!sharedUri.isNullOrBlank()) {
                    sendIntent(ReportBugIntents.UpdateField(Image, sharedUri))
                    sharedImageHandler.consumeSharedImage()
                }
            }
        }
    }

    override fun handleIntent(intent: ReportBugIntents) {
        when (intent) {
            ReportBugIntents.GetActiveTracker -> fetchActiveTracker()
            is ReportBugIntents.UpdateField -> updateField(intent.field, intent.value)
            ReportBugIntents.SubmitReport -> submitReport()
        }
    }

    private fun updateField(field: ReportBugFields, value: String) = setState {
        when (field) {
            Image -> copy(imageUri = FormField(value, value.validateImageUri()))
            Description -> copy(description = FormField(value, value.validateDescription()))
        }
    }


    private fun fetchActiveTracker() {
        setState { copy(activeTracker = getActiveTrackerUC()) }
    }

    private fun submitReport() {
        val currentState = viewState.value
        val imageValidation = currentState.imageUri.value.validateImageUri()
        val descValidation = currentState.description.value.validateDescription()

        setState {
            copy(
                imageUri = imageUri.copy(result = imageValidation),
                description = description.copy(result = descValidation)
            )
        }

        if (imageValidation !is Valid || descValidation !is Valid) return

        submitBugReportUC(
            scope = viewModelScope,
            body = BugReportRequest(
                description = currentState.description.value,
                imageUriString = currentState.imageUri.value
            )
        ) {
            when (it) {
                is Resource.Failure -> sendEvent(ReportBugEvents.Failure(it.exception))
                is Resource.Progress -> setState { copy(isLoading = it.loading) }
                is Resource.Success -> {
                    setState { copy(imageUri = FormField(""), description = FormField("")) }
                    sendEvent(ReportBugEvents.Success(it.model))
                }
            }
        }
    }
}