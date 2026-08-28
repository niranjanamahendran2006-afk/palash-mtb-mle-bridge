package com.palash.mtbmle.ui.screens.splash

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.palash.mtbmle.ui.components.CosmicBackground
import com.palash.mtbmle.ui.theme.CosmicText

private val Violet = Color(0xFF7652D6)
private val VioletDeep = Color(0xFF4C329E)
private val SkyBlue = Color(0xFF5CB9E8)
private val SunnyYellow = Color(0xFFFFC857)
private val MintGreen = Color(0xFF75D6B0)
private val Coral = Color(0xFFF28B7B)

@Composable
fun SplashScreen(
    statusLabel: String = "Getting things ready",
    onTap: () -> Unit = {}
) {
    var hasTapped by remember { mutableStateOf(false) }
    val burstAlpha by animateFloatAsState(if (hasTapped) 1f else 0f, tween(180), label = "tap burst")
    val burstScale by animateFloatAsState(if (hasTapped) 1.18f else 1f, tween(220), label = "tap scale")
    LaunchedEffect(hasTapped) {
        if (hasTapped) {
            kotlinx.coroutines.delay(220)
            onTap()
        }
    }
    val animation = rememberInfiniteTransition(label = "splash animation")
    val logoScale by animation.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(1300, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "logo pulse"
    )
    val floatOffset by animation.animateFloat(
        initialValue = 0f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "floating accents"
    )

    CosmicBackground(accentColor = Violet) {
    Box(
        modifier = Modifier.fillMaxSize().clickable { if (!hasTapped) hasTapped = true },
        contentAlignment = Alignment.Center
    ) {
        DecorativeAccents(floatOffset, burstAlpha)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(132.dp)
                    .scale(logoScale * burstScale)
                    .clip(RoundedCornerShape(40.dp)),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawCircle(color = Violet.copy(alpha = 0.12f), radius = size.minDimension / 2)
                    drawCircle(
                        color = Violet,
                        radius = size.minDimension * 0.37f
                    )
                    drawCircle(
                        color = Color.White.copy(alpha = 0.3f),
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
                text = "Tap to explore",
                color = CosmicText.copy(alpha = 0.86f),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
    }
}

@Composable
private fun DecorativeAccents(floatOffset: Float, burstAlpha: Float) {
    Box(modifier = Modifier.size(280.dp)) {
        AccentDot(
            color = SkyBlue,
            modifier = Modifier.align(Alignment.TopStart).offset(x = 14.dp, y = (34 + floatOffset).dp).scale(1f + burstAlpha * 0.5f)
        )
        AccentDot(
            color = SunnyYellow,
            modifier = Modifier.align(Alignment.TopEnd).offset(x = (-22).dp, y = (12 - floatOffset).dp).scale(1f + burstAlpha * 0.5f)
        )
        AccentDot(
            color = MintGreen,
            modifier = Modifier.align(Alignment.BottomStart).offset(x = 36.dp, y = (-24 + floatOffset).dp).scale(1f + burstAlpha * 0.5f)
        )
        AccentDot(
            color = Coral,
            modifier = Modifier.align(Alignment.BottomEnd).offset(x = (-8).dp, y = (-44 - floatOffset).dp).scale(1f + burstAlpha * 0.5f)
        )
    }
}

@Composable
private fun AccentDot(color: Color, modifier: Modifier = Modifier) {
    Box(modifier = modifier.size(14.dp).clip(CircleShape).alpha(0.9f).then(Modifier)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(color = color)
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
                    .then(Modifier)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawCircle(color = Violet)
                }
            }
        }
    }
}
