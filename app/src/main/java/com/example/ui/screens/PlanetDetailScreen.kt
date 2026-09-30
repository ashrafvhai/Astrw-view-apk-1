package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.CelestialBody
import com.example.ui.components.CosmicAudioDialog
import com.example.ui.components.CosmicBackground
import com.example.ui.components.LanguageSettingsDialog
import com.example.ui.components.PlanetGlobe3DCanvas
import com.example.ui.components.PlanetaryTimeCard
import com.example.ui.localization.AppLanguage
import com.example.ui.localization.CosmicStrings
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.CosmicViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanetDetailScreen(
    viewModel: CosmicViewModel
) {
    BackHandler {
        viewModel.navigateTo(AppScreen.SOLAR_SYSTEM)
    }

    val currentLanguage by viewModel.currentLanguage.collectAsState()
    val isEnglish = currentLanguage == AppLanguage.ENGLISH

    val body by viewModel.selectedBody.collectAsState()
    val showInternalLayers by viewModel.showInternalLayers.collectAsState()
    val userWeightInput by viewModel.userWeightKg.collectAsState()
    val userAgeInput by viewModel.userAgeEarthYears.collectAsState()
    val isBookmarked = viewModel.isBookmarked(body.id)
    val isAudioPlaying by viewModel.isAudioPlaying.collectAsState()

    var showLanguageDialog by remember { mutableStateOf(false) }
    var showAudioDialog by remember { mutableStateOf(false) }
    var isGlobeFullscreen by remember { mutableStateOf(false) }

    // Calculate weight & jump height
    val earthWeight = userWeightInput.toDoubleOrNull() ?: 65.0
    val planetWeight = earthWeight * (body.surfaceGravityMps2 / 9.807)
    val baseEarthJumpMeters = 0.5
    val planetJumpMeters = baseEarthJumpMeters * (9.807 / body.surfaceGravityMps2.coerceAtLeast(0.1))

    val planetDisplayName = if (isEnglish) body.nameEn else body.nameBn
    val planetSubName = if (isEnglish) body.nameBn else body.nameEn
    val typeLabel = if (isEnglish) body.type.labelEn else body.type.labelBn

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "$planetDisplayName ($planetSubName)",
                            color = StarlightWhite,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = typeLabel,
                            color = PulsarCyan,
                            fontSize = 12.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = { viewModel.navigateTo(AppScreen.SOLAR_SYSTEM) },
                        modifier = Modifier.testTag("back_button_planet_detail")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = StarlightWhite
                        )
                    }
                },
                actions = {
                    // Full Screen 3D Globe Mode Button
                    IconButton(
                        onClick = { isGlobeFullscreen = true },
                        modifier = Modifier.testTag("fullscreen_button_planet_detail")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Fullscreen,
                            contentDescription = "Full Screen 3D Globe",
                            tint = Color(0xFF00E676)
                        )
                    }

                    // Cosmic Sound Frequency Button
                    IconButton(
                        onClick = { showAudioDialog = true },
                        modifier = Modifier.testTag("sound_button_planet_detail")
                    ) {
                        Icon(
                            imageVector = if (isAudioPlaying) Icons.Default.GraphicEq else Icons.Default.VolumeUp,
                            contentDescription = "Cosmic Sound",
                            tint = if (isAudioPlaying) PulsarCyan else StarlightWhite
                        )
                    }

                    // Language Switcher
                    IconButton(onClick = { showLanguageDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Language,
                            contentDescription = "Language",
                            tint = PulsarCyan
                        )
                    }

                    // Bookmark Button
                    IconButton(
                        onClick = {
                            viewModel.toggleBookmark(
                                targetId = body.id,
                                nameEn = body.nameEn,
                                nameBn = body.nameBn,
                                category = typeLabel
                            )
                        }
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (isBookmarked) StarGold else StarlightMuted
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CosmicSurface.copy(alpha = 0.95f)
                )
            )
        },
        containerColor = SpaceBlack
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            CosmicBackground(particleCount = 50)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // Interactive 3D Globe Inspector Container (calm, slow rotation)
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = CosmicSurface.copy(alpha = 0.85f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CosmicBorder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(290.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        PlanetGlobe3DCanvas(
                            modifier = Modifier.fillMaxSize().testTag("planet_globe_3d_canvas"),
                            body = body,
                            showInternalLayers = showInternalLayers
                        )

                        // 3D Interaction Hint overlay
                        Text(
                            text = if (isEnglish) "↻ Drag to rotate 360°" else "↻ গ্রহটি ৩৬০° ঘুরিয়ে দেখতে টানুন",
                            color = StarlightMuted,
                            fontSize = 11.sp,
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(top = 10.dp)
                                .background(Color(0x88060A18), RoundedCornerShape(8.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )

                        // Fullscreen 3D Globe Button
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xDD090E22),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF00E676)),
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { isGlobeFullscreen = true }
                                .testTag("globe_card_fullscreen_button")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Fullscreen,
                                    contentDescription = "Full Screen",
                                    tint = Color(0xFF00E676),
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = CosmicStrings.enterFullscreenButton(currentLanguage),
                                    color = Color(0xFF00E676),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        // Mode Selector (Surface vs Core Layers)
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 10.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xCC090E22))
                                .border(1.dp, CosmicBorder, RoundedCornerShape(12.dp))
                                .padding(4.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            ViewToggleButton(
                                text = if (isEnglish) "3D Surface" else "৩ডি পৃষ্ঠতল",
                                isSelected = !showInternalLayers,
                                onClick = { if (showInternalLayers) viewModel.toggleInternalLayers() }
                            )
                            ViewToggleButton(
                                text = if (isEnglish) "Internal Core" else "অভ্যন্তরীণ স্তর",
                                isSelected = showInternalLayers,
                                onClick = { if (!showInternalLayers) viewModel.toggleInternalLayers() }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Space Sound & Acoustic Resonance Player Card
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0x33101B3A),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PulsarCyan.copy(alpha = 0.5f)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { showAudioDialog = true }
                        .testTag("planet_audio_card")
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(PulsarCyan.copy(alpha = 0.2f))
                                    .border(1.dp, PulsarCyan, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (isAudioPlaying) Icons.Default.GraphicEq else Icons.Default.VolumeUp,
                                    contentDescription = "Sound",
                                    tint = PulsarCyan,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = if (isEnglish) "Listen to Space Sound & Frequencies" else "মহাজাগতিক ধ্বনি ও কম্পাঙ্ক শুনুন",
                                    color = StarlightWhite,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = if (isEnglish) "Electromagnetic plasma resonance" else "নাসা ও এসা কর্তৃক ধারণকৃত প্লাজমা তরঙ্গ",
                                    color = PulsarCyan,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        Icon(
                            imageVector = Icons.Default.PlayCircle,
                            contentDescription = "Play",
                            tint = PulsarCyan,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Tagline & Overview
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = CosmicSurface,
                    border = androidx.compose.foundation.BorderStroke(1.dp, CosmicBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = if (isEnglish) "Overview" else "সংক্ষিপ্ত পরিচিতি",
                            color = PulsarCyan,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = if (isEnglish) body.tagLineEn else body.tagLineBn,
                            color = StarlightWhite,
                            fontSize = 14.sp,
                            lineHeight = 20.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // PROMINENT TIME & ORBIT CARD (কত দিনে এক বছর ও কত ঘণ্টায় এক দিন)
                PlanetaryTimeCard(
                    body = body,
                    currentLanguage = currentLanguage,
                    earthAgeInput = userAgeInput,
                    onEarthAgeChanged = { viewModel.updateUserAge(it) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Interactive Gravity & Weight Jump Calculator
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = CosmicSurfaceLight,
                    border = androidx.compose.foundation.BorderStroke(1.dp, PulsarCyan.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth().testTag("gravity_calculator_card")
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.FitnessCenter,
                                    contentDescription = "Gravity",
                                    tint = StarGold,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (isEnglish) "Gravity & Your Weight Calculator" else "মহাকর্ষ ও আপনার ওজন ক্যালকুলেটর",
                                    color = StarlightWhite,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = userWeightInput,
                                onValueChange = { viewModel.updateUserWeight(it) },
                                label = {
                                    Text(
                                        if (isEnglish) "Earth Weight (kg)" else "পৃথিবীতে ওজন (কেজি)",
                                        color = StarlightMuted,
                                        fontSize = 11.sp
                                    )
                                },
                                singleLine = true,
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("weight_input_field"),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = StarlightWhite,
                                    unfocusedTextColor = StarlightWhite,
                                    focusedBorderColor = PulsarCyan,
                                    unfocusedBorderColor = CosmicBorder
                                )
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = if (isEnglish) "Weight on $planetDisplayName:" else "$planetDisplayName-এ আপনার ওজন:",
                                    color = StarlightMuted,
                                    fontSize = 11.sp
                                )
                                Text(
                                    text = String.format("%.1f kg", planetWeight),
                                    color = PulsarCyan,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Jump Height Simulation
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color(0x66080D20))
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.North,
                                contentDescription = "Jump",
                                tint = StarGold,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isEnglish)
                                    "Jump Height: A 0.5m leap on Earth would reach ~${String.format("%.2f", planetJumpMeters)} meters here!"
                                else
                                    "লাফের উচ্চতা: পৃথিবীতে ০.৫ মিটার লাফ দিলে এখানে লাফাতে পারবেন প্রায় ${String.format("%.2f", planetJumpMeters)} মিটার!",
                                color = StarlightWhite,
                                fontSize = 12.sp,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Physical Specifications Grid
                Text(
                    text = if (isEnglish) "Physical & Astronomical Specifications" else "বৈজ্ঞানিক ও ভৌত পরিমাপ",
                    color = StarlightWhite,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                SpecDetailCard(
                    items = listOf(
                        CosmicStrings.specDiameter(currentLanguage) to body.diameterKm,
                        CosmicStrings.specMass(currentLanguage) to body.massKg,
                        CosmicStrings.specGravity(currentLanguage) to "${body.surfaceGravityMps2} m/s²",
                        CosmicStrings.specDistSun(currentLanguage) to body.distanceFromSunMillionKm,
                        CosmicStrings.dayLengthLabel(currentLanguage) to if (isEnglish) body.timeInfo.daySummaryEn else body.timeInfo.daySummaryBn,
                        CosmicStrings.yearLengthLabel(currentLanguage) to if (isEnglish) body.timeInfo.yearSummaryEn else body.timeInfo.yearSummaryBn,
                        CosmicStrings.specTemp(currentLanguage) to body.averageTempC,
                        CosmicStrings.specMoons(currentLanguage) to if (isEnglish) "${body.numberOfMoons} Moons" else "${body.numberOfMoons} টি"
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Atmospheric Composition
                if (body.atmosphereCompositionBn.isNotEmpty()) {
                    Text(
                        text = if (isEnglish) "Atmospheric Composition" else "বায়ুমণ্ডলীয় উপাদান",
                        color = StarlightWhite,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = CosmicSurface,
                        border = androidx.compose.foundation.BorderStroke(1.dp, CosmicBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            for (gas in body.atmosphereCompositionBn) {
                                Row(
                                    modifier = Modifier.padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(PulsarCyan)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = gas,
                                        color = StarlightWhite,
                                        fontSize = 13.sp
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Core Internal Layers Detail
                if (body.coreLayers.isNotEmpty()) {
                    Text(
                        text = if (isEnglish) "Internal Core Structure" else "অভ্যন্তরীণ স্তর কাঠামো",
                        color = StarlightWhite,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    for (layer in body.coreLayers) {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = CosmicSurface,
                            border = androidx.compose.foundation.BorderStroke(1.dp, CosmicBorder),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .clip(CircleShape)
                                        .background(layer.color)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = if (isEnglish) layer.nameEn else layer.nameBn,
                                            color = StarlightWhite,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "(${layer.thicknessDescription})",
                                            color = PulsarCyan,
                                            fontSize = 11.sp
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = if (isEnglish) layer.descriptionEn else layer.descriptionBn,
                                        color = StarlightMuted,
                                        fontSize = 12.sp,
                                        lineHeight = 16.sp
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Cosmic Mysteries Section
                if (body.cosmicMysteriesBn.isNotEmpty()) {
                    Text(
                        text = if (isEnglish) "Cosmic Mysteries & Unknown Questions" else "মহাজাগতিক রহস্য ও বৈজ্ঞানিক অনুসন্ধান",
                        color = StarGold,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    for (mystery in body.cosmicMysteriesBn) {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = Color(0x33FFB300),
                            border = androidx.compose.foundation.BorderStroke(1.dp, StarGold.copy(alpha = 0.4f)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Row(modifier = Modifier.padding(12.dp)) {
                                Icon(
                                    imageVector = Icons.Default.HelpOutline,
                                    contentDescription = "Mystery",
                                    tint = StarGold,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = mystery,
                                    color = StarlightWhite,
                                    fontSize = 13.sp,
                                    lineHeight = 18.sp
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Fascinating Facts
                if (body.fascinatingFactsBn.isNotEmpty()) {
                    Text(
                        text = if (isEnglish) "Fascinating Facts" else "চমকপ্রদ সত্যসমূহ",
                        color = StarlightWhite,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    for (fact in body.fascinatingFactsBn) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(text = "✦", color = PulsarCyan, fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = fact,
                                color = StarlightWhite,
                                fontSize = 13.sp,
                                lineHeight = 18.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Space Missions Timeline
                if (body.missions.isNotEmpty()) {
                    Text(
                        text = if (isEnglish) "Historic Space Missions" else "ঐতিহাসিক মহাকাশ অভিযান",
                        color = StarlightWhite,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    for (mission in body.missions) {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = CosmicSurface,
                            border = androidx.compose.foundation.BorderStroke(1.dp, CosmicBorder),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(CosmicSurfaceLight)
                                        .padding(horizontal = 8.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = mission.year,
                                        color = PulsarCyan,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = if (isEnglish) mission.nameEn else mission.nameBn,
                                            color = StarlightWhite,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "(${mission.agency})",
                                            color = StarlightMuted,
                                            fontSize = 11.sp
                                        )
                                    }
                                    Text(
                                        text = if (isEnglish) mission.descriptionEn else mission.descriptionBn,
                                        color = StarlightMuted,
                                        fontSize = 12.sp,
                                        lineHeight = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }

        if (showAudioDialog) {
            CosmicAudioDialog(
                body = body,
                currentLanguage = currentLanguage,
                isPlaying = isAudioPlaying,
                onTogglePlay = { viewModel.toggleAudio() },
                onDismiss = { showAudioDialog = false }
            )
        }

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

        // ===================== FULL SCREEN 3D GLOBE MODAL =====================
        if (isGlobeFullscreen) {
            Dialog(
                onDismissRequest = { isGlobeFullscreen = false },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) {
                BackHandler { isGlobeFullscreen = false }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(SpaceBlack)
                ) {
                    CosmicBackground(particleCount = 100)

                    PlanetGlobe3DCanvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("planet_globe_3d_canvas_fullscreen"),
                        body = body,
                        showInternalLayers = showInternalLayers,
                        isAutoSpinEnabled = false
                    )

                    // Top Bar in Fullscreen
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0xEE090E22),
                            border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFFF5252)),
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .clickable { isGlobeFullscreen = false }
                                .testTag("exit_fullscreen_globe_button")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FullscreenExit,
                                    contentDescription = "Exit Fullscreen",
                                    tint = Color(0xFFFF5252),
                                    modifier = Modifier.size(20.dp)
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

                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = Color(0xCC090E22),
                            border = androidx.compose.foundation.BorderStroke(1.dp, PulsarCyan)
                        ) {
                            Text(
                                text = if (isEnglish) "${body.nameEn} 360°" else "${body.nameBn} ৩৬০°",
                                color = PulsarCyan,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }

                    // Mode Selector (Surface vs Core Layers)
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .navigationBarsPadding()
                            .padding(bottom = 24.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xDD090E22))
                            .border(1.dp, CosmicBorder, RoundedCornerShape(16.dp))
                            .padding(6.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ViewToggleButton(
                            text = if (isEnglish) "3D Surface" else "৩ডি পৃষ্ঠতল",
                            isSelected = !showInternalLayers,
                            onClick = { if (showInternalLayers) viewModel.toggleInternalLayers() }
                        )
                        ViewToggleButton(
                            text = if (isEnglish) "Internal Core" else "অভ্যন্তরীণ স্তর",
                            isSelected = showInternalLayers,
                            onClick = { if (!showInternalLayers) viewModel.toggleInternalLayers() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ViewToggleButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) PulsarCyan else Color.Transparent)
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            color = if (isSelected) SpaceBlack else StarlightMuted,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
private fun SpecDetailCard(items: List<Pair<String, String>>) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = CosmicSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, CosmicBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            for (i in items.indices step 2) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val first = items[i]
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = first.first, color = StarlightMuted, fontSize = 11.sp)
                        Text(text = first.second, color = StarlightWhite, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    }
                    if (i + 1 < items.size) {
                        val second = items[i + 1]
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = second.first, color = StarlightMuted, fontSize = 11.sp)
                            Text(text = second.second, color = StarlightWhite, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
                if (i + 2 < items.size) {
                    HorizontalDivider(color = CosmicBorder.copy(alpha = 0.4f), thickness = 0.8.dp)
                }
            }
        }
    }
}
