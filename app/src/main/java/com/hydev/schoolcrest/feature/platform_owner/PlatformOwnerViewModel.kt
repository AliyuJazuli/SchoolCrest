package com.hydev.schoolcrest.feature.platform_owner

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class SchoolRecord(
    val id: String,
    val name: String,
    val location: String,
    val status: String, // "Active", "Pending", "Suspended", "Rejected"
    val adminName: String,
    val studentsCount: Int
)

data class UserRecord(
    val id: String,
    val name: String,
    val email: String,
    val role: String, // "School Admin", "Teacher", "Parent"
    val schoolName: String,
    val status: String
)

data class VerificationRecord(
    val id: String,
    val schoolName: String,
    val location: String,
    val documentsSubmitted: List<String>,
    val status: String // "Pending", "Approved", "Rejected"
)

data class AuditLogRecord(
    val id: String,
    val action: String,
    val actor: String,
    val role: String,
    val schoolContext: String,
    val timestamp: String
)

data class PlatformOwnerUiState(
    val schools: List<SchoolRecord> = listOf(
        SchoolRecord("sc1", "Kano Model Secondary School", "Kano, Kano State", "Active", "Dr. Aliyu Sani", 1240),
        SchoolRecord("sc2", "Zaria Excellence Academy", "Zaria, Kaduna State", "Pending", "Hajiya Fatima Bello", 450),
        SchoolRecord("sc3", "Sokoto Heritage College", "Sokoto, Sokoto State", "Suspended", "Malam Lawal", 890),
        SchoolRecord("sc4", "Maiduguri Central School", "Maiduguri, Borno State", "Active", "Ibrahim Zanna", 1100)
    ),
    val users: List<UserRecord> = listOf(
        UserRecord("u1", "Dr. Aliyu Sani", "aliyu.s@kanomodel.edu.ng", "School Admin", "Kano Model Secondary School", "Active"),
        UserRecord("u2", "Mallam Ibrahim Musa", "ibrahim.m@kanomodel.edu.ng", "Teacher", "Kano Model Secondary School", "Active"),
        UserRecord("u3", "Alhaji Sadiq", "sadiq.parent@gmail.com", "Parent", "Kano Model Secondary School", "Active"),
        UserRecord("u4", "Hajiya Fatima Bello", "fatima@zariaexcellence.edu.ng", "School Admin", "Zaria Excellence Academy", "Pending")
    ),
    val verifications: List<VerificationRecord> = listOf(
        VerificationRecord("v1", "Zaria Excellence Academy", "Zaria, Kaduna State", listOf("Ministry Approval Cert", "CAC Registration", "Principal Credentials"), "Pending"),
        VerificationRecord("v2", "Kaduna Premier School", "Kaduna, Kaduna State", listOf("State Board License", "Fire Safety Clearance"), "Pending"),
        VerificationRecord("v3", "Katsina State High", "Katsina, Katsina State", listOf("Ministry Approval Cert", "CAC Registration"), "Approved")
    ),
    val auditLogs: List<AuditLogRecord> = listOf(
        AuditLogRecord("l1", "School Registration Approved", "Super Platform Owner", "Platform Owner", "Zaria Excellence Academy", "Today, 10:45 AM"),
        AuditLogRecord("l2", "Teacher Authorization Granted", "Dr. Aliyu Sani", "School Admin", "Kano Model Secondary School", "Yesterday, 3:20 PM"),
        AuditLogRecord("l3", "Academic Results Published", "Mallam Ibrahim Musa", "Teacher", "Kano Model Secondary School", "2 days ago")
    ),
    val schoolSearchQuery: String = "",
    val schoolStatusFilter: String = "All",
    val userSearchQuery: String = "",
    val userRoleFilter: String = "All",
    val auditSearchQuery: String = "",
    val selectedSchool: SchoolRecord? = null,
    val selectedUser: UserRecord? = null,
    val selectedVerification: VerificationRecord? = null,
    val selectedAuditLog: AuditLogRecord? = null,
    val feedbackMessage: String? = null
)

@HiltViewModel
class PlatformOwnerViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(PlatformOwnerUiState())
    val uiState: StateFlow<PlatformOwnerUiState> = _uiState.asStateFlow()

    fun updateSchoolSearch(query: String) {
        _uiState.update { it.copy(schoolSearchQuery = query) }
    }

    fun updateSchoolStatusFilter(status: String) {
        _uiState.update { it.copy(schoolStatusFilter = status) }
    }

    fun updateUserSearch(query: String) {
        _uiState.update { it.copy(userSearchQuery = query) }
    }

    fun updateUserRoleFilter(role: String) {
        _uiState.update { it.copy(userRoleFilter = role) }
    }

    fun updateAuditSearch(query: String) {
        _uiState.update { it.copy(auditSearchQuery = query) }
    }

    fun selectSchool(school: SchoolRecord?) {
        _uiState.update { it.copy(selectedSchool = school) }
    }

    fun selectUser(user: UserRecord?) {
        _uiState.update { it.copy(selectedUser = user) }
    }

    fun selectVerification(verification: VerificationRecord?) {
        _uiState.update { it.copy(selectedVerification = verification) }
    }

    fun selectAuditLog(log: AuditLogRecord?) {
        _uiState.update { it.copy(selectedAuditLog = log) }
    }

    fun selectLog(log: AuditLogRecord?) {
        selectAuditLog(log)
    }

    fun performSchoolAction(schoolId: String, newStatus: String) {
        _uiState.update { state ->
            val updated = state.schools.map { if (it.id == schoolId) it.copy(status = newStatus) else it }
            state.copy(
                schools = updated,
                selectedSchool = null,
                feedbackMessage = "School status updated to '$newStatus'"
            )
        }
    }

    fun performVerificationAction(verificationId: String, newStatus: String) {
        _uiState.update { state ->
            val updated = state.verifications.map { if (it.id == verificationId) it.copy(status = newStatus) else it }
            state.copy(
                verifications = updated,
                selectedVerification = null,
                feedbackMessage = "School application verification status updated to '$newStatus'"
            )
        }
    }

    fun clearFeedback() {
        _uiState.update { it.copy(feedbackMessage = null) }
    }
}
