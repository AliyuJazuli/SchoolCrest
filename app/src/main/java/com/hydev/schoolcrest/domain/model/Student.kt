package com.hydev.schoolcrest.domain.model

/**
 * Domain model representing a student in a school.
 */
data class Student(
    val id: String,
    val schoolId: String,
    val fullName: String,
    val classId: String,
    val parentPhoneNumber: String,
    val parentUid: String? = null,
    val isArchived: Boolean = false
)
