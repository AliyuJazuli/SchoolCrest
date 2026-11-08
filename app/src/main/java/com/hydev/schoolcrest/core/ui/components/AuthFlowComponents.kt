package com.hydev.schoolcrest.core.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.FilterChip
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hydev.schoolcrest.core.model.UserRole

@Composable
fun AuthProgressIndicator(step: Int, totalSteps: Int, compact: Boolean = false) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Step $step of $totalSteps",
            style = if (compact) MaterialTheme.typography.labelMedium else MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(if (compact) 4.dp else 8.dp))
        LinearProgressIndicator(
            progress = { step.toFloat() / totalSteps },
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.primaryContainer
        )
    }
}

@Composable
fun AuthRoleSelector(
    roles: List<UserRole>,
    selectedRole: UserRole?,
    compact: Boolean = false,
    onRoleSelected: (UserRole) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(if (compact) 6.dp else 8.dp)
    ) {
        roles.chunked(2).forEach { rowRoles ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowRoles.forEach { role ->
                    val isSelected = selectedRole == role
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .height(if (compact) 68.dp else 76.dp)
                            .clickable(
                                role = Role.RadioButton,
                                onClick = { onRoleSelected(role) }
                            )
                            .semantics { selected = isSelected },
                        shape = RoundedCornerShape(18.dp),
                        color = if (isSelected) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            MaterialTheme.colorScheme.surfaceContainerLow
                        },
                        contentColor = if (isSelected) {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        },
                        border = BorderStroke(
                            if (isSelected) 1.5.dp else 1.dp,
                            if (isSelected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.outlineVariant
                        ),
                        tonalElevation = if (isSelected) 2.dp else 0.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(if (compact) 68.dp else 76.dp)
                                .padding(horizontal = 10.dp, vertical = if (compact) 8.dp else 10.dp),
                            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = role.icon(),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(if (compact) 20.dp else 22.dp)
                            )
                            Column {
                                Text(
                                    text = role.displayName,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold
                                )
                                Text(
                                    text = role.shortDescription(),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
                if (rowRoles.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

private fun UserRole.icon() = when (this) {
    UserRole.PLATFORM_OWNER -> Icons.Default.Security
    UserRole.SCHOOL_ADMIN -> Icons.Default.School
    UserRole.TEACHER -> Icons.Default.AutoStories
    UserRole.PARENT -> Icons.Default.ChildCare
}

private fun UserRole.shortDescription() = when (this) {
    UserRole.PLATFORM_OWNER -> "Platform access"
    UserRole.SCHOOL_ADMIN -> "Manage a school"
    UserRole.TEACHER -> "Teach classes"
    UserRole.PARENT -> "Family access"
}

@Composable
fun AuthContactMethodSelector(
    isEmailSelected: Boolean,
    onEmailSelected: () -> Unit,
    onPhoneSelected: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        FilterChip(
            selected = isEmailSelected,
            onClick = onEmailSelected,
            label = { Text("Email", fontWeight = FontWeight.SemiBold) },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            modifier = Modifier.weight(1f)
        )
        FilterChip(
            selected = !isEmailSelected,
            onClick = onPhoneSelected,
            label = { Text("Phone", fontWeight = FontWeight.SemiBold) },
            leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
            modifier = Modifier.weight(1f)
        )
    }
}
