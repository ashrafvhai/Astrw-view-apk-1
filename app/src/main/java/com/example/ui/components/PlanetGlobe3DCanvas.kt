package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import com.example.data.model.CelestialBody
import kotlin.math.*

@Composable
fun PlanetGlobe3DCanvas(
    modifier: Modifier = Modifier,
    body: CelestialBody,
    showInternalLayers: Boolean = false,
    isAutoSpinEnabled: Boolean = false
) {
    // Interactive Globe Rotation
    var globeYaw by remember(body.id) { mutableFloatStateOf(0f) }
    var globePitch by remember(body.id) { mutableFloatStateOf(Math.toRadians(body.axialTiltDeg.toDouble()).toFloat().coerceIn(-0.6f, 0.6f)) }

    // Auto-spin animation: slow, gentle and peaceful
    val infiniteTransition = rememberInfiniteTransition(label = "globe_rotation")
    val autoSpin by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 90000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "spin"
    )

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(body.id) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    globeYaw += dragAmount.x * 0.008f
                    globePitch = (globePitch + dragAmount.y * 0.008f).coerceIn(-1.1f, 1.1f)
                }
            }
    ) {
        val width = size.width
        val height = size.height
        val center = Offset(width / 2f, height / 2f)
        val globeRadius = (min(width, height) * 0.35f).coerceAtLeast(60f)

        val spinOffset = if (isAutoSpinEnabled) autoSpin * body.rotationSpeed * 0.08f else 0f
        val totalYaw = globeYaw + spinOffset

        if (showInternalLayers) {
            // Draw cross-section cutout of internal core layers!
            drawInternalCoreLayers(
                center = center,
                radius = globeRadius,
                body = body
            )
        } else {
            // Draw 3D Globe with Lighting, surface features, and rings
            draw3DGlobeSurface(
                center = center,
                radius = globeRadius,
                body = body,
                yaw = totalYaw,
                pitch = globePitch
            )
        }
    }
}

/**
 * Draws the 3D surface globe with spherical shading and procedural planet textures
 */
private fun DrawScope.draw3DGlobeSurface(
    center: Offset,
    radius: Float,
    body: CelestialBody,
    yaw: Float,
    pitch: Float
) {
    // 1. Back Ring (for Saturn / Uranus)
    if (body.ringConfig != null) {
        drawDetailedRings(
            center = center,
            planetRadius = radius,
            ringConfig = body.ringConfig,
            pitch = pitch,
            isFrontHalf = false
        )
    }

    // 2. Base Sphere with 3D Day/Night Terminator
    // Light source from upper-left (Sun at 10 o'clock)
    val lightOffset = Offset(center.x - radius * 0.35f, center.y - radius * 0.35f)

    // Base colored sphere
    drawCircle(
        color = body.primaryColor,
        radius = radius,
        center = center
    )

    // 3. Planet Surface Features
    when (body.id) {
        "jupiter" -> drawJupiterCloudBands(center, radius, yaw, pitch)
        "saturn" -> drawSaturnAtmosphericBands(center, radius, yaw, pitch)
        "earth" -> drawEarthContinentsAndClouds(center, radius, yaw, pitch)
        "mars" -> drawMarsSurfaceFeatures(center, radius, yaw, pitch)
        "sun" -> drawSunGranulation(center, radius, yaw)
        else -> drawGenericPlanetaryBands(center, radius, body, yaw, pitch)
    }

    // 4. 3D Spherical Ambient Shadow & Light Terminator Overlay
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.35f),
                Color.Transparent,
                Color.Black.copy(alpha = 0.5f),
                Color.Black.copy(alpha = 0.92f)
            ),
            center = lightOffset,
            radius = radius * 1.35f
        ),
        radius = radius,
        center = center
    )

    // 5. Atmosphere Limb Glow (Rayleigh scattering)
    if (body.atmosphereColor != null) {
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(
                    Color.Transparent,
                    body.atmosphereColor.copy(alpha = 0.2f),
                    body.atmosphereColor.copy(alpha = 0.65f),
                    Color.Transparent
                ),
                center = center,
                radius = radius * 1.12f
            ),
            radius = radius * 1.12f,
            center = center
        )
    }

    // 6. Front Ring (drawn over planet)
    if (body.ringConfig != null) {
        drawDetailedRings(
            center = center,
            planetRadius = radius,
            ringConfig = body.ringConfig,
            pitch = pitch,
            isFrontHalf = true
        )
    }
}

/**
 * Draws Jupiter's bands and Great Red Spot
 */
private fun DrawScope.drawJupiterCloudBands(
    center: Offset,
    radius: Float,
    yaw: Float,
    pitch: Float
) {
    val bandCount = 14
    for (i in 0 until bandCount) {
        val yNorm = (i.toFloat() / (bandCount - 1)) * 2f - 1f // -1 to 1
        val y = center.y + yNorm * radius * 0.95f
        val sliceWidth = sqrt((radius * radius - (y - center.y) * (y - center.y)).coerceAtLeast(0f)) * 2f
        if (sliceWidth <= 0f) continue

        val isDarkBelt = i % 2 == 0
        val bandColor = if (isDarkBelt) Color(0xFF8D6E63).copy(alpha = 0.6f) else Color(0xFFFFD54F).copy(alpha = 0.35f)

        drawRoundRect(
            color = bandColor,
            topLeft = Offset(center.x - sliceWidth / 2f, y - 6f),
            size = Size(sliceWidth, 12f)
        )
    }

    // Great Red Spot Oval Storm
    val grsLongitude = 1.2f
    val stormAngle = (yaw + grsLongitude) % (2f * PI.toFloat())
    val isFacingViewer = cos(stormAngle) > 0f
    if (isFacingViewer) {
        val grsX = center.x + sin(stormAngle) * radius * 0.7f
        val grsY = center.y + radius * 0.32f + pitch * 30f
        drawOval(
            brush = Brush.radialGradient(
                colors = listOf(Color(0xFFD84315), Color(0xFFBF360C)),
                center = Offset(grsX, grsY),
                radius = 24f
            ),
            topLeft = Offset(grsX - 22f, grsY - 14f),
            size = Size(44f, 28f)
        )
    }
}

/**
 * Draws Saturn's atmospheric bands
 */
private fun DrawScope.drawSaturnAtmosphericBands(
    center: Offset,
    radius: Float,
    yaw: Float,
    pitch: Float
) {
    for (i in -4..4) {
        val y = center.y + (i * radius * 0.2f)
        val sliceWidth = sqrt((radius * radius - (y - center.y) * (y - center.y)).coerceAtLeast(0f)) * 2f
        if (sliceWidth > 0f) {
            drawRoundRect(
                color = if (i % 2 == 0) Color(0xFFBCAAA4).copy(alpha = 0.35f) else Color(0xFFFFE082).copy(alpha = 0.25f),
                topLeft = Offset(center.x - sliceWidth / 2f, y - 5f),
                size = Size(sliceWidth, 10f)
            )
        }
    }
}

/**
 * Draws Earth continents and swirling clouds
 */
private fun DrawScope.drawEarthContinentsAndClouds(
    center: Offset,
    radius: Float,
    yaw: Float,
    pitch: Float
) {
    // Continents simulated using rotating organic arcs
    for (i in 0 until 5) {
        val continentYaw = yaw + (i * 1.25f)
        val visible = cos(continentYaw) > -0.2f
        if (visible) {
            val posX = center.x + sin(continentYaw) * radius * 0.65f
            val posY = center.y + (i - 2) * (radius * 0.3f) + pitch * 25f
            drawOval(
                color = Color(0xFF43A047).copy(alpha = 0.75f),
                topLeft = Offset(posX - 35f, posY - 22f),
                size = Size(70f, 44f)
            )
        }
    }

    // Swirling White Clouds
    for (i in 0 until 4) {
        val cloudYaw = (yaw * 1.2f) + (i * 1.6f)
        if (cos(cloudYaw) > -0.3f) {
            val cx = center.x + sin(cloudYaw) * radius * 0.75f
            val cy = center.y + ((i % 3) - 1) * (radius * 0.35f)
            drawOval(
                color = Color.White.copy(alpha = 0.45f),
                topLeft = Offset(cx - 30f, cy - 12f),
                size = Size(60f, 24f)
            )
        }
    }

    // Polar ice caps
    drawOval(
        color = Color.White.copy(alpha = 0.85f),
        topLeft = Offset(center.x - radius * 0.45f, center.y - radius),
        size = Size(radius * 0.9f, radius * 0.3f)
    )
    drawOval(
        color = Color.White.copy(alpha = 0.85f),
        topLeft = Offset(center.x - radius * 0.45f, center.y + radius * 0.7f),
        size = Size(radius * 0.9f, radius * 0.3f)
    )
}

/**
 * Draws Mars volcanic plateaus and polar ice
 */
private fun DrawScope.drawMarsSurfaceFeatures(
    center: Offset,
    radius: Float,
    yaw: Float,
    pitch: Float
) {
    // Dark volcanic basalt patches (Syrtis Major)
    for (i in 0 until 3) {
        val featureYaw = yaw + (i * 2.1f)
        if (cos(featureYaw) > -0.1f) {
            val px = center.x + sin(featureYaw) * radius * 0.6f
            val py = center.y + (i - 1) * (radius * 0.35f)
            drawOval(
                color = Color(0xFF4E342E).copy(alpha = 0.6f),
                topLeft = Offset(px - 32f, py - 20f),
                size = Size(64f, 40f)
            )
        }
    }

    // North polar ice cap
    drawOval(
        color = Color(0xFFEDE7F6).copy(alpha = 0.85f),
        topLeft = Offset(center.x - radius * 0.35f, center.y - radius),
        size = Size(radius * 0.7f, radius * 0.22f)
    )
}

/**
 * Draws Sun solar granulation cells
 */
private fun DrawScope.drawSunGranulation(
    center: Offset,
    radius: Float,
    yaw: Float
) {
    // Solar flare solar prominences looping off edge
    for (a in 0..12) {
        val angle = (a * 30f + yaw * 20f) * (PI.toFloat() / 180f)
        val loopX = center.x + cos(angle) * (radius * 1.08f)
        val loopY = center.y + sin(angle) * (radius * 1.08f)
        drawCircle(
            color = Color(0xFFFF5722).copy(alpha = 0.45f),
            radius = 12f,
            center = Offset(loopX, loopY)
        )
    }
}

/**
 * Draws generic banding for other worlds (Mercury, Venus, Uranus, Neptune)
 */
private fun DrawScope.drawGenericPlanetaryBands(
    center: Offset,
    radius: Float,
    body: CelestialBody,
    yaw: Float,
    pitch: Float
) {
    for (i in -3..3) {
        val y = center.y + (i * radius * 0.25f)
        val sliceWidth = sqrt((radius * radius - (y - center.y) * (y - center.y)).coerceAtLeast(0f)) * 2f
        if (sliceWidth > 0f) {
            drawRoundRect(
                color = body.secondaryColor.copy(alpha = 0.3f),
                topLeft = Offset(center.x - sliceWidth / 2f, y - 6f),
                size = Size(sliceWidth, 12f)
            )
        }
    }
}

/**
 * Draws detailed 3D rings with tilt
 */
private fun DrawScope.drawDetailedRings(
    center: Offset,
    planetRadius: Float,
    ringConfig: com.example.data.model.RingConfig,
    pitch: Float,
    isFrontHalf: Boolean
) {
    val outerR = planetRadius * ringConfig.outerRadiusFactor
    val innerR = planetRadius * ringConfig.innerRadiusFactor
    val midR = (outerR + innerR) / 2f
    val strokeW = outerR - innerR
    val tiltAspect = (0.28f + pitch * 0.15f).coerceIn(0.12f, 0.5f)

    val startAngle = if (isFrontHalf) 0f else 180f
    val sweepAngle = 180f

    drawArc(
        brush = Brush.linearGradient(
            colors = listOf(
                ringConfig.ringColorEdge,
                ringConfig.ringColor,
                ringConfig.ringColorEdge
            ),
            start = Offset(center.x - outerR, center.y),
            end = Offset(center.x + outerR, center.y)
        ),
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        topLeft = Offset(center.x - midR, center.y - (midR * tiltAspect)),
        size = Size(midR * 2f, midR * 2f * tiltAspect),
        style = Stroke(width = strokeW)
    )
}

/**
 * Draws a 3D Cross-Section of the planet's internal core layers!
 */
private fun DrawScope.drawInternalCoreLayers(
    center: Offset,
    radius: Float,
    body: CelestialBody
) {
    val layers = body.coreLayers
    if (layers.isEmpty()) return

    // Draw full outer sphere
    drawCircle(
        color = body.primaryColor,
        radius = radius,
        center = center
    )

    // Cutout pie wedge (top-right quadrant 90 degrees) to expose interior!
    val layerCount = layers.size
    for (i in layers.indices.reversed()) {
        val layer = layers[i]
        val layerRadius = radius * ((i + 1).toFloat() / layerCount)

        // Draw inner concentric sphere
        drawCircle(
            color = layer.color,
            radius = layerRadius,
            center = center
        )

        // Wedge slice
        drawArc(
            color = layer.color.copy(alpha = 0.95f),
            startAngle = -45f,
            sweepAngle = 90f,
            useCenter = true,
            topLeft = Offset(center.x - layerRadius, center.y - layerRadius),
            size = Size(layerRadius * 2f, layerRadius * 2f)
        )
    }

    // Draw outline separating cut
    drawArc(
        color = Color.White.copy(alpha = 0.7f),
        startAngle = -45f,
        sweepAngle = 90f,
        useCenter = true,
        topLeft = Offset(center.x - radius, center.y - radius),
        size = Size(radius * 2f, radius * 2f),
        style = Stroke(width = 2.5f)
    )
}
