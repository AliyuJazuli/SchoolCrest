package com.hydev.schoolcrest

import android.graphics.drawable.Animatable
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hydev.schoolcrest.core.theme.ThemeState
import com.hydev.schoolcrest.feature.parent.dashboard.ParentDashboardScreen
import com.hydev.schoolcrest.feature.platform_owner.dashboard.PlatformOwnerDashboardScreen
import com.hydev.schoolcrest.feature.school_admin.dashboard.SchoolAdminDashboardScreen
import com.hydev.schoolcrest.feature.teacher.dashboard.TeacherDashboardScreen
import com.hydev.schoolcrest.ui.theme.SchoolCrestTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeMode by ThemeState.themeMode.collectAsState()
            SchoolCrestTheme(themeMode = themeMode) {
                var showSplash by remember { mutableStateOf(true) }
                LaunchedEffect(Unit) {
                    delay(2200L)
                    showSplash = false
                }

                if (showSplash) {
                    SchoolCrestSplashScreen()
                } else {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
private fun SchoolCrestSplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            factory = { context ->
                ImageView(context).apply {
                    scaleType = ImageView.ScaleType.FIT_CENTER
                    setImageDrawable(
                        ContextCompat.getDrawable(context, R.drawable.animated_schoolcrest_logo)
                    )
                    post { (drawable as? Animatable)?.start() }
                }
            },
            modifier = Modifier.size(184.dp)
        )
    }
}

@Composable
fun MainScreen(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.OnboardingScreen.route,
        enterTransition = { fadeIn(animationSpec = tween(350)) + slideInHorizontally(initialOffsetX = { it / 3 }) },
        exitTransition = { fadeOut(animationSpec = tween(350)) + slideOutHorizontally(targetOffsetX = { -it / 3 }) },
        popEnterTransition = { fadeIn(animationSpec = tween(350)) + slideInHorizontally(initialOffsetX = { -it / 3 }) },
        popExitTransition = { fadeOut(animationSpec = tween(350)) + slideOutHorizontally(targetOffsetX = { it / 3 }) },
        builder = {
            composable(Screen.HomeScreen.route){
                HomeScreen(navController = navController)
            }
            composable(Screen.OnboardingScreen.route){
                OnboardingFlowScreen(navController = navController)
            }
            composable(Screen.SecondOnboardingScreen.route){
                SecondOnboardingScreen(navController = navController)
            }
            composable(Screen.ThirdOnboardingScreen.route){
                ThirdOnboardingScreen(navController = navController)
            }
            composable(Screen.LoginScreen.route){
                LoginScreen(navController = navController)
            }
            composable(Screen.SignupScreen.route){
                SignupScreen(navController = navController)
            }
            composable(Screen.SignupOrLoginScreen.route){
                SignUpOrLoginScreen(navController = navController)
            }
            composable(Screen.ForgotPasswordScreen.route){
                ForgotPasswordScreen(navController = navController)
            }
            composable(Screen.PlatformOwnerDashboard.route){
                PlatformOwnerDashboardScreen(navController = navController)
            }
            composable(Screen.SchoolAdminDashboard.route){
                SchoolAdminDashboardScreen(navController = navController)
            }
            composable(Screen.TeacherDashboard.route){
                TeacherDashboardScreen(navController = navController)
            }
            composable(Screen.ParentDashboard.route){
                ParentDashboardScreen(navController = navController)
            }
        }
    )
}
