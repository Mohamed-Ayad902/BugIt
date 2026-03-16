package com.example.bugit.application

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedImageHandler @Inject constructor() {
    private val _sharedImageUri = MutableStateFlow<String?>(null)
    val sharedImageUri: StateFlow<String?> = _sharedImageUri.asStateFlow()

    fun updateSharedImage(uri: String) {
        _sharedImageUri.value = uri
    }

    fun consumeSharedImage() {
        _sharedImageUri.value = null
    }
}