package com.hydev.schoolcrest.feature.school_admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Class
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hydev.schoolcrest.core.ui.components.DashboardActionCard
import com.hydev.schoolcrest.core.ui.components.DashboardEmptyCard
import com.hydev.schoolcrest.core.ui.components.DashboardListItem
import com.hydev.schoolcrest.core.ui.components.DashboardPage
import com.hydev.schoolcrest.core.ui.components.DashboardSection
import com.hydev.schoolcrest.core.ui.components.SchoolAdminDashboardCard

@Composable
fun SchoolAdminOverviewScreen(
    state: SchoolAdminUiState,
    onNavigate: (String) -> Unit,
    onEnrollStudent: () -> Unit,
    onCreateClass: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pendingTeachers = state.teachers.filterNot { it.isAuthorized }

    DashboardPage(modifier = modifier) {
        SchoolAdminDashboardCard(
            studentCount = state.students.size,
            teacherCount = state.teachers.size,
            authorizedTeacherCount = state.teachers.count { it.isAuthorized },
            classCount = state.classes.size,
            pendingTeacherCount = pendingTeachers.size,
            onReviewStaff = { onNavigate("sa_teachers") }
        )

        DashboardSection(
            title = "Needs your attention",
            actionText = "Review teachers",
            onAction = { onNavigate("sa_teachers") }
        ) {
            if (pendingTeachers.isEmpty()) {
                DashboardEmptyCard(
                    title = "No pending staff reviews",
                    description = "Teacher access requests that need your attention will appear here."
                )
            } else {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.72f)
                    )
                ) {
                    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                        pendingTeachers.take(3).forEachIndexed { index, teacher ->
                            DashboardListItem(
                                title = teacher.fullName,
                                supportingText = teacher.email,
                                trailingText = "Review",
                                onClick = { onNavigate("sa_teachers") }
                            )
                            if (index < minOf(pendingTeachers.size, 3) - 1) {
                                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                            }
                        }
                    }
                }
            }
        }

        DashboardSection(title = "Quick actions") {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                DashboardActionCard(
                    title = "Enroll a student",
                    description = "Add a learner to the student roster",
                    icon = Icons.Default.PersonAdd,
                    onClick = onEnrollStudent,
                    accentColor = MaterialTheme.colorScheme.primary
                )
                DashboardActionCard(
                    title = "Set up a class",
                    description = "Create a class group and assign subjects",
                    icon = Icons.Default.Class,
                    onClick = onCreateClass,
                    accentColor = MaterialTheme.colorScheme.secondary
                )
            }
        }

        DashboardSection(
            title = "Class groups",
            actionText = "View classes",
            onAction = { onNavigate("sa_classes") }
        ) {
            if (state.classes.isEmpty()) {
                DashboardEmptyCard(
                    title = "No classes yet",
                    description = "Create a class group to organize teachers and students."
                )
            } else {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.72f)
                    )
                ) {
                    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                        state.classes.take(4).forEachIndexed { index, classroom ->
                            DashboardListItem(
                                title = classroom.className,
                                supportingText = "${classroom.classTeacher} · ${classroom.studentCount} students",
                                trailingText = "${classroom.subjects.size} subjects",
                                onClick = { onNavigate("sa_classes") }
                            )
                            if (index < minOf(state.classes.size, 4) - 1) {
                                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                            }
                        }
                    }
                }
            }
        }
    }
}
