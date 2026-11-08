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
fun UserManagementScreen(
    state: PlatformOwnerUiState,
    onSearchChange: (String) -> Unit,
    onRoleFilterChange: (String) -> Unit,
    onSelectUser: (UserRecord?) -> Unit,
    modifier: Modifier = Modifier
) {
    val roles = listOf("All", "School Admin", "Teacher", "Parent")

    val filteredUsers = state.users.filter { user ->
        val matchesSearch = user.name.contains(state.userSearchQuery, ignoreCase = true) ||
                user.email.contains(state.userSearchQuery, ignoreCase = true) ||
                user.schoolName.contains(state.userSearchQuery, ignoreCase = true)
        val matchesRole = if (state.userRoleFilter == "All") true else user.role == state.userRoleFilter
        matchesSearch && matchesRole
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        OutlinedTextField(
            value = state.userSearchQuery,
            onValueChange = onSearchChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search users by name, email, or school...") },
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
            items(roles) { role ->
                FilterChip(
                    selected = state.userRoleFilter == role,
                    onClick = { onRoleFilterChange(role) },
                    label = { Text(role) }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Registered Users (${filteredUsers.size})",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (filteredUsers.isEmpty()) {
            EmptyState(
                title = "No Users Found",
                description = "No user accounts match your search or role filter."
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(filteredUsers) { user ->
                    AppCard {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = user.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = user.email,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    AppStatusChip(status = StatusType.IN_PROGRESS, customText = user.role)
                                    AppStatusChip(status = StatusType.PRESENT, customText = user.status)
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "School: ${user.schoolName}",
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
                                onClick = { onSelectUser(user) }
                            ) {
                                Text("View Details")
                            }
                        }
                    }
                }
            }
        }
    }

    state.selectedUser?.let { user ->
        AppAlertDialog(
            onDismissRequest = { onSelectUser(null) },
            title = { Text(user.name, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Email: ${user.email}")
                    Text("Role: ${user.role}")
                    Text("School: ${user.schoolName}")
                    Text("Account Status: ${user.status}")
                }
            },
            confirmButton = {
                TextButton(onClick = { onSelectUser(null) }) {
                    Text("Close")
                }
            },
            shape = RoundedCornerShape(20.dp)
        )
    }
}
