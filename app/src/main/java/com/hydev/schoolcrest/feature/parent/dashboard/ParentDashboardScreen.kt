package com.hydev.schoolcrest.feature.parent.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Announcement
import androidx.compose.material.icons.filled.ChildCare
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
import com.hydev.schoolcrest.core.ui.components.ParentDashboardCard
import com.hydev.schoolcrest.core.ui.components.RoleDashboardShell

@Composable
fun ParentDashboardScreen(navController: NavController, modifier: Modifier = Modifier) {
    RoleDashboardShell(
        role = UserRole.PARENT,
        navController = navController,
        modifier = modifier
    ) { route, _, onNavigate ->
        ParentContent(route, onNavigate)
    }
}

@Composable
private fun ParentContent(route: String, onNavigate: (String) -> Unit) {
    val isOverview = route == "p_dashboard"
    val title = when (route) {
        "p_dashboard" -> "Your family dashboard"
        "p_children" -> "Your children"
        "p_attendance" -> "Attendance"
        "p_results" -> "Learning results"
        else -> "School announcements"
    }

    DashboardPage {
        if (isOverview) {
            ParentDashboardCard(
                onOpenChildren = { onNavigate("p_children") },
                onOpenAttendance = { onNavigate("p_attendance") },
                onOpenResults = { onNavigate("p_results") },
                onOpenAnnouncements = { onNavigate("p_announcements") }
            )
        } else {
            DashboardHero(
                eyebrow = "FAMILY SCHOOL LIFE",
                title = title,
                description = "Keep important school information easy to find.",
                icon = Icons.Default.ChildCare,
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                accentColor = MaterialTheme.colorScheme.tertiary,
                accentContentColor = MaterialTheme.colorScheme.onTertiary
            )
        }

        if (isOverview) {
            DashboardSection(title = "Your learners") {
                DashboardEmptyCard(
                    title = "Learner updates will appear here",
                    description = "Linked learner profiles and their latest school updates will be shown in this space."
                )
            }
        } else {
            DashboardSection(title = "Updates") {
                DashboardEmptyCard(
                    title = "Your $title will appear here",
                    description = "School information for this section will be shown here when available."
                )
            }
            DashboardSection(title = "Family shortcuts") {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    DashboardActionCard(
                        title = "Back to dashboard",
                        description = "Return to your family overview",
                        icon = Icons.Default.School,
                        onClick = { onNavigate("p_dashboard") }
                    )
                    DashboardActionCard(
                        title = "School announcements",
                        description = "Read updates from school",
                        icon = Icons.AutoMirrored.Filled.Announcement,
                        onClick = { onNavigate("p_announcements") }
                    )
                }
            }
        }
    }
}
