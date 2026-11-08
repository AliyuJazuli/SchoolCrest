package com.hydev.schoolcrest.feature.school_admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Class
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hydev.schoolcrest.core.ui.components.DashboardAdaptiveColumns
import com.hydev.schoolcrest.core.ui.components.DashboardDataNotice
import com.hydev.schoolcrest.core.ui.components.DashboardEmptyCard
import com.hydev.schoolcrest.core.ui.components.DashboardHeroActionButton
import com.hydev.schoolcrest.core.ui.components.DashboardHeroCard
import com.hydev.schoolcrest.core.ui.components.DashboardHeroPrimaryMetric
import com.hydev.schoolcrest.core.ui.components.DashboardHeroStatGrid
import com.hydev.schoolcrest.core.ui.components.DashboardHeroSupportingTextColor
import com.hydev.schoolcrest.core.ui.components.DashboardLink
import com.hydev.schoolcrest.core.ui.components.DashboardListItem
import com.hydev.schoolcrest.core.ui.components.DashboardPage
import com.hydev.schoolcrest.core.ui.components.DashboardPageHeader
import com.hydev.schoolcrest.core.ui.components.DashboardQuickActionGrid
import com.hydev.schoolcrest.core.ui.components.DashboardRecordPanel
import com.hydev.schoolcrest.core.ui.components.DashboardSection
import com.hydev.schoolcrest.core.ui.components.DashboardHeroArtwork
import com.hydev.schoolcrest.core.ui.components.DashboardMetric
import com.hydev.schoolcrest.core.ui.components.DashboardReferenceActionButton
import com.hydev.schoolcrest.core.ui.components.DashboardReferenceAttendanceCard
import com.hydev.schoolcrest.core.ui.components.DashboardReferenceGreeting
import com.hydev.schoolcrest.core.ui.components.DashboardReferenceHeroCard
import com.hydev.schoolcrest.core.ui.components.DashboardReferenceHeroPill
import com.hydev.schoolcrest.core.ui.components.DashboardReferenceInfoCard
import com.hydev.schoolcrest.core.ui.components.DashboardReferenceMetricStrip
import com.hydev.schoolcrest.core.ui.components.DashboardReferenceQuickActions
import com.hydev.schoolcrest.core.ui.components.DashboardReferenceSection

@Composable
fun SchoolAdminOverviewScreen(
    state: SchoolAdminUiState,
    onNavigate: (String) -> Unit,
    onEnrollStudent: () -> Unit,
    onCreateClass: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pendingTeachers = state.teachers.filterNot { it.isAuthorized }
    val authorizedTeachers = state.teachers.count { it.isAuthorized }

    DashboardPage(modifier = modifier) {
        DashboardReferenceGreeting(
            title = "Good morning, Admin 👋",
            supportingText = "Here's what's happening in your school today."
        )

        DashboardReferenceHeroCard(artwork = DashboardHeroArtwork.CAMPUS) {
            DashboardReferenceHeroPill("Rimin Central School", Icons.Default.School)
            Text(
                text = "School at a glance",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            DashboardReferenceMetricStrip(
                metrics = listOf(
                    "Enrolled students" to "245",
                    "Teaching staff" to "12",
                    "Class groups" to "6",
                    "Pending request" to "1"
                ),
                prominentFirst = true,
                icons = listOf(
                    Icons.Default.School,
                    Icons.Default.Groups,
                    Icons.Default.Class,
                    Icons.Default.EventAvailable
                )
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                DashboardReferenceActionButton(
                    label = "Review staff requests",
                    onClick = { onNavigate("sa_teachers") },
                    icon = Icons.AutoMirrored.Filled.ArrowForward
                )
            }
        }

        DashboardReferenceAttendanceCard()

        DashboardReferenceSection(title = "Quick Actions") {
            DashboardReferenceQuickActions(
                links = listOf(
                    DashboardLink(
                        title = "Manage Students",
                        supportingText = "Manage student records",
                        icon = Icons.Default.Groups,
                        onClick = { onNavigate("sa_students") }
                    ),
                    DashboardLink(
                        title = "View Classes",
                        supportingText = "Open class groups",
                        icon = Icons.Default.Class,
                        onClick = { onNavigate("sa_classes") }
                    ),
                    DashboardLink(
                        title = "Manage Results",
                        supportingText = "Open academic records",
                        icon = Icons.Default.Grade,
                        onClick = { onNavigate("sa_academics") }
                    ),
                    DashboardLink(
                        title = "School Settings",
                        supportingText = "Manage school profile",
                        icon = Icons.Default.Settings,
                        onClick = { onNavigate("sa_profile") }
                    )
                )
            )
        }

        DashboardReferenceSection(
            title = "Recent Activity",
            actionText = "See all",
            onAction = { onNavigate("sa_audit_logs") }
        ) {
            DashboardReferenceInfoCard(
                title = "Staff request approved",
                description = "Mr. Ibrahim Musa – Mathematics Teacher",
                trailingText = "2h ago",
                leadingIcon = Icons.Default.EventAvailable
            )
        }
    }
}
