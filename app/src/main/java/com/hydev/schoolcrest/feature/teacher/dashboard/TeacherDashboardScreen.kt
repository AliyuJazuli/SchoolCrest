package com.hydev.schoolcrest.feature.teacher.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hydev.schoolcrest.core.model.UserRole
import com.hydev.schoolcrest.core.ui.components.DashboardActionCard
import com.hydev.schoolcrest.core.ui.components.DashboardEmptyCard
import com.hydev.schoolcrest.core.ui.components.DashboardHero
import com.hydev.schoolcrest.core.ui.components.DashboardPage
import com.hydev.schoolcrest.core.ui.components.DashboardSection
import com.hydev.schoolcrest.core.ui.components.RoleDashboardShell
import com.hydev.schoolcrest.core.ui.components.TeacherDashboardCard

@Composable
fun TeacherDashboardScreen(navController: NavController, modifier: Modifier = Modifier) {
    RoleDashboardShell(
        role = UserRole.TEACHER,
        navController = navController,
        modifier = modifier
    ) { route, _, onNavigate ->
        TeacherContent(route, onNavigate)
    }
}

@Composable
private fun TeacherContent(route: String, onNavigate: (String) -> Unit) {
    val isOverview = route == "t_dashboard"
    val title = when (route) {
        "t_dashboard" -> "Your teaching hub"
        "t_my_classes" -> "Your classes"
        "t_attendance" -> "Attendance"
        "t_assignments" -> "Assignments to review"
        else -> "Academic results"
    }
    val description = when (route) {
        "t_dashboard" -> "Get to classes, attendance, and the work ahead."
        "t_my_classes" -> "Open your class list and keep classroom details close."
        "t_attendance" -> "Track attendance for your assigned classes."
        "t_assignments" -> "Keep student work and reviews organized."
        else -> "Follow learning progress and academic results."
    }

    DashboardPage {
        if (isOverview) {
            TeacherDashboardCard(
                onOpenClasses = { onNavigate("t_my_classes") },
                onOpenAttendance = { onNavigate("t_attendance") },
                onOpenAssignments = { onNavigate("t_assignments") }
            )
        } else {
            DashboardHero(
                eyebrow = "TEACHING WORKSPACE",
                title = title,
                description = description,
                icon = Icons.Default.School,
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                accentColor = MaterialTheme.colorScheme.secondary,
                accentContentColor = MaterialTheme.colorScheme.onSecondary
            )
        }

        if (isOverview) {
            DashboardSection(title = "Your day") {
                DashboardEmptyCard(
                    title = "Your schedule will appear here",
                    description = "Assigned classes and upcoming lessons will be shown here when available."
                )
            }
        } else {
            DashboardSection(title = "Workspace") {
                DashboardEmptyCard(
                    title = "Your $title will appear here",
                    description = "This workspace is ready for your teaching information."
                )
            }
            DashboardSection(title = "Continue teaching") {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    DashboardActionCard(
                        title = "Back to overview",
                        description = "Return to your teaching dashboard",
                        icon = Icons.Default.School,
                        onClick = { onNavigate("t_dashboard") }
                    )
                    DashboardActionCard(
                        title = "Open attendance",
                        description = "Go to class attendance",
                        icon = Icons.Default.EventAvailable,
                        onClick = { onNavigate("t_attendance") }
                    )
                }
            }
        }
    }
}
