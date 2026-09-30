package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.datasource.GalaxyData
import com.example.data.datasource.SolarSystemData
import com.example.data.model.CelestialBody
import com.example.ui.components.CosmicAudioDialog
import com.example.ui.components.CosmicBackground
import com.example.ui.components.ExoplanetExplorerDialog
import com.example.ui.components.GalaxyDetailDialog
import com.example.ui.components.LanguageSettingsDialog
import com.example.ui.components.PlanetaryTimeCard
import com.example.ui.components.PlanetaryTimeDialog
import com.example.ui.components.SolarSystem3DCanvas
import com.example.ui.components.TimeWarpDialog
import com.example.ui.components.VoyagerDetailDialog
import com.example.ui.localization.AppLanguage
import com.example.ui.localization.CosmicStrings
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.CosmicDomain
import com.example.ui.viewmodel.CosmicViewModel

@Composable
fun SolarSystemScreen(
    viewModel: CosmicViewModel
) {
    val currentLanguage by viewModel.currentLanguage.collectAsState()
    val isEnglish = currentLanguage == AppLanguage.ENGLISH

    val isFullScreenMode by viewModel.isFullScreenMode.collectAsState()
    val selectedBody by viewModel.selectedBody.collectAsState()
    val selectedGalaxy by viewModel.selectedGalaxy.collectAsState()
    val focusedBody by viewModel.focusedBody.collectAsState()
    val orbitSpeed by viewModel.orbitSpeed.collectAsState()
    val cosmicDomain by viewModel.cosmicDomain.collectAsState()
    val cameraZoom by viewModel.cameraZoomMultiplier.collectAsState()
    val userAgeInput by viewModel.userAgeEarthYears.collectAsState()
    val showTimeDialog by viewModel.showTimeDialog.collectAsState()

    val showVoyagerDialog by viewModel.showVoyagerDialog.collectAsState()
    val showExoplanetDialog by viewModel.showExoplanetDialog.collectAsState()
    val showTimeWarpDialog by viewModel.showTimeWarpDialog.collectAsState()
    val isAudioPlaying by viewModel.isAudioPlaying.collectAsState()
    val isGyroEnabled by viewModel.isGyroEnabled.collectAsState()

    var showLanguageDialog by remember { mutableStateOf(false) }
    var showAudioDialog by remember { mutableStateOf(false) }

    // Handle Android system back button in fullscreen mode to exit cleanly
    if (isFullScreenMode) {
        BackHandler {
            viewModel.toggleFullScreenMode(false)
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(SpaceBlack)) {
        // Deep Space background with twinkling stars
        CosmicBackground(particleCount = if (isFullScreenMode) 120 else 80)

        // 3D Solar System & Deep Space Galaxies Canvas (smooth, calm and graceful)
        SolarSystem3DCanvas(
            modifier = Modifier.fillMaxSize().testTag("solar_system_3d_canvas"),
            orbitSpeedMultiplier = orbitSpeed,
            cameraZoomMultiplier = cameraZoom,
            currentLanguage = currentLanguage,
            selectedBodyId = selectedBody.id,
            selectedGalaxyId = selectedGalaxy?.id,
            focusedBody = focusedBody,
            onZoomChange = { delta ->
                viewModel.applyZoomFactor(delta)
            },
            onBodySelected = { body ->
                viewModel.selectCelestialBody(body)
            },
            onGalaxySelected = { galaxy ->
                viewModel.selectGalaxy(galaxy)
            }
        )

        // ===================== FULL SCREEN HUD =====================
        if (isFullScreenMode) {
            // Top Bar in Full Screen
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Exit Fullscreen Button
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Color(0xCC090E22),
                        border = androidx.compose.foundation.BorderStroke(1.dp, PulsarCyan),
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { viewModel.toggleFullScreenMode(false) }
                            .testTag("exit_fullscreen_button")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.FullscreenExit,
                                contentDescription = "Exit Fullscreen",
                                tint = PulsarCyan,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = CosmicStrings.exitFullscreen(currentLanguage),
                                color = StarlightWhite,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Domain / Scale Selector Chips
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xCC090E22))
                            .border(1.dp, CosmicBorder, RoundedCornerShape(16.dp))
                            .padding(3.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        for (domain in CosmicDomain.values()) {
                            val isDomainActive = (cosmicDomain == domain)
                            val domainLabel = if (isEnglish) domain.labelEn else domain.labelBn
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (isDomainActive) PulsarCyan else Color.Transparent)
                                    .clickable { viewModel.setCosmicDomain(domain) }
                                    .padding(horizontal = 10.dp, vertical = 5.dp)
                            ) {
                                Text(
                                    text = domainLabel,
                                    color = if (isDomainActive) SpaceBlack else StarlightMuted,
                                    fontSize = 11.sp,
                                    fontWeight = if (isDomainActive) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }

                    // Quick Language Switcher
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xCC090E22),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CosmicBorder),
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { showLanguageDialog = true }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Language,
                                contentDescription = "Language",
                                tint = PulsarCyan,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = currentLanguage.flagOrIcon,
                                color = PulsarCyan,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Right Floating Zoom & Orbit Speed Controls
            Column(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Zoom In Button (+)
                IconButton(
                    onClick = { viewModel.zoomIn() },
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color(0xDD090E22))
                        .border(1.dp, CosmicBorder, CircleShape)
                        .testTag("zoom_in_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Zoom In",
                        tint = PulsarCyan,
                        modifier = Modifier.size(22.dp)
                    )
                }

                // Current Zoom Multiplier Badge
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xCC090E22),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CosmicBorder)
                ) {
                    Text(
                        text = String.format("%.1fx", cameraZoom),
                        color = StarlightWhite,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                    )
                }

                // Zoom Out Button (-)
                IconButton(
                    onClick = { viewModel.zoomOut() },
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color(0xDD090E22))
                        .border(1.dp, CosmicBorder, CircleShape)
                        .testTag("zoom_out_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = "Zoom Out",
                        tint = PulsarCyan,
                        modifier = Modifier.size(22.dp)
                    )
                }

                // Reset Camera Button
                IconButton(
                    onClick = { viewModel.resetCamera() },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xDD090E22))
                        .border(1.dp, CosmicBorder, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.RestartAlt,
                        contentDescription = "Reset Camera",
                        tint = StarGold,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Quick Play/Pause Orbit Button in Fullscreen
                IconButton(
                    onClick = { viewModel.toggleOrbitPlayPause() },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(if (orbitSpeed == 0f) Color(0xDD090E22) else PulsarCyan.copy(alpha = 0.3f))
                        .border(1.dp, if (orbitSpeed == 0f) StarGold else PulsarCyan, CircleShape)
                ) {
                    Icon(
                        imageVector = if (orbitSpeed == 0f) Icons.Default.PlayArrow else Icons.Default.Pause,
                        contentDescription = "Play/Pause Orbit",
                        tint = if (orbitSpeed == 0f) StarGold else PulsarCyan,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Inspect Planetary Time in Fullscreen
                IconButton(
                    onClick = { viewModel.openTimeDialog() },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xDD090E22))
                        .border(1.dp, StarGold, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = "Planetary Time",
                        tint = StarGold,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Bottom Full Screen Floating Hint
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xCC090E22),
                border = androidx.compose.foundation.BorderStroke(1.dp, CosmicBorder),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
                    .padding(bottom = 16.dp, start = 20.dp, end = 20.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.RotateRight,
                        contentDescription = "360 rotation",
                        tint = PulsarCyan,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = CosmicStrings.fullScreenHint(currentLanguage),
                        color = StarlightWhite,
                        fontSize = 11.sp,
                        lineHeight = 15.sp
                    )
                }
            }
        } else {
            // ===================== NORMAL DASHBOARD OVERLAY =====================
            // Top Header Overlay
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = CosmicStrings.appTitle(currentLanguage),
                            color = PulsarCyan,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = CosmicStrings.appSubtitle(currentLanguage),
                            color = StarlightMuted,
                            fontSize = 12.sp
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Focus Indicator
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = if (focusedBody != null) CosmicSurfaceLight else Color(0x66101730),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (focusedBody != null) PulsarCyan else CosmicBorder
                            )
                        ) {
                            val displayName = if (isEnglish) selectedBody.nameEn else selectedBody.nameBn
                            Row(
                                modifier = Modifier
                                    .clickable { viewModel.toggleFocusLock(selectedBody) }
                                    .padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = if (focusedBody != null) Icons.Default.FilterCenterFocus else Icons.Default.CenterFocusWeak,
                                    contentDescription = "Focus",
                                    tint = if (focusedBody != null) PulsarCyan else StarlightMuted,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (focusedBody != null) CosmicStrings.focusedOn(displayName, currentLanguage) else CosmicStrings.focusUnlocked(currentLanguage),
                                    color = if (focusedBody != null) PulsarCyan else StarlightMuted,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        // PROMINENT TOP FULL SCREEN MODE BUTTON (Direct, impossible to miss)
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = Color(0x3300E676),
                            border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF00E676)),
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .clickable { viewModel.toggleFullScreenMode(true) }
                                .testTag("top_fullscreen_mode_button")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Fullscreen,
                                    contentDescription = "Full Screen Mode",
                                    tint = Color(0xFF00E676),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = CosmicStrings.enterFullscreenButton(currentLanguage),
                                    color = Color(0xFF00E676),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        // Language Toggle Icon Button
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = CosmicSurfaceLight,
                            border = androidx.compose.foundation.BorderStroke(1.dp, PulsarCyan.copy(alpha = 0.6f)),
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .clickable { showLanguageDialog = true }
                                .testTag("language_toggle_button")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Language,
                                    contentDescription = "Language",
                                    tint = PulsarCyan,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = currentLanguage.flagOrIcon,
                                    color = StarlightWhite,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Horizontal Planet Quick Selector Carousel
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (body in SolarSystemData.celestialBodies) {
                        val isSelected = body.id == selectedBody.id
                        val planetLabel = if (isEnglish) body.nameEn else body.nameBn
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = if (isSelected) CosmicSurfaceLight else Color(0x880C1226),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) body.primaryColor else CosmicBorder.copy(alpha = 0.5f)
                            ),
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .clickable {
                                    viewModel.selectCelestialBody(body)
                                }
                                .testTag("planet_chip_${body.id}")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(body.primaryColor)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = planetLabel,
                                    color = if (isSelected) StarlightWhite else StarlightMuted,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // PREMIER COSMIC DISCOVERIES ACTION BAR
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Fullscreen 360 Mode Button (First & Most Prominent)
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0x4400E676),
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF00E676)),
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { viewModel.toggleFullScreenMode(true) }
                            .testTag("premier_fullscreen_mode_button")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Fullscreen,
                                contentDescription = "Fullscreen 360",
                                tint = Color(0xFF00E676),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "⛶ ${CosmicStrings.enterFullscreenButton(currentLanguage)}",
                                color = Color(0xFF00E676),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Time Machine / Orrery Warp
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0x33FFB300),
                        border = androidx.compose.foundation.BorderStroke(1.dp, StarGold.copy(alpha = 0.7f)),
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { viewModel.openTimeWarpDialog() }
                            .testTag("time_machine_button")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.HourglassBottom,
                                contentDescription = "Time Machine",
                                tint = StarGold,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isEnglish) "Time Machine" else "টাইম মেশিন",
                                color = StarGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Space Sound & Acoustic Resonance Synthesizer
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = if (isAudioPlaying) PulsarCyan.copy(alpha = 0.25f) else Color(0x22101730),
                        border = androidx.compose.foundation.BorderStroke(1.dp, if (isAudioPlaying) PulsarCyan else CosmicBorder),
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { showAudioDialog = true }
                            .testTag("cosmic_sound_button")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (isAudioPlaying) Icons.Default.GraphicEq else Icons.Default.VolumeUp,
                                contentDescription = "Space Sound",
                                tint = if (isAudioPlaying) PulsarCyan else StarlightMuted,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isEnglish) {
                                    if (isAudioPlaying) "Sound: Playing" else "Space Sound"
                                } else {
                                    if (isAudioPlaying) "শব্দ: চলছে" else "মহাজাগতিক শব্দ"
                                },
                                color = if (isAudioPlaying) PulsarCyan else StarlightMuted,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Voyager 1 Telemetry
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0x3300E5FF),
                        border = androidx.compose.foundation.BorderStroke(1.dp, PulsarCyan.copy(alpha = 0.7f)),
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { viewModel.openVoyagerDialog() }
                            .testTag("voyager_telemetry_button")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.RocketLaunch,
                                contentDescription = "Voyager 1",
                                tint = PulsarCyan,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isEnglish) "Voyager 1" else "ভয়েজার ১",
                                color = PulsarCyan,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Exoplanet Explorer
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0x33B388FF),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFB388FF).copy(alpha = 0.7f)),
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .clickable { viewModel.openExoplanetDialog() }
                            .testTag("exoplanet_explorer_button")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Public,
                                contentDescription = "Exoplanets",
                                tint = Color(0xFFB388FF),
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isEnglish) "Exoplanets" else "বহির্জাগতিক গ্রহ",
                                color = Color(0xFFB388FF),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Bottom Controls and Selected Celestial Preview Card
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
                    .padding(bottom = 76.dp, start = 16.dp, end = 16.dp)
            ) {
                // Quick Fullscreen Entry Floating Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Color(0xDD09152A),
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF00E676)),
                        shadowElevation = 8.dp,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { viewModel.toggleFullScreenMode(true) }
                            .testTag("floating_fullscreen_entry_button")
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Fullscreen,
                                contentDescription = "Full Screen Mode",
                                tint = Color(0xFF00E676),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = CosmicStrings.enterFullscreenButton(currentLanguage),
                                color = Color(0xFF00E676),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                // Speed & Domain Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xCC090E22))
                        .border(1.dp, CosmicBorder, RoundedCornerShape(16.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Domain / Scale Selector
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        for (domain in CosmicDomain.values()) {
                            val isDomainActive = (cosmicDomain == domain)
                            val domainText = if (isEnglish) domain.labelEn else domain.labelBn
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isDomainActive) PulsarCyan else Color.Transparent)
                                    .clickable { viewModel.setCosmicDomain(domain) }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = domainText,
                                    color = if (isDomainActive) SpaceBlack else StarlightMuted,
                                    fontSize = 11.sp,
                                    fontWeight = if (isDomainActive) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }

                    // Orbits Play/Pause & Speed Controller (Paused by default so user can inspect calmly)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Play / Pause Icon Button
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (orbitSpeed == 0f) StarGold.copy(alpha = 0.25f) else PulsarCyan.copy(alpha = 0.25f))
                                .border(1.dp, if (orbitSpeed == 0f) StarGold else PulsarCyan, RoundedCornerShape(6.dp))
                                .clickable { viewModel.toggleOrbitPlayPause() }
                                .padding(horizontal = 6.dp, vertical = 3.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (orbitSpeed == 0f) Icons.Default.PlayArrow else Icons.Default.Pause,
                                    contentDescription = "Toggle Orbit Motion",
                                    tint = if (orbitSpeed == 0f) StarGold else PulsarCyan,
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = if (orbitSpeed == 0f) {
                                        if (isEnglish) "Paused" else "স্থির"
                                    } else {
                                        if (isEnglish) "Orbiting" else "ঘূর্ণায়মান"
                                    },
                                    color = if (orbitSpeed == 0f) StarGold else PulsarCyan,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        val speeds = listOf(
                            0.0f to if (isEnglish) "0x" else "০x",
                            0.1f to if (isEnglish) "0.1x" else "০.১x",
                            0.3f to if (isEnglish) "0.3x" else "০.৩x",
                            0.5f to if (isEnglish) "0.5x" else "০.৫x"
                        )
                        for ((spd, label) in speeds) {
                            val isSpeedActive = (orbitSpeed == spd)
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (isSpeedActive) PulsarCyan.copy(alpha = 0.35f) else Color.Transparent)
                                    .border(
                                        1.dp,
                                        if (isSpeedActive) PulsarCyan else Color.Transparent,
                                        RoundedCornerShape(6.dp)
                                    )
                                    .clickable { viewModel.setOrbitSpeed(spd) }
                                    .padding(horizontal = 5.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = label,
                                    color = if (isSpeedActive) PulsarCyan else StarlightMuted,
                                    fontSize = 10.sp,
                                    fontWeight = if (isSpeedActive) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Selected Planet Info, Orbital Year & Time Preview Card
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = CosmicSurface.copy(alpha = 0.94f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CosmicBorder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("planet_preview_card")
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(CircleShape)
                                        .background(
                                            Brush.radialGradient(
                                                listOf(selectedBody.glowColor, selectedBody.primaryColor)
                                            )
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    val initialLetter = if (isEnglish) selectedBody.nameEn.take(1) else selectedBody.nameBn.take(1)
                                    Text(
                                        text = initialLetter,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = if (isEnglish) selectedBody.nameEn else selectedBody.nameBn,
                                            color = StarlightWhite,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = if (isEnglish) "(${selectedBody.nameBn})" else "(${selectedBody.nameEn})",
                                            color = StarlightMuted,
                                            fontSize = 12.sp
                                        )
                                    }
                                    Text(
                                        text = if (isEnglish) selectedBody.type.labelEn else selectedBody.type.labelBn,
                                        color = PulsarCyan,
                                        fontSize = 11.sp
                                    )
                                }
                            }

                            // Bookmark Toggle Button
                            val isBookmarked = viewModel.isBookmarked(selectedBody.id)
                            IconButton(
                                onClick = {
                                    viewModel.toggleBookmark(
                                        targetId = selectedBody.id,
                                        nameEn = selectedBody.nameEn,
                                        nameBn = selectedBody.nameBn,
                                        category = if (isEnglish) selectedBody.type.labelEn else selectedBody.type.labelBn
                                    )
                                },
                                modifier = Modifier.testTag("bookmark_button_${selectedBody.id}")
                            ) {
                                Icon(
                                    imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                    contentDescription = "Bookmark",
                                    tint = if (isBookmarked) StarGold else StarlightMuted
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        // Tagline
                        Text(
                            text = if (isEnglish) selectedBody.tagLineEn else selectedBody.tagLineBn,
                            color = StarlightMuted,
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // PROMINENT TIME & ORBIT METRICS ROW (Clickable to open full Time Dossier)
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0x33101730),
                            border = androidx.compose.foundation.BorderStroke(1.dp, StarGold.copy(alpha = 0.5f)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { viewModel.openTimeDialog() }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Public,
                                            contentDescription = "Year",
                                            tint = StarGold,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = if (isEnglish) "1 YEAR (Orbit):" else "১ বছর (সূর্য প্রদক্ষিণ):",
                                            color = StarGold,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Text(
                                        text = if (isEnglish) selectedBody.timeInfo.yearSummaryEn else selectedBody.timeInfo.yearSummaryBn,
                                        color = StarlightWhite,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }

                                VerticalDivider(
                                    modifier = Modifier.height(30.dp),
                                    color = CosmicBorder,
                                    thickness = 1.dp
                                )

                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(start = 8.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.RotateRight,
                                            contentDescription = "Day",
                                            tint = PulsarCyan,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = if (isEnglish) "1 DAY (Axial Spin):" else "১ দিন (নিজের অক্ষে):",
                                            color = PulsarCyan,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Text(
                                        text = if (isEnglish) selectedBody.timeInfo.daySummaryEn else selectedBody.timeInfo.daySummaryBn,
                                        color = StarlightWhite,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }

                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = "Open Time Details",
                                    tint = StarGold,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Action Buttons: Time Calculator, Space Sound & 3D Globe Inspector
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = { viewModel.openTimeDialog() },
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = StarGold),
                                border = androidx.compose.foundation.BorderStroke(1.dp, StarGold),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp)
                                    .testTag("open_time_calculator_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Schedule,
                                    contentDescription = "Time Calculator",
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (isEnglish) "Time & Year" else "সময় ও বছর",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            OutlinedButton(
                                onClick = { showAudioDialog = true },
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = PulsarCyan),
                                border = androidx.compose.foundation.BorderStroke(1.dp, PulsarCyan),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .height(44.dp)
                                    .testTag("open_sound_dialog_button")
                            ) {
                                Icon(
                                    imageVector = if (isAudioPlaying) Icons.Default.GraphicEq else Icons.Default.VolumeUp,
                                    contentDescription = "Space Sound",
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (isEnglish) "Space Sound" else "শব্দ শুনুন",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Button(
                                onClick = {
                                    viewModel.selectCelestialBody(selectedBody, navigateToDetail = true)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = PulsarCyan,
                                    contentColor = SpaceBlack
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .weight(1.1f)
                                    .height(44.dp)
                                    .testTag("inspect_planet_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Explore,
                                    contentDescription = "Explore",
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                val targetName = if (isEnglish) selectedBody.nameEn else selectedBody.nameBn
                                Text(
                                    text = if (isEnglish) "Explore 3D" else "$targetName ৩ডি",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // ===================== GALAXY DETAIL MODAL =====================
        if (selectedGalaxy != null) {
            val galaxy = selectedGalaxy!!
            val isGalaxyBookmarked = viewModel.isBookmarked(galaxy.id)
            GalaxyDetailDialog(
                galaxy = galaxy,
                currentLanguage = currentLanguage,
                isBookmarked = isGalaxyBookmarked,
                onToggleBookmark = {
                    viewModel.toggleBookmark(
                        targetId = galaxy.id,
                        nameEn = galaxy.nameEn,
                        nameBn = galaxy.nameBn,
                        category = if (isEnglish) galaxy.type.labelEn else galaxy.type.labelBn
                    )
                },
                onDismiss = {
                    viewModel.selectGalaxy(null)
                }
            )
        }

        // ===================== PLANETARY TIME & YEAR DOSSIER DIALOG =====================
        if (showTimeDialog) {
            PlanetaryTimeDialog(
                body = selectedBody,
                currentLanguage = currentLanguage,
                earthAgeInput = userAgeInput,
                onEarthAgeChanged = { viewModel.updateUserAge(it) },
                onDismiss = { viewModel.closeTimeDialog() }
            )
        }

        // ===================== COSMIC AUDIO RESONANCE DIALOG =====================
        if (showAudioDialog) {
            CosmicAudioDialog(
                body = selectedBody,
                currentLanguage = currentLanguage,
                isPlaying = isAudioPlaying,
                onTogglePlay = { viewModel.toggleAudio() },
                onDismiss = { showAudioDialog = false }
            )
        }

        // ===================== TIME WARP DIALOG =====================
        if (showTimeWarpDialog) {
            TimeWarpDialog(
                currentLanguage = currentLanguage,
                onApplyEpoch = { viewModel.applyEpoch(it) },
                onDismiss = { viewModel.closeTimeWarpDialog() }
            )
        }

        // ===================== VOYAGER TELEMETRY DIALOG =====================
        if (showVoyagerDialog) {
            VoyagerDetailDialog(
                currentLanguage = currentLanguage,
                onDismiss = { viewModel.closeVoyagerDialog() }
            )
        }

        // ===================== EXOPLANET EXPLORER DIALOG =====================
        if (showExoplanetDialog) {
            ExoplanetExplorerDialog(
                currentLanguage = currentLanguage,
                onDismiss = { viewModel.closeExoplanetDialog() }
            )
        }

        // ===================== LANGUAGE SETTINGS DIALOG =====================
        if (showLanguageDialog) {
            LanguageSettingsDialog(
                currentLanguage = currentLanguage,
                onLanguageSelected = { lang ->
                    viewModel.setLanguage(lang)
                },
                onDismiss = {
                    showLanguageDialog = false
                }
            )
        }
    }
}
