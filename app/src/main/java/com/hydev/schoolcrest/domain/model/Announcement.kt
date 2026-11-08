package com.hydev.schoolcrest.domain.model

/**
 * Domain model representing school or class announcements.
 */
data class Announcement(
    val id: String,
    val schoolId: String,
    val authorUid: String,
    val authorName: String,
    val title: String,
    val content: String,
    val targetAudience: String, // "ALL", "TEACHERS", "PARENTS", or classId
    val timestamp: Long = System.currentTimeMillis()
)
