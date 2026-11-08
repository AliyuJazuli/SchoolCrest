package com.hydev.schoolcrest.data.repository

import com.hydev.schoolcrest.core.util.Resource
import com.hydev.schoolcrest.domain.model.User
import com.hydev.schoolcrest.domain.repository.AuthRepository

/**
 * Concrete implementation contract for AuthRepository.
 */
class AuthRepositoryImpl : AuthRepository {
    override suspend fun getCurrentUser(): Resource<User?> {
        // Will be wired to Firebase Auth and Room user cache in Phase 6 & 7
        return Resource.Success(null)
    }

    override suspend fun logout() {
        // Will handle session sign-out and local database clear in Phase 6 & 7
    }
}
