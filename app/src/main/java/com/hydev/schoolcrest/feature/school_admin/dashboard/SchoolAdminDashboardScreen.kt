package com.hydev.schoolcrest.feature.school_admin.dashboard

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hydev.schoolcrest.core.model.UserRole
import com.hydev.schoolcrest.core.ui.components.EmptyState
import com.hydev.schoolcrest.core.ui.components.AppAlertDialog
import com.hydev.schoolcrest.core.ui.components.RoleDashboardShell
import com.hydev.schoolcrest.feature.school_admin.ClassManagementScreen
import com.hydev.schoolcrest.feature.school_admin.SchoolAdminOverviewScreen
import com.hydev.schoolcrest.feature.school_admin.SchoolAdminViewModel
import com.hydev.schoolcrest.feature.school_admin.StudentManagementScreen
import com.hydev.schoolcrest.feature.school_admin.TeacherManagementScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchoolAdminDashboardScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: SchoolAdminViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    if (state.feedbackMessage != null) {
        AppAlertDialog(
            onDismissRequest = { viewModel.clearFeedback() },
            icon = { Icon(Icons.Default.Info, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
            title = { Text("School Admin Notice", fontWeight = FontWeight.Bold) },
            text = { Text(state.feedbackMessage ?: "") },
            confirmButton = {
                TextButton(onClick = { viewModel.clearFeedback() }) {
                    Text("OK", fontWeight = FontWeight.Bold)
                }
            },
            shape = RoundedCornerShape(20.dp)
        )
    }

    RoleDashboardShell(
        role = UserRole.SCHOOL_ADMIN,
        navController = navController,
        modifier = modifier
    ) { selectedRoute, _, onNavigate ->
        when (selectedRoute) {
            "sa_dashboard" -> SchoolAdminOverviewScreen(
                state = state,
                onNavigate = onNavigate,
                onEnrollStudent = {
                    viewModel.setShowEnrollDialog(true)
                    onNavigate("sa_students")
                },
                onCreateClass = {
                    viewModel.setShowCreateClassDialog(true)
                    onNavigate("sa_classes")
                }
            )
            "sa_teachers" -> TeacherManagementScreen(
                state = state,
                onSearchChange = { viewModel.updateTeacherSearch(it) },
                onToggleFilter = { viewModel.toggleTeacherFilter() },
                onAuthorize = { id, auth -> viewModel.authorizeTeacher(id, auth) }
            )
            "sa_students" -> StudentManagementScreen(
                state = state,
                onSearchChange = { viewModel.updateStudentSearch(it) },
                onEnrollStudent = { name, cls, pName, pPhone -> viewModel.enrollStudent(name, cls, pName, pPhone) },
                onShowEnrollDialog = { viewModel.setShowEnrollDialog(it) }
            )
            "sa_classes" -> ClassManagementScreen(
                state = state,
                onCreateClass = { name, teacher, subs -> viewModel.createClass(name, teacher, subs) },
                onShowCreateDialog = { viewModel.setShowCreateClassDialog(it) }
            )
            else -> {
                EmptyState(
                    title = selectedRoute,
                    description = "The module will be connected during upcoming feature integration."
                )
            }
        }
    }
}
