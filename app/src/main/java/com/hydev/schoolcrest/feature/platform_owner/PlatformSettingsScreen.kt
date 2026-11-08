package com.hydev.schoolcrest.feature.platform_owner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrightnessAuto
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hydev.schoolcrest.core.model.AppThemeMode
import com.hydev.schoolcrest.core.theme.ThemeState
import com.hydev.schoolcrest.core.ui.components.AppCard

@Composable
fun PlatformSettingsScreen(
    modifier: Modifier = Modifier
) {
    var pushNotifications by remember { mutableStateOf(true) }
    var emailAlerts by remember { mutableStateOf(true) }
    var maintenanceMode by remember { mutableStateOf(false) }
    val currentThemeMode by ThemeState.themeMode.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AppCard(
            borderColor = MaterialTheme.colorScheme.primaryContainer
        ) {
            Text(
                text = "Platform Settings & Preferences",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Configure global application defaults, theme appearance, notification channels, and operational maintenance flags.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        AppCard {
            Text(
                text = "Application Theme Preference",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Select your preferred theme mode for the entire application.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = currentThemeMode == AppThemeMode.SYSTEM_DEFAULT,
                    onClick = { ThemeState.setThemeMode(AppThemeMode.SYSTEM_DEFAULT) },
                    label = { Text("System Default") },
                    leadingIcon = { Icon(Icons.Default.BrightnessAuto, contentDescription = null, modifier = Modifier.size(18.dp)) },
                    shape = RoundedCornerShape(12.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
                FilterChip(
                    selected = currentThemeMode == AppThemeMode.LIGHT,
                    onClick = { ThemeState.setThemeMode(AppThemeMode.LIGHT) },
                    label = { Text("Light") },
                    leadingIcon = { Icon(Icons.Default.LightMode, contentDescription = null, modifier = Modifier.size(18.dp)) },
                    shape = RoundedCornerShape(12.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
                FilterChip(
                    selected = currentThemeMode == AppThemeMode.DARK,
                    onClick = { ThemeState.setThemeMode(AppThemeMode.DARK) },
                    label = { Text("Dark") },
                    leadingIcon = { Icon(Icons.Default.DarkMode, contentDescription = null, modifier = Modifier.size(18.dp)) },
                    shape = RoundedCornerShape(12.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }

        AppCard {
            Text(
                text = "Global Notifications",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Push Notifications", fontWeight = FontWeight.SemiBold)
                    Text("Broadcast system alerts to mobile apps", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Switch(checked = pushNotifications, onCheckedChange = { pushNotifications = it })
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Critical Email Alerts", fontWeight = FontWeight.SemiBold)
                    Text("Send email digests for school verifications", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Switch(checked = emailAlerts, onCheckedChange = { emailAlerts = it })
            }
        }

        AppCard {
            Text(
                text = "System Maintenance",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Maintenance Mode", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.error)
                    Text("Temporarily suspend non-admin logins", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Switch(checked = maintenanceMode, onCheckedChange = { maintenanceMode = it })
            }
        }
    }
}
