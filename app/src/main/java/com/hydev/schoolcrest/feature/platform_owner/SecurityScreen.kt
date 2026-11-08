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
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hydev.schoolcrest.core.ui.components.AppCard
import com.hydev.schoolcrest.core.ui.components.AppStatusChip
import com.hydev.schoolcrest.core.ui.components.StatusType

@Composable
fun SecurityScreen(
    modifier: Modifier = Modifier
) {
    var twoFactorEnforced by remember { mutableStateOf(true) }
    var strictIsolation by remember { mutableStateOf(true) }
    var auditLoggingActive by remember { mutableStateOf(true) }

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
                text = "Platform Security Overview",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Monitor suspicious activity, access control rules, school-level data isolation, and account protection.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        AppCard {
            Text(
                text = "Security Policies & Controls",
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
                    Text("Enforce Two-Factor Authentication", fontWeight = FontWeight.SemiBold)
                    Text("Require 2FA for all School Admin accounts", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Switch(checked = twoFactorEnforced, onCheckedChange = { twoFactorEnforced = it })
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Strict School Data Isolation", fontWeight = FontWeight.SemiBold)
                    Text("Prevent cross-tenant data queries at Firestore rules level", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Switch(checked = strictIsolation, onCheckedChange = { strictIsolation = it })
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Append-Only Audit Logging", fontWeight = FontWeight.SemiBold)
                    Text("Ensure administrative actions are immutably logged", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Switch(checked = auditLoggingActive, onCheckedChange = { auditLoggingActive = it })
            }
        }

        AppCard {
            Text(
                text = "Recent Security Events",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Multiple failed admin login attempts", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.error)
                    Text("IP: 197.210.55.12 — Zaria Academy Admin", style = MaterialTheme.typography.bodySmall)
                }
                AppStatusChip(status = StatusType.ABSENT, customText = "Blocked")
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Secure verification code generated", fontWeight = FontWeight.SemiBold)
                    Text("Platform Owner issued code for Kano Model", style = MaterialTheme.typography.bodySmall)
                }
                AppStatusChip(status = StatusType.PRESENT, customText = "Normal")
            }
        }
    }
}
