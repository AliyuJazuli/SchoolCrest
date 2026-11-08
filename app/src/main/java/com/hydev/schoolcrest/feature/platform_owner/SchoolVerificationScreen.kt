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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
fun SchoolVerificationScreen(
    state: PlatformOwnerUiState,
    onSelectVerification: (VerificationRecord?) -> Unit,
    onVerificationAction: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val pendingVerifications = state.verifications

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 96.dp, bottom = 140.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            AppCard(
                borderColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                Text(
                    text = "School Document Verification",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Review supporting documents and issue secure verification codes for active operation.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        item {
            Text(
                text = "Verification Queue (${pendingVerifications.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        if (pendingVerifications.isEmpty()) {
            item {
                EmptyState(
                    title = "No Pending Verifications",
                    description = "All school registration applications have been reviewed."
                )
            }
        } else {
            items(pendingVerifications) { verification ->
                AppCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = verification.schoolName,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = verification.location,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            val chipStatus = when (verification.status) {
                                "Approved" -> StatusType.PRESENT
                                "Rejected" -> StatusType.ABSENT
                                else -> StatusType.LATE
                            }
                            AppStatusChip(status = chipStatus, customText = verification.status)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Documents: ${verification.documentsSubmitted.joinToString(", ")}",
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
                            onClick = { onSelectVerification(verification) }
                        ) {
                            Text("Review Application")
                        }
                    }
                }
            }
        }
    }

    if (state.selectedVerification != null) {
        val v = state.selectedVerification!!
        AppAlertDialog(
            onDismissRequest = { onSelectVerification(null) },
            title = { Text(v.schoolName, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Location: ${v.location}")
                    Text("Status: ${v.status}")
                    Text("Submitted Documents:")
                    v.documentsSubmitted.forEach { doc ->
                        Text("• $doc", style = MaterialTheme.typography.bodySmall)
                    }
                }
            },
            confirmButton = {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { onVerificationAction(v.id, "Approved") },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text("Approve & Issue Code")
                    }
                    OutlinedButton(
                        onClick = { onVerificationAction(v.id, "Rejected") }
                    ) {
                        Text("Reject", color = MaterialTheme.colorScheme.error)
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = { onSelectVerification(null) }) {
                    Text("Close")
                }
            },
            shape = RoundedCornerShape(20.dp)
        )
    }
}
