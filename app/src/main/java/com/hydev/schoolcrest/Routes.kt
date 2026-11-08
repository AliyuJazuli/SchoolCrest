package com.hydev.schoolcrest

sealed class Screen(val route: String){

    object HomeScreen: Screen("homeScreen")

    object OnboardingScreen: Screen("onboardingScreen")

    object SecondOnboardingScreen: Screen("secondOnboardingScreen")

    object ThirdOnboardingScreen: Screen("thirdOnboardingScreen")

    object LoginScreen: Screen("loginScreen")

    object SignupScreen: Screen("SignupScreen")

    object SignupOrLoginScreen: Screen("SignupOrLogin")

    object ForgotPasswordScreen: Screen("forgotPasswordScreen")

    object PlatformOwnerDashboard: Screen("platformOwnerDashboard")

    object SchoolAdminDashboard: Screen("schoolAdminDashboard")

    object TeacherDashboard: Screen("teacherDashboard")

    object ParentDashboard: Screen("parentDashboard")
}
