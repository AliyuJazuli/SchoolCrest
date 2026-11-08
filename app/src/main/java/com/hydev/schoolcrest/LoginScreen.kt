package com.hydev.schoolcrest

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FilterChip
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

private val signInRoles = listOf(
    UserRole.PARENT,
    UserRole.SCHOOL_ADMIN,
    UserRole.TEACHER,
    UserRole.PLATFORM_OWNER
)

@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    var step by remember { mutableIntStateOf(1) }
    var selectedRole by remember { mutableStateOf<UserRole?>(null) }
    var selectedTab by remember { mutableIntStateOf(0) }
    var accountInput by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var otpCode by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var useOneTimeCode by remember { mutableStateOf(false) }
    var codeSent by remember { mutableStateOf(false) }
    var accountError by remember { mutableStateOf<String?>(null) }
    var roleError by remember { mutableStateOf<String?>(null) }
    var credentialError by remember { mutableStateOf<String?>(null) }

    val uiState by authViewModel.uiState.collectAsState()
    val errors = uiState.loginErrors

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState())
                .imePadding()
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 45.dp),
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

            Spacer(modifier = Modifier.height(10.dp))
            Image(
                painter = painterResource(id = R.drawable.schoolcrest_logo),
                contentDescription = "SchoolCrest Logo",
                modifier = Modifier.size(76.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                "SchoolCrest",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = if (step == 1) "Welcome back" else "Your sign-in details",
                fontSize = 25.sp,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = if (step == 1) {
                    "Choose your portal and enter your email or phone."
                } else {
                    "Continue to the ${selectedRole?.displayName ?: "selected"} portal."
                },
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(18.dp))
            AuthProgressIndicator(step = step, totalSteps = 2)
            Spacer(modifier = Modifier.height(18.dp))

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
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (step == 1) {
                        Text(
                            "Choose your portal",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        AuthRoleSelector(
                            roles = signInRoles,
                            selectedRole = selectedRole,
                            onRoleSelected = {
                                selectedRole = it
                                roleError = null
                            }
                        )
                        if (roleError != null) {
                            FieldError(roleError)
                        }
                        AuthContactMethodSelector(
                            isEmailSelected = selectedTab == 0,
                            onEmailSelected = {
                                if (selectedTab != 0) {
                                    selectedTab = 0
                                    accountInput = ""
                                    accountError = null
                                    otpCode = ""
                                    codeSent = false
                                }
                            },
                            onPhoneSelected = {
                                if (selectedTab != 1) {
                                    selectedTab = 1
                                    accountInput = ""
                                    accountError = null
                                    otpCode = ""
                                    codeSent = false
                                }
                            }
                        )
                        OutlinedTextField(
                            value = accountInput,
                            onValueChange = {
                                accountInput = it
                                accountError = null
                                otpCode = ""
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
                                            otpCode = ""
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
                        FormHint("Choose the portal associated with your account.")
                    } else {
                        Text(
                            text = "${if (selectedTab == 0) "Email" else "Phone"}: $accountInput",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        TextButton(
                            onClick = {
                                step = 1
                                credentialError = null
                            },
                            modifier = Modifier.align(Alignment.Start)
                        ) {
                            Text("Change email or phone")
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            FilterChip(
                                selected = !useOneTimeCode,
                                onClick = {
                                    useOneTimeCode = false
                                    credentialError = null
                                    otpCode = ""
                                    codeSent = false
                                },
                                label = { Text("Password") },
                                modifier = Modifier.weight(1f)
                            )
                            FilterChip(
                                selected = useOneTimeCode,
                                onClick = {
                                    useOneTimeCode = true
                                    credentialError = null
                                    otpCode = ""
                                    codeSent = false
                                },
                                label = { Text("One-time code") },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        if (useOneTimeCode) {
                            OutlinedTextField(
                                value = otpCode,
                                onValueChange = {
                                    otpCode = it.filter(Char::isDigit).take(6)
                                    credentialError = null
                                },
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text("6-digit verification code") },
                                singleLine = true,
                                isError = credentialError != null,
                                shape = RoundedCornerShape(14.dp),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Done
                                )
                            )
                            OutlinedButton(
                                onClick = { codeSent = true },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(if (codeSent) "Send code again" else "Send verification code")
                            }
                            FormHint("Enter the 6-digit code sent to your contact.")
                        } else {
                            OutlinedTextField(
                                value = password,
                                onValueChange = {
                                    password = it
                                    credentialError = null
                                    authViewModel.clearLoginErrors()
                                },
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text("Password") },
                                leadingIcon = {
                                    Icon(Icons.Default.Lock, contentDescription = null)
                                },
                                trailingIcon = {
                                    IconButton(
                                        onClick = { isPasswordVisible = !isPasswordVisible }
                                    ) {
                                        Icon(
                                            imageVector = if (isPasswordVisible) {
                                                Icons.Default.Visibility
                                            } else {
                                                Icons.Default.VisibilityOff
                                            },
                                            contentDescription = if (isPasswordVisible) {
                                                "Hide password"
                                            } else {
                                                "Show password"
                                            }
                                        )
                                    }
                                },
                                singleLine = true,
                                isError = credentialError != null || errors.passwordError != null,
                                shape = RoundedCornerShape(14.dp),
                                visualTransformation = if (isPasswordVisible) {
                                    VisualTransformation.None
                                } else {
                                    PasswordVisualTransformation()
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Password,
                                    imeAction = ImeAction.Done
                                )
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(
                                        checked = rememberMe,
                                        onCheckedChange = { rememberMe = it },
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = MaterialTheme.colorScheme.primary
                                        )
                                    )
                                    Text("Remember me", fontSize = 13.sp)
                                }
                                Text(
                                    text = "Forgot Password?",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.clickable {
                                        navController.navigate(Screen.ForgotPasswordScreen.route)
                                    }
                                )
                            }
                            FormHint("Enter your account password.")
                        }
                        FieldError(credentialError ?: errors.passwordError)
                    }

                    Button(
                        onClick = {
                            if (step == 1) {
                                roleError = if (selectedRole == null) {
                                    "Choose the portal you want to preview."
                                } else {
                                    null
                                }
                                accountError = if (selectedTab == 0) {
                                    FormValidator.validateEmail(accountInput)
                                } else {
                                    FormValidator.validatePhoneNumber(accountInput)
                                }
                                if (roleError == null && accountError == null) step = 2
                            } else if (useOneTimeCode) {
                                if (codeSent && otpCode.length == 6) {
                                    credentialError = null
                                    openDashboard(navController, selectedRole!!)
                                } else if (!codeSent) {
                                    credentialError = "Select “Send verification code” before continuing."
                                } else {
                                    credentialError = "Enter all 6 digits of the verification code."
                                }
                            } else {
                                authViewModel.validateAndLogin(
                                    accountInput = accountInput,
                                    password = password,
                                    isEmailTab = selectedTab == 0
                                )
                                val latestState = authViewModel.uiState.value
                                if (
                                    latestState.loginErrors.accountInputError == null &&
                                    latestState.loginErrors.passwordError == null &&
                                    selectedRole != null
                                ) {
                                    openDashboard(navController, selectedRole!!)
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
                                imageVector = Icons.AutoMirrored.Filled.Login,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (step == 1) "Continue" else "Sign In",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(22.dp))
            Row(
                modifier = Modifier.padding(bottom = 30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "New to SchoolCrest? ",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Create account",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable {
                        navController.navigate(Screen.SignupScreen.route)
                    }
                )
            }
        }
    }
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

private fun openDashboard(navController: NavController, role: UserRole) {
    navController.navigate(role.dashboardRoute()) {
        popUpTo(Screen.LoginScreen.route) { inclusive = true }
    }
}

private fun UserRole.dashboardRoute(): String = when (this) {
    UserRole.PLATFORM_OWNER -> Screen.PlatformOwnerDashboard.route
    UserRole.SCHOOL_ADMIN -> Screen.SchoolAdminDashboard.route
    UserRole.TEACHER -> Screen.TeacherDashboard.route
    UserRole.PARENT -> Screen.ParentDashboard.route
}
