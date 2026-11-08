package com.hydev.schoolcrest.core.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.hydev.schoolcrest.core.navigation.NavItem

@Composable
fun AppBottomNavigationBar(
    items: List<NavItem>,
    selectedItem: NavItem,
    onItemSelected: (NavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 10.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        contentColor = MaterialTheme.colorScheme.onSurface,
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.72f)
        ),
        tonalElevation = 6.dp,
        shadowElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 76.dp)
                .padding(horizontal = 8.dp, vertical = 7.dp),
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val isSelected = selectedItem.route == item.route
                val weight by animateFloatAsState(
                    targetValue = if (isSelected) 1.55f else 1f,
                    animationSpec = spring(dampingRatio = 0.82f, stiffness = 420f),
                    label = "navigationItemWeight"
                )
                val itemPadding by animateDpAsState(
                    targetValue = if (isSelected) 12.dp else 6.dp,
                    animationSpec = spring(dampingRatio = 0.82f, stiffness = 420f),
                    label = "navigationItemPadding"
                )
                val transition = rememberInfiniteTransition(label = "navigationBreathing")
                val breathingScale by transition.animateFloat(
                    initialValue = 0.94f,
                    targetValue = 1.06f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1700, easing = FastOutSlowInEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "selectedItemBreathing"
                )

                Surface(
                    modifier = Modifier
                        .weight(weight)
                        .heightIn(min = 56.dp)
                        .padding(horizontal = 1.dp)
                        .graphicsLayer {
                            if (isSelected) {
                                scaleX = breathingScale
                                scaleY = breathingScale
                            }
                        }
                        .clip(RoundedCornerShape(22.dp))
                        .clickable(
                            role = Role.Tab,
                            onClick = { onItemSelected(item) }
                        )
                        .semantics {
                            selected = isSelected
                            contentDescription = item.title
                        },
                    shape = RoundedCornerShape(22.dp),
                    color = if (isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.01f)
                    },
                    contentColor = if (isSelected) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    },
                    border = if (isSelected) {
                        BorderStroke(
                            1.dp,
                            MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.15f)
                        )
                    } else {
                        null
                    },
                    tonalElevation = if (isSelected) 3.dp else 0.dp,
                    shadowElevation = if (isSelected) 5.dp else 0.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 56.dp)
                            .padding(horizontal = itemPadding, vertical = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = null,
                            modifier = Modifier.size(if (isSelected) 24.dp else 22.dp)
                        )
                        if (isSelected) {
                            Text(
                                text = item.title,
                                modifier = Modifier.padding(start = 7.dp),
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}
