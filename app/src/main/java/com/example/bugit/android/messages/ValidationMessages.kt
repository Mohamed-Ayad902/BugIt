package com.example.bugit.android.messages

import androidx.annotation.StringRes
import com.example.bugit.R
import com.example.core_contracts.validation.ValidationResult

@StringRes
fun ValidationResult.toUserMessageRes(): Int? = when(this) {
    ValidationResult.Invalid.Empty -> R.string.validation_error_empty
    ValidationResult.Invalid.ImageNotSupported -> R.string.validation_error_image_type
    is ValidationResult.Invalid.ImageTooLarge -> R.string.validation_error_image_size
    is ValidationResult.Invalid.TooLong -> R.string.validation_error_too_long
    ValidationResult.Valid -> null
}