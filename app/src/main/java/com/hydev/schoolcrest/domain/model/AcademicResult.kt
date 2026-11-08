package com.hydev.schoolcrest.domain.model

/**
 * Domain model representing an academic test or examination mark.
 * Note: Academic result updates strictly require an active network connection (online-only).
 */
data class AcademicResult(
    val id: String,
    val schoolId: String,
    val studentId: String,
    val subjectId: String,
    val classId: String,
    val session: String,
    val term: String,
    val marksObtained: Double,
    val maxMarks: Double = 100.0,
    val isPublished: Boolean = false,
    val enteredByTeacherUid: String,
    val lastUpdatedTimestamp: Long = System.currentTimeMillis()
)
