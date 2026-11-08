package com.hydev.schoolcrest.feature.teacher.dashboard

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.hydev.schoolcrest.core.model.UserRole
import com.hydev.schoolcrest.core.ui.components.DashboardActionList
import com.hydev.schoolcrest.core.ui.components.DashboardAdaptiveColumns
import com.hydev.schoolcrest.core.ui.components.DashboardDataNotice
import com.hydev.schoolcrest.core.ui.components.DashboardLink
import com.hydev.schoolcrest.core.ui.components.DashboardPage
import com.hydev.schoolcrest.core.ui.components.DashboardPageHeader
import com.hydev.schoolcrest.core.ui.components.DashboardSection
import com.hydev.schoolcrest.core.ui.components.DashboardHeroSupportingTextColor
import com.hydev.schoolcrest.core.ui.components.RoleDashboardShell
import com.hydev.schoolcrest.core.ui.components.DashboardHeroArtwork
import com.hydev.schoolcrest.core.ui.components.DashboardReferenceActionButton
import com.hydev.schoolcrest.core.ui.components.DashboardReferenceClassOverviewCard
import com.hydev.schoolcrest.core.ui.components.DashboardReferenceGreeting
import com.hydev.schoolcrest.core.ui.components.DashboardReferenceHeroCard
import com.hydev.schoolcrest.core.ui.components.DashboardReferenceHeroPill
import com.hydev.schoolcrest.core.ui.components.DashboardReferenceInfoCard
import com.hydev.schoolcrest.core.ui.components.DashboardReferenceMetricStrip
import com.hydev.schoolcrest.core.ui.components.DashboardReferenceTeacherDayMetrics
import com.hydev.schoolcrest.core.ui.components.DashboardReferenceSection
import com.hydev.schoolcrest.core.ui.components.DashboardReferenceSummaryCards
import com.hydev.schoolcrest.core.ui.components.DashboardMetric

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
    val title = when (route) {
        "t_dashboard" -> "Teaching workspace"
        "t_my_classes" -> "Your classes"
        "t_attendance" -> "Attendance"
        "t_assignments" -> "Assignments"
        else -> "Academic results"
    }
    val isOverview = route == "t_dashboard"
    val teachingTools = listOf(
        DashboardLink(
            title = "Classes",
            supportingText = "Open assigned class workspace",
            icon = Icons.Default.School,
            onClick = { onNavigate("t_my_classes") }
        ),
        DashboardLink(
            title = "Attendance",
            supportingText = "Open attendance tools",
            icon = Icons.Default.EventAvailable,
            onClick = { onNavigate("t_attendance") }
        ),
        DashboardLink(
            title = "Assignments",
            supportingText = "Open assignment workspace",
            icon = Icons.AutoMirrored.Filled.Assignment,
            onClick = { onNavigate("t_assignments") }
        ),
        DashboardLink(
            title = "Academic results",
            supportingText = "Open results workspace",
            icon = Icons.Default.Grade,
            onClick = { onNavigate("t_academics") }
        )
    )

    DashboardPage {
        if (isOverview) {
            DashboardReferenceGreeting(
                title = "Hello, Teacher 👋",
                supportingText = "Stay on track with your teaching responsibilities."
            )
            DashboardReferenceHeroCard(artwork = DashboardHeroArtwork.BOOKS) {
                DashboardReferenceHeroPill("Today, Mon, Nov 10", Icons.Default.EventAvailable)
                Text(
                    text = "Your Teaching Day",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                DashboardReferenceTeacherDayMetrics()
                DashboardReferenceActionButton(
                    label = "View timetable",
                    onClick = { onNavigate("t_timetable") }
                )
            }
            DashboardReferenceSummaryCards(
                metrics = listOf(
                    DashboardMetric(
                        title = "Assigned classes",
                        value = "3",
                        supportingText = "",
                        icon = Icons.Default.School
                    ),
                    DashboardMetric(
                        title = "Subjects",
                        value = "2",
                        supportingText = "",
                        icon = Icons.Default.Grade
                    ),
                    DashboardMetric(
                        title = "Attendance",
                        value = "Today",
                        supportingText = "Today",
                        icon = Icons.Default.EventAvailable
                    )
                )
            )
            DashboardReferenceSection(
                title = "Class overview",
                actionText = "View all",
                onAction = { onNavigate("t_my_classes") }
            ) {
                DashboardReferenceClassOverviewCard()
            }
            DashboardReferenceSection(
                title = "Upcoming activities",
                actionText = "See all",
                onAction = { onNavigate("t_timetable") }
            ) {
                DashboardReferenceInfoCard(
                    title = "Enter class results",
                    description = "JSS 1A – Mathematics",
                    trailingText = "Today, 2:00 PM",
                    leadingIcon = Icons.Default.EventAvailable
                )
            }
        } else {
            DashboardPageHeader(
                eyebrow = "TEACHING WORKSPACE",
                title = title,
                supportingText = "Teaching information for your assigned classes."
            )
            DashboardAdaptiveColumns(
                primary = {
                    DashboardSection(title = "Workspace status") {
                        DashboardDataNotice(
                            title = "Class information is not available",
                            description = "Assigned classes, timetable, attendance, and academic records are not currently available from the app data source.",
                            icon = Icons.Default.School
                        )
                    }
                },
                secondary = {
                    DashboardSection(title = "Teaching tools") {
                        DashboardActionList(links = teachingTools)
                    }
                }
            )
        }
    }
}
