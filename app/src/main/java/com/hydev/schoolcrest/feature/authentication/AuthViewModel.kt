package com.hydev.schoolcrest.feature.authentication

import androidx.lifecycle.ViewModel
import com.hydev.schoolcrest.core.model.UserRole
import com.hydev.schoolcrest.core.util.FormValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class LoginValidationErrors(
    val accountInputError: String? = null,
    val passwordError: String? = null
)

data class SignupValidationErrors(
    val fullNameError: String? = null,
    val accountInputError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val roleSpecificError: String? = null,
    val termsError: String? = null
)

data class AuthUiState(
    val isLoading: Boolean = false,
    val loginErrors: LoginValidationErrors = LoginValidationErrors(),
    val signupErrors: SignupValidationErrors = SignupValidationErrors(),
    val feedbackMessage: String? = null,
    val isAuthAvailable: Boolean = false,
    val targetRole: UserRole? = null
)

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun validateAndLogin(accountInput: String, password: String, isEmailTab: Boolean) {
        val accountError = if (isEmailTab) {
            FormValidator.validateEmail(accountInput)
        } else {
            FormValidator.validatePhoneNumber(accountInput)
        }
        val passError = FormValidator.validateRequired(password, "Password")

        if (accountError != null || passError != null) {
            _uiState.update {
                it.copy(
                    loginErrors = LoginValidationErrors(
                        accountInputError = accountError,
                        passwordError = passError
                    ),
                    feedbackMessage = "Please resolve the form errors before logging in."
                )
            }
            return
        }

        // Form validation passed. Set auth boundary state (Firebase Auth not connected yet)
        _uiState.update {
            it.copy(
                loginErrors = LoginValidationErrors(),
                feedbackMessage = null,
                isAuthAvailable = false
            )
        }
    }

    fun validateAndRegister(
        fullName: String,
        accountInput: String,
        password: String,
        confirmPassword: String,
        selectedRoleStr: String,
        roleSpecificInput: String,
        isEmailTab: Boolean,
        agreedToTerms: Boolean
    ) {
        val nameError = FormValidator.validateRequired(fullName, "Full Name")
        val accountError = if (isEmailTab) {
            FormValidator.validateEmail(accountInput)
        } else {
            FormValidator.validatePhoneNumber(accountInput)
        }
        val passError = FormValidator.validatePassword(password)
        val confirmError = FormValidator.validateConfirmPassword(password, confirmPassword)

        val role = when (selectedRoleStr) {
            "School Admin" -> UserRole.SCHOOL_ADMIN
            "Teacher" -> UserRole.TEACHER
            else -> UserRole.PARENT
        }

        val roleError = when (role) {
            UserRole.SCHOOL_ADMIN, UserRole.TEACHER -> {
                FormValidator.validateRequired(roleSpecificInput, "School Verification Code")
            }
            UserRole.PARENT -> {
                FormValidator.validatePhoneNumber(roleSpecificInput)
            }
            else -> null
        }

        val termsErr = if (!agreedToTerms) "You must agree to the Terms of Service" else null

        if (nameError != null || accountError != null || passError != null || confirmError != null || roleError != null || termsErr != null) {
            _uiState.update {
                it.copy(
                    signupErrors = SignupValidationErrors(
                        fullNameError = nameError,
                        accountInputError = accountError,
                        passwordError = passError,
                        confirmPasswordError = confirmError,
                        roleSpecificError = roleError,
                        termsError = termsErr
                    ),
                    feedbackMessage = "Please correct errors in the registration form."
                )
            }
            return
        }

        // Form validation passed.
        _uiState.update {
            it.copy(
                signupErrors = SignupValidationErrors(),
                targetRole = role,
                feedbackMessage = null,
                isAuthAvailable = false
            )
        }
    }

    fun clearFeedbackMessage() {
        _uiState.update { it.copy(feedbackMessage = null) }
    }

    fun clearLoginErrors() {
        _uiState.update { it.copy(loginErrors = LoginValidationErrors()) }
    }

    fun clearSignupErrors() {
        _uiState.update { it.copy(signupErrors = SignupValidationErrors()) }
    }
}
