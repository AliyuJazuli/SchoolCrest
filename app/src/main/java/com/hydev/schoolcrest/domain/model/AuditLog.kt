package com.hydev.schoolcrest.domain.model

/**
 * Domain model representing an append-only administrative audit log.
 */
data class AuditLog(
    val id: String,
    val schoolId: String?,
    val actorUid: String,
    val actorName: String,
    val action: String,
    val targetRecord: String,
    val timestamp: Long = System.currentTimeMillis()
)
