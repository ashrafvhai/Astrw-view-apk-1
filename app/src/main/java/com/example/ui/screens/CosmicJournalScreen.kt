package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import com.example.data.datasource.SolarSystemData
import com.example.data.datasource.StellarData
import com.example.data.local.CosmicBookmark
import com.example.ui.components.CosmicBackground
import com.example.ui.components.LanguageSettingsDialog
import com.example.ui.localization.AppLanguage
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.CosmicViewModel

private data class ScaleStep(
    val powerExp: String,
    val meterScaleBn: String,
    val meterScaleEn: String,
    val titleBn: String,
    val titleEn: String,
    val descriptionBn: String,
    val descriptionEn: String,
    val color: Color
)

@Composable
fun CosmicJournalScreen(
    viewModel: CosmicViewModel
) {
    BackHandler {
        viewModel.navigateTo(AppScreen.SOLAR_SYSTEM)
    }

    val currentLanguage by viewModel.currentLanguage.collectAsState()
    val isEnglish = currentLanguage == AppLanguage.ENGLISH
    val bookmarks by viewModel.bookmarks.collectAsState()
    val cosmicScaleIdx by viewModel.cosmicScaleIndex.collectAsState()
    var activeSubTab by remember { mutableIntStateOf(0) } // 0 = Bookmarks, 1 = Cosmic Scale, 2 = Space Events
    var showLanguageDialog by remember { mutableStateOf(false) }

    val scaleSteps = remember {
        listOf(
            ScaleStep("10⁰ m", "১ মিটার", "1 Meter", "মানবদেহ", "Human Scale", "আমাদের দৈনন্দিন পরিচিত আকৃতির জগৎ।", "The everyday realm of human life and familiar biological scales.", Color(0xFF66BB6A)),
            ScaleStep("10⁶ m", "১,০০০ কিমি", "1,000 km", "চাঁদ ও পৃথিবীর ব্যাসার্ধ", "Moon & Earth", "আমাদের প্রিয় নীল গ্রহ পৃথিবীর ব্যাস প্রায় ১২,৭৪২ কিমি।", "Earth's diameter is 12,742 km, orbiting with its celestial partner the Moon.", Color(0xFF29B6F6)),
            ScaleStep("10⁹ m", "১০ লক্ষ কিমি", "1 Million km", "সূর্য ও এর বলয়", "The Sun & Corona", "সূর্য এত বিশাল যে এর ভেতরে ১৩ লক্ষ পৃথিবী অনায়াসে এঁটে যাবে।", "The Sun contains 99.8% of our solar system's entire mass; over 1.3 million Earths fit inside.", Color(0xFFFFB300)),
            ScaleStep("10¹² m", "১০০ কোটি কিমি", "1 Billion km", "সৌরজগতের বিস্তার", "Inner Solar System", "সূর্য থেকে বৃহস্পতি ও শনির কক্ষপথের বিশাল দূরত্ব।", "The vast distances between planets; sunlight takes hours to reach outer worlds.", Color(0xFFFF7043)),
            ScaleStep("10¹⁶ m", "১ আলোকবর্ষ", "1 Light Year", "উর্ট মেঘ (Oort Cloud)", "Oort Cloud Boundary", "সৌরজগতের চরম সীমানা—হাজার হাজার বরফময় ধূমকেতুর আঁতুড়ঘর।", "The outermost gravitational boundary of our solar system, home to billions of frozen comets.", Color(0xFFBA68C8)),
            ScaleStep("10²¹ m", "১ লক্ষ আলোকবর্ষ", "100,000 Light Years", "মিল্কিওয়ে ছায়াপথ", "Milky Way Galaxy", "আমাদের নিজস্ব গ্যালাক্সির সর্পিল ডিস্ক, যার মধ্যে অন্তত ৪০,০০০ কোটি নক্ষত্র রয়েছে।", "A barred spiral disc spanning 100,000 light-years and hosting over 200 billion stars.", Color(0xFF7C4DFF)),
            ScaleStep("10²³ m", "১ কোটি আলোকবর্ষ", "10 Million Light Years", "লোকাল গ্রুপ ও ক্লাস্টার", "Local Group Cluster", "মিল্কিওয়ে, অ্যান্ড্রোমিডা ও ট্রায়াঙ্গুলাম গ্যালাক্সির সমন্বয়ে গঠিত গুচ্ছ।", "Gravitationally bound cosmic family of over 50 galaxies including Andromeda and Milky Way.", Color(0xFF00E5FF)),
            ScaleStep("10²⁶ m", "৯,৩০০ কোটি আলোকবর্ষ", "93 Billion Light Years", "দৃশ্যমান মহাবিশ্ব", "Observable Universe", "মহাবিশ্বের সৃষ্টি থেকে আজ পর্যন্ত আলো যতদূর ভ্রমণ করতে পেরেছে—অন্তত ২ ট্রিলিয়ন গ্যালাক্সির মহাজাগতিক জালিকা!", "The observable sphere containing an estimated 2 trillion galaxies across space and time.", Color(0xFFFF4081))
        )
    }

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
                    text = if (isEnglish) "Cosmic Journal & Research" else "মহাকাশ ডায়রি ও আবিষ্কার",
                    color = PulsarCyan,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (isEnglish) "Saved bookmarks, Powers of Ten cosmic scale & sky calendar" else "সংরক্ষিত গবেষণা, মহাজাগতিক স্কেল ও আকাশ পর্যবেক্ষণের দিনলিপি",
                    color = StarlightMuted,
                    fontSize = 11.sp
                )
            }

            // Language Switcher
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = CosmicSurfaceLight,
                border = androidx.compose.foundation.BorderStroke(1.dp, PulsarCyan.copy(alpha = 0.6f)),
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
                        color = StarlightWhite,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Sub-Tab Switcher
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(CosmicSurface)
                .border(1.dp, CosmicBorder, RoundedCornerShape(14.dp))
                .padding(4.dp)
        ) {
            JournalTabItem(
                title = if (isEnglish) "Bookmarks (${bookmarks.size})" else "বুকমার্ক (${bookmarks.size})",
                isSelected = activeSubTab == 0,
                onClick = { activeSubTab = 0 },
                modifier = Modifier.weight(1f)
            )
            JournalTabItem(
                title = if (isEnglish) "Cosmic Scale" else "কসমিক স্কেল",
                isSelected = activeSubTab == 1,
                onClick = { activeSubTab = 1 },
                modifier = Modifier.weight(1f)
            )
            JournalTabItem(
                title = if (isEnglish) "Sky Events" else "আকাশ ইভেন্ট",
                isSelected = activeSubTab == 2,
                onClick = { activeSubTab = 2 },
                modifier = Modifier.weight(1f)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 76.dp)
        ) {
            CosmicBackground(particleCount = 50)

            when (activeSubTab) {
                0 -> {
                    // Bookmarks & Personal Notes
                    if (bookmarks.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.BookmarkBorder,
                                contentDescription = "No bookmarks",
                                tint = StarlightMuted,
                                modifier = Modifier.size(54.dp)
                            )
                            Spacer(modifier = Modifier.height(14.dp))
                            Text(
                                text = if (isEnglish) "No Bookmarked Celestial Objects" else "কোনো সংরক্ষিত গ্রহ বা তারা নেই",
                                color = StarlightWhite,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = if (isEnglish)
                                    "Tap the bookmark icon on any Planet, Galaxy, or Constellation in 3D to save it to your personal space log."
                                else
                                    "সৌরজগত বা স্টেলার ম্যাপ দেখার সময় বুকমার্ক আইকনে চাপ দিয়ে আপনার প্রিয় মহাজাগতিক বস্তু এখানে সংরক্ষণ করুন।",
                                color = StarlightMuted,
                                fontSize = 12.sp,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                lineHeight = 17.sp
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(bookmarks, key = { it.id }) { bookmark ->
                                BookmarkCard(
                                    bookmark = bookmark,
                                    currentLanguage = currentLanguage,
                                    onOpen = {
                                        val maybeBody = SolarSystemData.celestialBodies.find { it.id == bookmark.targetId }
                                        if (maybeBody != null) {
                                            viewModel.selectCelestialBody(maybeBody, navigateToDetail = true)
                                        } else {
                                            viewModel.navigateTo(AppScreen.STELLAR_MAP)
                                        }
                                    },
                                    onSaveNote = { note ->
                                        viewModel.updateBookmarkNote(bookmark, note)
                                    },
                                    onDelete = {
                                        viewModel.deleteBookmark(bookmark)
                                    }
                                )
                            }
                        }
                    }
                }

                1 -> {
                    // Powers of Ten Scale Explorer
                    val currentStep = scaleSteps[cosmicScaleIdx]
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = if (isEnglish) "Powers of Ten: Cosmic Zoom" else "পাওয়ার্স অব টেন: মহাজাগতিক স্কেল",
                            color = StarlightWhite,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (isEnglish) "From sub-meter human realms to the entire 93-billion-light-year cosmic web" else "১ মিটার মানব আকৃতি থেকে শুরু করে সমগ্র দৃশ্যমান মহাবিশ্ব",
                            color = StarlightMuted,
                            fontSize = 11.sp
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Big Interactive Scale Card
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = CosmicSurface,
                            border = androidx.compose.foundation.BorderStroke(2.dp, currentStep.color),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = currentStep.color.copy(alpha = 0.2f),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, currentStep.color)
                                ) {
                                    Text(
                                        text = currentStep.powerExp,
                                        color = currentStep.color,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Text(
                                    text = if (isEnglish) currentStep.titleEn else currentStep.titleBn,
                                    color = StarlightWhite,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = if (isEnglish) "${currentStep.titleBn} (${currentStep.meterScaleEn})" else "${currentStep.titleEn} (${currentStep.meterScaleBn})",
                                    color = currentStep.color,
                                    fontSize = 13.sp
                                )

                                Spacer(modifier = Modifier.height(14.dp))

                                Text(
                                    text = if (isEnglish) currentStep.descriptionEn else currentStep.descriptionBn,
                                    color = StarlightMuted,
                                    fontSize = 13.sp,
                                    lineHeight = 19.sp,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Interactive Scale Slider
                        Text(
                            text = if (isEnglish) "Drag slider to traverse orders of magnitude (10⁰ to 10²⁶ m):" else "স্কেল পরিবর্তন করতে স্লাইডার টানুন (১০⁰ থেকে ১০²৬ মিটার):",
                            color = StarlightWhite,
                            fontSize = 12.sp
                        )

                        Slider(
                            value = cosmicScaleIdx.toFloat(),
                            onValueChange = { viewModel.setCosmicScaleIndex(it.toInt()) },
                            valueRange = 0f..7f,
                            steps = 6,
                            colors = SliderDefaults.colors(
                                thumbColor = currentStep.color,
                                activeTrackColor = currentStep.color,
                                inactiveTrackColor = CosmicBorder
                            ),
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = if (isEnglish) "Human (1 m)" else "ক্ষুদ্র (মানুষ)", color = StarlightMuted, fontSize = 11.sp)
                            Text(text = if (isEnglish) "Cosmos (10²⁶ m)" else "অনন্ত (মহাবিশ্ব)", color = StarlightMuted, fontSize = 11.sp)
                        }
                    }
                }

                2 -> {
                    // Stargazing Space Events
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(StellarData.spaceEvents) { event ->
                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = CosmicSurface,
                                border = androidx.compose.foundation.BorderStroke(1.dp, CosmicBorder),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = if (isEnglish) event.titleEn else event.titleBn,
                                            color = StarGold,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 14.sp
                                        )
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(6.dp))
                                                .background(CosmicSurfaceLight)
                                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                        ) {
                                            Text(
                                                text = event.dateOrSeason,
                                                color = PulsarCyan,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = event.descriptionBn,
                                        color = StarlightWhite,
                                        fontSize = 12.sp,
                                        lineHeight = 16.sp
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color(0x55060A18))
                                            .padding(8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Visibility,
                                            contentDescription = "Viewing tip",
                                            tint = PulsarCyan,
                                            modifier = Modifier.size(15.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = event.viewingTipBn,
                                            color = StarlightMuted,
                                            fontSize = 11.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
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
    }
}

@Composable
private fun JournalTabItem(
    title: String,
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
        Text(
            text = title,
            color = if (isSelected) SpaceBlack else StarlightMuted,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
private fun BookmarkCard(
    bookmark: CosmicBookmark,
    currentLanguage: AppLanguage,
    onOpen: () -> Unit,
    onSaveNote: (String) -> Unit,
    onDelete: () -> Unit
) {
    val isEnglish = currentLanguage == AppLanguage.ENGLISH
    var isEditingNote by remember { mutableStateOf(false) }
    var noteText by remember(bookmark.userNote) { mutableStateOf(bookmark.userNote) }

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = CosmicSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, CosmicBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onOpen() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(CosmicSurfaceLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Explore,
                            contentDescription = "Open",
                            tint = PulsarCyan,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = if (isEnglish) bookmark.nameEn else bookmark.nameBn,
                            color = StarlightWhite,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = if (isEnglish) "${bookmark.nameBn} • ${bookmark.category}" else "${bookmark.nameEn} • ${bookmark.category}",
                            color = PulsarCyan,
                            fontSize = 11.sp
                        )
                    }
                }

                Row {
                    IconButton(onClick = { isEditingNote = !isEditingNote }) {
                        Icon(
                            imageVector = Icons.Default.EditNote,
                            contentDescription = "Edit note",
                            tint = if (bookmark.userNote.isNotEmpty()) StarGold else StarlightMuted
                        )
                    }
                    IconButton(onClick = { onDelete() }) {
                        Icon(
                            imageVector = Icons.Default.DeleteOutline,
                            contentDescription = "Delete",
                            tint = StarlightMuted
                        )
                    }
                }
            }

            if (bookmark.userNote.isNotEmpty() && !isEditingNote) {
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0x33101730),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = bookmark.userNote,
                        color = StarlightWhite,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            if (isEditingNote) {
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = noteText,
                    onValueChange = { noteText = it },
                    label = { Text(if (isEnglish) "Personal Observation Note" else "আপনার নিজস্ব পর্যবেক্ষণ নোট") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PulsarCyan,
                        unfocusedBorderColor = CosmicBorder,
                        focusedTextColor = StarlightWhite,
                        unfocusedTextColor = StarlightWhite
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = { isEditingNote = false }) {
                        Text(if (isEnglish) "Cancel" else "বাতিল", color = StarlightMuted)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            onSaveNote(noteText)
                            isEditingNote = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PulsarCyan, contentColor = SpaceBlack)
                    ) {
                        Text(if (isEnglish) "Save" else "সংরক্ষণ করুন")
                    }
                }
            }
        }
    }
}
