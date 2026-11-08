package com.hydev.schoolcrest.core.model

/**
 * Defines the four primary user roles in SchoolCrest.
 * Note: Role assigned during registration represents requested role;
 * actual access permissions must be authorized through the backend system.
 */
enum class UserRole(
    val roleKey: String,
    val displayName: String,
    val description: String
) {
    PLATFORM_OWNER(
        roleKey = "platform_owner",
        displayName = "Platform Owner",
        description = "Global platform management, school verification, and security oversight."
    ),
    SCHOOL_ADMIN(
        roleKey = "school_admin",
        displayName = "School Admin",
        description = "Manage school operations, staff authorization, students, and academic publishing."
    ),
    TEACHER(
        roleKey = "teacher",
        displayName = "Teacher",
        description = "Manage assigned classes, record attendance, enter subject marks, and update class status."
    ),
    PARENT(
        roleKey = "parent",
        displayName = "Parent",
        description = "Access records, attendance, published results, and timetables for linked children."
    );

    companion object {
        fun fromKey(key: String?): UserRole {
            return entries.find { it.roleKey.equals(key, ignoreCase = true) } ?: PARENT
        }
    }
}
