package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.ui.theme.DeepSpaceNavy
import com.example.ui.theme.SpaceBlack
import kotlin.random.Random

private data class StarParticle(
    val xRatio: Float,
    val yRatio: Float,
    val baseRadius: Float,
    val twinklePhase: Float,
    val color: Color
)

@Composable
fun CosmicBackground(
    modifier: Modifier = Modifier,
    particleCount: Int = 90
) {
    val infiniteTransition = rememberInfiniteTransition(label = "stars_twinkle")
    val twinkleFactor = infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2400, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "twinkle"
    )

    // Generate fixed randomized stars based on seed
    val stars = remember {
        val random = Random(42)
        val palette = listOf(
            Color(0xFFFFFFFF),
            Color(0xFFE1F5FE),
            Color(0xFFFFECB3),
            Color(0xFF80D8FF),
            Color(0xFFFFCC80)
        )
        List(particleCount) {
            StarParticle(
                xRatio = random.nextFloat(),
                yRatio = random.nextFloat(),
                baseRadius = random.nextFloat() * 1.8f + 0.6f,
                twinklePhase = random.nextFloat() * 3.14159f,
                color = palette[random.nextInt(palette.size)]
            )
        }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        // Deep cosmic radial gradient
        drawRect(
            brush = Brush.radialGradient(
                colors = listOf(
                    DeepSpaceNavy,
                    SpaceBlack,
                    Color(0xFF020308)
                ),
                center = Offset(width * 0.5f, height * 0.4f),
                radius = width.coerceAtLeast(height) * 0.9f
            )
        )

        // Subtle nebula cloud glow in backdrop
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color(0x187C4DFF),
                    Color(0x0C00E5FF),
                    Color.Transparent
                ),
                center = Offset(width * 0.3f, height * 0.25f),
                radius = width * 0.7f
            ),
            radius = width * 0.7f,
            center = Offset(width * 0.3f, height * 0.25f)
        )

        // Draw twinkling stars
        val factor = twinkleFactor.value
        for (star in stars) {
            val alpha = (kotlin.math.sin(factor * 3.14f + star.twinklePhase) * 0.35f + 0.65f)
                .coerceIn(0.15f, 1f)
            val pos = Offset(star.xRatio * width, star.yRatio * height)
            drawCircle(
                color = star.color.copy(alpha = alpha),
                radius = star.baseRadius,
                center = pos
            )
        }
    }
}
