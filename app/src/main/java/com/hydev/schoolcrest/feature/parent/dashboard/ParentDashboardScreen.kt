package com.hydev.schoolcrest.feature.parent.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Announcement
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hydev.schoolcrest.core.model.UserRole
import com.hydev.schoolcrest.core.ui.components.DashboardAdaptiveColumns
import com.hydev.schoolcrest.core.ui.components.DashboardDataNotice
import com.hydev.schoolcrest.core.ui.components.DashboardEmptyCard
import com.hydev.schoolcrest.core.ui.components.DashboardHeroActionButton
import com.hydev.schoolcrest.core.ui.components.DashboardHeroCard
import com.hydev.schoolcrest.core.ui.components.DashboardHeroStatGrid
import com.hydev.schoolcrest.core.ui.components.DashboardHeroSupportingTextColor
import com.hydev.schoolcrest.core.ui.components.DashboardLink
import com.hydev.schoolcrest.core.ui.components.DashboardPage
import com.hydev.schoolcrest.core.ui.components.DashboardPageHeader
import com.hydev.schoolcrest.core.ui.components.DashboardQuickActionGrid
import com.hydev.schoolcrest.core.ui.components.DashboardSection
import com.hydev.schoolcrest.core.ui.components.RoleDashboardShell
import com.hydev.schoolcrest.core.ui.components.DashboardHeroArtwork
import com.hydev.schoolcrest.core.ui.components.DashboardReferenceActionButton
import com.hydev.schoolcrest.core.ui.components.DashboardReferenceGreeting
import com.hydev.schoolcrest.core.ui.components.DashboardReferenceHeroCard
import com.hydev.schoolcrest.core.ui.components.DashboardReferenceChildHeading
import com.hydev.schoolcrest.core.ui.components.DashboardReferenceInfoCard
import com.hydev.schoolcrest.core.ui.components.DashboardReferenceMetricStrip
import com.hydev.schoolcrest.core.ui.components.DashboardReferenceSection

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
    val title = when (route) {
        "p_dashboard" -> "Family dashboard"
        "p_children" -> "Linked children"
        "p_attendance" -> "Attendance"
        "p_results" -> "Published results"
        "p_timetable" -> "Timetable"
        else -> "School announcements"
    }
    val isOverview = route == "p_dashboard"
    val familyLinks = listOf(
        DashboardLink(
            title = "Children",
            supportingText = "View linked learner profiles",
            icon = Icons.Default.ChildCare,
            onClick = { onNavigate("p_children") }
        ),
        DashboardLink(
            title = "Attendance",
            supportingText = "View available attendance records",
            icon = Icons.Default.EventAvailable,
            onClick = { onNavigate("p_attendance") }
        ),
        DashboardLink(
            title = "Published results",
            supportingText = "View results released by the school",
            icon = Icons.Default.Grade,
            onClick = { onNavigate("p_results") }
        ),
        DashboardLink(
            title = "Timetable",
            supportingText = "View school schedule information",
            icon = Icons.Default.CalendarToday,
            onClick = { onNavigate("p_timetable") }
        ),
        DashboardLink(
            title = "Announcements",
            supportingText = "Read school notices",
            icon = Icons.AutoMirrored.Filled.Announcement,
            onClick = { onNavigate("p_announcements") }
        )
    )

    DashboardPage {
        if (isOverview) {
            DashboardReferenceGreeting(
                title = "Welcome, Parent 👋",
                supportingText = "Here's your child's progress."
            )
            DashboardReferenceHeroCard(artwork = DashboardHeroArtwork.FAMILY) {
                DashboardReferenceChildHeading("Aisha Bello", "Primary 5", Icons.Default.ChildCare)
                DashboardReferenceMetricStrip(
                    metrics = listOf(
                        "Attendance" to "92%",
                        "Latest result" to "85%",
                        "Academic status" to "On Track"
                    )
                )
                DashboardReferenceActionButton(
                    label = "View child",
                    onClick = { onNavigate("p_children") }
                )
            }
            DashboardReferenceSection(
                title = "Recent Updates",
                actionText = "View all",
                onAction = { onNavigate("p_announcements") }
            ) {
                DashboardReferenceInfoCard(
                    title = "Mathematics result published",
                    description = "Sat, 8 Nov • 5:20 PM",
                    trailingText = "›",
                    leadingIcon = Icons.Default.Grade
                )
            }
        } else {
            DashboardPageHeader(
                eyebrow = "FAMILY SCHOOL LIFE",
                title = title,
                supportingText = "Information is limited to linked children and published school updates."
            )
            DashboardAdaptiveColumns(
                primary = {
                    DashboardSection(title = "Linked learners") {
                        DashboardDataNotice(
                            title = "No learner records are available",
                            description = "Linked child profiles and their school updates will appear here when available.",
                            icon = Icons.Default.ChildCare
                        )
                    }
                    DashboardSection(title = "Published learning updates") {
                        DashboardDataNotice(
                            title = "No published results are available",
                            description = "Only results published by the school are shown here.",
                            icon = Icons.Default.Grade
                        )
                    }
                },
                secondary = {
                    DashboardSection(title = "Family information") {
                        DashboardQuickActionGrid(links = familyLinks)
                    }
                }
            )
        }
    }
}
