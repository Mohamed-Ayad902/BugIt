package com.example.bugit.screen.report_bug.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.bugit.android.base.BaseViewModel
import com.example.bugit.application.SharedImageHandler
import com.example.bugit.screen.report_bug.viewmodel.ReportBugFields.Description
import com.example.bugit.screen.report_bug.viewmodel.ReportBugFields.Image
import com.example.core.feature.bug_reporting.domain.model.BugReportRequest
import com.example.core.feature.bug_reporting.domain.model.SyncStatus.COMPLETED
import com.example.core.feature.bug_reporting.domain.model.SyncStatus.FAILED
import com.example.core.feature.bug_reporting.domain.model.SyncStatus.PENDING
import com.example.core.feature.bug_reporting.domain.model.SyncStatus.UPLOADING
import com.example.core.feature.bug_reporting.domain.usecase.EnqueueBugReportUC
import com.example.core.feature.bug_reporting.domain.usecase.GetActiveTrackerUC
import com.example.core.feature.bug_reporting.domain.usecase.GetBugByIdUC
import com.example.core_contracts.exceptions.BugItExceptions
import com.example.core_contracts.utils.Resource
import com.example.core_contracts.validation.FormField
import com.example.core_contracts.validation.ValidationResult.Valid
import com.example.core_contracts.validation.validateDescription
import com.example.core_contracts.validation.validateImageUri
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.takeWhile
import javax.inject.Inject

@HiltViewModel
class ReportBugVM @Inject constructor(
    private val sharedImageHandler: SharedImageHandler,
    private val getActiveTrackerUC: GetActiveTrackerUC,
    private val enqueueBugReportUC: EnqueueBugReportUC,
    private val getBugById: GetBugByIdUC
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

        enqueueBugReportUC(
            scope = viewModelScope,
            body = BugReportRequest(
                description = currentState.description.value,
                imageUriString = currentState.imageUri.value
            )
        ) { result ->
            when (result) {
                is Resource.Failure -> sendEvent(ReportBugEvents.Failure(result.exception))
                is Resource.Progress -> setState { copy(isLoading = result.loading) }
                is Resource.Success -> {
                    sendEvent(ReportBugEvents.Queued)
                    setState {
                        copy(
                            imageUri = FormField(""),
                            description = FormField(""),
                            isLoadingInBackground = true
                        )
                    }
                    observeBugReportStatus(result.model.id)
                }
            }
        }
    }

    private fun observeBugReportStatus(id: String) {
        getBugById(body = id)
            .onEach { result ->
                when (result) {
                    is Resource.Failure -> {
                        setState { copy(isLoadingInBackground = false) }
                        sendEvent(ReportBugEvents.Failure(result.exception))
                    }
                    is Resource.Progress -> Unit
                    is Resource.Success -> {
                        val updatedBug = result.model ?: return@onEach
                        when (updatedBug.status) {
                            PENDING, UPLOADING -> Unit
                            FAILED -> {
                                setState { copy(isLoadingInBackground = false) }
                                sendEvent(ReportBugEvents.Failure(BugItExceptions.Unknown("",null)))
                            }
                            COMPLETED -> {
                                setState { copy(isLoadingInBackground = false) }
                                sendEvent(ReportBugEvents.Success(updatedBug))
                            }
                        }
                    }
                }
            }
            .takeWhile { result ->
                when (result) {
                    is Resource.Success -> {
                        val bug = result.model
                        if (bug != null) {
                            bug.status == PENDING || bug.status == UPLOADING
                        } else {
                            false
                        }
                    }
                    is Resource.Failure -> false
                    else -> true
                }
            }.launchIn(viewModelScope)
    }
}