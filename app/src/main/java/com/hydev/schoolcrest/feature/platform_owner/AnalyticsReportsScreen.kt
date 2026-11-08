package com.hydev.schoolcrest.feature.platform_owner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hydev.schoolcrest.core.ui.components.AppCard
import com.hydev.schoolcrest.core.ui.components.AppPrimaryButton
import com.hydev.schoolcrest.core.ui.components.AppStatusChip
import com.hydev.schoolcrest.core.ui.components.StatusType

@Composable
fun AnalyticsReportsScreen(
    state: PlatformOwnerUiState,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf("School Registrations") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AppCard(
            borderColor = MaterialTheme.colorScheme.primaryContainer
        ) {
            Text(
                text = "Analytics & Platform Reports",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Analyze multi-tenant activity, user distribution by role, and school growth statistics across Northern Nigeria.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        AppCard {
            Text(
                text = "User Distribution by Role",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("School Admins")
                AppStatusChip(status = StatusType.PRESENT, customText = "14 Schools (18%)")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Teachers")
                AppStatusChip(status = StatusType.IN_PROGRESS, customText = "340 Active (45%)")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Parents / Guardians")
                AppStatusChip(status = StatusType.LATE, customText = "2,850 Linked (37%)")
            }
        }

        AppCard {
            Text(
                text = "Export Summary Report",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Generate PDF or CSV report for platform auditing and stakeholder reviews.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))
            AppPrimaryButton(
                text = "Export Platform Report (PDF)",
                onClick = { /* UI preview action */ }
            )
        }
    }
}
