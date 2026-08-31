package com.palash.mtbmle.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin
import com.palash.mtbmle.ui.theme.CosmicCard
import com.palash.mtbmle.ui.theme.CosmicMidnight
import com.palash.mtbmle.ui.theme.CosmicNavy
import com.palash.mtbmle.ui.theme.CosmicText
import com.palash.mtbmle.ui.theme.CosmicTextMuted

/* =========================================================================================
 * GALAXY BACKGROUND SYSTEM
 *
 * Every PALASH screen shares one deep-space base, one star language and one cosmic-dust
 * language. Only the nebula's accent/secondary/tertiary colors change per screen, so the
 * whole app reads as "one universe, many regions" rather than reskinned per screen.
 * ========================================================================================= */

private const val TAU = (Math.PI * 2).toFloat()

// ---- Star field (deterministic — no per-star recomposition, drawn once per Canvas frame) ----

private data class CosmicStarSpec(val x: Float, val y: Float, val radius: Float, val alpha: Float, val phase: Float)

private val StarField = listOf(
    CosmicStarSpec(0.08f, 0.14f, 1.5f, 0.62f, 0.2f), CosmicStarSpec(0.22f, 0.08f, 1f, 0.42f, 1.8f),
    CosmicStarSpec(0.39f, 0.2f, 1.2f, 0.52f, 3.1f), CosmicStarSpec(0.58f, 0.1f, 1.7f, 0.5f, 4.5f),
    CosmicStarSpec(0.82f, 0.16f, 1f, 0.58f, 5.4f), CosmicStarSpec(0.94f, 0.31f, 1.4f, 0.46f, 2.7f),
    CosmicStarSpec(0.13f, 0.42f, 1f, 0.5f, 0.9f), CosmicStarSpec(0.31f, 0.52f, 1.4f, 0.38f, 4.1f),
    CosmicStarSpec(0.68f, 0.44f, 1f, 0.48f, 1.4f), CosmicStarSpec(0.88f, 0.58f, 1.5f, 0.42f, 5.8f),
    CosmicStarSpec(0.06f, 0.76f, 1.2f, 0.5f, 3.8f), CosmicStarSpec(0.27f, 0.88f, 1f, 0.4f, 0.5f),
    CosmicStarSpec(0.5f, 0.72f, 1.5f, 0.5f, 2.2f), CosmicStarSpec(0.76f, 0.84f, 1.1f, 0.46f, 4.9f),
    CosmicStarSpec(0.96f, 0.9f, 1.3f, 0.4f, 1.1f), CosmicStarSpec(0.17f, 0.68f, 0.8f, 0.38f, 3.6f),
    CosmicStarSpec(0.45f, 0.36f, 0.7f, 0.35f, 5.1f), CosmicStarSpec(0.73f, 0.63f, 0.8f, 0.4f, 2.6f),
    CosmicStarSpec(0.55f, 0.91f, 0.9f, 0.34f, 0.7f), CosmicStarSpec(0.35f, 0.07f, 0.7f, 0.42f, 4.3f),
    CosmicStarSpec(0.62f, 0.27f, 0.6f, 0.3f, 1.6f), CosmicStarSpec(0.9f, 0.06f, 0.9f, 0.36f, 3.3f),
    CosmicStarSpec(0.03f, 0.35f, 0.7f, 0.32f, 0.4f), CosmicStarSpec(0.41f, 0.62f, 0.6f, 0.28f, 5.6f),
    CosmicStarSpec(0.85f, 0.78f, 0.7f, 0.3f, 2.1f), CosmicStarSpec(0.24f, 0.3f, 0.6f, 0.3f, 4.7f)
)

// Handful of brighter "hero" stars with a soft bloom halo and an occasional twinkle.
private val BrightStars = listOf(
    CosmicStarSpec(0.78f, 0.22f, 2.1f, 0.9f, 0.6f),
    CosmicStarSpec(0.16f, 0.58f, 1.9f, 0.85f, 3.4f),
    CosmicStarSpec(0.58f, 0.82f, 1.7f, 0.8f, 5.0f)
)

private val CosmicDust = listOf(
    0.11f to 0.28f, 0.18f to 0.58f, 0.26f to 0.31f, 0.34f to 0.78f,
    0.42f to 0.13f, 0.48f to 0.62f, 0.57f to 0.3f, 0.63f to 0.76f,
    0.71f to 0.22f, 0.79f to 0.49f, 0.86f to 0.74f, 0.92f to 0.12f,
    0.23f to 0.93f, 0.66f to 0.93f, 0.04f to 0.52f, 0.97f to 0.45f,
    0.51f to 0.42f, 0.09f to 0.9f, 0.38f to 0.48f, 0.81f to 0.9f
)

// ---- Organic nebula geometry ----
// Deliberately NOT circles: each blob is an irregular, smoothed polygon so the nebula reads
// as atmospheric cloud rather than "colored dots". Layout (position/size/shape) is shared
// across screens for a consistent "universe language" — only the fill colors change.

private data class NebulaBlob(
    val cxFrac: Float,
    val cyFrac: Float,
    val radiusFrac: Float,
    val points: Int,
    val irregularity: Float,
    val seed: Float,
    val driftFrac: Float,
    val strength: Float
)

private val NebulaLayout = listOf(
    NebulaBlob(0.28f, 0.24f, 0.68f, 9, 0.34f, 1.3f, 0.030f, 1f),
    NebulaBlob(0.80f, 0.60f, 0.54f, 8, 0.38f, 4.7f, 0.022f, 0.85f),
    NebulaBlob(0.16f, 0.82f, 0.44f, 7, 0.30f, 2.9f, 0.018f, 0.7f),
    NebulaBlob(0.62f, 0.10f, 0.34f, 7, 0.34f, 6.1f, 0.026f, 0.55f)
)

private fun organicBlobPath(centerX: Float, centerY: Float, baseRadius: Float, spec: NebulaBlob): Path {
    val angleStep = TAU / spec.points
    val pts = (0 until spec.points).map { i ->
        val angle = i * angleStep
        val noise = sin(spec.seed + i * 2.399963f) * 0.55f + sin(spec.seed * 1.7f + i * 4.123f) * 0.32f
        val r = baseRadius * (1f + noise * spec.irregularity)
        Offset(centerX + cos(angle) * r, centerY + sin(angle) * r)
    }
    val path = Path()
    val first = pts.first()
    val last = pts.last()
    path.moveTo((first.x + last.x) / 2f, (first.y + last.y) / 2f)
    for (i in pts.indices) {
        val current = pts[i]
        val next = pts[(i + 1) % pts.size]
        path.quadraticBezierTo(current.x, current.y, (current.x + next.x) / 2f, (current.y + next.y) / 2f)
    }
    path.close()
    return path
}

private fun DrawScope.drawNebula(accent: Color, secondary: Color, tertiary: Color, phase: Float, intensity: Float) {
    val w = size.width
    val h = size.height
    val minDim = if (w < h) w else h
    NebulaLayout.forEachIndexed { index, blob ->
        val color = when (index % 3) {
            0 -> accent
            1 -> secondary
            else -> tertiary
        }
        val drift = sin(phase * TAU + blob.seed)
        val driftY = cos(phase * TAU + blob.seed * 0.7f)
        val cx = w * blob.cxFrac + drift * w * blob.driftFrac
        val cy = h * blob.cyFrac + driftY * h * blob.driftFrac * 0.6f
        val radius = minDim * blob.radiusFrac
        val path = organicBlobPath(cx, cy, radius, blob)
        clipPath(path) {
            drawRect(
                brush = Brush.radialGradient(
                    colors = listOf(
                        color.copy(alpha = 0.30f * intensity * blob.strength),
                        color.copy(alpha = 0.13f * intensity * blob.strength),
                        Color.Transparent
                    ),
                    center = Offset(cx, cy),
                    radius = radius * 1.2f
                ),
                blendMode = BlendMode.Screen
            )
        }
    }
}

private fun DrawScope.drawCosmicDust(accent: Color, phase: Float, intensity: Float) {
    val w = size.width
    val h = size.height
    CosmicDust.forEachIndexed { index, (x, y) ->
        val dustDrift = sin(phase * TAU + index) * 5.dp.toPx()
        drawCircle(
            color = accent.copy(alpha = (0.09f + (index % 3) * 0.03f) * intensity),
            radius = (1.1f + index % 3) * density,
            center = Offset(w * x + dustDrift, h * y - phase * 8.dp.toPx())
        )
    }
}

private fun DrawScope.drawStars(accent: Color, phase: Float) {
    val w = size.width
    val h = size.height
    StarField.forEach { star ->
        val twinkle = (sin(star.phase + phase * TAU) + 1f) / 2f
        drawCircle(
            color = Color.White.copy(alpha = star.alpha * (0.6f + twinkle * 0.4f)),
            radius = star.radius,
            center = Offset(w * star.x, h * star.y)
        )
    }
    BrightStars.forEach { star ->
        val twinkle = (sin(star.phase + phase * TAU) + 1f) / 2f
        val center = Offset(w * star.x, h * star.y)
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(accent.copy(alpha = 0.30f + twinkle * 0.18f), Color.Transparent),
                radius = star.radius * 7f
            ),
            radius = star.radius * 7f,
            center = center
        )
        drawCircle(
            color = Color.White.copy(alpha = star.alpha * (0.75f + twinkle * 0.25f)),
            radius = star.radius,
            center = center
        )
    }
}

private fun DrawScope.drawVignette() {
    val w = size.width
    val h = size.height
    drawRect(
        brush = Brush.radialGradient(
            colors = listOf(Color.Transparent, Color.Transparent, CosmicMidnight.copy(alpha = 0.42f)),
            center = Offset(w / 2f, h * 0.45f),
            radius = w * 0.95f
        )
    )
}

/**
 * The reusable galaxy environment every screen sits inside.
 *
 * [accentColor]/[secondaryAccent]/[tertiaryAccent] set this region's nebula palette; the
 * deep-space base, star field and cosmic dust are shared across the whole app so every
 * screen still reads as the same universe. [intensity] softens the effect for calmer
 * screens (e.g. Settings) without changing the visual language.
 */
@Composable
fun CosmicBackground(
    accentColor: Color,
    secondaryAccent: Color = accentColor,
    tertiaryAccent: Color = secondaryAccent,
    intensity: Float = 1f,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val motion = rememberInfiniteTransition(label = "nebula motion")
    val nebulaPhase by motion.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(14000, easing = LinearEasing), RepeatMode.Reverse),
        label = "nebula breathing"
    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(accentColor.copy(alpha = 0.14f * intensity), CosmicNavy, CosmicMidnight),
                    radius = 1000f
                )
            )
    ) {
        GalaxyCanvas(
            accentColor = accentColor,
            secondaryAccent = secondaryAccent,
            tertiaryAccent = tertiaryAccent,
            phase = nebulaPhase,
            intensity = intensity
        )
        content()
    }
}

@Composable
private fun GalaxyCanvas(
    accentColor: Color,
    secondaryAccent: Color,
    tertiaryAccent: Color,
    phase: Float,
    intensity: Float,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        drawNebula(accentColor, secondaryAccent, tertiaryAccent, phase, intensity)
        drawCosmicDust(accentColor, phase, intensity)
        drawStars(accentColor, phase)
        drawVignette()
    }
}

/** A static (non-animated) star field — used for small decorative areas that don't need the full background. */
@Composable
fun StarField(accentColor: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxSize()) {
        drawNebula(accentColor, accentColor, accentColor, phase = 0f, intensity = 0.7f)
        drawStars(accentColor, phase = 0f)
    }
}

/* =========================================================================================
 * COSMIC SURFACES — glass panels, glowing cards and buttons that float above the galaxy
 * ========================================================================================= */

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

    val breathing = rememberInfiniteTransition(label = "card breathing")
    val breathScale by breathing.animateFloat(
        initialValue = 0.996f,
        targetValue = 1.006f,
        animationSpec = infiniteRepeatable(tween(2600, easing = LinearEasing), RepeatMode.Reverse),
        label = "card breath scale"
    )
    val breathGlow by breathing.animateFloat(
        initialValue = 0.30f,
        targetValue = 0.46f,
        animationSpec = infiniteRepeatable(tween(2600, easing = LinearEasing), RepeatMode.Reverse),
        label = "card breath glow"
    )

    val pressScale by animateFloatAsState(if (pressed) 0.965f else 1f, tween(120), label = "card press scale")
    val flash by animateFloatAsState(
        targetValue = if (pressed) 1f else 0f,
        animationSpec = if (pressed) tween(90) else tween(360),
        label = "card press flash"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .scale(breathScale * pressScale)
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        accentColor.copy(alpha = 0.16f + flash * 0.10f),
                        CosmicCard.copy(alpha = 0.86f)
                    ),
                    radius = 480f
                ),
                RoundedCornerShape(24.dp)
            )
            .border(
                1.dp,
                accentColor.copy(alpha = (if (pressed) 0.85f else breathGlow)),
                RoundedCornerShape(24.dp)
            )
            .clickable(interactionSource = interactionSource, indication = null, onClick = onClick)
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(accentColor.copy(alpha = 0.16f), RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = title, tint = accentColor, modifier = Modifier.size(26.dp))
            }
            Spacer(Modifier.weight(1f))
            Text("›", color = accentColor.copy(alpha = 0.85f), style = MaterialTheme.typography.headlineMedium)
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

    val shimmer = rememberInfiniteTransition(label = "button shimmer")
    val shimmerGlow by shimmer.animateFloat(
        initialValue = 0.55f,
        targetValue = 0.85f,
        animationSpec = infiniteRepeatable(tween(1800, easing = LinearEasing), RepeatMode.Reverse),
        label = "button shimmer glow"
    )
    val pressScale by animateFloatAsState(if (pressed) 0.965f else 1f, tween(110), label = "button press scale")

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .scale(pressScale)
            .background(
                Brush.horizontalGradient(
                    listOf(accentColor.copy(alpha = if (enabled) 0.30f else 0.10f), accentColor.copy(alpha = if (enabled) 0.14f else 0.05f))
                ),
                RoundedCornerShape(18.dp)
            )
            .border(
                1.dp,
                accentColor.copy(alpha = if (!enabled) 0.2f else if (pressed) 0.95f else shimmerGlow),
                RoundedCornerShape(18.dp)
            )
            .clickable(
                enabled = enabled,
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text,
            color = if (enabled) CosmicText else CosmicText.copy(alpha = 0.4f),
            style = MaterialTheme.typography.titleMedium
        )
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
            .background(
                Brush.verticalGradient(
                    listOf(CosmicCard.copy(alpha = 0.92f), CosmicCard.copy(alpha = 0.82f))
                ),
                RoundedCornerShape(20.dp)
            )
            .border(1.dp, accentColor.copy(alpha = 0.30f), RoundedCornerShape(20.dp))
            .padding(16.dp),
        content = content
    )
}

/* =========================================================================================
 * MIC FOCAL POINT — voice screen's central interactive object
 * ========================================================================================= */

@Composable
fun CosmicMicButton(
    icon: ImageVector,
    isListening: Boolean,
    accentColor: Color,
    listeningColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String = "Tap to speak"
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    val motion = rememberInfiniteTransition(label = "mic motion")
    val idleGlow by motion.animateFloat(
        initialValue = 0.28f,
        targetValue = 0.44f,
        animationSpec = infiniteRepeatable(tween(2000, easing = LinearEasing), RepeatMode.Reverse),
        label = "mic idle glow"
    )
    val auraRadius by motion.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1400, easing = LinearEasing)),
        label = "mic aura ring"
    )
    val pressScale by animateFloatAsState(if (pressed) 0.94f else 1f, tween(110), label = "mic press scale")
    val activeColor = if (isListening) listeningColor else accentColor

    Box(modifier = modifier.size(160.dp), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            if (isListening) {
                // Expanding aura rings communicate "listening" without a literal waveform.
                for (ring in 0..2) {
                    val ringPhase = (auraRadius + ring / 3f) % 1f
                    drawCircle(
                        color = activeColor.copy(alpha = (1f - ringPhase) * 0.30f),
                        radius = size.minDimension * (0.30f + ringPhase * 0.34f),
                        center = Offset(size.width / 2f, size.height / 2f)
                    )
                }
            } else {
                drawCircle(
                    color = activeColor.copy(alpha = idleGlow * 0.5f),
                    radius = size.minDimension * 0.42f,
                    center = Offset(size.width / 2f, size.height / 2f)
                )
            }
        }
        Box(
            modifier = Modifier
                .size(104.dp)
                .scale(pressScale)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        listOf(activeColor.copy(alpha = 0.85f), activeColor.copy(alpha = 0.55f))
                    )
                )
                .border(1.dp, Color.White.copy(alpha = 0.35f), CircleShape)
                .clickable(interactionSource = interactionSource, indication = null, onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = contentDescription, tint = Color.White, modifier = Modifier.size(44.dp))
        }
    }
}

/* =========================================================================================
 * COSMIC SPARKLES — used behind branding and for tap/press micro-feedback
 * ========================================================================================= */

private data class SparkleSpec(val angle: Float, val orbitRadius: Float, val size: Float, val speed: Float, val phase: Float)

private val OrbitingSparkles = listOf(
    SparkleSpec(0.2f, 0.92f, 3.2f, 0.6f, 0f),
    SparkleSpec(2.1f, 1.05f, 2.4f, 0.4f, 1.4f),
    SparkleSpec(3.6f, 0.85f, 2.8f, 0.5f, 2.6f),
    SparkleSpec(4.8f, 1.12f, 2.2f, 0.45f, 0.8f),
    SparkleSpec(5.6f, 0.98f, 3f, 0.55f, 3.6f)
)

/** Small glowing sparkles slowly orbiting a center point — replaces flat colored "confetti dots". */
@Composable
fun CosmicOrbitSparkles(accentColor: Color, modifier: Modifier = Modifier) {
    val motion = rememberInfiniteTransition(label = "orbit sparkles")
    val orbitPhase by motion.animateFloat(
        initialValue = 0f,
        targetValue = TAU,
        animationSpec = infiniteRepeatable(tween(9000, easing = LinearEasing)),
        label = "orbit phase"
    )
    Canvas(modifier = modifier.fillMaxSize()) {
        val cx = size.width / 2f
        val cy = size.height / 2f
        val orbitPx = size.minDimension / 2f
        OrbitingSparkles.forEach { sparkle ->
            val angle = sparkle.angle + orbitPhase * sparkle.speed
            val twinkle = (sin(orbitPhase * 3f + sparkle.phase) + 1f) / 2f
            val x = cx + cos(angle) * orbitPx * sparkle.orbitRadius
            val y = cy + sin(angle) * orbitPx * sparkle.orbitRadius * 0.7f
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color.White.copy(alpha = 0.5f + twinkle * 0.3f), accentColor.copy(alpha = 0.2f), Color.Transparent),
                    radius = sparkle.size * 5f
                ),
                radius = sparkle.size * 5f,
                center = Offset(x, y)
            )
            drawCircle(
                color = Color.White.copy(alpha = 0.7f + twinkle * 0.3f),
                radius = sparkle.size,
                center = Offset(x, y)
            )
        }
    }
}

/** A brief radial sparkle burst used for tap confirmation (e.g. splash screen). */
@Composable
fun CosmicTapBurst(triggered: Boolean, accentColor: Color, modifier: Modifier = Modifier) {
    val progress by animateFloatAsState(
        targetValue = if (triggered) 1f else 0f,
        animationSpec = tween(420),
        label = "tap burst progress"
    )
    if (progress <= 0f) return
    Canvas(modifier = modifier.fillMaxSize()) {
        val cx = size.width / 2f
        val cy = size.height / 2f
        val maxRadius = size.minDimension * 0.62f
        val ringRadius = maxRadius * progress
        val fade = 1f - progress
        drawCircle(
            color = accentColor.copy(alpha = 0.35f * fade),
            radius = ringRadius,
            center = Offset(cx, cy)
        )
        val sparkleCount = 8
        for (i in 0 until sparkleCount) {
            val angle = (i.toFloat() / sparkleCount) * TAU
            val dist = ringRadius * 0.9f
            drawCircle(
                color = Color.White.copy(alpha = 0.85f * fade),
                radius = 2.2f,
                center = Offset(cx + cos(angle) * dist, cy + sin(angle) * dist)
            )
        }
    }
}