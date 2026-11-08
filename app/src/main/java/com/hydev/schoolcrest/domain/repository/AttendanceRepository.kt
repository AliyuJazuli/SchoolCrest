package com.hydev.schoolcrest.domain.repository

import com.hydev.schoolcrest.core.util.Resource
import com.hydev.schoolcrest.domain.model.AttendanceRecord

/**
 * Interface defining attendance persistence and retrieval operations.
 */
interface AttendanceRepository {
    suspend fun markAttendance(record: AttendanceRecord): Resource<Unit>
    suspend fun getStudentAttendanceHistory(studentId: String): Resource<List<AttendanceRecord>>
}
