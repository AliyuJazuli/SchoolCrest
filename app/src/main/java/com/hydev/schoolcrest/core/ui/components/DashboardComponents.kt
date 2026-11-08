package com.hydev.schoolcrest.core.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class DashboardMetric(
    val title: String,
    val value: String,
    val supportingText: String,
    val icon: ImageVector,
    val onClick: (() -> Unit)? = null,
    val accentColor: Color? = null
)

@Composable
fun DashboardPage(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
            .padding(top = 96.dp, bottom = 140.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp),
        content = content
    )
}

@Composable
fun DashboardHero(
    title: String,
    description: String,
    icon: ImageVector,
    eyebrow: String? = null,
    signalLabel: String? = null,
    signalText: String? = null,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    accentColor: Color = MaterialTheme.colorScheme.primary,
    accentContentColor: Color = MaterialTheme.colorScheme.onPrimary,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        color = containerColor,
        contentColor = contentColor,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f)),
        tonalElevation = 1.dp
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (eyebrow != null) {
                        Text(
                            text = eyebrow,
                            style = MaterialTheme.typography.labelMedium,
                            color = accentColor,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = contentColor
                    )
                }
                Surface(
                    modifier = Modifier.size(60.dp),
                    shape = RoundedCornerShape(19.dp),
                    color = accentColor
                ) {
                    androidx.compose.foundation.layout.Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = accentContentColor,
                            modifier = Modifier.size(29.dp)
                        )
                    }
                }
            }

            if (signalLabel != null || (actionLabel != null && onAction != null)) {
                HorizontalDivider(color = contentColor.copy(alpha = 0.16f))
                BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                    if (maxWidth < 480.dp) {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            DashboardHeroSignal(
                                label = signalLabel,
                                text = signalText,
                                contentColor = contentColor,
                                accentColor = accentColor
                            )
                            if (actionLabel != null && onAction != null) {
                                DashboardHeroAction(
                                    label = actionLabel,
                                    onClick = onAction,
                                    accentColor = accentColor,
                                    contentColor = accentContentColor,
                                    modifier = Modifier.align(Alignment.End)
                                )
                            }
                        }
                    } else {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            DashboardHeroSignal(
                                label = signalLabel,
                                text = signalText,
                                contentColor = contentColor,
                                accentColor = accentColor,
                                modifier = Modifier.weight(1f)
                            )
                            if (actionLabel != null && onAction != null) {
                                DashboardHeroAction(
                                    label = actionLabel,
                                    onClick = onAction,
                                    accentColor = accentColor,
                                    contentColor = accentContentColor
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DashboardHeroSignal(
    label: String?,
    text: String?,
    contentColor: Color,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        label?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.labelSmall,
                color = accentColor,
                fontWeight = FontWeight.Bold
            )
        }
        text?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                color = contentColor,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun DashboardHeroAction(
    label: String,
    onClick: () -> Unit,
    accentColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = accentColor,
            contentColor = contentColor
        )
    ) {
        Text(label, fontWeight = FontWeight.Bold)
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            modifier = Modifier
                .padding(start = 8.dp)
                .size(18.dp)
        )
    }
}

@Composable
fun DashboardSection(
    title: String,
    actionText: String? = null,
    onAction: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            if (actionText != null && onAction != null) {
                TextButton(onClick = onAction) {
                    Text(actionText)
                }
            }
        }
        content()
    }
}

@Composable
fun DashboardMetricGrid(metrics: List<DashboardMetric>) {
    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val columns = if (maxWidth >= 840.dp) 4 else 2
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            metrics.chunked(columns).forEach { rowMetrics ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowMetrics.forEach { metric ->
                        DashboardMetricCard(metric = metric, modifier = Modifier.weight(1f))
                    }
                    repeat(columns - rowMetrics.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun DashboardMetricCard(metric: DashboardMetric, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .heightIn(min = 142.dp)
            .then(if (metric.onClick != null) Modifier.clickable(onClick = metric.onClick) else Modifier),
        shape = RoundedCornerShape(22.dp),
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        contentColor = MaterialTheme.colorScheme.onSurface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.72f)),
        tonalElevation = 1.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = metric.title,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Medium
                )
                Surface(
                    shape = CircleShape,
                    color = (metric.accentColor ?: MaterialTheme.colorScheme.primary)
                        .copy(alpha = 0.16f),
                    modifier = Modifier.size(34.dp)
                ) {
                    androidx.compose.foundation.layout.Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = metric.icon,
                            contentDescription = null,
                            tint = metric.accentColor ?: MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
            Text(
                text = metric.value,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = metric.supportingText,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun DashboardActionCard(
    title: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    accentColor: Color = MaterialTheme.colorScheme.primary
) {
    Surface(
        modifier = modifier
            .heightIn(min = 112.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(22.dp),
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        contentColor = MaterialTheme.colorScheme.onSurface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.72f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = accentColor.copy(alpha = 0.16f),
                modifier = Modifier.size(42.dp)
            ) {
                androidx.compose.foundation.layout.Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(21.dp)
                    )
                }
            }
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(text = title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun DashboardListItem(
    title: String,
    supportingText: String,
    trailingText: String? = null,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.size(10.dp)
        ) {}
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
            Text(
                text = supportingText,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        if (trailingText != null) {
            Text(
                text = trailingText,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun DashboardEmptyCard(
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.72f))
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
