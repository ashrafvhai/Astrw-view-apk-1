package com.example.ui.components

import androidx.compose.animation.*
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HourglassBottom
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
import com.example.data.datasource.CosmicEpoch
import com.example.data.datasource.TimeWarpData
import com.example.ui.localization.AppLanguage
import com.example.ui.theme.*

@Composable
fun TimeWarpDialog(
    currentLanguage: AppLanguage,
    onApplyEpoch: (CosmicEpoch) -> Unit,
    onDismiss: () -> Unit
) {
    val isEnglish = currentLanguage == AppLanguage.ENGLISH
    var selectedEpoch by remember { mutableStateOf(TimeWarpData.epochs[3]) } // Present Day default

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = CosmicSurface.copy(alpha = 0.98f),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, selectedEpoch.accentColor),
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .fillMaxHeight(0.86f)
                .testTag("time_warp_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(selectedEpoch.accentColor.copy(alpha = 0.25f))
                                .border(1.dp, selectedEpoch.accentColor, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.HourglassBottom,
                                contentDescription = "Time Warp",
                                tint = selectedEpoch.accentColor,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (isEnglish) "Cosmic Time Machine" else "মহাজাগতিক টাইম মেশিন",
                                color = StarlightWhite,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (isEnglish) "Traverse space-time across 100 Trillion Years" else "১০০ ট্রিলিয়ন বছরের মহাজাগতিক কালরেখা ভ্রমণ",
                                color = selectedEpoch.accentColor,
                                fontSize = 11.sp
                            )
                        }
                    }

                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = StarlightWhite
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Timeline Milestone Chips
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (epoch in TimeWarpData.epochs) {
                        val isSelected = epoch.id == selectedEpoch.id
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = if (isSelected) CosmicSurfaceLight else Color(0x66080D20),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) epoch.accentColor else CosmicBorder
                            ),
                            modifier = Modifier
                                .clip(RoundedCornerShape(14.dp))
                                .clickable {
                                    selectedEpoch = epoch
                                    onApplyEpoch(epoch)
                                }
                        ) {
                            Text(
                                text = epoch.getYear(currentLanguage),
                                color = if (isSelected) epoch.accentColor else StarlightMuted,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Big Epoch Visual Card
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color(0x33101730),
                    border = androidx.compose.foundation.BorderStroke(1.2.dp, selectedEpoch.accentColor),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = selectedEpoch.accentColor.copy(alpha = 0.2f),
                                border = androidx.compose.foundation.BorderStroke(1.dp, selectedEpoch.accentColor)
                            ) {
                                Text(
                                    text = selectedEpoch.getYear(currentLanguage),
                                    color = selectedEpoch.accentColor,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }

                            Text(
                                text = selectedEpoch.getEra(currentLanguage),
                                color = StarlightMuted,
                                fontSize = 11.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = selectedEpoch.getTitle(currentLanguage),
                            color = StarlightWhite,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = selectedEpoch.getDescription(currentLanguage),
                            color = StarlightWhite,
                            fontSize = 12.sp,
                            lineHeight = 17.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = selectedEpoch.accentColor.copy(alpha = 0.15f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, selectedEpoch.accentColor.copy(alpha = 0.35f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(modifier = Modifier.padding(10.dp)) {
                                Text(text = "⚡", fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Column {
                                    Text(
                                        text = if (isEnglish) "Planetary & Cosmic Impact:" else "গ্রহীয় ও মহাজাগতিক প্রভাব:",
                                        color = selectedEpoch.accentColor,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = selectedEpoch.getImpact(currentLanguage),
                                        color = StarlightWhite,
                                        fontSize = 11.sp,
                                        lineHeight = 15.sp
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Timeline Scrubber Slider
                Text(
                    text = if (isEnglish) "Scrub Cosmic Time (-4.6 Billion to +100 Trillion Years):" else "কালরেখা স্লাইডার টানুন (-৪.৬ বিলিয়ন থেকে +১০০ ট্রিলিয়ন বছর):",
                    color = StarlightMuted,
                    fontSize = 11.sp
                )

                val epochIndex = TimeWarpData.epochs.indexOfFirst { it.id == selectedEpoch.id }.coerceAtLeast(0)
                Slider(
                    value = epochIndex.toFloat(),
                    onValueChange = { idx ->
                        val selected = TimeWarpData.epochs[idx.toInt().coerceIn(0, TimeWarpData.epochs.size - 1)]
                        selectedEpoch = selected
                        onApplyEpoch(selected)
                    },
                    valueRange = 0f..(TimeWarpData.epochs.size - 1).toFloat(),
                    steps = TimeWarpData.epochs.size - 2,
                    colors = SliderDefaults.colors(
                        thumbColor = selectedEpoch.accentColor,
                        activeTrackColor = selectedEpoch.accentColor,
                        inactiveTrackColor = CosmicBorder
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = if (isEnglish) "Big Bang Birth" else "আদি জন্ম", color = StarlightMuted, fontSize = 10.sp)
                    Text(text = if (isEnglish) "Today (2026)" else "বর্তমান", color = PulsarCyan, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Text(text = if (isEnglish) "Heat Death" else "মহাবিলুপ্তি", color = StarlightMuted, fontSize = 10.sp)
                }

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = selectedEpoch.accentColor,
                        contentColor = SpaceBlack
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                ) {
                    Text(
                        text = if (isEnglish) "Apply Cosmic Epoch to Orrery" else "কক্ষপথে এই যুগ প্রয়োগ করুন",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
