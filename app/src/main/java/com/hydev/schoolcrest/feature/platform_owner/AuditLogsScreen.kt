package com.hydev.schoolcrest.feature.platform_owner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hydev.schoolcrest.core.ui.components.AppCard
import com.hydev.schoolcrest.core.ui.components.AppAlertDialog
import com.hydev.schoolcrest.core.ui.components.AppStatusChip
import com.hydev.schoolcrest.core.ui.components.EmptyState
import com.hydev.schoolcrest.core.ui.components.StatusType

@Composable
fun AuditLogsScreen(
    state: PlatformOwnerUiState,
    onSearchChange: (String) -> Unit,
    onSelectLog: (AuditLogRecord?) -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredLogs = state.auditLogs.filter { log ->
        log.action.contains(state.auditSearchQuery, ignoreCase = true) ||
                log.actor.contains(state.auditSearchQuery, ignoreCase = true) ||
                log.schoolContext.contains(state.auditSearchQuery, ignoreCase = true)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 96.dp, bottom = 16.dp)
    ) {
        OutlinedTextField(
            value = state.auditSearchQuery,
            onValueChange = onSearchChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search audit logs by action, actor, or school...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Platform Audit Trail (${filteredLogs.size})",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (filteredLogs.isEmpty()) {
            EmptyState(
                title = "No Audit Logs Found",
                description = "No logs match your search query."
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(filteredLogs) { log ->
                    AppCard {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = log.action,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Actor: ${log.actor} (${log.role})",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    AppStatusChip(status = StatusType.IN_PROGRESS, customText = log.schoolContext)
                                    AppStatusChip(status = StatusType.PRESENT, customText = log.timestamp)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            OutlinedButton(
                                onClick = { onSelectLog(log) }
                            ) {
                                Text("View Details")
                            }
                        }
                    }
                }
            }
        }
    }

    if (state.selectedAuditLog != null) {
        val log = state.selectedAuditLog!!
        AppAlertDialog(
            onDismissRequest = { onSelectLog(null) },
            title = { Text(log.action, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Actor: ${log.actor}")
                    Text("Role: ${log.role}")
                    Text("School Context: ${log.schoolContext}")
                    Text("Timestamp: ${log.timestamp}")
                }
            },
            confirmButton = {
                TextButton(onClick = { onSelectLog(null) }) {
                    Text("Close")
                }
            },
            shape = RoundedCornerShape(20.dp)
        )
    }
}
