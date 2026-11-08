package com.hydev.schoolcrest.feature.school_admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hydev.schoolcrest.core.ui.components.AppCard
import com.hydev.schoolcrest.core.ui.components.AppStatusChip
import com.hydev.schoolcrest.core.ui.components.EmptyState
import com.hydev.schoolcrest.core.ui.components.StatusType

@Composable
fun TeacherManagementScreen(
    state: SchoolAdminUiState,
    onSearchChange: (String) -> Unit,
    onToggleFilter: () -> Unit,
    onAuthorize: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredTeachers = state.teachers.filter { teacher ->
        val matchesSearch = teacher.fullName.contains(state.teacherSearchQuery, ignoreCase = true) ||
                teacher.email.contains(state.teacherSearchQuery, ignoreCase = true)
        val matchesFilter = if (state.teacherFilterAuthorizedOnly) teacher.isAuthorized else true
        matchesSearch && matchesFilter
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 96.dp, bottom = 16.dp)
    ) {
        // Search & Filter
        OutlinedTextField(
            value = state.teacherSearchQuery,
            onValueChange = onSearchChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search teachers by name or email...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Registered Teachers (${filteredTeachers.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            FilterChip(
                selected = state.teacherFilterAuthorizedOnly,
                onClick = onToggleFilter,
                label = { Text("Authorized Only") },
                leadingIcon = { Icon(Icons.Default.FilterList, contentDescription = null) }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (filteredTeachers.isEmpty()) {
            EmptyState(
                title = "No Teachers Found",
                description = "No teachers match your search or filter criteria."
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(filteredTeachers) { teacher ->
                    AppCard {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = teacher.fullName,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = teacher.email,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    if (teacher.isAuthorized) {
                                        AppStatusChip(status = StatusType.PRESENT, customText = "Authorized")
                                    } else {
                                        AppStatusChip(status = StatusType.LATE, customText = "Pending Review")
                                    }
                                    teacher.assignedClass?.let {
                                        AppStatusChip(status = StatusType.IN_PROGRESS, customText = "Class: $it")
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Subjects: ${teacher.assignedSubjects.joinToString(", ")}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            if (teacher.isAuthorized) {
                                OutlinedButton(
                                    onClick = { onAuthorize(teacher.id, false) }
                                ) {
                                    Text("Revoke Authorization", color = MaterialTheme.colorScheme.error)
                                }
                            } else {
                                Button(
                                    onClick = { onAuthorize(teacher.id, true) },
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                ) {
                                    Icon(Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Authorize Teacher")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
