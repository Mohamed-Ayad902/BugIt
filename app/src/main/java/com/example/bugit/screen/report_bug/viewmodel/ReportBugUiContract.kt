package com.example.bugit.screen.report_bug.viewmodel

import com.example.bugit.android.base.Event
import com.example.bugit.android.base.Intent
import com.example.bugit.android.base.State
import com.example.core.feature.bug_reporting.domain.model.Bug
import com.example.core.model.ReportingDestination
import com.example.core_contracts.exceptions.BugItExceptions
import com.example.core_contracts.validation.FieldType
import com.example.core_contracts.validation.FormField

data class ReportBugState(
    val activeTracker: ReportingDestination? = null,
    val imageUri: FormField<String> = FormField(""),
    val description: FormField<String> = FormField(""),
    val isLoading: Boolean = false,
) : State

enum class ReportBugFields : FieldType {
    Image,
    Description,
}

sealed interface ReportBugIntents : Intent {
    data object GetActiveTracker : ReportBugIntents
    data class UpdateField(val field: ReportBugFields, val value: String) : ReportBugIntents
    data object SubmitReport : ReportBugIntents
}

sealed interface ReportBugEvents : Event {
    data class Failure(val exception: BugItExceptions) : ReportBugEvents
    data class Success(val bug: Bug) : ReportBugEvents
}