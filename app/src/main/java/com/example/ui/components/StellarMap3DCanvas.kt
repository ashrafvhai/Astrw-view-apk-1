package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.sp
import com.example.data.datasource.StellarData
import com.example.data.model.Constellation
import com.example.data.model.DeepSpaceWonder
import com.example.engine3d.ProjectionEngine
import com.example.engine3d.Vector3D
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalTextApi::class)
@Composable
fun StellarMap3DCanvas(
    modifier: Modifier = Modifier,
    selectedWonderId: String? = null,
    onWonderSelected: (DeepSpaceWonder) -> Unit = {},
    onConstellationSelected: (Constellation) -> Unit = {}
) {
    var skyYaw by remember { mutableFloatStateOf(0.3f) }
    var skyPitch by remember { mutableFloatStateOf(0.1f) }
    val cameraDistance = 350f // Celestial sphere radius
    val fov = 750f

    val textMeasurer = rememberTextMeasurer()

    // Pulse animation for deep space anomalies
    val infiniteTransition = rememberInfiniteTransition(label = "stellar_pulse")
    val pulseFactor by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.35f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    // Screen hit-test coordinates
    val wonderHitTargets = remember { mutableStateMapOf<String, Offset>() }
    val constellationHitTargets = remember { mutableStateMapOf<String, Offset>() }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    skyYaw += dragAmount.x * 0.005f
                    skyPitch = (skyPitch + dragAmount.y * 0.005f).coerceIn(-1.3f, 1.3f)
                }
            }
            .pointerInput(Unit) {
                detectTapGestures { tapOffset ->
                    // Check Deep Space Wonders first
                    var clickedWonder: DeepSpaceWonder? = null
                    var minWonderDist = 60f
                    for (wonder in StellarData.deepSpaceWonders) {
                        val pt = wonderHitTargets[wonder.id] ?: continue
                        val dist = (pt - tapOffset).getDistance()
                        if (dist < minWonderDist) {
                            minWonderDist = dist
                            clickedWonder = wonder
                        }
                    }
                    if (clickedWonder != null) {
                        onWonderSelected(clickedWonder)
                        return@detectTapGestures
                    }

                    // Check Constellations
                    var clickedConst: Constellation? = null
                    var minConstDist = 70f
                    for (c in StellarData.constellations) {
                        val pt = constellationHitTargets[c.id] ?: continue
                        val dist = (pt - tapOffset).getDistance()
                        if (dist < minConstDist) {
                            minConstDist = dist
                            clickedConst = c
                        }
                    }
                    if (clickedConst != null) {
                        onConstellationSelected(clickedConst)
                    }
                }
            }
    ) {
        val width = size.width
        val height = size.height

        // 1. Draw Constellations (Lines and Stars)
        for (constellation in StellarData.constellations) {
            val projectedStars = constellation.stars.map { star ->
                ProjectionEngine.project(
                    worldPos = star.position,
                    cameraPitch = skyPitch,
                    cameraYaw = skyYaw,
                    cameraDistance = cameraDistance,
                    fov = fov,
                    viewportWidth = width,
                    viewportHeight = height
                )
            }

            // Draw Constellation Lines
            for ((idxA, idxB) in constellation.lines) {
                if (idxA in projectedStars.indices && idxB in projectedStars.indices) {
                    val pA = projectedStars[idxA]
                    val pB = projectedStars[idxB]
                    if (pA.isVisible && pB.isVisible) {
                        drawLine(
                            color = Color(0x6600E5FF),
                            start = Offset(pA.screenX, pA.screenY),
                            end = Offset(pB.screenX, pB.screenY),
                            strokeWidth = 1.4f,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f), 0f)
                        )
                    }
                }
            }

            // Draw Stars in Constellation
            var sumX = 0f
            var sumY = 0f
            var visibleStarCount = 0

            for (i in constellation.stars.indices) {
                val star = constellation.stars[i]
                val proj = projectedStars[i]
                if (proj.isVisible) {
                    val center = Offset(proj.screenX, proj.screenY)
                    sumX += proj.screenX
                    sumY += proj.screenY
                    visibleStarCount++

                    // Star size based on astronomical magnitude
                    val starRadius = (5.5f - star.magnitude).coerceIn(2.5f, 8.5f)

                    // Outer halo
                    drawCircle(
                        color = star.spectralColor.copy(alpha = 0.35f),
                        radius = starRadius * 2.2f,
                        center = center
                    )

                    // Bright core
                    drawCircle(
                        color = star.spectralColor,
                        radius = starRadius,
                        center = center
                    )

                    // Star name for prominent stars
                    if (star.magnitude < 1.0f) {
                        val text = textMeasurer.measure(
                            text = AnnotatedString(star.nameBn),
                            style = TextStyle(color = Color(0xFFCFD8DC), fontSize = 10.sp)
                        )
                        drawText(
                            textLayoutResult = text,
                            topLeft = Offset(center.x + starRadius + 4f, center.y - 7f)
                        )
                    }
                }
            }

            // Constellation Center Label
            if (visibleStarCount > 0) {
                val avgCenter = Offset(sumX / visibleStarCount, sumY / visibleStarCount)
                constellationHitTargets[constellation.id] = avgCenter

                val constText = textMeasurer.measure(
                    text = AnnotatedString("${constellation.nameBn} (${constellation.nameEn})"),
                    style = TextStyle(
                        color = Color(0xFF80D8FF),
                        fontSize = 11.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
                    )
                )
                val tx = avgCenter.x - constText.size.width / 2f
                val ty = avgCenter.y - 30f

                drawRoundRect(
                    color = Color(0x990A1028),
                    topLeft = Offset(tx - 4f, ty - 2f),
                    size = androidx.compose.ui.geometry.Size(constText.size.width + 8f, constText.size.height + 4f),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(4f, 4f)
                )

                drawText(
                    textLayoutResult = constText,
                    topLeft = Offset(tx, ty)
                )
            }
        }

        // 2. Draw Deep Space Wonders (Black Holes, Nebulae, Galaxies)
        for (wonder in StellarData.deepSpaceWonders) {
            val proj = ProjectionEngine.project(
                worldPos = wonder.visualPosition,
                cameraPitch = skyPitch,
                cameraYaw = skyYaw,
                cameraDistance = cameraDistance,
                fov = fov,
                viewportWidth = width,
                viewportHeight = height
            )

            if (proj.isVisible) {
                val center = Offset(proj.screenX, proj.screenY)
                wonderHitTargets[wonder.id] = center
                val isSelected = wonder.id == selectedWonderId

                // Pulsing indicator ring
                drawCircle(
                    color = wonder.primaryColor.copy(alpha = 0.5f),
                    radius = (16f * pulseFactor),
                    center = center,
                    style = Stroke(width = 1.5f)
                )

                // Category-specific marker icon
                when (wonder.category) {
                    com.example.data.model.WonderCategory.BLACK_HOLE -> {
                        // Accretion disk ring around black center
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(Color.Black, Color(0xFFFF6D00), Color.Transparent),
                                center = center,
                                radius = 18f
                            ),
                            radius = 18f,
                            center = center
                        )
                        drawCircle(color = Color.Black, radius = 7f, center = center)
                    }
                    com.example.data.model.WonderCategory.NEBULA -> {
                        // Colorful gas cloud diffuse
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(wonder.primaryColor.copy(alpha = 0.8f), wonder.accentColor.copy(alpha = 0.4f), Color.Transparent),
                                center = center,
                                radius = 22f
                            ),
                            radius = 22f,
                            center = center
                        )
                    }
                    com.example.data.model.WonderCategory.GALAXY -> {
                        // Spiral oval
                        drawOval(
                            brush = Brush.radialGradient(
                                colors = listOf(Color.White, wonder.primaryColor, Color.Transparent),
                                center = center,
                                radius = 24f
                            ),
                            topLeft = Offset(center.x - 22f, center.y - 12f),
                            size = androidx.compose.ui.geometry.Size(44f, 24f)
                        )
                    }
                    else -> {
                        drawCircle(color = wonder.primaryColor, radius = 9f, center = center)
                    }
                }

                // Wonder Title Tag
                val tagText = textMeasurer.measure(
                    text = AnnotatedString("✦ ${wonder.nameBn}"),
                    style = TextStyle(
                        color = if (isSelected) Color(0xFF00E5FF) else wonder.primaryColor,
                        fontSize = 11.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )
                )
                val badgeX = center.x - tagText.size.width / 2f
                val badgeY = center.y + 20f

                drawRoundRect(
                    color = Color(0xCC090E22),
                    topLeft = Offset(badgeX - 6f, badgeY - 2f),
                    size = androidx.compose.ui.geometry.Size(tagText.size.width + 12f, tagText.size.height + 4f),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(6f, 6f)
                )

                drawText(
                    textLayoutResult = tagText,
                    topLeft = Offset(badgeX, badgeY)
                )
            }
        }
    }
}
