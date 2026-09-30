package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun BlackHole3DCanvas(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "black_hole_spin")
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    val flarePulse by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "flare"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        val center = Offset(width / 2f, height / 2f)
        val horizonRadius = (minOf(width, height) * 0.18f).coerceAtLeast(35f)

        // 1. Gravitational Lensing Halo (Light bent over the top)
        drawOval(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0xFFFF6D00).copy(alpha = 0.85f),
                    Color(0xFFFFB300).copy(alpha = 0.5f),
                    Color(0x33FF3D00),
                    Color.Transparent
                ),
                center = center,
                radius = horizonRadius * 2.6f * flarePulse
            ),
            topLeft = Offset(center.x - horizonRadius * 2.4f, center.y - horizonRadius * 1.6f),
            size = Size(horizonRadius * 4.8f, horizonRadius * 3.2f),
            style = Stroke(width = horizonRadius * 0.7f)
        )

        // 2. Accretion Disk - Back Half (behind the shadow)
        drawArc(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color(0xFFFF3D00).copy(alpha = 0.35f),
                    Color(0xFFFF9100).copy(alpha = 0.75f),
                    Color(0xFFFFD180).copy(alpha = 0.9f)
                ),
                start = Offset(center.x - horizonRadius * 3f, center.y),
                end = Offset(center.x + horizonRadius * 3f, center.y)
            ),
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(center.x - horizonRadius * 3f, center.y - horizonRadius * 0.8f),
            size = Size(horizonRadius * 6f, horizonRadius * 1.6f),
            style = Stroke(width = horizonRadius * 0.8f)
        )

        // 3. Relativistic Jet streams (Perpendicular to disk)
        drawLine(
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xFF00E5FF), Color(0x4400E5FF), Color.Transparent),
                startY = center.y - horizonRadius * 2.8f,
                endY = center.y
            ),
            start = Offset(center.x, center.y),
            end = Offset(center.x, center.y - horizonRadius * 2.8f),
            strokeWidth = 4f
        )
        drawLine(
            brush = Brush.verticalGradient(
                colors = listOf(Color.Transparent, Color(0x4400E5FF), Color(0xFF00E5FF)),
                startY = center.y,
                endY = center.y + horizonRadius * 2.8f
            ),
            start = Offset(center.x, center.y),
            end = Offset(center.x, center.y + horizonRadius * 2.8f),
            strokeWidth = 4f
        )

        // 4. Photon Sphere (Bright golden thin ring right outside horizon)
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color.Transparent,
                    Color(0xFFFFF9C4),
                    Color(0xFFFFD54F),
                    Color.Transparent
                ),
                center = center,
                radius = horizonRadius * 1.35f
            ),
            radius = horizonRadius * 1.35f,
            center = center,
            style = Stroke(width = 3.5f)
        )

        // 5. Event Horizon / Gravitational Shadow (Pure Absolute Black Sphere)
        drawCircle(
            color = Color(0xFF000000),
            radius = horizonRadius * 1.15f,
            center = center
        )

        // 6. Accretion Disk - Front Half (in front of the shadow)
        // With Doppler Beaming (approaching side on the left is much brighter and bluer)
        drawArc(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color(0xFFFFF8E1), // Doppler blueshift/brightening on approaching side
                    Color(0xFFFF9100),
                    Color(0xFFDD2C00).copy(alpha = 0.7f) // Redshifted receding side
                ),
                start = Offset(center.x - horizonRadius * 3f, center.y),
                end = Offset(center.x + horizonRadius * 3f, center.y)
            ),
            startAngle = 0f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = Offset(center.x - horizonRadius * 3f, center.y - horizonRadius * 0.8f),
            size = Size(horizonRadius * 6f, horizonRadius * 1.6f),
            style = Stroke(width = horizonRadius * 0.85f)
        )
    }
}
