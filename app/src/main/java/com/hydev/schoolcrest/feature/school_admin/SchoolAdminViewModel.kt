package com.hydev.schoolcrest.feature.school_admin

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class TeacherUiModel(
    val id: String,
    val fullName: String,
    val email: String,
    val isAuthorized: Boolean,
    val assignedClass: String?,
    val assignedSubjects: List<String>
)

data class StudentUiModel(
    val id: String,
    val fullName: String,
    val className: String,
    val parentName: String,
    val parentPhoneNumber: String
)

data class ClassUiModel(
    val id: String,
    val className: String,
    val classTeacher: String,
    val studentCount: Int,
    val subjects: List<String>
)

data class SchoolAdminUiState(
    val teachers: List<TeacherUiModel> = listOf(
        TeacherUiModel("t1", "Mallam Ibrahim Musa", "ibrahim.m@school.com", true, "JSS 1A", listOf("Mathematics", "Physics")),
        TeacherUiModel("t2", "Aisha Bello", "aisha.b@school.com", false, null, listOf("English Language")),
        TeacherUiModel("t3", "Usman Dan Fodio", "usman.d@school.com", true, "SS 2B", listOf("History", "Civic Education"))
    ),
    val students: List<StudentUiModel> = listOf(
        StudentUiModel("s1", "Abubakar Sadiq", "JSS 1A", "Alhaji Sadiq", "+234 803 111 2223"),
        StudentUiModel("s2", "Zainab Ahmed", "SS 2B", "Hajiya Maryam", "+234 805 444 5556")
    ),
    val classes: List<ClassUiModel> = listOf(
        ClassUiModel("c1", "JSS 1A", "Mallam Ibrahim Musa", 35, listOf("Mathematics", "English", "Physics")),
        ClassUiModel("c2", "SS 2B", "Usman Dan Fodio", 40, listOf("History", "Civic Education", "Biology"))
    ),
    val teacherSearchQuery: String = "",
    val studentSearchQuery: String = "",
    val teacherFilterAuthorizedOnly: Boolean = false,
    val showEnrollStudentDialog: Boolean = false,
    val showCreateClassDialog: Boolean = false,
    val feedbackMessage: String? = null
)

@HiltViewModel
class SchoolAdminViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(SchoolAdminUiState())
    val uiState: StateFlow<SchoolAdminUiState> = _uiState.asStateFlow()

    fun updateTeacherSearch(query: String) {
        _uiState.update { it.copy(teacherSearchQuery = query) }
    }

    fun updateStudentSearch(query: String) {
        _uiState.update { it.copy(studentSearchQuery = query) }
    }

    fun toggleTeacherFilter() {
        _uiState.update { it.copy(teacherFilterAuthorizedOnly = !it.teacherFilterAuthorizedOnly) }
    }

    fun authorizeTeacher(teacherId: String, authorize: Boolean) {
        _uiState.update { state ->
            val updated = state.teachers.map { teacher ->
                if (teacher.id == teacherId) teacher.copy(isAuthorized = authorize) else teacher
            }
            state.copy(
                teachers = updated,
                feedbackMessage = if (authorize) "Teacher authorized successfully (Backend persistence pending)" else "Teacher authorization revoked"
            )
        }
    }

    fun assignTeacherToClass(teacherId: String, className: String) {
        _uiState.update { state ->
            val updated = state.teachers.map { teacher ->
                if (teacher.id == teacherId) teacher.copy(assignedClass = className, isAuthorized = true) else teacher
            }
            state.copy(
                teachers = updated,
                feedbackMessage = "Teacher assigned to $className (Backend persistence pending)"
            )
        }
    }

    fun enrollStudent(fullName: String, className: String, parentName: String, parentPhone: String) {
        if (fullName.isBlank() || className.isBlank() || parentPhone.isBlank()) {
            _uiState.update { it.copy(feedbackMessage = "Please fill in all required student enrollment fields.") }
            return
        }
        val newStudent = StudentUiModel(
            id = "s_${System.currentTimeMillis()}",
            fullName = fullName,
            className = className,
            parentName = parentName.ifBlank { "Guardian" },
            parentPhoneNumber = parentPhone
        )
        _uiState.update { state ->
            state.copy(
                students = state.students + newStudent,
                showEnrollStudentDialog = false,
                feedbackMessage = "Student $fullName enrolled successfully into $className (Backend persistence pending)"
            )
        }
    }

    fun createClass(className: String, classTeacher: String, subjects: List<String>) {
        if (className.isBlank()) {
            _uiState.update { it.copy(feedbackMessage = "Class name cannot be empty.") }
            return
        }
        val newClass = ClassUiModel(
            id = "c_${System.currentTimeMillis()}",
            className = className,
            classTeacher = classTeacher.ifBlank { "Unassigned" },
            studentCount = 0,
            subjects = subjects.ifEmpty { listOf("General") }
        )
        _uiState.update { state ->
            state.copy(
                classes = state.classes + newClass,
                showCreateClassDialog = false,
                feedbackMessage = "Class $className created successfully (Backend persistence pending)"
            )
        }
    }

    fun setShowEnrollDialog(show: Boolean) {
        _uiState.update { it.copy(showEnrollStudentDialog = show) }
    }

    fun setShowCreateClassDialog(show: Boolean) {
        _uiState.update { it.copy(showCreateClassDialog = show) }
    }

    fun clearFeedback() {
        _uiState.update { it.copy(feedbackMessage = null) }
    }
}
