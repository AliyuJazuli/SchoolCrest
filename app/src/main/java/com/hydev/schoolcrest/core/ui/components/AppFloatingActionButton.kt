package com.hydev.schoolcrest.core.ui.components

import androidx.compose.foundation.layout.offset
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier.offset(y = 24.dp),
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 12.dp,
            pressedElevation = 16.dp,
            focusedElevation = 14.dp,
            hoveredElevation = 14.dp
        ),
        content = content
    )
}
