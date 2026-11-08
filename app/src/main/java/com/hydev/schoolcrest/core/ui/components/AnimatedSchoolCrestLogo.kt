package com.hydev.schoolcrest.core.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hydev.schoolcrest.R
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * Premium animated SchoolCrest logo component featuring:
 * 1. Smooth Fade + Scale Reveal
 * 2. Shape-Clipped Light Sweep Across Surface
 * 3. Soft Radial Ambient Glow
 * 4. Continuous Gentle Breathing Effect
 */
@Composable
fun AnimatedSchoolCrestLogo(
    modifier: Modifier = Modifier,
    size: Dp = 90.dp,
    isAnimated: Boolean = true
) {
    // 1. Reveal Animation (Scale-up + Fade-in)
    val revealAlpha = remember { Animatable(if (isAnimated) 0f else 1f) }
    val revealScale = remember { Animatable(if (isAnimated) 0.85f else 1f) }

    // 2. Light Sweep Animation Progress
    val sweepProgress = remember { Animatable(0f) }

    // 3. Gentle Breathing Loop
    val infiniteTransition = rememberInfiniteTransition(label = "breathing")
    val breathingScale by infiniteTransition.animateFloat(
        initialValue = 0.98f,
        targetValue = 1.02f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "breathingScale"
    )

    LaunchedEffect(isAnimated) {
        if (isAnimated) {
            // Step 1: Reveal opacity and scale together, then sweep the crest.
            coroutineScope {
                launch {
                    revealAlpha.animateTo(1f, animationSpec = tween(600, easing = FastOutSlowInEasing))
                }
                launch {
                    revealScale.animateTo(1f, animationSpec = tween(600, easing = FastOutSlowInEasing))
                }
            }

            // Step 2: Light sweep across the fully revealed logo.
            sweepProgress.animateTo(1f, animationSpec = tween(1100, easing = LinearEasing))
        }
    }

    val currentScale = if (isAnimated) revealScale.value * breathingScale else 1f

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        // 4. Soft Radial Ambient Background Glow
        val glowAlpha = if (sweepProgress.value > 0f && sweepProgress.value < 1f) {
            0.38f + (0.32f * (1f - kotlin.math.abs(sweepProgress.value - 0.5f) * 2f))
        } else {
            0.22f
        }

        Box(
            modifier = Modifier
                .size(size * 1.25f)
                .graphicsLayer {
                    alpha = glowAlpha
                    scaleX = currentScale
                    scaleY = currentScale
                }
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.55f),
                            MaterialTheme.colorScheme.secondary.copy(alpha = 0.25f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        // Logo Image with Shape-Clipped Light Sweep
        Image(
            painter = painterResource(id = R.drawable.schoolcrest_logo),
            contentDescription = "SchoolCrest Logo",
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    alpha = revealAlpha.value
                    scaleX = currentScale
                    scaleY = currentScale
                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .drawWithContent {
                    drawContent()

                    // Apply Light Sweep overlay if sweep is active
                    if (sweepProgress.value > 0f && sweepProgress.value < 1f) {
                        val strokeWidth = size.toPx() * 0.4f
                        val currentOffset = (size.toPx() * 2.2f) * sweepProgress.value - size.toPx() * 0.6f

                        drawRect(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.White.copy(alpha = 0.7f),
                                    Color.Transparent
                                ),
                                start = Offset(currentOffset - strokeWidth, currentOffset - strokeWidth),
                                end = Offset(currentOffset + strokeWidth, currentOffset + strokeWidth)
                            ),
                            blendMode = BlendMode.SrcAtop
                        )
                    }
                }
        )
    }
}
