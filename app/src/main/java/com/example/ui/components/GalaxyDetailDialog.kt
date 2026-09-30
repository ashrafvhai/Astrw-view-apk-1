package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.datasource.GalaxyTarget
import com.example.ui.localization.AppLanguage
import com.example.ui.theme.*

@Composable
fun GalaxyDetailDialog(
    galaxy: GalaxyTarget,
    currentLanguage: AppLanguage = AppLanguage.ENGLISH,
    isBookmarked: Boolean,
    onToggleBookmark: () -> Unit,
    onDismiss: () -> Unit
) {
    val isEnglish = currentLanguage == AppLanguage.ENGLISH

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = CosmicSurface.copy(alpha = 0.96f),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, galaxy.armColor.copy(alpha = 0.6f)),
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .fillMaxHeight(0.85f)
                .testTag("galaxy_detail_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Top Header Row
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
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.radialGradient(
                                        listOf(galaxy.coreColor, galaxy.armColor)
                                    )
                                )
                                .border(1.dp, galaxy.armColor, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "🌌",
                                fontSize = 20.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = if (isEnglish) galaxy.nameEn else galaxy.nameBn,
                                color = StarlightWhite,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (isEnglish)
                                    "${galaxy.nameBn} • ${galaxy.type.labelEn}"
                                else
                                    "${galaxy.nameEn} • ${galaxy.type.labelBn}",
                                color = galaxy.armColor,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Row {
                        IconButton(onClick = onToggleBookmark) {
                            Icon(
                                imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = "Bookmark",
                                tint = if (isBookmarked) StarGold else StarlightMuted
                            )
                        }
                        IconButton(onClick = onDismiss) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = StarlightWhite
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Subtitle Badge
                Text(
                    text = galaxy.getSubtitle(currentLanguage),
                    color = PulsarCyan,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Quick Specs Grid
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = CosmicSurfaceLight,
                    border = androidx.compose.foundation.BorderStroke(1.dp, CosmicBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            GalaxySpecItem(
                                if (isEnglish) "Distance" else "দূরত্ব",
                                galaxy.getDistance(currentLanguage),
                                Modifier.weight(1f)
                            )
                            GalaxySpecItem(
                                if (isEnglish) "Diameter" else "ব্যাস",
                                galaxy.getDiameter(currentLanguage),
                                Modifier.weight(1f)
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        HorizontalDivider(color = CosmicBorder.copy(alpha = 0.5f), thickness = 0.8.dp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            GalaxySpecItem(
                                if (isEnglish) "Estimated Stars" else "নক্ষত্রের সংখ্যা",
                                galaxy.getStars(currentLanguage),
                                Modifier.weight(1f)
                            )
                            GalaxySpecItem(
                                if (isEnglish) "Constellation" else "মণ্ডল",
                                if (isEnglish) "${galaxy.constellationEn} (${galaxy.constellationBn})" else "${galaxy.constellationBn} (${galaxy.constellationEn})",
                                Modifier.weight(1f)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Description
                Text(
                    text = if (isEnglish) "Galaxy Exploration & Dynamics" else "ছায়াপথের বিশদ বিবরণ",
                    color = StarlightWhite,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = galaxy.getDescription(currentLanguage),
                    color = StarlightMuted,
                    fontSize = 13.sp,
                    lineHeight = 19.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Cosmic Mysteries
                val mysteries = galaxy.getMysteries(currentLanguage)
                if (mysteries.isNotEmpty()) {
                    Text(
                        text = if (isEnglish) "Cosmic Mysteries & Research" else "মহাজাগতিক রহস্য ও বৈজ্ঞানিক অনুসন্ধান",
                        color = StarGold,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    for (mystery in mysteries) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0x22FFB300),
                            border = androidx.compose.foundation.BorderStroke(1.dp, StarGold.copy(alpha = 0.35f)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Row(modifier = Modifier.padding(12.dp)) {
                                Text(text = "✦", color = StarGold, fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = mystery,
                                    color = StarlightWhite,
                                    fontSize = 12.sp,
                                    lineHeight = 17.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Fascinating Facts
                val facts = galaxy.getFacts(currentLanguage)
                if (facts.isNotEmpty()) {
                    Text(
                        text = if (isEnglish) "Astronomical Highlights" else "আকর্ষণীয় তথ্য",
                        color = PulsarCyan,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    for (fact in facts) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0x1800E5FF),
                            border = androidx.compose.foundation.BorderStroke(1.dp, PulsarCyan.copy(alpha = 0.35f)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Row(modifier = Modifier.padding(12.dp)) {
                                Text(text = "🔭", fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = fact,
                                    color = StarlightWhite,
                                    fontSize = 12.sp,
                                    lineHeight = 17.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Close Button
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PulsarCyan,
                        contentColor = SpaceBlack
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                ) {
                    Text(
                        text = if (isEnglish) "Back to Cosmic Map" else "মহাজাগতিক মানচিত্রে ফিরে যান",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun GalaxySpecItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            color = StarlightMuted,
            fontSize = 11.sp
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = value,
            color = StarlightWhite,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
