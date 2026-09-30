package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.data.datasource.GalaxyData
import com.example.data.datasource.GalaxyTarget
import com.example.data.datasource.GalaxyType
import com.example.data.datasource.SolarSystemData
import com.example.data.model.CelestialBody
import com.example.engine3d.ProjectionEngine
import com.example.engine3d.Vector3D
import com.example.sensor.DeviceOrientationAngles
import com.example.ui.localization.AppLanguage
import com.example.ui.theme.StarlightWhite
import kotlin.math.*

private data class RenderablePlanet(
    val body: CelestialBody,
    val worldPos: Vector3D,
    val screenX: Float,
    val screenY: Float,
    val depthZ: Float,
    val visualRadius: Float,
    val isVisible: Boolean
)

private data class AsteroidParticle(
    val distance: Float,
    val initialAngle: Float,
    val speed: Float,
    val yOffset: Float,
    val size: Float
)

private data class InterstellarStar(
    val nameBn: String,
    val nameEn: String,
    val position: Vector3D,
    val color: Color,
    val baseSize: Float
)

private fun getInitialOrbitAngle(id: String): Float = when (id) {
    "mercury" -> 0.75f
    "venus" -> 2.15f
    "earth" -> 3.95f
    "mars" -> 1.35f
    "jupiter" -> 3.10f
    "saturn" -> 5.25f
    "uranus" -> 0.40f
    "neptune" -> 2.80f
    "pluto" -> 4.60f
    "moon" -> 4.05f
    else -> 0f
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun SolarSystem3DCanvas(
    modifier: Modifier = Modifier,
    orbitSpeedMultiplier: Float = 0.0f,
    cameraZoomMultiplier: Float = 1.0f,
    currentLanguage: AppLanguage = AppLanguage.ENGLISH,
    selectedBodyId: String? = null,
    selectedGalaxyId: String? = null,
    focusedBody: CelestialBody? = null,
    isGyroEnabled: Boolean = false,
    deviceOrientation: DeviceOrientationAngles = DeviceOrientationAngles(),
    onZoomChange: (Float) -> Unit = {},
    onBodySelected: (CelestialBody) -> Unit = {},
    onGalaxySelected: (GalaxyTarget) -> Unit = {},
    onVoyagerSelected: () -> Unit = {}
) {
    // Camera State: 360-degree rotation angles
    var cameraYaw by remember { mutableFloatStateOf(0.45f) }
    var cameraPitch by remember { mutableFloatStateOf(0.75f) }
    val baseCameraDistance = 1050f

    // Calm and serene animation clock (speed reduced ~10x so planets move with majestic grace)
    val infiniteTransition = rememberInfiniteTransition(label = "orbit_clock")
    val animTime by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 100000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "time"
    )

    // Gentle solar flare and pulse
    val pulseAnimation by infiniteTransition.animateFloat(
        initialValue = 0.92f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "corona"
    )

    // Main Asteroid Belt particles
    val asteroidBelt = remember {
        val list = mutableListOf<AsteroidParticle>()
        val rnd = java.util.Random(1337)
        for (i in 0 until 80) {
            val dist = 215f + rnd.nextFloat() * 25f
            val angle = rnd.nextFloat() * (2f * PI.toFloat())
            val speed = 0.28f + rnd.nextFloat() * 0.08f
            val y = (rnd.nextFloat() - 0.5f) * 12f
            val sz = 0.8f + rnd.nextFloat() * 1.5f
            list.add(AsteroidParticle(dist, angle, speed, y, sz))
        }
        list
    }

    // Oort Cloud icy particles (beyond Pluto)
    val oortCloudParticles = remember {
        val list = mutableListOf<Vector3D>()
        val rnd = java.util.Random(4242)
        for (i in 0 until 90) {
            val radius = 580f + rnd.nextFloat() * 260f
            val u = rnd.nextFloat() * 2f - 1f
            val phi = rnd.nextFloat() * (2f * PI.toFloat())
            val theta = acos(u)
            val x = radius * sin(theta) * cos(phi)
            val y = radius * sin(theta) * sin(phi)
            val z = radius * cos(theta)
            list.add(Vector3D(x, y, z))
        }
        list
    }

    // Nearby Interstellar Stars
    val nearbyStars = remember {
        listOf(
            InterstellarStar("আলফা সেন্টরাই", "Alpha Centauri", Vector3D(650f, 220f, -480f), Color(0xFFFFD54F), 5f),
            InterstellarStar("লুব্ধক (সিরিয়াস)", "Sirius", Vector3D(-720f, -310f, 520f), Color(0xFFE1F5FE), 6f),
            InterstellarStar("ভেগা", "Vega", Vector3D(480f, -590f, 620f), Color(0xFF80D8FF), 5f),
            InterstellarStar("বেটেলজুস", "Betelgeuse", Vector3D(-620f, 680f, -580f), Color(0xFFFF5722), 7f),
            InterstellarStar("কৃত্তিকা মণ্ডল", "Pleiades Cluster", Vector3D(-820f, 150f, 750f), Color(0xFF82B1FF), 8f)
        )
    }

    val textMeasurer = rememberTextMeasurer()

    // Screen click coordinates
    val planetClickTargets = remember { mutableStateMapOf<String, Offset>() }
    val galaxyClickTargets = remember { mutableStateMapOf<String, Offset>() }
    var voyagerClickTarget by remember { mutableStateOf<Offset?>(null) }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                awaitEachGesture {
                    val down = awaitFirstDown(requireUnconsumed = false)
                    var isMoved = false
                    var prevDistance = 0f

                    while (true) {
                        val event = awaitPointerEvent()
                        val active = event.changes.filter { it.pressed }
                        if (active.isEmpty()) {
                            if (!isMoved) {
                                // Tap detected!
                                val tapOffset = down.position

                                // Check Voyager 1 first
                                if (voyagerClickTarget != null) {
                                    val dist = (voyagerClickTarget!! - tapOffset).getDistance()
                                    if (dist < 50f) {
                                        onVoyagerSelected()
                                        break
                                    }
                                }

                                // Check Galaxies first if tapped
                                var closestGalaxy: GalaxyTarget? = null
                                var minGalaxyDist = 65f

                                for (galaxy in GalaxyData.galaxies) {
                                    val pos = galaxyClickTargets[galaxy.id] ?: continue
                                    val dist = (pos - tapOffset).getDistance()
                                    if (dist < minGalaxyDist) {
                                        minGalaxyDist = dist
                                        closestGalaxy = galaxy
                                    }
                                }

                                if (closestGalaxy != null) {
                                    onGalaxySelected(closestGalaxy)
                                } else {
                                    // Check Planets
                                    var closestBody: CelestialBody? = null
                                    var minDistance = 55f

                                    for (body in SolarSystemData.celestialBodies) {
                                        val pos = planetClickTargets[body.id] ?: continue
                                        val dist = (pos - tapOffset).getDistance()
                                        if (dist < minDistance) {
                                            minDistance = dist
                                            closestBody = body
                                        }
                                    }

                                    if (closestBody != null) {
                                        onBodySelected(closestBody)
                                    }
                                }
                            }
                            break
                        }

                        if (active.size == 1) {
                            val change = active[0]
                            val pan = change.position - change.previousPosition
                            if (pan.getDistance() > 2f) {
                                isMoved = true
                                cameraYaw += pan.x * 0.007f
                                cameraPitch = (cameraPitch + pan.y * 0.006f).coerceIn(-1.52f, 1.52f)
                                change.consume()
                            }
                        } else if (active.size >= 2) {
                            isMoved = true
                            val p1 = active[0]
                            val p2 = active[1]
                            val currentDist = (p1.position - p2.position).getDistance()
                            if (prevDistance > 0f && currentDist > 0f) {
                                val ratio = currentDist / prevDistance
                                if (abs(ratio - 1f) > 0.008f) {
                                    onZoomChange(1f / ratio)
                                }
                            }
                            prevDistance = currentDist
                            p1.consume()
                            p2.consume()
                        }
                    }
                }
            }
    ) {
        val width = size.width
        val height = size.height
        // Serene orbit rate: reduced so planets float calmly and clearly
        val effectiveTime = animTime * orbitSpeedMultiplier
        val effectiveCameraDistance = baseCameraDistance * cameraZoomMultiplier

        val currentYaw = if (isGyroEnabled) deviceOrientation.yawRad else cameraYaw
        val currentPitch = if (isGyroEnabled) deviceOrientation.pitchRad else cameraPitch

        // Camera focus offset (if a planet is locked)
        val centerOffset = if (focusedBody != null && focusedBody.id != "sun" && cameraZoomMultiplier < 2.5f) {
            val baseAngle = getInitialOrbitAngle(focusedBody.id)
            val angle = baseAngle + effectiveTime * (focusedBody.orbitSpeedMultiplier * 0.005f)
            val incRad = Math.toRadians(focusedBody.orbitInclinationDeg.toDouble()).toFloat()
            val r = focusedBody.orbitDistanceUnits
            Vector3D(
                x = r * cos(angle),
                y = r * sin(angle) * sin(incRad),
                z = r * sin(angle) * cos(incRad)
            )
        } else {
            Vector3D(0f, 0f, 0f)
        }

        // 1. Draw 3D Orbits for Solar System
        for (body in SolarSystemData.celestialBodies) {
            if (body.orbitDistanceUnits > 0f) {
                draw3DOrbitPath(
                    body = body,
                    cameraPitch = currentPitch,
                    cameraYaw = currentYaw,
                    cameraDistance = effectiveCameraDistance,
                    centerOffset = centerOffset,
                    viewportWidth = width,
                    viewportHeight = height,
                    isSelected = body.id == selectedBodyId
                )
            }
        }

        // 2. Draw 3D Asteroid Belt (visible when in Solar System range)
        if (effectiveCameraDistance < 4000f) {
            for (asteroid in asteroidBelt) {
                val astAngle = asteroid.initialAngle + (effectiveTime * asteroid.speed * 0.015f)
                val worldPos = Vector3D(
                    x = asteroid.distance * cos(astAngle),
                    y = asteroid.yOffset,
                    z = asteroid.distance * sin(astAngle)
                )
                val proj = ProjectionEngine.project(
                    worldPos = worldPos,
                    cameraPitch = cameraPitch,
                    cameraYaw = cameraYaw,
                    cameraDistance = effectiveCameraDistance,
                    viewportWidth = width,
                    viewportHeight = height,
                    centerOffset = centerOffset
                )
                if (proj.isVisible) {
                    drawCircle(
                        color = Color(0xFF90A4AE).copy(alpha = 0.5f),
                        radius = (asteroid.size * proj.scaleFactor).coerceAtLeast(0.8f),
                        center = Offset(proj.screenX, proj.screenY)
                    )
                }
            }
        }

        // 3. Draw Halley's Comet
        if (effectiveCameraDistance < 4000f) {
            drawHalleysComet(
                effectiveTime = effectiveTime,
                cameraPitch = cameraPitch,
                cameraYaw = cameraYaw,
                cameraDistance = effectiveCameraDistance,
                centerOffset = centerOffset,
                viewportWidth = width,
                viewportHeight = height
            )
        }

        // 4. Draw Oort Cloud (Icy Cometary Shell beyond solar system)
        if (effectiveCameraDistance > 1200f) {
            for (pt in oortCloudParticles) {
                val proj = ProjectionEngine.project(
                    worldPos = pt,
                    cameraPitch = cameraPitch,
                    cameraYaw = cameraYaw,
                    cameraDistance = effectiveCameraDistance,
                    viewportWidth = width,
                    viewportHeight = height,
                    centerOffset = centerOffset
                )
                if (proj.isVisible) {
                    drawCircle(
                        color = Color(0xFF80D8FF).copy(alpha = 0.35f),
                        radius = (1.2f * proj.scaleFactor).coerceIn(0.6f, 2.5f),
                        center = Offset(proj.screenX, proj.screenY)
                    )
                }
            }
        }

        // 5. Draw Interstellar Stars (Alpha Centauri, Sirius, Vega, Betelgeuse)
        if (effectiveCameraDistance > 1600f) {
            for (star in nearbyStars) {
                val proj = ProjectionEngine.project(
                    worldPos = star.position,
                    cameraPitch = cameraPitch,
                    cameraYaw = cameraYaw,
                    cameraDistance = effectiveCameraDistance,
                    viewportWidth = width,
                    viewportHeight = height,
                    centerOffset = centerOffset
                )
                if (proj.isVisible) {
                    val center = Offset(proj.screenX, proj.screenY)
                    val r = (star.baseSize * proj.scaleFactor).coerceIn(2f, 9f)

                    drawCircle(
                        color = star.color.copy(alpha = 0.35f),
                        radius = r * 2.2f,
                        center = center
                    )
                    drawCircle(
                        color = star.color,
                        radius = r,
                        center = center
                    )

                    val starDisplayName = if (currentLanguage == AppLanguage.ENGLISH) star.nameEn else star.nameBn
                    val starTag = textMeasurer.measure(
                        text = AnnotatedString("★ $starDisplayName"),
                        style = TextStyle(color = star.color, fontSize = 9.sp)
                    )
                    drawText(
                        textLayoutResult = starTag,
                        topLeft = Offset(center.x + r + 3f, center.y - 6f)
                    )
                }
            }
        }

        // 6. Draw Deep Space Galaxies (Andromeda, Whirlpool, Sombrero, Pinwheel, etc.)
        for (galaxy in GalaxyData.galaxies) {
            val proj = ProjectionEngine.project(
                worldPos = galaxy.position3D,
                cameraPitch = cameraPitch,
                cameraYaw = cameraYaw,
                cameraDistance = effectiveCameraDistance,
                viewportWidth = width,
                viewportHeight = height,
                centerOffset = centerOffset
            )

            if (proj.isVisible) {
                val center = Offset(proj.screenX, proj.screenY)
                val visualRadius = (galaxy.visualRadiusDp * proj.scaleFactor * (effectiveCameraDistance / 1050f).coerceAtLeast(0.8f))
                    .coerceIn(16f, 85f)

                galaxyClickTargets[galaxy.id] = center
                val isSelected = galaxy.id == selectedGalaxyId

                drawGalaxyIn3D(
                    galaxy = galaxy,
                    center = center,
                    radius = visualRadius,
                    isSelected = isSelected,
                    textMeasurer = textMeasurer,
                    pulseFactor = pulseAnimation,
                    rotationTime = effectiveTime * 0.005f,
                    currentLanguage = currentLanguage
                )
            }
        }

        // 7. Calculate 3D world positions of all Solar System bodies
        val renderList = mutableListOf<RenderablePlanet>()
        for (body in SolarSystemData.celestialBodies) {
            val worldPos = if (body.id == "sun") {
                Vector3D(0f, 0f, 0f)
            } else {
                // Calmer orbit speed: 0.005f multiplier ensures steady, peaceful, visible motion
                val baseAngle = getInitialOrbitAngle(body.id)
                val angle = baseAngle + effectiveTime * (body.orbitSpeedMultiplier * 0.005f)
                val incRad = Math.toRadians(body.orbitInclinationDeg.toDouble()).toFloat()
                val r = body.orbitDistanceUnits
                Vector3D(
                    x = r * cos(angle),
                    y = r * sin(angle) * sin(incRad),
                    z = r * sin(angle) * cos(incRad)
                )
            }

            val proj = ProjectionEngine.project(
                worldPos = worldPos,
                cameraPitch = cameraPitch,
                cameraYaw = cameraYaw,
                cameraDistance = effectiveCameraDistance,
                viewportWidth = width,
                viewportHeight = height,
                centerOffset = centerOffset
            )

            if (proj.isVisible) {
                val visualRad = (body.baseRadiusDp * proj.scaleFactor).coerceIn(3.5f, 75f)
                renderList.add(
                    RenderablePlanet(
                        body = body,
                        worldPos = worldPos,
                        screenX = proj.screenX,
                        screenY = proj.screenY,
                        depthZ = proj.depthZ,
                        visualRadius = visualRad,
                        isVisible = true
                    )
                )
                planetClickTargets[body.id] = Offset(proj.screenX, proj.screenY)
            }
        }

        // Sort by Depth: Render farther objects first
        renderList.sortBy { it.depthZ }

        // Draw Planets and Sun
        for (item in renderList) {
            val body = item.body
            val center = Offset(item.screenX, item.screenY)
            val rad = item.visualRadius
            val isSelected = body.id == selectedBodyId

            if (body.id == "sun") {
                drawSun(center = center, radius = rad, coronaPulse = pulseAnimation)
            } else {
                drawPlanetSphere(
                    body = body,
                    center = center,
                    radius = rad,
                    worldPos = item.worldPos,
                    isSelected = isSelected,
                    cameraYaw = cameraYaw,
                    cameraPitch = cameraPitch
                )
            }

            // Labels with localized display
            if (isSelected || (rad > 10f && effectiveCameraDistance < 2500f)) {
                drawPlanetLabel(
                    textMeasurer = textMeasurer,
                    body = body,
                    center = center,
                    radius = rad,
                    isSelected = isSelected,
                    currentLanguage = currentLanguage
                )
            }
        }
    }
}

/**
 * Draws a beautiful 3D galaxy with glowing core, spiral arms, and name badge
 */
@OptIn(ExperimentalTextApi::class)
private fun DrawScope.drawGalaxyIn3D(
    galaxy: GalaxyTarget,
    center: Offset,
    radius: Float,
    isSelected: Boolean,
    textMeasurer: TextMeasurer,
    pulseFactor: Float,
    rotationTime: Float,
    currentLanguage: AppLanguage
) {
    // 1. Outer Diffuse Glow
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(
                galaxy.armColor.copy(alpha = 0.35f),
                galaxy.coreColor.copy(alpha = 0.15f),
                Color.Transparent
            ),
            center = center,
            radius = radius * 1.8f
        ),
        radius = radius * 1.8f,
        center = center
    )

    // 2. Galaxy Structure based on GalaxyType
    when (galaxy.type) {
        GalaxyType.SPIRAL, GalaxyType.BARRED_SPIRAL -> {
            val armCount = if (galaxy.type == GalaxyType.BARRED_SPIRAL) 2 else 3
            for (a in 0 until armCount) {
                val armOffsetAngle = (a * (2f * PI.toFloat() / armCount)) + rotationTime
                val path = Path()
                val steps = 24
                var started = false

                for (s in 0..steps) {
                    val progress = s.toFloat() / steps
                    val r = radius * progress * 1.15f
                    val theta = armOffsetAngle + (progress * 3.2f)
                    val x = center.x + r * cos(theta)
                    val y = center.y + (r * sin(theta) * galaxy.diskTiltRad)

                    if (!started) {
                        path.moveTo(x, y)
                        started = true
                    } else {
                        path.lineTo(x, y)
                    }
                }

                drawPath(
                    path = path,
                    color = galaxy.armColor.copy(alpha = 0.75f),
                    style = Stroke(width = (radius * 0.12f).coerceIn(1.8f, 4.5f))
                )
            }
        }
        GalaxyType.RING_GALAXY -> {
            drawOval(
                color = galaxy.armColor.copy(alpha = 0.85f),
                topLeft = Offset(center.x - radius, center.y - (radius * galaxy.diskTiltRad)),
                size = Size(radius * 2f, radius * 2f * galaxy.diskTiltRad),
                style = Stroke(width = 3.5f)
            )
            drawCircle(
                color = galaxy.coreColor,
                radius = radius * 0.3f,
                center = center
            )
        }
        GalaxyType.STARBURST -> {
            drawOval(
                color = galaxy.coreColor,
                topLeft = Offset(center.x - radius * 1.2f, center.y - (radius * 0.35f)),
                size = Size(radius * 2.4f, radius * 0.7f)
            )
            drawLine(
                color = Color(0xFFFF5252).copy(alpha = 0.75f),
                start = Offset(center.x, center.y - radius * 0.9f),
                end = Offset(center.x, center.y + radius * 0.9f),
                strokeWidth = 3f
            )
        }
        else -> {
            drawOval(
                brush = Brush.radialGradient(
                    colors = listOf(Color.White, galaxy.coreColor, galaxy.armColor.copy(alpha = 0.4f), Color.Transparent),
                    center = center,
                    radius = radius
                ),
                topLeft = Offset(center.x - radius, center.y - (radius * galaxy.diskTiltRad)),
                size = Size(radius * 2f, radius * 2f * galaxy.diskTiltRad)
            )
        }
    }

    // 3. Central Luminous Core Bulge
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(
                Color.White,
                galaxy.coreColor,
                Color.Transparent
            ),
            center = center,
            radius = radius * 0.45f * pulseFactor
        ),
        radius = radius * 0.45f * pulseFactor,
        center = center
    )

    // 4. Selection Reticle
    if (isSelected) {
        drawCircle(
            color = Color(0xFF00E5FF),
            radius = radius * 1.6f,
            center = center,
            style = Stroke(width = 2.2f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f), 0f))
        )
    }

    // 5. Galaxy Name Badge
    val galaxyName = if (currentLanguage == AppLanguage.ENGLISH) galaxy.nameEn else galaxy.nameBn
    val labelText = "🌌 $galaxyName"
    val textResult = textMeasurer.measure(
        text = AnnotatedString(labelText),
        style = TextStyle(
            color = if (isSelected) Color(0xFF00E5FF) else StarlightWhite,
            fontSize = if (isSelected) 12.sp else 10.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
        )
    )

    val badgeX = center.x - (textResult.size.width / 2f)
    val badgeY = center.y + (radius * galaxy.diskTiltRad) + 6f

    drawRoundRect(
        color = Color(0xDD080D20),
        topLeft = Offset(badgeX - 6f, badgeY - 2f),
        size = Size(textResult.size.width + 12f, textResult.size.height + 4f),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(6f, 6f)
    )

    drawText(
        textLayoutResult = textResult,
        topLeft = Offset(badgeX, badgeY)
    )
}

/**
 * Draws the 3D projected orbit path for a planet
 */
private fun DrawScope.draw3DOrbitPath(
    body: CelestialBody,
    cameraPitch: Float,
    cameraYaw: Float,
    cameraDistance: Float,
    centerOffset: Vector3D,
    viewportWidth: Float,
    viewportHeight: Float,
    isSelected: Boolean
) {
    val segments = 48
    val path = Path()
    var firstPoint = true
    val incRad = Math.toRadians(body.orbitInclinationDeg.toDouble()).toFloat()
    val r = body.orbitDistanceUnits

    for (i in 0..segments) {
        val theta = (i.toFloat() / segments) * (2f * PI.toFloat())
        val worldPos = Vector3D(
            x = r * cos(theta),
            y = r * sin(theta) * sin(incRad),
            z = r * sin(theta) * cos(incRad)
        )
        val proj = ProjectionEngine.project(
            worldPos = worldPos,
            cameraPitch = cameraPitch,
            cameraYaw = cameraYaw,
            cameraDistance = cameraDistance,
            viewportWidth = viewportWidth,
            viewportHeight = viewportHeight,
            centerOffset = centerOffset
        )

        if (proj.isVisible) {
            val pt = Offset(proj.screenX, proj.screenY)
            if (firstPoint) {
                path.moveTo(pt.x, pt.y)
                firstPoint = false
            } else {
                path.lineTo(pt.x, pt.y)
            }
        }
    }

    val orbitColor = if (isSelected) {
        Color(0xFF00E5FF).copy(alpha = 0.85f)
    } else {
        body.glowColor.copy(alpha = 0.22f)
    }

    drawPath(
        path = path,
        color = orbitColor,
        style = Stroke(
            width = if (isSelected) 2.2f else 1.0f,
            pathEffect = if (!isSelected) PathEffect.dashPathEffect(floatArrayOf(8f, 10f), 0f) else null
        )
    )
}

/**
 * Draws Halley's Comet with dynamic 3D ion tail
 */
private fun DrawScope.drawHalleysComet(
    effectiveTime: Float,
    cameraPitch: Float,
    cameraYaw: Float,
    cameraDistance: Float,
    centerOffset: Vector3D,
    viewportWidth: Float,
    viewportHeight: Float
) {
    val cometSpeed = 0.008f
    val theta = effectiveTime * cometSpeed
    val a = 280f
    val e = 0.72f
    val r = (a * (1f - e * e)) / (1f + e * cos(theta))

    val cometPos = Vector3D(
        x = r * cos(theta),
        y = (r * sin(theta)) * 0.3f,
        z = r * sin(theta)
    )

    val proj = ProjectionEngine.project(
        worldPos = cometPos,
        cameraPitch = cameraPitch,
        cameraYaw = cameraYaw,
        cameraDistance = cameraDistance,
        viewportWidth = viewportWidth,
        viewportHeight = viewportHeight,
        centerOffset = centerOffset
    )

    if (proj.isVisible) {
        val head = Offset(proj.screenX, proj.screenY)
        val tailVector3D = cometPos.normalized() * (45f * (200f / r.coerceAtLeast(60f)))
        val tailEndWorld = cometPos + tailVector3D
        val tailProj = ProjectionEngine.project(
            worldPos = tailEndWorld,
            cameraPitch = cameraPitch,
            cameraYaw = cameraYaw,
            cameraDistance = cameraDistance,
            viewportWidth = viewportWidth,
            viewportHeight = viewportHeight,
            centerOffset = centerOffset
        )

        drawLine(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color(0xFF80D8FF).copy(alpha = 0.9f),
                    Color(0x4400E5FF),
                    Color.Transparent
                ),
                start = head,
                end = Offset(tailProj.screenX, tailProj.screenY)
            ),
            start = head,
            end = Offset(tailProj.screenX, tailProj.screenY),
            strokeWidth = 3.5f * proj.scaleFactor
        )

        drawCircle(
            color = Color(0xFFE0F7FA),
            radius = 3.5f * proj.scaleFactor,
            center = head
        )
    }
}

/**
 * Draws the central Sun with solar flare corona
 */
private fun DrawScope.drawSun(
    center: Offset,
    radius: Float,
    coronaPulse: Float
) {
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(
                Color(0x88FFB300),
                Color(0x44FF5722),
                Color.Transparent
            ),
            center = center,
            radius = radius * 2.8f * coronaPulse
        ),
        radius = radius * 2.8f * coronaPulse,
        center = center
    )

    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(
                Color(0xFFFFFFFF),
                Color(0xFFFFF176),
                Color(0xFFFFB300),
                Color(0xFFFF6F00)
            ),
            center = center,
            radius = radius
        ),
        radius = radius,
        center = center
    )
}

/**
 * Draws a planet with 3D day/night hemisphere lighting and rings if present
 */
private fun DrawScope.drawPlanetSphere(
    body: CelestialBody,
    center: Offset,
    radius: Float,
    worldPos: Vector3D,
    isSelected: Boolean,
    cameraYaw: Float,
    cameraPitch: Float
) {
    val lightDir = (-worldPos).normalized()
    val lightView = lightDir.rotateY(cameraYaw).rotateX(cameraPitch)
    val lightCenterOffset = Offset(lightView.x * radius * 0.45f, lightView.y * radius * 0.45f)

    if (body.atmosphereColor != null) {
        drawCircle(
            color = body.atmosphereColor.copy(alpha = 0.4f),
            radius = radius * 1.22f,
            center = center
        )
    }

    if (body.ringConfig != null) {
        drawSaturnRingHalf(
            center = center,
            planetRadius = radius,
            ringConfig = body.ringConfig,
            isFrontHalf = false
        )
    }

    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(
                body.glowColor,
                body.primaryColor,
                body.secondaryColor,
                Color(0xFF03050C)
            ),
            center = center + lightCenterOffset,
            radius = radius * 1.15f
        ),
        radius = radius,
        center = center
    )

    if (body.ringConfig != null) {
        drawSaturnRingHalf(
            center = center,
            planetRadius = radius,
            ringConfig = body.ringConfig,
            isFrontHalf = true
        )
    }

    if (isSelected) {
        drawCircle(
            color = Color(0xFF00E5FF),
            radius = radius + 15f,
            center = center,
            style = Stroke(width = 2f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f), 0f))
        )
    }
}

private fun DrawScope.drawSaturnRingHalf(
    center: Offset,
    planetRadius: Float,
    ringConfig: com.example.data.model.RingConfig,
    isFrontHalf: Boolean
) {
    val rx = planetRadius * ringConfig.outerRadiusFactor
    val ringStrokeWidth = (planetRadius * (ringConfig.outerRadiusFactor - ringConfig.innerRadiusFactor)).coerceAtLeast(3f)
    val ringMidRadius = planetRadius * ((ringConfig.outerRadiusFactor + ringConfig.innerRadiusFactor) / 2f)

    val startAngle = if (isFrontHalf) 0f else 180f
    val sweepAngle = 180f

    drawArc(
        brush = Brush.linearGradient(
            colors = listOf(
                ringConfig.ringColorEdge,
                ringConfig.ringColor,
                ringConfig.ringColorEdge
            ),
            start = Offset(center.x - rx, center.y),
            end = Offset(center.x + rx, center.y)
        ),
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = false,
        topLeft = Offset(center.x - ringMidRadius, center.y - (ringMidRadius * 0.32f)),
        size = Size(ringMidRadius * 2f, ringMidRadius * 0.64f),
        style = Stroke(width = ringStrokeWidth)
    )
}

@OptIn(ExperimentalTextApi::class)
private fun DrawScope.drawPlanetLabel(
    textMeasurer: TextMeasurer,
    body: CelestialBody,
    center: Offset,
    radius: Float,
    isSelected: Boolean,
    currentLanguage: AppLanguage
) {
    val labelText = if (currentLanguage == AppLanguage.ENGLISH) {
        "${body.nameEn} (${body.nameBn})"
    } else {
        "${body.nameBn} (${body.nameEn})"
    }

    val textLayoutResult = textMeasurer.measure(
        text = AnnotatedString(labelText),
        style = TextStyle(
            color = if (isSelected) Color(0xFF00E5FF) else Color(0xFFE0E0E0),
            fontSize = if (isSelected) 12.sp else 10.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    )

    val labelX = center.x - (textLayoutResult.size.width / 2f)
    val labelY = center.y + radius + 8f

    drawCircle(
        color = if (isSelected) Color(0xFF00E5FF) else Color(0x88FFFFFF),
        radius = 2.5f,
        center = Offset(center.x, center.y + radius + 3f)
    )

    drawRoundRect(
        color = Color(0xBB0A0F24),
        topLeft = Offset(labelX - 6f, labelY - 2f),
        size = Size(
            textLayoutResult.size.width + 12f,
            textLayoutResult.size.height + 4f
        ),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(6f, 6f)
    )

    drawText(
        textLayoutResult = textLayoutResult,
        topLeft = Offset(labelX, labelY)
    )
}
