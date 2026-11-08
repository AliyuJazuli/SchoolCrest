package com.hydev.schoolcrest.core.ui.components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.BrightnessAuto
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hydev.schoolcrest.Screen
import com.hydev.schoolcrest.core.model.AppThemeMode
import com.hydev.schoolcrest.core.model.UserRole
import com.hydev.schoolcrest.core.navigation.RoleNavigationProvider
import com.hydev.schoolcrest.core.theme.ThemeState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoleDashboardShell(
    role: UserRole,
    navController: NavController,
    modifier: Modifier = Modifier,
    customContent: @Composable (
        selectedRoute: String,
        onOpenDrawer: () -> Unit,
        onNavigate: (String) -> Unit
    ) -> Unit = { selectedRoute, onOpenDrawer, _ ->
        val bottomItems = RoleNavigationProvider.getBottomNavItems(role)
        if (selectedRoute == bottomItems.first().route) {
            AppCard(borderColor = MaterialTheme.colorScheme.primaryContainer) {
                Text(
                    text = "${role.displayName} Dashboard",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = role.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            EmptyState(
                title = selectedRoute,
                description = "The $selectedRoute screen module will be connected during feature integration."
            )
        }
    }
) {
    val bottomItems = RoleNavigationProvider.getBottomNavItems(role)
    val drawerItems = RoleNavigationProvider.getDrawerItems(role)

    var selectedNavItem by remember { mutableStateOf(bottomItems.first()) }
    val selectedBottomItem = bottomItems.firstOrNull { it.route == selectedNavItem.route }
        ?: bottomItems.first()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val currentThemeMode by ThemeState.themeMode.collectAsState()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    // Clean Header without inner container card
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f))
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AnimatedSchoolCrestLogo(size = 48.dp, isAnimated = true)
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text(
                                text = "SchoolCrest",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            AppStatusChip(status = StatusType.IN_PROGRESS, customText = role.displayName)
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "PRIMARY DESTINATIONS",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )

                    bottomItems.forEach { item ->
                        val isSelected = selectedNavItem.route == item.route
                        NavigationDrawerItem(
                            icon = { Icon(item.icon, contentDescription = item.title, tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant) },
                            label = { Text(item.title, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium) },
                            selected = isSelected,
                            onClick = {
                                selectedNavItem = item
                                scope.launch { drawerState.close() }
                            },
                            shape = RoundedCornerShape(16.dp),
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "MORE DESTINATIONS",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )

                    drawerItems.forEach { item ->
                        val isSelected = selectedNavItem.route == item.route
                        NavigationDrawerItem(
                            icon = { Icon(item.icon, contentDescription = item.title, tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant) },
                            label = { Text(item.title, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium) },
                            selected = isSelected,
                            onClick = {
                                selectedNavItem = item
                                scope.launch { drawerState.close() }
                            },
                            shape = RoundedCornerShape(16.dp),
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Theme Preference placed at the bottom (above Exit Portal)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                            .padding(12.dp)
                    ) {
                        Text(
                            text = "THEME PREFERENCE",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            FilterChip(
                                selected = currentThemeMode == AppThemeMode.SYSTEM_DEFAULT,
                                onClick = { ThemeState.setThemeMode(AppThemeMode.SYSTEM_DEFAULT) },
                                label = { Text("System", style = MaterialTheme.typography.labelSmall) },
                                leadingIcon = { Icon(Icons.Default.BrightnessAuto, contentDescription = null, modifier = Modifier.size(14.dp)) },
                                shape = RoundedCornerShape(10.dp),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                            FilterChip(
                                selected = currentThemeMode == AppThemeMode.LIGHT,
                                onClick = { ThemeState.setThemeMode(AppThemeMode.LIGHT) },
                                label = { Text("Light", style = MaterialTheme.typography.labelSmall) },
                                leadingIcon = { Icon(Icons.Default.LightMode, contentDescription = null, modifier = Modifier.size(14.dp)) },
                                shape = RoundedCornerShape(10.dp),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                            FilterChip(
                                selected = currentThemeMode == AppThemeMode.DARK,
                                onClick = { ThemeState.setThemeMode(AppThemeMode.DARK) },
                                label = { Text("Dark", style = MaterialTheme.typography.labelSmall) },
                                leadingIcon = { Icon(Icons.Default.DarkMode, contentDescription = null, modifier = Modifier.size(14.dp)) },
                                shape = RoundedCornerShape(10.dp),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    NavigationDrawerItem(
                        icon = { Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Exit Portal", tint = MaterialTheme.colorScheme.error) },
                        label = { Text("Exit Portal", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold) },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(Screen.SignupOrLoginScreen.route) {
                                popUpTo(Screen.SignupOrLoginScreen.route) { inclusive = true }
                            }
                        },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
            }
        }
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = Color.Transparent
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    customContent(
                        selectedNavItem.route,
                        { scope.launch { drawerState.open() } },
                        { route ->
                            (bottomItems + drawerItems)
                                .firstOrNull { it.route == route }
                                ?.let { selectedNavItem = it }
                        }
                    )
                }
            }

            Surface(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .fillMaxWidth()
                    .height(62.dp),
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = 0.96f),
                contentColor = MaterialTheme.colorScheme.onSurface,
                border = BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.72f)
                ),
                tonalElevation = 3.dp,
                shadowElevation = 0.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { scope.launch { drawerState.open() } },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Open navigation menu",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    IconButton(
                        onClick = {
                            val destination = when (role) {
                                UserRole.PLATFORM_OWNER -> "po_audit_logs"
                                UserRole.SCHOOL_ADMIN -> "sa_announcements"
                                UserRole.TEACHER -> "t_assignments"
                                UserRole.PARENT -> "p_notifications"
                            }
                            drawerItems.firstOrNull { it.route == destination }?.let {
                                selectedNavItem = it
                            }
                        },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(
                        onClick = {
                            val destination = when (role) {
                                UserRole.PLATFORM_OWNER -> "po_settings"
                                UserRole.SCHOOL_ADMIN -> "sa_profile"
                                UserRole.TEACHER -> "t_profile"
                                UserRole.PARENT -> "p_profile"
                            }
                            drawerItems.firstOrNull { it.route == destination }?.let {
                                selectedNavItem = it
                            }
                        },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            AppBottomNavigationBar(
                items = bottomItems,
                selectedItem = selectedBottomItem,
                onItemSelected = { selectedNavItem = it },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
            )
        }
    }
}
