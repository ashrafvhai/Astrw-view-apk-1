package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CelestialBody
import com.example.ui.localization.AppLanguage
import com.example.ui.localization.CosmicStrings
import com.example.ui.theme.*

@Composable
fun PlanetaryTimeCard(
    body: CelestialBody,
    currentLanguage: AppLanguage,
    earthAgeInput: String,
    onEarthAgeChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val isEnglish = currentLanguage == AppLanguage.ENGLISH
    val earthAge = earthAgeInput.toDoubleOrNull() ?: 25.0
    val planetYearDays = body.timeInfo.yearInEarthDays.coerceAtLeast(0.01)
    val planetDayHours = body.timeInfo.dayInHours.coerceAtLeast(0.1)

    // Calculate Planetary Age and Sunrises
    val planetaryAge = if (body.id == "sun") {
        earthAge / 230000000.0 // Galactic years
    } else {
        earthAge * (365.25 / planetYearDays)
    }

    val birthdaysCount = planetaryAge.toInt()
    val sunrisesWitnessed = ((earthAge * 365.25 * 24.0) / planetDayHours).toLong().coerceAtLeast(0)

    Surface(
        shape = RoundedCornerShape(20.dp),
        color = CosmicSurfaceLight,
        border = androidx.compose.foundation.BorderStroke(1.2.dp, StarGold.copy(alpha = 0.6f)),
        modifier = modifier
            .fillMaxWidth()
            .testTag("planetary_time_card")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header with clock icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(StarGold.copy(alpha = 0.2f))
                            .border(1.dp, StarGold, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = "Time & Orbit",
                            tint = StarGold,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = if (isEnglish) "Orbital Year & Planetary Time" else "কক্ষপথ ও গ্রহীয় সময় চক্র",
                            color = StarlightWhite,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (isEnglish) "How long is 1 Year & 1 Day on this world" else "কত দিনে এক বছর ও কত ঘণ্টায় এক দিন",
                            color = StarGold,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Two Big Metric Cards: 1 Year vs 1 Day
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // 1 Year Box
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color(0x33FFD54F),
                    border = androidx.compose.foundation.BorderStroke(1.dp, StarGold.copy(alpha = 0.4f)),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Public,
                                contentDescription = "Year",
                                tint = StarGold,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isEnglish) "1 YEAR (Orbit)" else "১ বছর (কক্ষপথ)",
                                color = StarGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = if (isEnglish) body.timeInfo.yearSummaryEn else body.timeInfo.yearSummaryBn,
                            color = StarlightWhite,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 17.sp
                        )
                    }
                }

                // 1 Day Box
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = Color(0x3300E5FF),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PulsarCyan.copy(alpha = 0.4f)),
                    modifier = Modifier.weight(1f)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.RotateRight,
                                contentDescription = "Day",
                                tint = PulsarCyan,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isEnglish) "1 DAY (Spin)" else "১ দিন (ঘূর্ণন)",
                                color = PulsarCyan,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = if (isEnglish) body.timeInfo.daySummaryEn else body.timeInfo.daySummaryBn,
                            color = StarlightWhite,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 17.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Sunlight Travel Time & Planetary Resonance Row
            val lightTravelText = remember(body.id, isEnglish) {
                val distNum = body.distanceFromSunMillionKm.replace(",", "").replace(" ", "").filter { it.isDigit() || it == '.' }.toDoubleOrNull() ?: 149.6
                if (body.id == "sun") {
                    if (isEnglish) "0 sec (Surface Origin)" else "০ সে. (পৃষ্ঠ থেকে উৎপত্তি)"
                } else {
                    val totalSeconds = (distNum * 1_000_000.0) / 299792.0
                    val minutes = (totalSeconds / 60).toInt()
                    val seconds = (totalSeconds % 60).toInt()
                    val hours = minutes / 60
                    val remMinutes = minutes % 60
                    if (hours > 0) {
                        if (isEnglish) "$hours hr $remMinutes min" else "$hours ঘণ্টা $remMinutes মিনিট"
                    } else if (minutes > 0) {
                        if (isEnglish) "$minutes min $seconds sec" else "$minutes মিনিট $seconds সেকেন্ড"
                    } else {
                        if (isEnglish) "$seconds seconds" else "$seconds সেকেন্ড"
                    }
                }
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0x330A1430),
                border = androidx.compose.foundation.BorderStroke(1.dp, PulsarCyan.copy(alpha = 0.35f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.FlashOn,
                            contentDescription = "Light Speed",
                            tint = StarGold,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isEnglish) "Sunlight Arrival Time:" else "সূর্যরশ্মি পৌঁছানোর সময়:",
                            color = StarlightMuted,
                            fontSize = 11.sp
                        )
                    }
                    Text(
                        text = lightTravelText,
                        color = PulsarCyan,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Time Dynamics Mystery Quote
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color(0x44080D20),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(10.dp)) {
                    Text(text = "✦", color = StarGold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isEnglish) body.timeInfo.timeComparisonEn else body.timeInfo.timeComparisonBn,
                        color = StarlightWhite,
                        fontSize = 12.sp,
                        lineHeight = 17.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Interactive Planetary Age Converter
            Text(
                text = if (isEnglish) "Your Age on ${body.nameEn}:" else "${body.nameBn}-এ আপনার বয়স রূপান্তর:",
                color = PulsarCyan,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = earthAgeInput,
                    onValueChange = onEarthAgeChanged,
                    label = {
                        Text(
                            text = if (isEnglish) "Age on Earth (Years)" else "পৃথিবীতে বয়স (বছর)",
                            color = StarlightMuted,
                            fontSize = 11.sp
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("age_input_field"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = StarlightWhite,
                        unfocusedTextColor = StarlightWhite,
                        focusedBorderColor = StarGold,
                        unfocusedBorderColor = CosmicBorder
                    )
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1.2f),
                    horizontalAlignment = Alignment.Start
                ) {
                    val ageDisplay = if (body.id == "sun") {
                        String.format("%.8f", planetaryAge) + if (isEnglish) " galactic yrs" else " গ্যালাকটিক বছর"
                    } else {
                        String.format("%.2f", planetaryAge) + if (isEnglish) " years" else " বছর"
                    }
                    Text(
                        text = ageDisplay,
                        color = StarGold,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (isEnglish) "$birthdaysCount Birthdays celebrated!" else "$birthdaysCount টি জন্মদিন পার হয়েছে!",
                        color = StarlightMuted,
                        fontSize = 11.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Sunrise Count on this planet
            Text(
                text = if (isEnglish)
                    "You would have lived through ~$sunrisesWitnessed planetary sunrises here."
                else
                    "এখানে থাকলে আপনি প্রায় $sunrisesWitnessed টি সূর্যোদয় প্রত্যক্ষ করতেন।",
                color = StarlightMuted,
                fontSize = 11.sp,
                lineHeight = 15.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Time Mystery
            Text(
                text = if (isEnglish) "⏳ ${body.timeInfo.timeMysteryEn}" else "⏳ ${body.timeInfo.timeMysteryBn}",
                color = StarlightWhite.copy(alpha = 0.85f),
                fontSize = 11.sp,
                lineHeight = 16.sp,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0x33101730))
                    .padding(8.dp)
            )
        }
    }
}
