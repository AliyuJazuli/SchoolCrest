package com.hydev.schoolcrest.feature.platform_owner.dashboard

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
import com.hydev.schoolcrest.feature.platform_owner.AnalyticsReportsScreen
import com.hydev.schoolcrest.feature.platform_owner.AuditLogsScreen
import com.hydev.schoolcrest.feature.platform_owner.PlatformOwnerOverviewScreen
import com.hydev.schoolcrest.feature.platform_owner.PlatformOwnerViewModel
import com.hydev.schoolcrest.feature.platform_owner.PlatformSettingsScreen
import com.hydev.schoolcrest.feature.platform_owner.SchoolVerificationScreen
import com.hydev.schoolcrest.feature.platform_owner.SchoolsManagementScreen
import com.hydev.schoolcrest.feature.platform_owner.SecurityScreen
import com.hydev.schoolcrest.feature.platform_owner.UserManagementScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlatformOwnerDashboardScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PlatformOwnerViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    if (state.feedbackMessage != null) {
        AppAlertDialog(
            onDismissRequest = { viewModel.clearFeedback() },
            icon = { Icon(Icons.Default.Info, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
            title = { Text("Platform Owner Notice", fontWeight = FontWeight.Bold) },
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
        role = UserRole.PLATFORM_OWNER,
        navController = navController,
        modifier = modifier
    ) { selectedRoute, _, onNavigate ->
        when (selectedRoute) {
            "po_overview" -> PlatformOwnerOverviewScreen(state = state, onNavigate = onNavigate)
            "po_schools" -> SchoolsManagementScreen(
                state = state,
                onSearchChange = { viewModel.updateSchoolSearch(it) },
                onStatusFilterChange = { viewModel.updateSchoolStatusFilter(it) },
                onSelectSchool = { viewModel.selectSchool(it) },
                onSchoolAction = { id, status -> viewModel.performSchoolAction(id, status) }
            )
            "po_users" -> UserManagementScreen(
                state = state,
                onSearchChange = { viewModel.updateUserSearch(it) },
                onRoleFilterChange = { viewModel.updateUserRoleFilter(it) },
                onSelectUser = { viewModel.selectUser(it) }
            )
            "po_verification" -> SchoolVerificationScreen(
                state = state,
                onSelectVerification = { viewModel.selectVerification(it) },
                onVerificationAction = { id, status -> viewModel.performVerificationAction(id, status) }
            )
            "po_security" -> SecurityScreen()
            "po_analytics" -> AnalyticsReportsScreen(state = state)
            "po_settings" -> PlatformSettingsScreen()
            "po_audit_logs" -> AuditLogsScreen(
                state = state,
                onSearchChange = { viewModel.updateAuditSearch(it) },
                onSelectLog = { viewModel.selectLog(it) }
            )
            else -> {
                EmptyState(
                    title = selectedRoute,
                    description = "The module is ready for interaction."
                )
            }
        }
    }
}
