package com.hydev.schoolcrest

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

/** Kept for existing deep links; the account choice is now onboarding page three. */
@Composable
fun SignUpOrLoginScreen(navController: NavController) {
    OnboardingFlowScreen(navController)
}
