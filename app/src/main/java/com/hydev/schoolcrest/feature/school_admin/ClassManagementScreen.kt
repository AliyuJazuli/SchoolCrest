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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.hydev.schoolcrest.core.ui.components.AppFloatingActionButton
import com.hydev.schoolcrest.core.ui.components.AppAlertDialog
import com.hydev.schoolcrest.core.ui.components.AppPrimaryButton
import com.hydev.schoolcrest.core.ui.components.AppStatusChip
import com.hydev.schoolcrest.core.ui.components.EmptyState
import com.hydev.schoolcrest.core.ui.components.StatusType

@Composable
fun ClassManagementScreen(
    state: SchoolAdminUiState,
    onCreateClass: (String, String, List<String>) -> Unit,
    onShowCreateDialog: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var classNameInput by remember { mutableStateOf("") }
    var classTeacherInput by remember { mutableStateOf("") }
    var subjectsInput by remember { mutableStateOf("") }
    val classListState = rememberLazyListState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            AppFloatingActionButton(
                onClick = { onShowCreateDialog(true) },
                text = "Create class",
                expanded = !classListState.isScrollInProgress
            ) {
                Icon(Icons.Default.Add, contentDescription = "Create Class")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(start = 16.dp, end = 16.dp, top = 60.dp, bottom = 16.dp)
        ) {
            Text(
                text = "School Classes (${state.classes.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (state.classes.isEmpty()) {
                EmptyState(
                    title = "No Classes Created",
                    description = "Create classes to assign teachers and students."
                )
            } else {
                LazyColumn(
                    state = classListState,
                    contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(state.classes) { schoolClass ->
                        AppCard {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = schoolClass.className,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        AppStatusChip(status = StatusType.PRESENT, customText = "${schoolClass.studentCount} Students")
                                    }
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "Class Teacher: ${schoolClass.classTeacher}",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = "Subjects: ${schoolClass.subjects.joinToString(", ")}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (state.showCreateClassDialog) {
        AppAlertDialog(
            onDismissRequest = { onShowCreateDialog(false) },
            title = { Text("Create New Class", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = classNameInput,
                        onValueChange = { classNameInput = it },
                        label = { Text("Class Name (e.g. JSS 2B)") },
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = classTeacherInput,
                        onValueChange = { classTeacherInput = it },
                        label = { Text("Designated Class Teacher") },
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = subjectsInput,
                        onValueChange = { subjectsInput = it },
                        label = { Text("Subjects (comma separated)") },
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                AppPrimaryButton(
                    text = "Create Class",
                    onClick = {
                        val subs = subjectsInput.split(",").map { it.trim() }.filter { it.isNotEmpty() }
                        onCreateClass(classNameInput, classTeacherInput, subs)
                        classNameInput = ""
                        classTeacherInput = ""
                        subjectsInput = ""
                    }
                )
            },
            dismissButton = {
                TextButton(onClick = { onShowCreateDialog(false) }) {
                    Text("Cancel")
                }
            },
            shape = RoundedCornerShape(20.dp)
        )
    }
}
