package com.hydev.schoolcrest.domain.model

import com.hydev.schoolcrest.core.ui.components.StatusType

/**
 * Domain model representing a student attendance record.
 */
data class AttendanceRecord(
    val id: String,
    val schoolId: String,
    val studentId: String,
    val classId: String,
    val date: String,
    val status: StatusType,
    val markedByTeacherUid: String,
    val timestamp: Long = System.currentTimeMillis()
)
