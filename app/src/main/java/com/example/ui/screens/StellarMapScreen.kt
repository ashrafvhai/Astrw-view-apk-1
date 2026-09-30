package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.data.datasource.StellarData
import com.example.data.model.Constellation
import com.example.data.model.DeepSpaceWonder
import com.example.ui.components.BlackHole3DCanvas
import com.example.ui.components.CosmicBackground
import com.example.ui.components.StellarMap3DCanvas
import com.example.ui.localization.CosmicStrings
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.CosmicViewModel

@Composable
fun StellarMapScreen(
    viewModel: CosmicViewModel
) {
    BackHandler {
        viewModel.navigateTo(AppScreen.SOLAR_SYSTEM)
    }

    var activeTab by remember { mutableIntStateOf(0) } // 0 = 3D Sky Map, 1 = Cosmic Mysteries
    var isFullscreenSky by remember { mutableStateOf(false) }
    val selectedWonder by viewModel.selectedWonder.collectAsState()
    val selectedConstellation by viewModel.selectedConstellation.collectAsState()
    val currentLanguage by viewModel.currentLanguage.collectAsState()
    val isEnglish = currentLanguage == com.example.ui.localization.AppLanguage.ENGLISH

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(SpaceBlack)
            .statusBarsPadding()
    ) {
        // Top Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (isEnglish) "Stellar Map & Cosmic Mysteries" else "স্টেলার ম্যাপ ও মহাজাগতিক রহস্য",
                    color = PulsarCyan,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (isEnglish) "Constellations, nebulae & deep celestial anomalies" else "নক্ষত্রমণ্ডল, নীহারিকা এবং অনন্ত শূন্যতার অজানা অনুসন্ধান",
                    color = StarlightMuted,
                    fontSize = 11.sp
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Full Screen Mode Button
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0x3300E676),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFF00E676)),
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { isFullscreenSky = true }
                        .testTag("stellar_fullscreen_button")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Fullscreen,
                            contentDescription = "Full Screen Sky",
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

                // Language Toggle
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = CosmicSurfaceLight,
                    border = androidx.compose.foundation.BorderStroke(1.dp, PulsarCyan.copy(alpha = 0.6f)),
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { viewModel.toggleLanguage() }
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

        // Tab Selector Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(CosmicSurface)
                .border(1.dp, CosmicBorder, RoundedCornerShape(14.dp))
                .padding(4.dp)
        ) {
            TabButton(
                text = if (isEnglish) "3D Stellar Sky" else "৩ডি স্টেলার স্কাই",
                icon = Icons.Default.Public,
                isSelected = activeTab == 0,
                onClick = { activeTab = 0 },
                modifier = Modifier.weight(1f)
            )
            TabButton(
                text = if (isEnglish) "Cosmic Mysteries" else "মহাজাগতিক রহস্যকোষ",
                icon = Icons.Default.AutoAwesome,
                isSelected = activeTab == 1,
                onClick = { activeTab = 1 },
                modifier = Modifier.weight(1f)
            )
        }

        if (activeTab == 0) {
            // 3D Stellar Sky View
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 76.dp)
            ) {
                CosmicBackground(particleCount = 70)

                StellarMap3DCanvas(
                    modifier = Modifier.fillMaxSize().testTag("stellar_map_3d_canvas"),
                    selectedWonderId = selectedWonder?.id,
                    onWonderSelected = { wonder ->
                        viewModel.selectWonder(wonder)
                    },
                    onConstellationSelected = { const ->
                        viewModel.selectConstellation(const)
                    }
                )

                // Quick selector for constellations & wonders
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = if (isEnglish) "Visible Constellations & Deep Space Wonders:" else "আকাশে দৃশ্যমান তারামণ্ডল ও মহাজাগতিক বিস্ময়সমূহ:",
                        color = StarlightMuted,
                        fontSize = 11.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        for (wonder in StellarData.deepSpaceWonders) {
                            val isSel = wonder.id == selectedWonder?.id
                            val wonderDisplayName = if (isEnglish) wonder.nameEn else wonder.nameBn
                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = if (isSel) CosmicSurfaceLight else Color(0x990E1428),
                                border = androidx.compose.foundation.BorderStroke(
                                    1.dp,
                                    if (isSel) wonder.primaryColor else CosmicBorder
                                ),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .clickable { viewModel.selectWonder(wonder) }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(wonder.primaryColor)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = wonderDisplayName,
                                        color = if (isSel) StarlightWhite else StarlightMuted,
                                        fontSize = 11.sp,
                                        fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            }
                        }
                    }
                }

                // Interactive Detail Sheet for Selected Wonder
                if (selectedWonder != null) {
                    val wonder = selectedWonder!!
                    val wonderDisplayName = if (isEnglish) wonder.nameEn else wonder.nameBn
                    val wonderSubName = if (isEnglish) wonder.nameBn else wonder.nameEn
                    val categoryLabel = if (isEnglish) wonder.category.labelEn else wonder.category.labelBn
                    Surface(
                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                        color = CosmicSurface.copy(alpha = 0.95f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CosmicBorder),
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .testTag("wonder_detail_card")
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .verticalScroll(rememberScrollState())
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = wonderDisplayName,
                                        color = wonder.primaryColor,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "$wonderSubName • $categoryLabel",
                                        color = StarlightMuted,
                                        fontSize = 12.sp
                                    )
                                }

                                val isBk = viewModel.isBookmarked(wonder.id)
                                IconButton(
                                    onClick = {
                                        viewModel.toggleBookmark(
                                            targetId = wonder.id,
                                            nameEn = wonder.nameEn,
                                            nameBn = wonder.nameBn,
                                            category = categoryLabel
                                        )
                                    }
                                ) {
                                    Icon(
                                        imageVector = if (isBk) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                        contentDescription = "Bookmark",
                                        tint = if (isBk) StarGold else StarlightMuted
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                Text(
                                    text = if (isEnglish) "Distance: ${wonder.distanceLightYears}" else "দূরত্ব: ${wonder.distanceLightYears}",
                                    color = PulsarCyan,
                                    fontSize = 11.sp
                                )
                                Text(
                                    text = if (isEnglish) "Constellation: ${wonder.constellation}" else "মণ্ডল: ${wonder.constellation}",
                                    color = StarGold,
                                    fontSize = 11.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // If this is Sagittarius A*, render the real-time relativistic Black Hole 3D simulation!
                            if (wonder.id == "sagittarius_a_star") {
                                Surface(
                                    shape = RoundedCornerShape(16.dp),
                                    color = Color.Black,
                                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFF6D00).copy(alpha = 0.4f)),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(180.dp)
                                ) {
                                    Box(modifier = Modifier.fillMaxSize()) {
                                        BlackHole3DCanvas(modifier = Modifier.fillMaxSize())
                                        Text(
                                            text = if (isEnglish) "Relativistic Event Horizon & Accretion Disk" else "ইভেন্ট হরাইজন ও অ্যাক্রিশন ডিস্ক সিমুলেশন",
                                            color = StarlightMuted,
                                            fontSize = 10.sp,
                                            modifier = Modifier
                                                .align(Alignment.BottomCenter)
                                                .padding(bottom = 6.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                            }

                            Text(
                                text = wonder.descriptionBn,
                                color = StarlightWhite,
                                fontSize = 13.sp,
                                lineHeight = 18.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = CosmicSurfaceLight,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(modifier = Modifier.padding(10.dp)) {
                                    Text(text = "✦", color = StarGold, fontSize = 12.sp)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = if (isEnglish) "Scientific Significance: ${wonder.whyItMattersBn}" else "কেন এটি বিজ্ঞানীদের ভাবিয়ে তোলে: ${wonder.whyItMattersBn}",
                                        color = StarlightWhite,
                                        fontSize = 12.sp,
                                        lineHeight = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // Cosmic Mysteries Collection (মহাজাগতিক রহস্যকোষ)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 76.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // Hero Banner
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = CosmicSurface,
                    border = androidx.compose.foundation.BorderStroke(1.dp, CosmicBorder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = painterResource(id = R.drawable.deep_space_nebula_1790423743045),
                            contentDescription = "Cosmic Nebula",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    androidx.compose.ui.graphics.Brush.verticalGradient(
                                        colors = listOf(Color.Transparent, Color(0xDD050815))
                                    )
                                )
                        )
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = if (isEnglish) "Unsolved Cosmic Mysteries" else "মহাবিশ্বের অমীমাংসিত রহস্য",
                                color = StarlightWhite,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (isEnglish) "Deepest and most thrilling questions of modern astrophysics" else "আধুনিক জ্যোতির্বিজ্ঞানের সবচেয়ে গভীর ও রোমাঞ্চকর প্রশ্নসমূহ",
                                color = PulsarCyan,
                                fontSize = 12.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                for (topic in StellarData.cosmicMysteries) {
                    var isExpanded by remember { mutableStateOf(false) }
                    val topicTitle = if (isEnglish) topic.titleEn else topic.titleBn
                    val topicSub = if (isEnglish) "${topic.titleBn} • ${topic.categoryBn}" else "${topic.titleEn} • ${topic.categoryBn}"

                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = CosmicSurface,
                        border = androidx.compose.foundation.BorderStroke(1.dp, topic.accentColor.copy(alpha = 0.45f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable { isExpanded = !isExpanded }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    modifier = Modifier.weight(1f),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(10.dp)
                                            .clip(CircleShape)
                                            .background(topic.accentColor)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text(
                                            text = topicTitle,
                                            color = StarlightWhite,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp
                                        )
                                        Text(
                                            text = topicSub,
                                            color = topic.accentColor,
                                            fontSize = 11.sp
                                        )
                                    }
                                }
                                Icon(
                                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                    contentDescription = "Expand",
                                    tint = StarlightMuted
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = topic.overviewBn,
                                color = StarlightMuted,
                                fontSize = 13.sp,
                                lineHeight = 18.sp
                            )

                            if (isExpanded) {
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = if (isEnglish) "Current Theories & Hypotheses:" else "বিজ্ঞানীদের বর্তমান হাইপোথিসিস ও তত্ত্ব:",
                                    color = PulsarCyan,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                for (theory in topic.currentTheoriesBn) {
                                    Row(
                                        modifier = Modifier.padding(vertical = 3.dp),
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Text(text = "•", color = topic.accentColor, fontSize = 14.sp)
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = theory,
                                            color = StarlightWhite,
                                            fontSize = 12.sp,
                                            lineHeight = 16.sp
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = topic.accentColor.copy(alpha = 0.15f),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, topic.accentColor.copy(alpha = 0.35f)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(modifier = Modifier.padding(10.dp)) {
                                        Icon(
                                            imageVector = Icons.Default.Lightbulb,
                                            contentDescription = "Mind blowing fact",
                                            tint = topic.accentColor,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = topic.mindBlowingFactBn,
                                            color = StarlightWhite,
                                            fontSize = 12.sp,
                                            lineHeight = 16.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }

    // ===================== FULL SCREEN 3D STELLAR SKY MODAL =====================
    if (isFullscreenSky) {
        Dialog(
            onDismissRequest = { isFullscreenSky = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            BackHandler { isFullscreenSky = false }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(SpaceBlack)
            ) {
                CosmicBackground(particleCount = 120)

                StellarMap3DCanvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("stellar_map_3d_canvas_fullscreen"),
                    selectedWonderId = selectedWonder?.id,
                    onWonderSelected = { wonder -> viewModel.selectWonder(wonder) },
                    onConstellationSelected = { const -> viewModel.selectConstellation(const) }
                )

                // Top Exit Fullscreen bar
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
                            .clickable { isFullscreenSky = false }
                            .testTag("exit_fullscreen_sky_button")
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
                            text = if (isEnglish) "360° Deep Space Sky" else "৩৬০° মহাকাশ তারামণ্ডল",
                            color = PulsarCyan,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TabButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (isSelected) PulsarCyan else Color.Transparent)
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = if (isSelected) SpaceBlack else StarlightMuted,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = text,
                color = if (isSelected) SpaceBlack else StarlightMuted,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}
