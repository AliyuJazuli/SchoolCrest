package com.hydev.schoolcrest.core.util

/**
 * Utility functions for consistent form validation and user-feedback across auth screens.
 */
object FormValidator {

    fun validateEmail(email: String): String? {
        val trimmed = email.trim()
        if (trimmed.isEmpty()) return "Email address is required"
        val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"
        if (!trimmed.matches(emailRegex.toRegex())) return "Invalid email address format"
        return null
    }

    fun validatePhoneNumber(phone: String): String? {
        val trimmed = phone.trim()
        if (trimmed.isEmpty()) return "Phone number is required"
        val normalized = trimmed.replace(Regex("[\\s()-]"), "")
        if (!normalized.matches(Regex("^\\+?\\d+$"))) {
            return "Invalid phone number format"
        }
        val digitCount = normalized.count(Char::isDigit)
        if (digitCount < 10) return "Phone number must be at least 10 digits"
        if (digitCount > 15) {
            return "Invalid phone number format"
        }
        return null
    }

    fun validatePassword(password: String): String? {
        if (password.isEmpty()) return "Password is required"
        if (password.length < 6) return "Password must be at least 6 characters"
        return null
    }

    fun validateConfirmPassword(password: String, confirm: String): String? {
        if (confirm.isEmpty()) return "Please confirm password"
        if (password != confirm) return "Passwords do not match"
        return null
    }

    fun validateRequired(input: String, fieldName: String): String? {
        if (input.trim().isEmpty()) return "$fieldName is required"
        return null
    }
}
