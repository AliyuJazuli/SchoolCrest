package com.hydev.schoolcrest.core.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Announcement
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.Domain
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun PlatformOwnerDashboardCard(
    schoolCount: Int,
    activeSchoolCount: Int,
    userCount: Int,
    pendingReviewCount: Int,
    onReview: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        color = colors.primaryContainer,
        contentColor = colors.onPrimaryContainer,
        border = BorderStroke(1.dp, colors.outlineVariant.copy(alpha = 0.45f))
    ) {
        Column(
            modifier = Modifier.padding(22.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    DashboardCardEyebrow("PLATFORM CONTROL", colors.primary)
                    Text(
                        text = "School network",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Access, accounts, and school approvals.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.onPrimaryContainer
                    )
                }
                Box(
                    modifier = Modifier.size(72.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val progress = if (schoolCount == 0) 0f else {
                        (activeSchoolCount.toFloat() / schoolCount).coerceIn(0f, 1f)
                    }
                    Canvas(Modifier.fillMaxSize()) {
                        drawArc(
                            color = colors.primary.copy(alpha = 0.18f),
                            startAngle = -90f,
                            sweepAngle = 360f,
                            useCenter = false,
                            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 7.dp.toPx())
                        )
                        drawArc(
                            color = colors.primary,
                            startAngle = -90f,
                            sweepAngle = 360f * progress,
                            useCenter = false,
                            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 7.dp.toPx())
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = activeSchoolCount.toString(),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text("active", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
            HorizontalDivider(color = colors.onPrimaryContainer.copy(alpha = 0.16f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                DashboardCompactStat(
                    value = schoolCount.toString(),
                    label = "SCHOOLS",
                    modifier = Modifier.weight(1f)
                )
                DashboardCompactStat(
                    value = userCount.toString(),
                    label = "ACCOUNTS",
                    modifier = Modifier.weight(1f)
                )
                Column(
                    modifier = Modifier.weight(1.2f),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = if (pendingReviewCount == 0) "All reviewed" else "$pendingReviewCount to review",
                        style = MaterialTheme.typography.labelLarge,
                        color = colors.onPrimaryContainer,
                        fontWeight = FontWeight.SemiBold
                    )
                    Button(
                        onClick = onReview,
                        modifier = Modifier.padding(top = 5.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colors.primary,
                            contentColor = colors.onPrimary
                        )
                    ) {
                        Text("Review", fontWeight = FontWeight.Bold)
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 6.dp)
                                .size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SchoolAdminDashboardCard(
    studentCount: Int,
    teacherCount: Int,
    authorizedTeacherCount: Int,
    classCount: Int,
    pendingTeacherCount: Int,
    onReviewStaff: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        color = colors.secondaryContainer,
        contentColor = colors.onSecondaryContainer,
        border = BorderStroke(1.dp, colors.outlineVariant.copy(alpha = 0.45f))
    ) {
        Column(
            modifier = Modifier.padding(22.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    DashboardCardEyebrow("SCHOOL OVERVIEW", colors.secondary)
                    Text(
                        text = "Your school, at a glance",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = null,
                    tint = colors.secondary,
                    modifier = Modifier.size(30.dp)
                )
            }
            HorizontalDivider(color = colors.onSecondaryContainer.copy(alpha = 0.16f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = studentCount.toString(),
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                        color = colors.secondary
                    )
                    Text("STUDENTS", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    DashboardCompactStat(
                        value = "$authorizedTeacherCount / $teacherCount",
                        label = "TEACHERS AUTHORIZED"
                    )
                    DashboardCompactStat(
                        value = classCount.toString(),
                        label = "CLASS GROUPS"
                    )
                }
            }
            HorizontalDivider(color = colors.onSecondaryContainer.copy(alpha = 0.16f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (pendingTeacherCount == 0) {
                        "Staff access is up to date"
                    } else {
                        "$pendingTeacherCount staff ${if (pendingTeacherCount == 1) "request" else "requests"} need review"
                    },
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
                DashboardTextAction(
                    label = "Review",
                    onClick = onReviewStaff,
                    color = colors.secondary
                )
            }
        }
    }
}

@Composable
fun TeacherDashboardCard(
    onOpenClasses: () -> Unit,
    onOpenAttendance: () -> Unit,
    onOpenAssignments: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        color = colors.primaryContainer,
        contentColor = colors.onPrimaryContainer,
        border = BorderStroke(1.dp, colors.outlineVariant.copy(alpha = 0.45f))
    ) {
        Column(
            modifier = Modifier.padding(22.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = null,
                    tint = colors.primary,
                    modifier = Modifier.size(30.dp)
                )
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    DashboardCardEyebrow("TEACHING SPACE", colors.primary)
                    Text(
                        text = "Ready for your classes",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DashboardQuickAction(
                    label = "Classes",
                    icon = Icons.Default.Groups,
                    color = colors.primary,
                    onClick = onOpenClasses,
                    modifier = Modifier.weight(1f)
                )
                DashboardQuickAction(
                    label = "Attendance",
                    icon = Icons.Default.EventAvailable,
                    color = colors.secondary,
                    onClick = onOpenAttendance,
                    modifier = Modifier.weight(1f)
                )
                DashboardQuickAction(
                    label = "Assignments",
                    icon = Icons.AutoMirrored.Filled.Assignment,
                    color = colors.tertiary,
                    onClick = onOpenAssignments,
                    modifier = Modifier.weight(1f)
                )
            }
            Text(
                text = "Open a workspace to see its current class information.",
                style = MaterialTheme.typography.bodySmall,
                color = colors.onPrimaryContainer
            )
        }
    }
}

@Composable
fun ParentDashboardCard(
    onOpenChildren: () -> Unit,
    onOpenAttendance: () -> Unit,
    onOpenResults: () -> Unit,
    onOpenAnnouncements: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        color = colors.tertiaryContainer,
        contentColor = colors.onTertiaryContainer,
        border = BorderStroke(1.dp, colors.outlineVariant.copy(alpha = 0.45f))
    ) {
        Column(
            modifier = Modifier.padding(22.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    DashboardCardEyebrow("FAMILY CONNECTION", colors.tertiary)
                    Text(
                        text = "Your child’s school life",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
                Icon(
                    imageVector = Icons.Default.ChildCare,
                    contentDescription = null,
                    tint = colors.tertiary,
                    modifier = Modifier.size(32.dp)
                )
            }
            HorizontalDivider(color = colors.onTertiaryContainer.copy(alpha = 0.16f))
            Text(
                text = "Connect a learner to see attendance, results, and school updates together.",
                style = MaterialTheme.typography.bodyMedium,
                color = colors.onTertiaryContainer
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DashboardQuickAction(
                    label = "Attendance",
                    icon = Icons.Default.EventAvailable,
                    color = colors.primary,
                    onClick = onOpenAttendance,
                    modifier = Modifier.weight(1f)
                )
                DashboardQuickAction(
                    label = "Results",
                    icon = Icons.Default.Assessment,
                    color = colors.secondary,
                    onClick = onOpenResults,
                    modifier = Modifier.weight(1f)
                )
                DashboardQuickAction(
                    label = "Updates",
                    icon = Icons.AutoMirrored.Filled.Announcement,
                    color = colors.tertiary,
                    onClick = onOpenAnnouncements,
                    modifier = Modifier.weight(1f)
                )
            }
            DashboardTextAction(
                label = "View children",
                onClick = onOpenChildren,
                color = colors.tertiary
            )
        }
    }
}

@Composable
private fun DashboardQuickAction(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.72f),
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.52f)
        )
    ) {
        Column(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun DashboardCardEyebrow(text: String, color: Color) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelMedium,
        color = color,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun DashboardCompactStat(
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun DashboardTextAction(
    label: String,
    onClick: () -> Unit,
    color: Color
) {
    androidx.compose.material3.TextButton(onClick = onClick) {
        Text(label, color = color, fontWeight = FontWeight.Bold)
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = color,
            modifier = Modifier
                .padding(start = 6.dp)
                .size(18.dp)
        )
    }
}
