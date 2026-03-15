package com.example.bugit.android.messages

import androidx.annotation.StringRes
import com.example.bugit.R
import com.example.core_contracts.exceptions.BugItExceptions

@StringRes
fun BugItExceptions.toUserMessageRes(): Int = when (this) {
    is BugItExceptions.ValidationException -> R.string.error_validation
    is BugItExceptions.LocalIOOperation -> R.string.error_local_io
    is BugItExceptions.SerializationException -> R.string.error_serialization

    is BugItExceptions.Network.Connection -> R.string.error_network_connection
    is BugItExceptions.Network.BadRequest -> R.string.error_bad_request
    is BugItExceptions.Network.Unauthorized -> R.string.error_unauthorized
    is BugItExceptions.Network.Forbidden -> R.string.error_forbidden
    is BugItExceptions.Network.NotFound -> R.string.error_not_found
    is BugItExceptions.Network.Server -> R.string.error_server
    is BugItExceptions.Network.Unhandled -> R.string.error_unhandled

    is BugItExceptions.Unknown -> R.string.error_unknown
}