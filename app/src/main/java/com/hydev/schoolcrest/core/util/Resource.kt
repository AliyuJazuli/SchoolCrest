package com.hydev.schoolcrest.core.util

/**
 * Generic class representing the state of asynchronous data operations.
 */
sealed interface Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>
    data class Error(val message: String, val cause: Throwable? = null) : Resource<Nothing>
    object Loading : Resource<Nothing>
}
