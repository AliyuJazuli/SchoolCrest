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
import androidx.compose.material.icons.filled.Search
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
fun StudentManagementScreen(
    state: SchoolAdminUiState,
    onSearchChange: (String) -> Unit,
    onEnrollStudent: (String, String, String, String) -> Unit,
    onShowEnrollDialog: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var studentNameInput by remember { mutableStateOf("") }
    var classInput by remember { mutableStateOf("") }
    var parentNameInput by remember { mutableStateOf("") }
    var parentPhoneInput by remember { mutableStateOf("") }
    val studentListState = rememberLazyListState()

    val filteredStudents = state.students.filter { student ->
        student.fullName.contains(state.studentSearchQuery, ignoreCase = true) ||
                student.className.contains(state.studentSearchQuery, ignoreCase = true) ||
                student.parentPhoneNumber.contains(state.studentSearchQuery, ignoreCase = true)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            AppFloatingActionButton(
                onClick = { onShowEnrollDialog(true) },
                text = "Enroll student",
                expanded = !studentListState.isScrollInProgress
            ) {
                Icon(Icons.Default.Add, contentDescription = "Enroll Student")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(start = 16.dp, end = 16.dp, top = 60.dp, bottom = 16.dp)
        ) {
            OutlinedTextField(
                value = state.studentSearchQuery,
                onValueChange = onSearchChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search students by name, class, or parent phone...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = false,
                maxLines = 2,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Enrolled Students (${filteredStudents.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (filteredStudents.isEmpty()) {
                EmptyState(
                    title = "No Students Found",
                    description = "No student records match your search query."
                )
            } else {
                LazyColumn(
                    state = studentListState,
                    contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(filteredStudents) { student ->
                        AppCard {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = student.fullName,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        AppStatusChip(status = StatusType.IN_PROGRESS, customText = student.className)
                                    }
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "Parent/Guardian: ${student.parentName}",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    Text(
                                        text = "Phone: ${student.parentPhoneNumber}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (state.showEnrollStudentDialog) {
        AppAlertDialog(
            onDismissRequest = { onShowEnrollDialog(false) },
            title = { Text("Enroll New Student", fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = studentNameInput,
                        onValueChange = { studentNameInput = it },
                        label = { Text("Student Full Name") },
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = classInput,
                        onValueChange = { classInput = it },
                        label = { Text("Assigned Class (e.g. JSS 1A)") },
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = parentNameInput,
                        onValueChange = { parentNameInput = it },
                        label = { Text("Parent / Guardian Name") },
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = parentPhoneInput,
                        onValueChange = { parentPhoneInput = it },
                        label = { Text("Parent Phone Number") },
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                AppPrimaryButton(
                    text = "Enroll Student",
                    onClick = {
                        onEnrollStudent(studentNameInput, classInput, parentNameInput, parentPhoneInput)
                        studentNameInput = ""
                        classInput = ""
                        parentNameInput = ""
                        parentPhoneInput = ""
                    }
                )
            },
            dismissButton = {
                TextButton(onClick = { onShowEnrollDialog(false) }) {
                    Text("Cancel")
                }
            },
            shape = RoundedCornerShape(20.dp)
        )
    }
}
