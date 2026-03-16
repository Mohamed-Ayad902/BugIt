package com.example.bugit.screen.bugs_history.viewmodel

import com.example.bugit.android.base.Event
import com.example.bugit.android.base.Intent
import com.example.bugit.android.base.State
import com.example.core.feature.bug_reporting.domain.model.Bug
import com.example.core_contracts.exceptions.BugItExceptions

data class BugsHistoryState(
    val isLoading: Boolean = false,
    val bugs: List<Bug> = emptyList()
) : State

sealed interface BugsHistoryIntents : Intent {
    data object GetHistory : BugsHistoryIntents
}

sealed interface BugsHistoryEvents : Event {
    data class Failure(val exception: BugItExceptions) : BugsHistoryEvents
}