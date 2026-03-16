package com.example.bugit.screen.bugs_history.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.bugit.android.base.BaseViewModel
import com.example.bugit.common.mockup.dummyBugs
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@HiltViewModel
class BugsHistoryVM @Inject constructor() :
    BaseViewModel<BugsHistoryState, BugsHistoryIntents, BugsHistoryEvents>(BugsHistoryState()) {

    init {
        handleIntent(BugsHistoryIntents.GetHistory)
    }

    override fun handleIntent(intent: BugsHistoryIntents) {
        when (intent) {
            BugsHistoryIntents.GetHistory -> getBugs()
        }
    }

    private fun getBugs() {
        viewModelScope.launch {
            setState { copy(isLoading = true, bugs = emptyList()) }
            delay(3500)

            val successProbability = Random.nextInt(100)
            val result = if (successProbability < 90) dummyBugs else emptyList()

            setState { copy(isLoading = false, bugs = result) }
        }
    }
}