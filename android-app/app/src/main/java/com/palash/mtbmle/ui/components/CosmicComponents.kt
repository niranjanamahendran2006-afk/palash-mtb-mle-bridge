package com.palash.mtbmle.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.sin
import com.palash.mtbmle.ui.theme.CosmicCard
import com.palash.mtbmle.ui.theme.CosmicMidnight
import com.palash.mtbmle.ui.theme.CosmicNavy
import com.palash.mtbmle.ui.theme.CosmicText
import com.palash.mtbmle.ui.theme.CosmicTextMuted

private data class Star(val x: Float, val y: Float, val radius: Float, val alpha: Float, val phase: Float)

private val Stars = listOf(
    Star(0.08f, 0.14f, 1.5f, 0.62f, 0.2f), Star(0.22f, 0.08f, 1f, 0.42f, 1.8f),
    Star(0.39f, 0.2f, 1.2f, 0.52f, 3.1f), Star(0.58f, 0.1f, 1.7f, 0.5f, 4.5f),
    Star(0.82f, 0.16f, 1f, 0.58f, 5.4f), Star(0.94f, 0.31f, 1.4f, 0.46f, 2.7f),
    Star(0.13f, 0.42f, 1f, 0.5f, 0.9f), Star(0.31f, 0.52f, 1.4f, 0.38f, 4.1f),
    Star(0.68f, 0.44f, 1f, 0.48f, 1.4f), Star(0.88f, 0.58f, 1.5f, 0.42f, 5.8f),
    Star(0.06f, 0.76f, 1.2f, 0.5f, 3.8f), Star(0.27f, 0.88f, 1f, 0.4f, 0.5f),
    Star(0.5f, 0.72f, 1.5f, 0.5f, 2.2f), Star(0.76f, 0.84f, 1.1f, 0.46f, 4.9f),
    Star(0.96f, 0.9f, 1.3f, 0.4f, 1.1f), Star(0.17f, 0.68f, 0.8f, 0.38f, 3.6f),
    Star(0.45f, 0.36f, 0.7f, 0.35f, 5.1f), Star(0.73f, 0.63f, 0.8f, 0.4f, 2.6f),
    Star(0.55f, 0.91f, 0.9f, 0.34f, 0.7f), Star(0.35f, 0.07f, 0.7f, 0.42f, 4.3f)
)

private val Dust = listOf(
    0.11f to 0.28f, 0.18f to 0.58f, 0.26f to 0.31f, 0.34f to 0.78f,
    0.42f to 0.13f, 0.48f to 0.62f, 0.57f to 0.3f, 0.63f to 0.76f,
    0.71f to 0.22f, 0.79f to 0.49f, 0.86f to 0.74f, 0.92f to 0.12f,
    0.23f to 0.93f, 0.66f to 0.93f, 0.04f to 0.52f, 0.97f to 0.45f
)

@Composable
fun CosmicBackground(
    accentColor: Color,
    secondaryAccent: Color = accentColor,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val motion = rememberInfiniteTransition(label = "nebula motion")
    val nebulaPhase by motion.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(12000), RepeatMode.Reverse),
        label = "nebula breathing"
    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(accentColor.copy(alpha = 0.16f), CosmicNavy, CosmicMidnight),
                    radius = 900f
                )
            )
    ) {
        GalaxyCanvas(accentColor = accentColor, secondaryAccent = secondaryAccent, phase = nebulaPhase)
        content()
    }
}

@Composable
private fun GalaxyCanvas(
    accentColor: Color,
    secondaryAccent: Color,
    phase: Float,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        val drift = sin(phase * PI * 2).toFloat()
        val cloud = accentColor.copy(alpha = 0.2f)

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(cloud.copy(alpha = 0.34f), cloud.copy(alpha = 0.12f), Color.Transparent),
                radius = width * 0.72f
            ),
            radius = width * 0.72f,
            center = Offset(width * (0.38f + drift * 0.035f), height * 0.34f)
        )
        drawOval(
            brush = Brush.radialGradient(
                colors = listOf(accentColor.copy(alpha = 0.18f), accentColor.copy(alpha = 0.07f), Color.Transparent),
                radius = width * 0.62f
            ),
            topLeft = Offset(width * (0.04f + drift * 0.02f), height * 0.1f),
            size = androidx.compose.ui.geometry.Size(width * 0.92f, height * 0.5f)
        )
        drawOval(
            brush = Brush.radialGradient(
                colors = listOf(secondaryAccent.copy(alpha = 0.14f), accentColor.copy(alpha = 0.07f), Color.Transparent),
                radius = width * 0.5f
            ),
            topLeft = Offset(width * 0.28f, height * 0.52f),
            size = androidx.compose.ui.geometry.Size(width * 0.82f, height * 0.38f)
        )
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color.White.copy(alpha = 0.08f), Color.Transparent),
                radius = width * 0.28f
            ),
            radius = width * 0.28f,
            center = Offset(width * 0.5f, height * 0.43f)
        )

        Dust.forEachIndexed { index, (x, y) ->
            val dustDrift = sin(phase * PI * 2 + index).toFloat() * 5.dp.toPx()
            drawCircle(
                color = accentColor.copy(alpha = 0.1f + (index % 3) * 0.035f),
                radius = (1.2f + index % 3) * density,
                center = Offset(width * x + dustDrift, height * y - phase * 8.dp.toPx())
            )
        }
        Stars.forEach { star ->
            val twinkle = (sin(star.phase + phase * PI * 2).toFloat() + 1f) / 2f
            drawCircle(
                color = Color.White.copy(alpha = star.alpha * (0.65f + twinkle * 0.35f)),
                radius = star.radius,
                center = Offset(width * star.x, height * star.y)
            )
            if (star.radius > 1.3f) {
                drawCircle(
                    color = accentColor.copy(alpha = 0.08f + twinkle * 0.08f),
                    radius = star.radius * 4f,
                    center = Offset(width * star.x, height * star.y)
                )
            }
        }
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color.White.copy(alpha = 0.95f), accentColor.copy(alpha = 0.32f), Color.Transparent),
                radius = 16.dp.toPx()
            ),
            radius = 16.dp.toPx(),
            center = Offset(width * 0.76f, height * 0.28f)
        )
    }
}

@Composable
fun StarField(accentColor: Color, modifier: Modifier = Modifier) {
    GalaxyCanvas(accentColor = accentColor, secondaryAccent = accentColor, phase = 0f, modifier = modifier)
}

@Composable
fun CosmicGlowCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .scale(if (pressed) 0.97f else 1f)
            .background(accentColor.copy(alpha = if (pressed) 0.16f else 0.09f), RoundedCornerShape(24.dp))
            .border(1.dp, accentColor.copy(alpha = if (pressed) 0.9f else 0.42f), RoundedCornerShape(24.dp))
            .clickable(interactionSource = interactionSource, indication = null, onClick = onClick)
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = title, tint = accentColor, modifier = Modifier.size(36.dp))
            Spacer(Modifier.weight(1f))
            Text("›", color = accentColor, style = MaterialTheme.typography.headlineMedium)
        }
        Text(title, color = CosmicText, style = MaterialTheme.typography.titleLarge)
        Text(subtitle, color = CosmicTextMuted, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun CosmicButton(
    text: String,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .scale(if (pressed) 0.97f else 1f)
            .alpha(if (enabled) 1f else 0.45f)
            .background(accentColor.copy(alpha = 0.18f), RoundedCornerShape(18.dp))
            .border(1.dp, accentColor.copy(alpha = 0.72f), RoundedCornerShape(18.dp))
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = CosmicText, style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
fun CosmicPanel(
    accentColor: Color,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(CosmicCard.copy(alpha = 0.9f), RoundedCornerShape(20.dp))
            .border(1.dp, accentColor.copy(alpha = 0.28f), RoundedCornerShape(20.dp))
            .padding(16.dp),
        content = content
    )
}
