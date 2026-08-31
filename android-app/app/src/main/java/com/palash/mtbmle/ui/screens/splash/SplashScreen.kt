package com.palash.mtbmle.ui.screens.splash

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.palash.mtbmle.ui.components.CosmicBackground
import com.palash.mtbmle.ui.components.CosmicOrbitSparkles
import com.palash.mtbmle.ui.components.CosmicTapBurst
import com.palash.mtbmle.ui.theme.CosmicText
import com.palash.mtbmle.ui.theme.CosmicViolet
import com.palash.mtbmle.ui.theme.CosmicMagenta
import com.palash.mtbmle.ui.theme.CosmicIndigo

/**
 * The user's entrance into the PALASH universe (roadmap Section 4).
 *
 * The splash remains on screen until the user taps it — [isReady] optionally gates that
 * transition on setup/initialization finishing in the background too (defaults to true,
 * so existing call sites that don't pass it behave exactly as before: tap-to-continue).
 */
@Composable
fun SplashScreen(
    statusLabel: String = "Getting things ready",
    isReady: Boolean = true,
    onTap: () -> Unit = {}
) {
    var hasTapped by remember { mutableStateOf(false) }
    val transitioning = hasTapped && isReady

    val burstScale by animateFloatAsState(if (transitioning) 1.16f else 1f, tween(260), label = "tap scale")
    val fadeOut by animateFloatAsState(if (transitioning) 0f else 1f, tween(320), label = "tap fade")

    LaunchedEffect(transitioning) {
        if (transitioning) {
            kotlinx.coroutines.delay(300)
            onTap()
        }
    }

    val animation = rememberInfiniteTransition(label = "splash animation")
    val logoPulse by animation.animateFloat(
        initialValue = 0.97f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "logo pulse"
    )
    val glowPulse by animation.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "logo glow pulse"
    )
    val promptAlpha by animation.animateFloat(
        initialValue = 0.55f,
        targetValue = 0.95f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "tap prompt pulse"
    )

    CosmicBackground(
        accentColor = CosmicViolet,
        secondaryAccent = CosmicMagenta,
        tertiaryAccent = CosmicIndigo
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .alpha(fadeOut)
                .clickable(enabled = !hasTapped) { hasTapped = true },
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier.size(280.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Sparkles drifting slowly around the branding, folded into the galaxy
                    // rather than sitting on top of it as flat decoration.
                    CosmicOrbitSparkles(accentColor = CosmicViolet, modifier = Modifier.fillMaxSize())
                    CosmicTapBurst(
                        triggered = hasTapped,
                        accentColor = CosmicViolet,
                        modifier = Modifier.fillMaxSize()
                    )

                    Box(
                        modifier = Modifier
                            .size(132.dp)
                            .scale(logoPulse * burstScale)
                            .clip(RoundedCornerShape(40.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            drawCircle(
                                brush = Brush.radialGradient(
                                    listOf(CosmicViolet.copy(alpha = glowPulse), Color.Transparent)
                                ),
                                radius = size.minDimension * 0.62f
                            )
                            drawCircle(
                                brush = Brush.radialGradient(
                                    listOf(CosmicViolet, CosmicMagenta.copy(alpha = 0.85f))
                                ),
                                radius = size.minDimension * 0.37f
                            )
                            drawCircle(
                                color = Color.White.copy(alpha = 0.32f),
                                radius = size.minDimension * 0.37f,
                                style = Stroke(width = 3.dp.toPx())
                            )
                        }
                        Text(
                            text = "P",
                            color = Color.White,
                            style = MaterialTheme.typography.displayMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "PALASH",
                    color = CosmicText,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Learn. Understand. Explore.",
                    color = CosmicText.copy(alpha = 0.72f),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(28.dp))
                LoadingDots()
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = statusLabel,
                    color = CosmicText.copy(alpha = 0.58f),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Tap to explore ✨",
                    color = CosmicText.copy(alpha = promptAlpha),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Composable
private fun LoadingDots() {
    val animation = rememberInfiniteTransition(label = "loading dots")
    Row(
        horizontalArrangement = Arrangement.spacedBy(7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(3) { index ->
            val alpha by animation.animateFloat(
                initialValue = 0.35f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(700, delayMillis = index * 140),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "loading dot $index"
            )
            Box(
                modifier = Modifier
                    .width(8.dp)
                    .height(8.dp)
                    .alpha(alpha)
                    .clip(CircleShape)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawCircle(
                        brush = Brush.radialGradient(listOf(CosmicViolet, CosmicMagenta))
                    )
                }
            }
        }
    }
}