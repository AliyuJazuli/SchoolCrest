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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
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
fun SchoolsManagementScreen(
    state: PlatformOwnerUiState,
    onSearchChange: (String) -> Unit,
    onStatusFilterChange: (String) -> Unit,
    onSelectSchool: (SchoolRecord?) -> Unit,
    onSchoolAction: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val statuses = listOf("All", "Active", "Pending", "Suspended", "Rejected")

    val filteredSchools = state.schools.filter { school ->
        val matchesSearch = school.name.contains(state.schoolSearchQuery, ignoreCase = true) ||
                school.location.contains(state.schoolSearchQuery, ignoreCase = true)
        val matchesStatus = if (state.schoolStatusFilter == "All") true else school.status == state.schoolStatusFilter
        matchesSearch && matchesStatus
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 96.dp)
    ) {
        OutlinedTextField(
            value = state.schoolSearchQuery,
            onValueChange = onSearchChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search schools by name or location...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Horizontal scrolling filter chip row
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(statuses) { status ->
                FilterChip(
                    selected = state.schoolStatusFilter == status,
                    onClick = { onStatusFilterChange(status) },
                    label = { Text(status) }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Registered Schools (${filteredSchools.size})",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (filteredSchools.isEmpty()) {
            EmptyState(
                title = "No Schools Found",
                description = "No schools match your search or filter status."
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(filteredSchools) { school ->
                    AppCard {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = school.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = school.location,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    val chipStatus = when (school.status) {
                                        "Active" -> StatusType.PRESENT
                                        "Pending" -> StatusType.LATE
                                        else -> StatusType.ABSENT
                                    }
                                    AppStatusChip(status = chipStatus, customText = school.status)
                                    AppStatusChip(status = StatusType.IN_PROGRESS, customText = "${school.studentsCount} Students")
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Admin: ${school.adminName}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            OutlinedButton(
                                onClick = { onSelectSchool(school) }
                            ) {
                                Text("View Details")
                            }
                        }
                    }
                }
            }
        }
    }

    state.selectedSchool?.let { school ->
        AppAlertDialog(
            onDismissRequest = { onSelectSchool(null) },
            title = { Text(school.name, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Location: ${school.location}")
                    Text("Admin: ${school.adminName}")
                    Text("Enrolled Students: ${school.studentsCount}")
                    Text("Current Status: ${school.status}")
                }
            },
            confirmButton = {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (school.status != "Active") {
                        Button(
                            onClick = { onSchoolAction(school.id, "Active") },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text("Approve")
                        }
                    }
                    if (school.status != "Suspended") {
                        OutlinedButton(
                            onClick = { onSchoolAction(school.id, "Suspended") }
                        ) {
                            Text("Suspend", color = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = { onSelectSchool(null) }) {
                    Text("Close")
                }
            },
            shape = RoundedCornerShape(20.dp)
        )
    }
}
