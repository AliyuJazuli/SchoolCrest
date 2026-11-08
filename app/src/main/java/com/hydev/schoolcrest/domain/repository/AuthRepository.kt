package com.hydev.schoolcrest.domain.repository

import com.hydev.schoolcrest.core.util.Resource
import com.hydev.schoolcrest.domain.model.User

/**
 * Interface defining authentication and identity contracts.
 */
interface AuthRepository {
    suspend fun getCurrentUser(): Resource<User?>
    suspend fun logout()
}
