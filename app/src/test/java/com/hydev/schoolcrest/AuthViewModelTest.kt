package com.hydev.schoolcrest

import com.hydev.schoolcrest.feature.authentication.AuthViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class AuthViewModelTest {

    private lateinit var viewModel: AuthViewModel

    @Before
    fun setUp() {
        viewModel = AuthViewModel()
    }

    @Test
    fun validateAndLogin_invalidEmail_setsAccountInputError() {
        viewModel.validateAndLogin("invalid-email", "password123", isEmailTab = true)
        val state = viewModel.uiState.value

        assertNotNull(state.loginErrors.accountInputError)
        assertEquals("Invalid email address format", state.loginErrors.accountInputError)
    }

    @Test
    fun validateAndLogin_validEmailAndPass_clearsLoginErrors() {
        viewModel.validateAndLogin("user@school.com", "password123", isEmailTab = true)
        val state = viewModel.uiState.value

        assertNull(state.loginErrors.accountInputError)
        assertNull(state.loginErrors.passwordError)
        assertNull(state.feedbackMessage)
    }

    @Test
    fun validateAndLogin_nonEmptyShortPassword_isAccepted() {
        viewModel.validateAndLogin("user@school.com", "x", isEmailTab = true)
        val state = viewModel.uiState.value

        assertNull(state.loginErrors.accountInputError)
        assertNull(state.loginErrors.passwordError)
        assertNull(state.feedbackMessage)
    }

    @Test
    fun validateAndLogin_malformedPhone_setsAccountInputError() {
        viewModel.validateAndLogin("phone-not-valid", "x", isEmailTab = false)

        assertEquals(
            "Invalid phone number format",
            viewModel.uiState.value.loginErrors.accountInputError
        )
    }

    @Test
    fun validateAndLogin_nigerianPhoneAndShortPassword_areAcceptedForUiDemo() {
        viewModel.validateAndLogin("+234 801 234 5678", "x", isEmailTab = false)
        val state = viewModel.uiState.value

        assertNull(state.loginErrors.accountInputError)
        assertNull(state.loginErrors.passwordError)
    }

    @Test
    fun validateAndRegister_unacceptedTerms_setsTermsError() {
        viewModel.validateAndRegister(
            fullName = "John Doe",
            accountInput = "john@school.com",
            password = "password123",
            confirmPassword = "password123",
            selectedRoleStr = "Parent",
            roleSpecificInput = "08012345678",
            isEmailTab = true,
            agreedToTerms = false
        )
        val state = viewModel.uiState.value

        assertNotNull(state.signupErrors.termsError)
        assertEquals("You must agree to the Terms of Service", state.signupErrors.termsError)
    }

    @Test
    fun validateAndRegister_missingSchoolCodeForTeacher_setsRoleSpecificError() {
        viewModel.validateAndRegister(
            fullName = "Teacher Jane",
            accountInput = "jane@school.com",
            password = "password123",
            confirmPassword = "password123",
            selectedRoleStr = "Teacher",
            roleSpecificInput = "", // Missing school code
            isEmailTab = true,
            agreedToTerms = true
        )
        val state = viewModel.uiState.value

        assertNotNull(state.signupErrors.roleSpecificError)
        assertEquals("School Verification Code is required", state.signupErrors.roleSpecificError)
    }
}
