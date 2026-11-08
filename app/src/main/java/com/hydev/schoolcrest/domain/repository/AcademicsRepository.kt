package com.hydev.schoolcrest.domain.repository

import com.hydev.schoolcrest.core.util.Resource
import com.hydev.schoolcrest.domain.model.AcademicResult

/**
 * Interface defining academic result management operations.
 * Note: Result updates require active online connectivity and must not be queued offline.
 */
interface AcademicsRepository {
    suspend fun saveAcademicResult(result: AcademicResult): Resource<Unit>
    suspend fun publishResultsForClass(schoolId: String, classId: String, term: String): Resource<Unit>
    suspend fun getPublishedResultsForStudent(studentId: String): Resource<List<AcademicResult>>
}
