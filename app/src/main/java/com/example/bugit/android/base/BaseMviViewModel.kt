package com.example.bugit.android.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal interface State // Represents UI states
internal interface Intent // Represents user intents or actions
internal interface Event // Represents one time events


/**
 * Base ViewModel to handle UI state, user intents, and one-time events.
 *
 * @param S Represents the UI state.
 * @param E Represents one-time events.
 * @param I Represents user intents or actions.
 */
internal abstract class BaseViewModel<S : State, I : Intent, E : Event>(initialState: S) :
    ViewModel() {

    // Holds the UI state
    private val _viewState = MutableStateFlow(initialState)
    val viewState: StateFlow<S> = _viewState.asStateFlow()

    // Holds one-time events
    private val _eventChannel = Channel<E>(Channel.BUFFERED)
    val eventFlow: Flow<E> = _eventChannel.receiveAsFlow()

    // Processes user intents
    private val intentChannel = Channel<I>(Channel.UNLIMITED)
    private val intents = intentChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            intents.collect { handleIntent(it) }
        }
    }

    /**
     * Updates the state atomically.
     */
    protected fun setState(reducer: S.() -> S) {
        _viewState.update { it.reducer() }
    }

    /**
     * Sends a one-time event.
     * @param event The event to be sent.
     */
    protected fun sendEvent(event: E) {
        viewModelScope.launch {
            _eventChannel.send(event)
        }
    }

    /**
     * To be implemented by child ViewModels to handle specific intents.
     * @param intent The user action or event to process.
     */
    protected abstract fun handleIntent(intent: I)

    /**
     * Called from the UI to dispatch user intents.
     * @param intent The user action to process.
     */
    fun sendIntent(intent: I) {
        viewModelScope.launch {
            intentChannel.send(intent)
        }
    }
}