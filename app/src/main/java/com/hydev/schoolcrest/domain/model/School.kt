package com.hydev.schoolcrest.domain.model

/**
 * Domain model representing a registered school entity.
 */
data class School(
    val id: String,
    val name: String,
    val verificationCode: String,
    val isVerified: Boolean = false,
    val isSuspended: Boolean = false,
    val address: String? = null,
    val adminUid: String? = null
)
