package com.hydev.schoolcrest.domain.model

import com.hydev.schoolcrest.core.model.UserRole

/**
 * Domain model representing a SchoolCrest user.
 */
data class User(
    val uid: String,
    val email: String,
    val fullName: String,
    val role: UserRole,
    val schoolId: String? = null,
    val phoneNumber: String? = null,
    val isAuthorized: Boolean = false
)
