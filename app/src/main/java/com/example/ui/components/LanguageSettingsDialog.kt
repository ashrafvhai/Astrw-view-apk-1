package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.localization.AppLanguage
import com.example.ui.localization.CosmicStrings
import com.example.ui.theme.*

@Composable
fun LanguageSettingsDialog(
    currentLanguage: AppLanguage,
    onLanguageSelected: (AppLanguage) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = CosmicSurface.copy(alpha = 0.98f),
            border = androidx.compose.foundation.BorderStroke(1.dp, PulsarCyan),
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .testTag("language_settings_dialog")
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Language,
                            contentDescription = "Language",
                            tint = PulsarCyan,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = CosmicStrings.settingsTitle(currentLanguage),
                            color = StarlightWhite,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
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

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = CosmicStrings.selectLanguage(currentLanguage),
                    color = StarlightMuted,
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Language Option Cards
                for (lang in AppLanguage.values()) {
                    val isSelected = (currentLanguage == lang)
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = if (isSelected) CosmicSurfaceLight else Color(0x66080D20),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isSelected) PulsarCyan else CosmicBorder
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .clickable {
                                onLanguageSelected(lang)
                                onDismiss()
                            }
                            .testTag("language_option_${lang.code}")
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isSelected) PulsarCyan else CosmicBorder)
                                        .padding(horizontal = 10.dp, vertical = 5.dp)
                                ) {
                                    Text(
                                        text = lang.flagOrIcon,
                                        color = if (isSelected) SpaceBlack else StarlightWhite,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = lang.label,
                                        color = StarlightWhite,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    )
                                    Text(
                                        text = if (lang == AppLanguage.ENGLISH) "Default language" else "ডিফল্ট বা পছন্দের ভাষা",
                                        color = if (isSelected) PulsarCyan else StarlightMuted,
                                        fontSize = 11.sp
                                    )
                                }
                            }

                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected",
                                    tint = PulsarCyan,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = PulsarCyan, contentColor = SpaceBlack),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().height(44.dp)
                ) {
                    Text(
                        text = if (currentLanguage == AppLanguage.ENGLISH) "Apply & Continue" else "প্রয়োগ করুন",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
