package com.hydev.schoolcrest.feature.platform_owner

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Domain
import androidx.compose.material.icons.filled.History
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
import com.hydev.schoolcrest.core.ui.components.PlatformOwnerDashboardCard

@Composable
fun PlatformOwnerOverviewScreen(
    state: PlatformOwnerUiState,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val activeSchools = state.schools.count { it.status.equals("Active", ignoreCase = true) }
    val pendingVerifications = state.verifications.filter {
        it.status.equals("Pending", ignoreCase = true)
    }

    DashboardPage(modifier = modifier) {
        PlatformOwnerDashboardCard(
            schoolCount = state.schools.size,
            activeSchoolCount = activeSchools,
            userCount = state.users.size,
            pendingReviewCount = pendingVerifications.size,
            onReview = { onNavigate("po_verification") }
        )

        DashboardSection(
            title = "Applications to review",
            actionText = "View all",
            onAction = { onNavigate("po_verification") }
        ) {
            if (pendingVerifications.isEmpty()) {
                DashboardEmptyCard(
                    title = "You're up to date",
                    description = "There are no school applications awaiting review."
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
                        pendingVerifications.take(3).forEachIndexed { index, verification ->
                            DashboardListItem(
                                title = verification.schoolName,
                                supportingText = verification.location,
                                trailingText = "Review",
                                onClick = { onNavigate("po_verification") }
                            )
                            if (index < minOf(pendingVerifications.size, 3) - 1) {
                                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                            }
                        }
                    }
                }
            }
        }

        DashboardSection(title = "Platform tools") {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                DashboardActionCard(
                    title = "Manage schools",
                    description = "Review status and school records",
                    icon = Icons.Default.Domain,
                    onClick = { onNavigate("po_schools") }
                )
                DashboardActionCard(
                    title = "Review platform activity",
                    description = "Inspect recent audit entries",
                    icon = Icons.Default.History,
                    onClick = { onNavigate("po_audit_logs") }
                )
            }
        }

        DashboardSection(
            title = "Recent activity",
            actionText = "Audit log",
            onAction = { onNavigate("po_audit_logs") }
        ) {
            if (state.auditLogs.isEmpty()) {
                DashboardEmptyCard(
                    title = "No activity recorded",
                    description = "Platform events will appear here when available."
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
                        state.auditLogs.take(4).forEachIndexed { index, log ->
                            DashboardListItem(
                                title = log.action,
                                supportingText = "${log.schoolContext} · ${log.actor}",
                                trailingText = log.timestamp,
                                onClick = { onNavigate("po_audit_logs") }
                            )
                            if (index < minOf(state.auditLogs.size, 4) - 1) {
                                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                            }
                        }
                    }
                }
            }
        }
    }
}
