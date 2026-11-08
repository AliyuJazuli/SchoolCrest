package com.hydev.schoolcrest

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hydev.schoolcrest.core.ui.components.AppAlertDialog
import kotlinx.coroutines.launch


@Composable
fun OnboardingFlowScreen(navController: NavController) {
    val pager = rememberPagerState(pageCount = { 3 })
    val scope = rememberCoroutineScope()
    var showDeveloperPreview by remember { mutableStateOf(false) }
    val page = pager.currentPage

    Surface(color = MaterialTheme.colorScheme.background) {
        Box(Modifier.fillMaxSize()) {

            SchoolCrestWordmark(Modifier.align(Alignment.TopCenter))

            HorizontalPager(
                state = pager,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(top = 100.dp, bottom = 140.dp)
            ) { current ->
                SchoolCrestStoryPage(current)
            }


            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = 28.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                StaticPageDots(page)
                Spacer(Modifier.height(18.dp))
                if (page < 2) {
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        QuietAction("Skip", Modifier.weight(.30f)) {
                            navController.navigate(Screen.LoginScreen.route) {
                                popUpTo(Screen.OnboardingScreen.route) { inclusive = true }
                            }
                        }
                        Spacer(Modifier.width(12.dp))
                        PrimaryAction("Next", Modifier.weight(.70f)) {
                            scope.launch { pager.animateScrollToPage(page + 1) }
                        }
                    }
                } else {
                    PrimaryAction("Create account", Modifier.fillMaxWidth(), Icons.Default.PersonAdd) {
                        navController.navigate(Screen.SignupScreen.route) {
                            popUpTo(Screen.OnboardingScreen.route) { inclusive = true }
                        }
                    }
                    Spacer(Modifier.height(10.dp))
                    QuietAction("Log in", Modifier.fillMaxWidth(), Icons.AutoMirrored.Filled.Login) {
                        navController.navigate(Screen.LoginScreen.route) {
                            popUpTo(Screen.OnboardingScreen.route) { inclusive = true }
                        }
                    }
                    Text("Developer preview", Modifier.padding(top = 12.dp).clickable { showDeveloperPreview = true }, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                    Text("Back", Modifier.padding(top = 8.dp).clickable { scope.launch { pager.animateScrollToPage(1) } }, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
    if (showDeveloperPreview) {
        DeveloperPreviewDialog(
            onDismiss = { showDeveloperPreview = false },
            onOpen = { route -> showDeveloperPreview = false; navController.navigate(route) }
        )
    }
}

@Composable
private fun SchoolCrestWordmark(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .statusBarsPadding()
            .padding(top = 12.dp),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = 0.94f),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.65f)
        ),
        tonalElevation = 3.dp,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            Image(
                painterResource(R.drawable.schoolcrest_logo),
                contentDescription = null,
                modifier = Modifier.size(34.dp)
            )
            Text(
                "SchoolCrest",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun SchoolCrestStoryPage(page: Int) {
    val title = listOf("Every school voice, together", "The school day, clearly connected", "Your SchoolCrest starts here")[page]
    val copy = listOf(
        "A shared home for teachers, families, and school leaders.",
        "Attendance, learning updates, and records—kept close when they matter.",
        "Create an account or sign in to your school community."
    )[page]
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SchoolCrestIllustration(page)
        Spacer(Modifier.height(30.dp))
        Text(title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center)
        Spacer(Modifier.height(10.dp))
        Text(copy, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center, lineHeight = 22.sp)
    }
}

@Composable
private fun SchoolCrestIllustration(page: Int) {
    val motion = rememberInfiniteTransition(label = "schoolcrest-story-$page")
    val breathe by motion.animateFloat(
        .98f,
        1.02f,
        infiniteRepeatable(
            tween(2400, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        ),
        label = "illustrationBreathing"
    )
    val accent = when (page) {
        0 -> MaterialTheme.colorScheme.primary
        1 -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.secondary
    }
    val centerIcon = when (page) {
        0 -> Icons.Default.School
        1 -> Icons.Default.CalendarToday
        else -> Icons.Default.PersonAdd
    }
    val supportIcons = when (page) {
        0 -> listOf(Icons.Default.Groups, Icons.Default.Person, Icons.Default.Notifications)
        1 -> listOf(Icons.Default.CheckCircle, Icons.Default.AutoStories, Icons.Default.Grade)
        else -> listOf(Icons.Default.Email, Icons.Default.Phone, Icons.Default.VerifiedUser)
    }

    Box(
        modifier = Modifier.size(276.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(244.dp)
                .graphicsLayer {
                    scaleX = breathe
                    scaleY = breathe
                }
                .background(
                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.58f),
                    CircleShape
                )
        )
        Box(
            modifier = Modifier
                .size(188.dp)
                .background(
                    MaterialTheme.colorScheme.surfaceContainerHigh,
                    RoundedCornerShape(56.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier.size(110.dp),
                shape = RoundedCornerShape(36.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                tonalElevation = 4.dp,
                shadowElevation = 8.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        centerIcon,
                        contentDescription = null,
                        tint = accent,
                        modifier = Modifier.size(58.dp)
                    )
                }
            }
        }
        val badgePositions = listOf(
            Alignment.TopStart,
            Alignment.TopEnd,
            Alignment.BottomEnd
        )
        supportIcons.forEachIndexed { index, icon ->
            Surface(
                modifier = Modifier
                    .align(badgePositions[index])
                    .padding(if (index == 1) 14.dp else 8.dp)
                    .size(58.dp)
                    .graphicsLayer {
                        translationY = if (index % 2 == 0) {
                            (breathe - 1f) * 240
                        } else {
                            (1f - breathe) * 240
                        }
                    },
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colorScheme.surface,
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.outlineVariant
                ),
                tonalElevation = 3.dp,
                shadowElevation = 6.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        icon,
                        contentDescription = null,
                        tint = accent,
                        modifier = Modifier.size(27.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun StaticPageDots(current: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        repeat(3) { index ->
            Box(Modifier.width(if (index == current) 28.dp else 8.dp).height(8.dp).clip(CircleShape).background(if (index == current) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = .45f)))
        }
    }
}

@Composable
private fun PrimaryAction(label: String, modifier: Modifier, icon: androidx.compose.ui.graphics.vector.ImageVector? = null, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = modifier.height(54.dp), shape = RoundedCornerShape(16.dp), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onPrimary)) {
        if (icon != null) { Icon(icon, null, Modifier.size(19.dp)); Spacer(Modifier.width(8.dp)) }
        Text(label, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}

@Composable
private fun QuietAction(label: String, modifier: Modifier, icon: androidx.compose.ui.graphics.vector.ImageVector? = null, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = modifier.height(54.dp), shape = RoundedCornerShape(16.dp), colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant, contentColor = MaterialTheme.colorScheme.onSurface), elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp, pressedElevation = 0.dp)) {
        if (icon != null) { Icon(icon, null, Modifier.size(19.dp)); Spacer(Modifier.width(8.dp)) }
        Text(label, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}

@Composable
private fun DeveloperPreviewDialog(onDismiss: () -> Unit, onOpen: (String) -> Unit) {
    AppAlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Developer preview", fontWeight = FontWeight.Bold) },
        text = { Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text("Open a role dashboard for layout and navigation review.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            PreviewRole("Platform owner") { onOpen(Screen.PlatformOwnerDashboard.route) }
            PreviewRole("School admin") { onOpen(Screen.SchoolAdminDashboard.route) }
            PreviewRole("Teacher") { onOpen(Screen.TeacherDashboard.route) }
            PreviewRole("Parent") { onOpen(Screen.ParentDashboard.route) }
        } },
        confirmButton = { Text("Close", Modifier.clickable(onClick = onDismiss).padding(12.dp), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold) }
    )
}

@Composable
private fun PreviewRole(label: String, onClick: () -> Unit) {
    Text(label, Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).clickable(onClick = onClick).padding(14.dp), fontWeight = FontWeight.SemiBold)
}
