package com.hydev.schoolcrest

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hydev.schoolcrest.core.model.UserRole
import com.hydev.schoolcrest.core.ui.components.AuthContactMethodSelector
import com.hydev.schoolcrest.core.ui.components.AuthProgressIndicator
import com.hydev.schoolcrest.core.ui.components.AuthRoleSelector
import com.hydev.schoolcrest.core.util.FormValidator
import com.hydev.schoolcrest.feature.authentication.AuthViewModel

private val signUpRoles = listOf(
    UserRole.PARENT,
    UserRole.SCHOOL_ADMIN,
    UserRole.TEACHER
)

@Composable
fun SignupScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    var step by remember { mutableIntStateOf(1) }
    var selectedRole by remember { mutableStateOf(UserRole.PARENT) }
    var selectedTab by remember { mutableIntStateOf(0) }
    var fullName by remember { mutableStateOf("") }
    var accountInput by remember { mutableStateOf("") }
    var verificationCode by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var roleSpecificInput by remember { mutableStateOf("") }
    var agreedToTerms by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }
    var codeSent by remember { mutableStateOf(false) }
    var fullNameError by remember { mutableStateOf<String?>(null) }
    var accountError by remember { mutableStateOf<String?>(null) }
    var verificationError by remember { mutableStateOf<String?>(null) }
    var roleSpecificError by remember { mutableStateOf<String?>(null) }

    val uiState by authViewModel.uiState.collectAsState()
    val errors = uiState.signupErrors

    val screenTitle = when (step) {
        1 -> "Create your account"
        2 -> "Verify your contact"
        3 -> if (selectedRole == UserRole.PARENT) "Link your school contact" else "School verification"
        else -> "Secure your account"
    }
    val screenDescription = when (step) {
        1 -> "Choose your role, then enter your name and preferred contact."
        2 -> "Confirm the email address or phone number you entered."
        3 -> if (selectedRole == UserRole.PARENT) {
            "Enter the parent phone number used by your school."
        } else {
            "Enter the verification code provided by your school."
        }
        else -> "Create a password and accept the terms to finish."
    }

    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .imePadding()
        ) {
            val compactLayout = maxHeight < 900.dp
            val allowScrolling = imeVisible || maxHeight < 780.dp

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .then(
                        if (allowScrolling) Modifier.verticalScroll(rememberScrollState())
                        else Modifier
                    )
                    .padding(horizontal = if (compactLayout) 20.dp else 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = if (compactLayout) 12.dp else 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f))
                            .clickable {
                                if (step > 1) step-- else navController.popBackStack()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = if (step > 1) "Previous step" else "Back",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.schoolcrest_logo),
                    contentDescription = "SchoolCrest Logo",
                    modifier = Modifier.size(if (compactLayout) 56.dp else 60.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "SchoolCrest",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = screenTitle,
                    fontSize = if (compactLayout) 24.sp else 25.sp,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(if (compactLayout) 5.dp else 6.dp))
                Text(
                    text = screenDescription,
                    fontSize = if (compactLayout) 13.sp else 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(if (compactLayout) 10.dp else 14.dp))
                AuthProgressIndicator(step = step, totalSteps = 4, compact = compactLayout)
                Spacer(modifier = Modifier.height(if (compactLayout) 10.dp else 14.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(if (compactLayout) 16.dp else 20.dp),
                        verticalArrangement = Arrangement.spacedBy(if (compactLayout) 9.dp else 12.dp)
                    ) {
                        when (step) {
                            1 -> {
                                Text(
                                    "I am a...",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                AuthRoleSelector(
                                    roles = signUpRoles,
                                    selectedRole = selectedRole,
                                    compact = compactLayout,
                                    onRoleSelected = {
                                        selectedRole = it
                                        roleSpecificInput = ""
                                        roleSpecificError = null
                                        verificationCode = ""
                                        verificationError = null
                                        codeSent = false
                                    }
                                )

                                AuthContactMethodSelector(
                                    isEmailSelected = selectedTab == 0,
                                    onEmailSelected = {
                                        if (selectedTab != 0) {
                                            selectedTab = 0
                                            accountInput = ""
                                            accountError = null
                                            verificationCode = ""
                                            verificationError = null
                                            codeSent = false
                                        }
                                    },
                                    onPhoneSelected = {
                                        if (selectedTab != 1) {
                                            selectedTab = 1
                                            accountInput = ""
                                            accountError = null
                                            verificationCode = ""
                                            verificationError = null
                                            codeSent = false
                                        }
                                    }
                                )
                                OutlinedTextField(
                                    value = fullName,
                                    onValueChange = {
                                        fullName = it
                                        fullNameError = null
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    label = { Text("Full Name") },
                                    placeholder = { Text("John Doe") },
                                    leadingIcon = {
                                        Icon(Icons.Default.Person, contentDescription = null)
                                    },
                                    trailingIcon = {
                                        if (fullName.isNotEmpty()) {
                                            IconButton(onClick = { fullName = "" }) {
                                                Icon(Icons.Default.Clear, contentDescription = "Clear name")
                                            }
                                        }
                                    },
                                    singleLine = true,
                                    isError = fullNameError != null,
                                    shape = RoundedCornerShape(14.dp),
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Next
                                    )
                                )
                                FieldError(fullNameError)
                                OutlinedTextField(
                                    value = accountInput,
                                    onValueChange = {
                                        accountInput = it
                                        accountError = null
                                        verificationCode = ""
                                        verificationError = null
                                        codeSent = false
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    label = {
                                        Text(if (selectedTab == 0) "Email Address" else "Phone Number")
                                    },
                                    placeholder = {
                                        Text(if (selectedTab == 0) "example@school.com" else "+234 801 234 5678")
                                    },
                                    leadingIcon = {
                                        Icon(
                                            if (selectedTab == 0) Icons.Default.Email else Icons.Default.Phone,
                                            contentDescription = null
                                        )
                                    },
                                    trailingIcon = {
                                        if (accountInput.isNotEmpty()) {
                                            IconButton(
                                                onClick = {
                                                    accountInput = ""
                                                    accountError = null
                                                    verificationCode = ""
                                                    verificationError = null
                                                    codeSent = false
                                                }
                                            ) {
                                                Icon(
                                                    Icons.Default.Clear,
                                                    contentDescription = "Clear contact"
                                                )
                                            }
                                        }
                                    },
                                    singleLine = true,
                                    isError = accountError != null,
                                    shape = RoundedCornerShape(14.dp),
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = if (selectedTab == 0) KeyboardType.Email else KeyboardType.Phone,
                                        imeAction = ImeAction.Done
                                    )
                                )
                                FieldError(accountError)
                                FormHint("Use an email address or phone number you can access.")
                            }

                            2 -> {
                                Text(
                                    text = "Verify contact: $accountInput",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                OutlinedTextField(
                                    value = verificationCode,
                                    onValueChange = {
                                        verificationCode = it.filter(Char::isDigit).take(6)
                                        verificationError = null
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    label = { Text("6-digit verification code") },
                                    leadingIcon = {
                                        Icon(Icons.Default.CheckCircle, contentDescription = null)
                                    },
                                    singleLine = true,
                                    isError = verificationError != null,
                                    shape = RoundedCornerShape(14.dp),
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number,
                                        imeAction = ImeAction.Done
                                    )
                                )
                                FieldError(verificationError)
                                OutlinedButton(
                                    onClick = { codeSent = true },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text(if (codeSent) "Send code again" else "Send verification code")
                                }
                                FormHint("Enter the 6-digit code sent to your contact.")
                                TextButton(
                                    onClick = {
                                        step = 1
                                        verificationError = null
                                    },
                                    modifier = Modifier.align(Alignment.Start)
                                ) {
                                    Text("Change email or phone")
                                }
                            }

                            3 -> {
                                val isSchoolRole = selectedRole != UserRole.PARENT
                                val fieldLabel = if (isSchoolRole) {
                                    "School Verification Code"
                                } else {
                                    "Parent Phone Number"
                                }
                                OutlinedTextField(
                                    value = roleSpecificInput,
                                    onValueChange = {
                                        roleSpecificInput = it
                                        roleSpecificError = null
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    label = { Text(fieldLabel) },
                                    placeholder = {
                                        Text(if (isSchoolRole) "e.g. SCH-90210" else "+234 801 234 5678")
                                    },
                                    leadingIcon = {
                                        Icon(
                                            if (isSchoolRole) Icons.Default.School else Icons.Default.Phone,
                                            contentDescription = null
                                        )
                                    },
                                    singleLine = true,
                                    isError = roleSpecificError != null,
                                    shape = RoundedCornerShape(14.dp),
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = if (isSchoolRole) KeyboardType.Text else KeyboardType.Phone,
                                        imeAction = ImeAction.Done
                                    )
                                )
                                FieldError(roleSpecificError)
                                FormHint(
                                    if (isSchoolRole) {
                                        "Use the code provided by your school."
                                    } else {
                                        "Use the phone number linked to your child's school record."
                                    }
                                )
                            }

                            else -> {
                                PasswordField(
                                    value = password,
                                    label = "Password",
                                    visible = isPasswordVisible,
                                    onVisibilityChange = { isPasswordVisible = it },
                                    onValueChange = {
                                        password = it
                                        authViewModel.clearSignupErrors()
                                    },
                                    isError = errors.passwordError != null,
                                    imeAction = ImeAction.Next
                                )
                                FieldError(errors.passwordError)
                                PasswordField(
                                    value = confirmPassword,
                                    label = "Confirm Password",
                                    visible = isConfirmPasswordVisible,
                                    onVisibilityChange = { isConfirmPasswordVisible = it },
                                    onValueChange = {
                                        confirmPassword = it
                                        authViewModel.clearSignupErrors()
                                    },
                                    isError = errors.confirmPasswordError != null,
                                    imeAction = ImeAction.Done
                                )
                                FieldError(errors.confirmPasswordError)
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = agreedToTerms,
                                        onCheckedChange = {
                                            agreedToTerms = it
                                            authViewModel.clearSignupErrors()
                                        },
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = MaterialTheme.colorScheme.primary
                                        )
                                    )
                                    Text(
                                        text = "I agree to the Terms of Service & Privacy Policy",
                                        fontSize = 12.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                                FieldError(errors.termsError)
                                FormHint("Choose a password to secure your account and accept the terms.")
                            }
                        }

                        Spacer(modifier = Modifier.height(if (compactLayout) 0.dp else 4.dp))
                        Button(
                            onClick = {
                                when (step) {
                                    1 -> {
                                        fullNameError = FormValidator.validateRequired(fullName, "Full Name")
                                        accountError = if (selectedTab == 0) {
                                            FormValidator.validateEmail(accountInput)
                                        } else {
                                            FormValidator.validatePhoneNumber(accountInput)
                                        }
                                        if (fullNameError == null && accountError == null) step = 2
                                    }

                                    2 -> {
                                        if (codeSent && verificationCode.length == 6) {
                                            verificationError = null
                                            step = 3
                                        } else if (!codeSent) {
                                            verificationError = "Select “Send verification code” before continuing."
                                        } else {
                                            verificationError = "Enter all 6 digits of the verification code."
                                        }
                                    }

                                    3 -> {
                                        roleSpecificError = if (selectedRole == UserRole.PARENT) {
                                            FormValidator.validatePhoneNumber(roleSpecificInput)
                                        } else {
                                            FormValidator.validateRequired(
                                                roleSpecificInput,
                                                "School Verification Code"
                                            )
                                        }
                                        if (roleSpecificError == null) step = 4
                                    }

                                    else -> {
                                        authViewModel.validateAndRegister(
                                            fullName = fullName,
                                            accountInput = accountInput,
                                            password = password,
                                            confirmPassword = confirmPassword,
                                            selectedRoleStr = selectedRole.displayName,
                                            roleSpecificInput = roleSpecificInput,
                                            isEmailTab = selectedTab == 0,
                                            agreedToTerms = agreedToTerms
                                        )
                                        if (authViewModel.uiState.value.signupErrors.isValid()) {
                                            navController.navigate(selectedRole.dashboardRoute()) {
                                                popUpTo(Screen.SignupScreen.route) { inclusive = true }
                                            }
                                        }
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = if (step == 4) Icons.Default.PersonAdd else Icons.AutoMirrored.Filled.Login,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = when (step) {
                                        1 -> "Continue"
                                        2 -> "Verify and continue"
                                        3 -> "Continue"
                                        else -> "Create Account"
                                    },
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                    if (!allowScrolling) {
                        Spacer(modifier = Modifier.weight(1f))
                    } else {
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                    Row(
                        modifier = Modifier.padding(bottom = if (compactLayout) 12.dp else 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Already have an account? ",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Log in",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.clickable {
                                navController.navigate(Screen.LoginScreen.route)
                            }
                        )
                    }
                }
            }
    }
}

@Composable
private fun PasswordField(
    value: String,
    label: String,
    visible: Boolean,
    onVisibilityChange: (Boolean) -> Unit,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    imeAction: ImeAction
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label) },
        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
        trailingIcon = {
            IconButton(onClick = { onVisibilityChange(!visible) }) {
                Icon(
                    imageVector = if (visible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = if (visible) "Hide $label" else "Show $label"
                )
            }
        },
        singleLine = true,
        isError = isError,
        shape = RoundedCornerShape(14.dp),
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
            focusedContainerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f),
            unfocusedContainerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Composable
private fun FieldError(message: String?) {
    if (message != null) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
private fun FormHint(message: String) {
    Text(
        text = message,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

private fun com.hydev.schoolcrest.feature.authentication.SignupValidationErrors.isValid(): Boolean =
    fullNameError == null &&
        accountInputError == null &&
        passwordError == null &&
        confirmPasswordError == null &&
        roleSpecificError == null &&
        termsError == null

private fun UserRole.dashboardRoute(): String = when (this) {
    UserRole.PLATFORM_OWNER -> Screen.PlatformOwnerDashboard.route
    UserRole.SCHOOL_ADMIN -> Screen.SchoolAdminDashboard.route
    UserRole.TEACHER -> Screen.TeacherDashboard.route
    UserRole.PARENT -> Screen.ParentDashboard.route
}
