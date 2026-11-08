package com.hydev.schoolcrest.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChildCare
import androidx.compose.material.icons.filled.Class
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material.icons.filled.CoPresent
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.ui.graphics.vector.ImageVector
import com.hydev.schoolcrest.core.model.UserRole

data class NavItem(
    val title: String,
    val route: String,
    val icon: ImageVector,
    val isPrimary: Boolean = true
)

object RoleNavigationProvider {

    fun getBottomNavItems(role: UserRole): List<NavItem> {
        return when (role) {
            UserRole.PLATFORM_OWNER -> listOf(
                NavItem("Overview", "po_overview", Icons.Default.Dashboard),
                NavItem("Schools", "po_schools", Icons.Default.School),
                NavItem("Verify", "po_verification", Icons.Default.Verified),
                NavItem("Users", "po_users", Icons.Default.People),
            )

            UserRole.SCHOOL_ADMIN -> listOf(
                NavItem("Dashboard", "sa_dashboard", Icons.Default.Dashboard),
                NavItem("Students", "sa_students", Icons.Default.PersonSearch),
                NavItem("Teachers", "sa_teachers", Icons.Default.CoPresent),
                NavItem("Classes", "sa_classes", Icons.Default.Class)
            )

            UserRole.TEACHER -> listOf(
                NavItem("Dashboard", "t_dashboard", Icons.Default.Dashboard),
                NavItem("Classes", "t_my_classes", Icons.Default.Class),
                NavItem("Attend", "t_attendance", Icons.Default.EventAvailable),
                NavItem("Results", "t_academics", Icons.Default.Grade)
            )

            UserRole.PARENT -> listOf(
                NavItem("Dashboard", "p_dashboard", Icons.Default.Dashboard),
                NavItem("Children", "p_children", Icons.Default.ChildCare),
                NavItem("Attend", "p_attendance", Icons.Default.EventAvailable),
                NavItem("Results", "p_results", Icons.Default.Grade)
            )
        }
    }

    fun getDrawerItems(role: UserRole): List<NavItem> {
        return when (role) {
            UserRole.PLATFORM_OWNER -> listOf(
                NavItem("Verification", "po_verification", Icons.Default.VerifiedUser, false),
                NavItem("Analytics", "po_analytics", Icons.Default.Analytics, false),
                NavItem("Settings", "po_settings", Icons.Default.Settings, false),
                NavItem("Audit Logs", "po_audit_logs", Icons.Default.History, false),
                NavItem("Security", "po_security", Icons.Default.Security, false)
            )

            UserRole.SCHOOL_ADMIN -> listOf(
                NavItem("Academics", "sa_academics", Icons.Default.Book, false),
                NavItem("School Profile", "sa_profile", Icons.Default.School, false),
                NavItem("Parents", "sa_parents", Icons.Default.Group, false),
                NavItem("Attendance Summary", "sa_attendance", Icons.Default.CheckCircle, false),
                NavItem("Reports", "sa_reports", Icons.Default.Analytics, false),
                NavItem("Timetable", "sa_timetable", Icons.Default.CalendarToday, false),
                NavItem("Calendar", "sa_calendar", Icons.Default.CalendarMonth, false),
                NavItem("Announcements", "sa_announcements", Icons.Default.Campaign, false),
                NavItem("Audit Logs", "sa_audit_logs", Icons.Default.History, false)
            )

            UserRole.TEACHER -> listOf(
                NavItem("Profile", "t_profile", Icons.Default.Person, false),
                NavItem("Class Status", "t_class_status", Icons.Default.CheckCircle, false),
                NavItem("Students", "t_students", Icons.Default.People, false),
                NavItem("Assignments", "t_assignments", Icons.AutoMirrored.Filled.Assignment, false),
                NavItem("Reports", "t_reports", Icons.Default.Analytics, false),
                NavItem("Timetable", "t_timetable", Icons.Default.CalendarToday, false)
            )

            UserRole.PARENT -> listOf(
                NavItem("Profile", "p_profile", Icons.Default.Person, false),
                NavItem("Announcements", "p_announcements", Icons.Default.Campaign, false),
                NavItem("Timetable", "p_timetable", Icons.Default.CalendarToday, false),
                NavItem("Notifications", "p_notifications", Icons.Default.Notifications, false)
            )
        }
    }
}
