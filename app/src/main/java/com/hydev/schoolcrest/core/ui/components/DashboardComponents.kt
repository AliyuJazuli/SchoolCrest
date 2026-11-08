package com.hydev.schoolcrest.core.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.ui.text.style.TextAlign
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

object DashboardTokens {
    val pageTopInset = 96.dp
    val pageBottomInset = 140.dp
    val compactHorizontalInset = 20.dp
    val expandedHorizontalInset = 32.dp
    val contentMaxWidth = 1280.dp
    val sectionSpacing = 16.dp
    val itemSpacing = 10.dp
    val cardPadding = 16.dp
    val cardCornerRadius = 14.dp
    val compactCardCornerRadius = 12.dp
    val outlineWidth = 1.dp
}

enum class DashboardHeroArtwork {
    CAMPUS,
    BOOKS,
    FAMILY,
    PLATFORM
}

data class DashboardLink(
    val title: String,
    val supportingText: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

data class DashboardMetric(
    val title: String,
    val value: String,
    val supportingText: String,
    val icon: ImageVector,
    val onClick: (() -> Unit)? = null,
    val accentColor: Color? = null
)

@Composable
private fun dashboardSurfaceColor(): Color = MaterialTheme.colorScheme.surfaceContainerLow

@Composable
private fun dashboardOutlineColor(): Color = MaterialTheme.colorScheme.outlineVariant

@Composable
private fun dashboardHeroBackground(): Color =
    if (MaterialTheme.colorScheme.background.luminance() < 0.5f) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.onPrimaryContainer
    }

/* The reference dashboards deliberately use a deeper teal than the rest of the
 * application.  Keeping these colours local to the dashboard card makes the
 * card read as one illustrated surface rather than a standard Material card. */
private val ReferenceHeroTop = Color(0xFF007B85)
private val ReferenceHeroBottom = Color(0xFF004C57)
private val ReferenceHeroInk = Color(0xFFFFFFFF)
private val ReferenceHeroMutedInk = Color(0xFFD3F1F1)

@Composable
private fun dashboardHeroContentColor(): Color =
    if (MaterialTheme.colorScheme.background.luminance() < 0.5f) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.surface
    }

@Composable
private fun dashboardHeroSupportingColor(): Color =
    if (MaterialTheme.colorScheme.background.luminance() < 0.5f) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

@Composable
fun DashboardHeroSupportingTextColor(): Color = ReferenceHeroMutedInk

@Composable
fun DashboardPage(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val horizontalInset = if (maxWidth < 600.dp) {
            DashboardTokens.compactHorizontalInset
        } else {
            DashboardTokens.expandedHorizontalInset
        }

        Column(
            modifier = Modifier
                .widthIn(max = DashboardTokens.contentMaxWidth)
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.TopCenter)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = horizontalInset)
                .padding(
                    top = DashboardTokens.pageTopInset,
                    bottom = DashboardTokens.pageBottomInset
                ),
            verticalArrangement = Arrangement.spacedBy(DashboardTokens.sectionSpacing),
            content = content
        )
    }
}

@Composable
fun DashboardPageHeader(
    title: String,
    supportingText: String,
    eyebrow: String
) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(
            text = eyebrow,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = supportingText,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun DashboardHeroCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val heroAccent = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
    val heroContentAccent = dashboardHeroContentColor().copy(alpha = 0.16f)
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = dashboardHeroBackground(),
        contentColor = dashboardHeroContentColor(),
        border = BorderStroke(
            DashboardTokens.outlineWidth,
            dashboardHeroContentColor().copy(alpha = 0.18f)
        ),
        tonalElevation = 2.dp
    ) {
        Box {
            Canvas(Modifier.matchParentSize()) {
                val radius = size.minDimension * 0.62f
                drawCircle(
                    color = heroAccent,
                    radius = radius * 0.78f,
                    center = Offset(size.width * 1.02f, size.height * 0.04f)
                )
                drawCircle(
                    color = heroContentAccent,
                    radius = radius * 0.52f,
                    center = Offset(size.width * 1.02f, size.height * 0.04f),
                    style = Stroke(width = 1.dp.toPx())
                )
            }
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                content = content
            )
        }
    }
}

@Composable
fun DashboardReferenceGreeting(
    title: String,
    supportingText: String
) {
    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = supportingText,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun DashboardReferenceHeroPill(label: String, icon: ImageVector) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = Color.White.copy(alpha = .13f),
        contentColor = Color.White
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
            horizontalArrangement = Arrangement.spacedBy(7.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(16.dp), tint = Color(0xFFCBFFFF))
            Text(label, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
            Text("⌄", style = MaterialTheme.typography.labelLarge, color = ReferenceHeroMutedInk)
        }
    }
}

@Composable
fun DashboardReferenceChildHeading(name: String, year: String, icon: ImageVector) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
        Surface(modifier = Modifier.size(58.dp), shape = CircleShape, color = Color(0xFFE9F5F5), border = BorderStroke(2.dp, Color.White.copy(alpha = .8f))) {
            Box(contentAlignment = Alignment.Center) {
                Icon(icon, contentDescription = null, tint = Color(0xFF006676), modifier = Modifier.size(31.dp))
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(year, style = MaterialTheme.typography.bodySmall, color = ReferenceHeroMutedInk, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun DashboardReferenceHeroCard(
    artwork: DashboardHeroArtwork,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 128.dp),
        shape = RoundedCornerShape(20.dp),
        color = Color.Transparent,
        contentColor = ReferenceHeroInk,
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.14f)),
        tonalElevation = 0.dp
    ) {
        Box {
            Canvas(Modifier.matchParentSize()) {
                drawRoundRect(
                    brush = Brush.verticalGradient(listOf(ReferenceHeroTop, ReferenceHeroBottom)),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(20.dp.toPx())
                )
                // The shallow waves along the lower edge are present in every
                // supplied dashboard, even where the illustration changes.
                val wave = Path().apply {
                    moveTo(-size.width * .08f, size.height * .91f)
                    cubicTo(size.width * .20f, size.height * .68f, size.width * .48f, size.height * 1.03f, size.width * .72f, size.height * .83f)
                    cubicTo(size.width * .89f, size.height * .69f, size.width * 1.04f, size.height * .72f, size.width * 1.08f, size.height * .67f)
                    lineTo(size.width * 1.08f, size.height * 1.08f)
                    lineTo(-size.width * .08f, size.height * 1.08f)
                    close()
                }
                drawPath(wave, Color(0xFF0B8790).copy(alpha = .36f))
                drawPath(wave, Color(0xFF8AE5E4).copy(alpha = .35f), style = Stroke(width = 1.dp.toPx()))
            }
            DashboardHeroArtworkLayer(
                artwork = artwork,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .fillMaxHeight()
                    .width(190.dp)
            )
            Column(
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 17.dp),
                verticalArrangement = Arrangement.spacedBy(11.dp),
                content = content
            )
        }
    }
}

@Composable
private fun DashboardHeroArtworkLayer(
    artwork: DashboardHeroArtwork,
    modifier: Modifier = Modifier
) {
    val tint = Color(0xFF65CFD0).copy(alpha = 0.22f)
    val detail = Color(0xFFD7FFFF).copy(alpha = 0.32f)
    Canvas(modifier = modifier) {
        when (artwork) {
            DashboardHeroArtwork.CAMPUS,
            DashboardHeroArtwork.PLATFORM -> {
                drawCircle(
                    color = tint.copy(alpha = 0.55f),
                    radius = size.height * 0.70f,
                    center = Offset(size.width * 0.76f, size.height * 0.30f)
                )
                drawCircle(
                    color = tint.copy(alpha = 0.26f),
                    radius = size.height * 0.49f,
                    center = Offset(size.width * 0.98f, size.height * 0.22f)
                )
                drawRoundRect(
                    color = tint.copy(alpha = 0.82f),
                    topLeft = Offset(size.width * 0.27f, size.height * 0.39f),
                    size = Size(size.width * 0.61f, size.height * 0.48f),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(3.dp.toPx())
                )
                val roof = Path().apply {
                    moveTo(size.width * 0.18f, size.height * 0.41f)
                    lineTo(size.width * 0.58f, size.height * 0.13f)
                    lineTo(size.width * 0.98f, size.height * 0.41f)
                    close()
                }
                drawPath(roof, tint.copy(alpha = 0.9f))
                for (column in 0..3) {
                    for (row in 0..1) {
                        drawRoundRect(
                            color = detail,
                            topLeft = Offset(
                                size.width * (0.36f + column * 0.13f),
                                size.height * (0.51f + row * 0.15f)
                            ),
                            size = Size(size.width * 0.055f, size.height * 0.085f),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(1.dp.toPx())
                        )
                    }
                }
                drawRect(
                    color = detail.copy(alpha = 0.48f),
                    topLeft = Offset(size.width * 0.54f, size.height * 0.72f),
                    size = Size(size.width * 0.11f, size.height * 0.15f)
                )
            }

            DashboardHeroArtwork.BOOKS -> {
                drawCircle(tint.copy(alpha = .54f), size.height * .64f, Offset(size.width * .82f, size.height * .15f))
                drawCircle(tint.copy(alpha = .30f), size.height * .49f, Offset(size.width * .48f, size.height * .08f))
                val left = size.width * 0.38f
                listOf(0.52f, 0.67f, 0.82f).forEachIndexed { index, y ->
                    drawRoundRect(
                        color = tint.copy(alpha = 0.92f - index * 0.12f),
                        topLeft = Offset(left + index * 4.dp.toPx(), size.height * y),
                        size = Size(size.width * 0.46f, size.height * 0.12f),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(3.dp.toPx())
                    )
                }
                drawRoundRect(
                    color = detail.copy(alpha = .72f),
                    topLeft = Offset(size.width * 0.76f, size.height * 0.13f),
                    size = Size(4.dp.toPx(), size.height * 0.34f),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(2.dp.toPx())
                )
            }

            DashboardHeroArtwork.FAMILY -> {
                drawCircle(
                    color = tint.copy(alpha = 0.56f),
                    radius = size.height * 0.62f,
                    center = Offset(size.width * 0.76f, size.height * 0.25f)
                )
                drawCircle(
                    color = tint.copy(alpha = .80f),
                    radius = size.height * 0.18f,
                    center = Offset(size.width * 0.73f, size.height * 0.38f)
                )
                drawRoundRect(
                    color = tint.copy(alpha = .80f),
                    topLeft = Offset(size.width * 0.55f, size.height * 0.56f),
                    size = Size(size.width * 0.37f, size.height * 0.32f),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(size.height * 0.16f)
                )
                drawPath(
                    Path().apply {
                        moveTo(size.width * 0.45f, size.height * 0.5f)
                        lineTo(size.width * 0.72f, size.height * 0.28f)
                        lineTo(size.width, size.height * 0.5f)
                    },
                    color = detail.copy(alpha = .62f),
                    style = Stroke(width = 2.dp.toPx())
                )
            }
        }
    }
}

@Composable
fun DashboardReferenceMetricStrip(
    metrics: List<Pair<String, String>>,
    modifier: Modifier = Modifier,
    prominentFirst: Boolean = false,
    icons: List<ImageVector> = emptyList()
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        metrics.forEachIndexed { index, (label, value) ->
            if (index > 0) {
                androidx.compose.foundation.layout.Box(
                    Modifier
                        .padding(vertical = 3.dp)
                        .width(1.dp)
                        .heightIn(min = 38.dp)
                        .background(ReferenceHeroInk.copy(alpha = 0.30f))
                )
            }
            Column(
                modifier = Modifier
                    .weight(if (prominentFirst && index == 0) 1.25f else 1f)
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (index < icons.size) {
                        Icon(
                            imageVector = icons[index],
                            contentDescription = null,
                            tint = ReferenceHeroMutedInk,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                    Text(
                        text = value,
                        style = if (prominentFirst && index == 0) {
                            MaterialTheme.typography.headlineMedium
                        } else {
                            MaterialTheme.typography.titleMedium
                        },
                        color = ReferenceHeroInk,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = ReferenceHeroMutedInk,
                    maxLines = 2,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun DashboardReferenceTeacherDayMetrics() {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(.8f)) {
            Text("2", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text("Classes Today", style = MaterialTheme.typography.labelMedium, color = ReferenceHeroMutedInk)
        }
        Box(Modifier.width(1.dp).heightIn(min = 58.dp).background(Color.White.copy(alpha = .55f)))
        Column(modifier = Modifier.weight(1.35f).padding(horizontal = 13.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text("Maths", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text("Next Class", style = MaterialTheme.typography.labelSmall, color = ReferenceHeroMutedInk)
            Text("10:00 AM – 11:00 AM", style = MaterialTheme.typography.labelSmall, color = ReferenceHeroInk)
        }
        Box(Modifier.width(1.dp).heightIn(min = 58.dp).background(Color.White.copy(alpha = .55f)))
        Column(modifier = Modifier.weight(.75f).padding(start = 13.dp)) {
            Text("1", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text("Pending Task", style = MaterialTheme.typography.labelMedium, color = ReferenceHeroMutedInk)
        }
    }
}

@Composable
fun DashboardReferenceActionButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null
) {
    TextButton(
        onClick = onClick,
        modifier = modifier.heightIn(min = 38.dp),
        shape = RoundedCornerShape(18.dp),
        colors = androidx.compose.material3.ButtonDefaults.textButtonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(
            horizontal = 10.dp,
            vertical = 5.dp
        )
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold
        )
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 6.dp)
                    .size(16.dp)
            )
        }
    }
}

@Composable
fun DashboardReferenceSection(
    title: String,
    actionText: String? = null,
    onAction: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            if (actionText != null && onAction != null) {
                TextButton(onClick = onAction) {
                    Text(
                        text = actionText,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
        content()
    }
}

@Composable
fun DashboardReferenceQuickActions(
    links: List<DashboardLink>,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val columns = when {
            maxWidth < 340.dp -> 2
            maxWidth < 620.dp -> 4
            maxWidth < 900.dp -> 4
            else -> links.size.coerceAtMost(5)
        }.coerceAtLeast(1)
        Column(verticalArrangement = Arrangement.spacedBy(7.dp)) {
            links.chunked(columns).forEach { rowLinks ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    rowLinks.forEach { link ->
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .heightIn(min = 68.dp)
                                .clickable(role = Role.Button, onClick = link.onClick),
                            shape = RoundedCornerShape(12.dp),
                            color = dashboardSurfaceColor(),
                            contentColor = MaterialTheme.colorScheme.onSurface,
                            border = BorderStroke(1.dp, dashboardOutlineColor())
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 4.dp,
                                        vertical = 10.dp
                                    ),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = link.icon,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(
                                    text = link.title,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Center,
                                    maxLines = 2,
                                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                    repeat(columns - rowLinks.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardReferenceSummaryCards(
    metrics: List<DashboardMetric>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        metrics.forEach { metric ->
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 78.dp)
                    .then(
                        if (metric.onClick != null) {
                            Modifier.clickable(role = Role.Button, onClick = metric.onClick)
                        } else {
                            Modifier
                        }
                    ),
                shape = RoundedCornerShape(10.dp),
                color = dashboardSurfaceColor(),
                border = BorderStroke(1.dp, dashboardOutlineColor())
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 9.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Icon(
                        imageVector = metric.icon,
                        contentDescription = null,
                        tint = metric.accentColor ?: MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = metric.title,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                    Text(
                        text = metric.value,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

/** Matches the compact attendance panel used on the administrator reference screen. */
@Composable
fun DashboardReferenceAttendanceCard(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = dashboardSurfaceColor(),
        border = BorderStroke(1.dp, dashboardOutlineColor())
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Attendance overview",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "This week",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(contentAlignment = Alignment.Center) {
                    Canvas(Modifier.size(92.dp)) {
                        val stroke = 11.dp.toPx()
                        drawArc(Color(0xFF14BE7C), -90f, 338.4f, false, style = Stroke(stroke))
                        drawArc(Color(0xFFFFCD5A), 248.4f, 14.4f, false, style = Stroke(stroke))
                        drawArc(Color(0xFFB9C8D8), 262.8f, 7.2f, false, style = Stroke(stroke))
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("94%", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Text("Overall", style = MaterialTheme.typography.labelSmall)
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(.82f)
                        .padding(start = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    listOf(
                        Color(0xFF14BE7C) to "Present     94%",
                        Color(0xFFFFCD5A) to "Absent        4%",
                        Color(0xFFB9C8D8) to "Late            2%"
                    ).forEach { (color, label) ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(modifier = Modifier.size(9.dp), shape = CircleShape, color = color) {}
                            Text(label, modifier = Modifier.padding(start = 8.dp), style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
                Surface(
                    modifier = Modifier
                        .weight(1.05f)
                        .padding(start = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFDDF7F2)
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(7.dp)
                    ) {
                        Surface(shape = CircleShape, color = Color(0xFF17B87A), modifier = Modifier.size(22.dp)) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("✓", color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }
                        Column {
                            Text("All systems normal", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = Color(0xFF00645B))
                            Text("School operations\nare running smoothly.", style = MaterialTheme.typography.labelSmall, color = Color(0xFF245F63))
                        }
                    }
                }
            }
        }
    }
}

/** The teacher reference has one wide class card instead of a generic list item. */
@Composable
fun DashboardReferenceClassOverviewCard(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = dashboardSurfaceColor(),
        border = BorderStroke(1.dp, dashboardOutlineColor())
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(66.dp),
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFF006675)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("JSS 1A", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("JSS 1A", modifier = Modifier.weight(1f), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Surface(shape = RoundedCornerShape(18.dp), color = Color(0xFFD8F5DE)) {
                        Text("✓  On Track", modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp), style = MaterialTheme.typography.labelSmall, color = Color(0xFF18713B), fontWeight = FontWeight.Bold)
                    }
                }
                Text("32 Students  •  Maths", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 9.dp, max = 9.dp)
                        .background(Color(0xFFD5E0EA), RoundedCornerShape(10.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(.58f)
                            .background(Color(0xFF007887), RoundedCornerShape(10.dp))
                    )
                }
            }
        }
    }
}

@Composable
fun DashboardReferenceInfoCard(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    trailingText: String? = null,
    onClick: (() -> Unit)? = null
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
        shape = RoundedCornerShape(12.dp),
        color = dashboardSurfaceColor(),
        border = BorderStroke(1.dp, dashboardOutlineColor())
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (leadingIcon != null) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (trailingText != null) {
                Text(
                    text = trailingText,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun DashboardHeroPrimaryMetric(
    label: String,
    value: String,
    supportingText: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
            color = ReferenceHeroMutedInk,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium,
            color = ReferenceHeroInk,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = supportingText,
            style = MaterialTheme.typography.bodyMedium,
            color = ReferenceHeroMutedInk
        )
    }
}

@Composable
fun DashboardHeroStatGrid(
    stats: List<Pair<String, String>>,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val compact = maxWidth < 220.dp
        val rows = if (compact && stats.size > 2) stats.chunked(2) else listOf(stats)
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            rows.forEach { rowStats ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 52.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    rowStats.forEachIndexed { index, (label, value) ->
                        if (index > 0) {
                            androidx.compose.foundation.layout.Box(
                                Modifier
                                    .width(1.dp)
                                    .heightIn(min = 40.dp)
                                    .background(dashboardHeroContentColor().copy(alpha = 0.26f))
                            )
                        }
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                text = value,
                                style = if (value.length > 5) {
                                    MaterialTheme.typography.labelLarge
                                } else {
                                    MaterialTheme.typography.titleLarge
                                },
                                maxLines = 1,
                                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                                fontWeight = FontWeight.SemiBold,
                                color = if (value == "Unavailable") {
                                    MaterialTheme.colorScheme.tertiary
                                } else {
                                    dashboardHeroContentColor()
                                }
                            )
                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelSmall,
                                maxLines = 2,
                                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                                color = dashboardHeroSupportingColor()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardHeroActionButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Text(label, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun DashboardQuickActionGrid(
    links: List<DashboardLink>,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val columns = when {
            maxWidth < 320.dp -> 2
            maxWidth < 700.dp -> 3
            else -> 4
        }
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            links.chunked(columns).forEach { rowLinks ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    rowLinks.forEach { link ->
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .heightIn(min = 76.dp)
                                .clickable(role = Role.Button, onClick = link.onClick),
                            shape = RoundedCornerShape(DashboardTokens.compactCardCornerRadius),
                            color = dashboardSurfaceColor(),
                            contentColor = MaterialTheme.colorScheme.onSurface,
                            border = BorderStroke(
                                DashboardTokens.outlineWidth,
                                dashboardOutlineColor()
                            ),
                            tonalElevation = 1.dp
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = link.icon,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = link.title,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Center,
                                    maxLines = 2,
                                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                    repeat(columns - rowLinks.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardSummaryStrip(
    stats: List<Pair<String, String>>,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val columns = if (maxWidth < 300.dp) 2 else stats.size.coerceAtMost(3)
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            stats.chunked(columns).forEach { rowStats ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rowStats.forEach { (label, value) ->
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .heightIn(min = 70.dp),
                            shape = RoundedCornerShape(DashboardTokens.compactCardCornerRadius),
                            color = dashboardSurfaceColor(),
                            contentColor = MaterialTheme.colorScheme.onSurface,
                            border = BorderStroke(
                                DashboardTokens.outlineWidth,
                                dashboardOutlineColor()
                            ),
                            tonalElevation = 1.dp
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(3.dp)
                            ) {
                                Text(
                                    text = value,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.SemiBold,
                                    maxLines = 1,
                                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                                )
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 2,
                                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                    repeat(columns - rowStats.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardAdaptiveColumns(
    primary: @Composable ColumnScope.() -> Unit,
    secondary: @Composable ColumnScope.() -> Unit
) {
    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        if (maxWidth < 600.dp) {
            Column(verticalArrangement = Arrangement.spacedBy(DashboardTokens.sectionSpacing)) {
                primary()
                secondary()
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(DashboardTokens.sectionSpacing),
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1.35f),
                    verticalArrangement = Arrangement.spacedBy(DashboardTokens.sectionSpacing),
                    content = primary
                )
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(DashboardTokens.sectionSpacing),
                    content = secondary
                )
            }
        }
    }
}

@Composable
fun DashboardDataNotice(
    title: String,
    description: String,
    icon: ImageVector,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(DashboardTokens.cardCornerRadius),
        color = dashboardSurfaceColor(),
        contentColor = MaterialTheme.colorScheme.onSurface,
        border = BorderStroke(
            DashboardTokens.outlineWidth,
            dashboardOutlineColor()
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(DashboardTokens.cardPadding),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(top = 2.dp)
                    .size(20.dp)
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (actionLabel != null && onAction != null) {
                    TextButton(
                        onClick = onAction
                    ) {
                        Text(actionLabel)
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardActionList(
    links: List<DashboardLink>,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(DashboardTokens.cardCornerRadius),
        color = dashboardSurfaceColor(),
        border = BorderStroke(
            DashboardTokens.outlineWidth,
            dashboardOutlineColor()
        )
    ) {
        Column(modifier = Modifier.padding(horizontal = 14.dp)) {
            links.forEachIndexed { index, link ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 68.dp)
                        .clickable(role = Role.Button, onClick = link.onClick)
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = link.icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(19.dp)
                    )
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = link.title,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = link.supportingText,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(18.dp)
                    )
                }
                if (index < links.lastIndex) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                }
            }
        }
    }
}

@Composable
fun DashboardRecordPanel(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(DashboardTokens.cardCornerRadius),
        color = dashboardSurfaceColor(),
        border = BorderStroke(
            DashboardTokens.outlineWidth,
            dashboardOutlineColor()
        )
    ) {
        Column(
            modifier = Modifier.padding(horizontal = DashboardTokens.cardPadding),
            content = content
        )
    }
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
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
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
        val columns = when {
            maxWidth < 600.dp -> 2
            maxWidth < 840.dp -> 3
            else -> 4
        }
        Column(verticalArrangement = Arrangement.spacedBy(DashboardTokens.itemSpacing)) {
            metrics.chunked(columns).forEach { rowMetrics ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(DashboardTokens.itemSpacing)
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
            .heightIn(min = 124.dp)
            .then(
                if (metric.onClick != null) {
                    Modifier.clickable(role = Role.Button, onClick = metric.onClick)
                } else {
                    Modifier
                }
            ),
        shape = RoundedCornerShape(DashboardTokens.compactCardCornerRadius),
        color = dashboardSurfaceColor(),
        contentColor = MaterialTheme.colorScheme.onSurface,
        tonalElevation = 1.dp,
        border = BorderStroke(
            DashboardTokens.outlineWidth,
            dashboardOutlineColor()
        )
    ) {
        Column(
            modifier = Modifier.padding(DashboardTokens.cardPadding),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = metric.title,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.SemiBold
                )
                Icon(
                    imageVector = metric.icon,
                    contentDescription = null,
                    tint = metric.accentColor ?: MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(17.dp)
                )
            }
            Text(
                text = metric.value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
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
            .heightIn(min = 84.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(DashboardTokens.compactCardCornerRadius),
        color = dashboardSurfaceColor(),
        contentColor = MaterialTheme.colorScheme.onSurface,
        tonalElevation = 1.dp,
        border = BorderStroke(
            DashboardTokens.outlineWidth,
            dashboardOutlineColor()
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(36.dp)
            ) {
                androidx.compose.foundation.layout.Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(18.dp)
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
            .heightIn(min = 60.dp)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(7.dp)
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
        shape = RoundedCornerShape(DashboardTokens.cardCornerRadius),
        color = dashboardSurfaceColor(),
        tonalElevation = 1.dp,
        border = BorderStroke(
            DashboardTokens.outlineWidth,
            dashboardOutlineColor()
        )
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
