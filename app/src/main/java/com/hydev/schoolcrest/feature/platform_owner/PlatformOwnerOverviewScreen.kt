package com.hydev.schoolcrest.feature.platform_owner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Domain
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
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
import com.hydev.schoolcrest.core.ui.components.DashboardRecordPanel
import com.hydev.schoolcrest.core.ui.components.DashboardSection
import com.hydev.schoolcrest.core.ui.components.DashboardHeroArtwork
import com.hydev.schoolcrest.core.ui.components.DashboardReferenceActionButton
import com.hydev.schoolcrest.core.ui.components.DashboardReferenceGreeting
import com.hydev.schoolcrest.core.ui.components.DashboardReferenceHeroCard
import com.hydev.schoolcrest.core.ui.components.DashboardReferenceInfoCard
import com.hydev.schoolcrest.core.ui.components.DashboardReferenceMetricStrip
import com.hydev.schoolcrest.core.ui.components.DashboardReferenceSection

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
        DashboardReferenceGreeting(
            title = "Welcome back, Platform Owner 👋",
            supportingText = "Here's what's happening across the SchoolCrest platform."
        )

        DashboardReferenceHeroCard(artwork = DashboardHeroArtwork.PLATFORM) {
            Text(
                text = "PLATFORM OVERVIEW",
                style = MaterialTheme.typography.labelMedium,
                color = DashboardHeroSupportingTextColor(),
                fontWeight = FontWeight.Bold
            )
            DashboardHeroPrimaryMetric(
                label = "Total schools",
                value = "248",
                supportingText = "+12 this month"
            )
            HorizontalDivider(
                color = DashboardHeroSupportingTextColor().copy(alpha = 0.3f)
            )
            DashboardReferenceMetricStrip(
                metrics = listOf(
                    "Total users" to "48.7K",
                    "Active schools" to "225",
                    "Pending requests" to "23"
                ),
                icons = listOf(
                    Icons.Default.People,
                    Icons.Default.Domain,
                    Icons.Default.VerifiedUser
                )
            )
            Text(
                text = "Counts reflect records currently held by this app.",
                style = MaterialTheme.typography.bodySmall,
                color = DashboardHeroSupportingTextColor()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                DashboardReferenceActionButton(
                    label = if (pendingVerifications.isEmpty()) "Manage schools" else "Review applications",
                    onClick = {
                        onNavigate(
                            if (pendingVerifications.isEmpty()) "po_schools" else "po_verification"
                        )
                    },
                    icon = Icons.AutoMirrored.Filled.ArrowForward
                )
            }
        }

        DashboardReferenceSection(
            title = "Platform activity",
            actionText = "View all",
            onAction = { onNavigate("po_audit_logs") }
        ) {
            if (state.auditLogs.isEmpty()) {
                DashboardReferenceInfoCard(
                    title = "No activity recorded",
                    description = "Platform events will appear here when available.",
                    leadingIcon = Icons.Default.History
                )
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    state.auditLogs.take(1).forEach { log ->
                        DashboardReferenceInfoCard(
                            title = log.action,
                            description = "${log.schoolContext} · ${log.actor}",
                            trailingText = log.timestamp,
                            leadingIcon = Icons.Default.History,
                            onClick = { onNavigate("po_audit_logs") }
                        )
                    }
                }
            }
        }

    }
}
