package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.RocketLaunch
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
import com.example.ui.localization.AppLanguage
import com.example.ui.theme.*

@Composable
fun VoyagerDetailDialog(
    currentLanguage: AppLanguage,
    onDismiss: () -> Unit
) {
    val isEnglish = currentLanguage == AppLanguage.ENGLISH

    // Dynamic distance calculation (Voyager 1 travels at ~17 km/s = ~61,200 km/h)
    val infiniteTransition = rememberInfiniteTransition(label = "voyager_odometer")
    val ticker by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 60000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "odometer"
    )

    val baseKm = 24520184900L // ~24.52 billion km
    val currentDistanceKm = baseKm + (ticker * 17).toLong()
    val distanceAU = currentDistanceKm / 149597870.7
    val signalDelayHours = (currentDistanceKm / 299792.458) / 3600.0

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = CosmicSurface.copy(alpha = 0.98f),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, StarGold),
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .fillMaxHeight(0.88f)
                .testTag("voyager_detail_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Top Header
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
                                .background(StarGold.copy(alpha = 0.2f))
                                .border(1.dp, StarGold, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.RocketLaunch,
                                contentDescription = "Voyager",
                                tint = StarGold,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (isEnglish) "Voyager 1: Interstellar Explorer" else "ভয়েজার ১: আন্তঃনাক্ষত্রিক দূত",
                                color = StarlightWhite,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (isEnglish) "Humanity's most distant human-made object" else "মহাশূন্যে মানুষের সবচেয়ে দূরবর্তী সৃষ্টি",
                                color = StarGold,
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

                // Hero Banner
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(175.dp)
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.voyager_interstellar_1790580672247),
                        contentDescription = "Voyager Space Probe",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Live Distance & Telemetry Dashboard
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0x33101730),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PulsarCyan.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (isEnglish) "LIVE DISTANCE FROM EARTH" else "পৃথিবী থেকে বর্তমান লাইভ দূরত্ব",
                                color = PulsarCyan,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(PulsarCyan.copy(alpha = 0.2f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = if (isEnglish) "17 km/s Speed" else "গতি ১৭ কিমি/সে.",
                                    color = PulsarCyan,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "%,d km".format(currentDistanceKm),
                            color = StarlightWhite,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(text = if (isEnglish) "Astronomical Units" else "মহাজাগতিক একক (AU)", color = StarlightMuted, fontSize = 11.sp)
                                Text(text = String.format("%.2f AU", distanceAU), color = StarGold, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                            }
                            Column {
                                Text(text = if (isEnglish) "One-Way Light Time" else "আলো পৌঁছানোর সময়", color = StarlightMuted, fontSize = 11.sp)
                                Text(text = String.format("%.2f Hours", signalDelayHours), color = StarGold, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // The Golden Record Highlight
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0x22FFD700),
                    border = androidx.compose.foundation.BorderStroke(1.2.dp, StarGold.copy(alpha = 0.6f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "📀", fontSize = 20.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isEnglish) "The Golden Record: Earth's Time Capsule" else "গোল্ডেন রেকর্ড: পৃথিবীর মহাজাগতিক বাণী",
                                color = StarGold,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = if (isEnglish)
                                "Carries greetings in 55 human languages, 115 scientific images, sounds of nature, and music across human civilizations, designed to survive for billions of years in interstellar void."
                            else
                                "ভয়েজার বহন করছে পৃথিবীর ১১৫টি ছবি, ঝিঁঝিঁ পোকা ও সমুদ্রের শব্দ, পৃথিবীর সেরা সঙ্গীত এবং ৫৫টি মানব ভাষার শুভেচ্ছা বার্তা—যা কোটি কোটি বছর অক্ষত থাকবে।",
                            color = StarlightWhite,
                            fontSize = 12.sp,
                            lineHeight = 17.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Historic Bengali Greeting on the Golden Record
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0x44080D20),
                            border = androidx.compose.foundation.BorderStroke(1.dp, StarGold.copy(alpha = 0.4f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = if (isEnglish) "Historic Bengali Greeting on Golden Record:" else "গোল্ডেন রেকর্ডে সংরক্ষিত ঐতিহাসিক বাংলা বাণী:",
                                    color = StarGold,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "\"নমস্কার, বিশ্বের শান্তি হউক\"",
                                    color = PulsarCyan,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "(Translation: \"Greetings, peace to all the universe\")",
                                    color = StarlightMuted,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // The Pale Blue Dot Wisdom
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = CosmicSurfaceLight,
                    border = androidx.compose.foundation.BorderStroke(1.dp, CosmicBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = if (isEnglish) "🔭 The Pale Blue Dot (Carl Sagan)" else "🔭 পেল ব্লু ডট: মহাজাগতিক দর্শন",
                            color = PulsarCyan,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (isEnglish)
                                "\"Look again at that dot. That's here. That's home. That's us. On it everyone you love, everyone you know... lived out their lives on a mote of dust suspended in a sunbeam.\""
                            else
                                "\"ওই ক্ষুদ্র বিন্দুটির দিকে আবার তাকাও। ওটাই আমাদের বাড়ি, ওটাই আমরা। তোমার পরিচিত ও প্রিয় প্রতিটি মানুষ সূর্যের আলোয় ভাসমান ওই ধূলিকণাতেই তাদের জীবন কাটিয়ে গেছে।\"",
                            color = StarlightMuted,
                            fontSize = 12.sp,
                            lineHeight = 17.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = StarGold, contentColor = SpaceBlack),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                ) {
                    Text(
                        text = if (isEnglish) "Close Mission Dossier" else "বন্ধ করুন",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
