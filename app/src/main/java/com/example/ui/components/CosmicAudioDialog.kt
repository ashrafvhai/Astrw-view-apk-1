package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.CelestialBody
import com.example.ui.localization.AppLanguage
import com.example.ui.theme.*
import kotlin.math.PI
import kotlin.math.sin

data class PlanetaryAcousticData(
    val frequencyLabelEn: String,
    val frequencyLabelBn: String,
    val phenomenonEn: String,
    val phenomenonBn: String,
    val descriptionEn: String,
    val descriptionBn: String,
    val waveColor: Color
)

fun getPlanetaryAcoustics(bodyId: String): PlanetaryAcousticData = when (bodyId) {
    "sun" -> PlanetaryAcousticData(
        frequencyLabelEn = "3.3 mHz (5-Minute Acoustic Oscillation)",
        frequencyLabelBn = "৩.৩ মিলিহার্টজ (৫ মিনিটের হেলিওসিজমোলজি)",
        phenomenonEn = "Internal Convective Sound Waves",
        phenomenonBn = "সূর্যের অভ্যন্তরীণ পরিচলন শব্দতরঙ্গ",
        descriptionEn = "Massive sound waves reverberate through the Sun's dense plasma, creating global vibrations captured by SOHO and SDO spacecraft.",
        descriptionBn = "সূর্যের উত্তপ্ত প্লাজমার মধ্যে সৃষ্ট দানবীয় শব্দতরঙ্গ পুরো সৌরগোলক জুড়ে প্রতিধ্বনিত হয়, যা উপগ্রহের মাধ্যমে রেকর্ড করা হয়েছে।",
        waveColor = StarGold
    )
    "earth" -> PlanetaryAcousticData(
        frequencyLabelEn = "7.83 Hz (Schumann Resonance)",
        frequencyLabelBn = "৭.৮৩ হার্টজ (শুম্যান রেজোন্যান্স)",
        phenomenonEn = "Global Electromagnetic Heartbeat & Auroral Chorus",
        phenomenonBn = "পৃথিবীর বৈশ্বিক হৃদস্পন্দন ও অরোরাল সুর",
        descriptionEn = "Continuous global lightning discharges resonate between the Earth's surface and the ionosphere, creating a persistent 7.83 Hz standing wave.",
        descriptionBn = "পৃথিবীর পৃষ্ঠ ও আয়নমণ্ডলের মাঝে প্রতিনিয়ত হওয়া বজ্রপাতের ফলে ৭.৮৩ হার্টজের এক অনন্ত সুর ধ্বনিত হয়, যা পৃথিবীর হৃদস্পন্দন নামে পরিচিত।",
        waveColor = PulsarCyan
    )
    "jupiter" -> PlanetaryAcousticData(
        frequencyLabelEn = "10 – 40 MHz (Jovian Decametric Radio Storms)",
        frequencyLabelBn = "১০ – ৪০ মেগাহার্টজ (বৃহস্পতির রেডিও তরঙ্গ)",
        phenomenonEn = "Io Plasma Torus & Auroral Roar",
        phenomenonBn = "আইও প্লাজমা টরাস ও দানবীয় রেডিও ঝড়",
        descriptionEn = "Jupiter's colossal magnetic field strips gas from volcanic moon Io, creating intense electromagnetic radio bursts that sound like ocean waves breaking on a cosmic shore.",
        descriptionBn = "বৃহস্পতির দানবীয় চুম্বকমণ্ডলের প্রভাবে আগ্নেয়গিরির চাঁদ আইও থেকে নির্গত আয়নিত গ্যাস প্রবল রেডিও ঝড়ের সৃষ্টি করে, যা মহাসাগরের ঢেউয়ের মতো গর্জন তোলে।",
        waveColor = Color(0xFFFF9E80)
    )
    "saturn" -> PlanetaryAcousticData(
        frequencyLabelEn = "100 – 500 kHz (Saturn Kilometric Radiation)",
        frequencyLabelBn = "১০০ – ৫০০ কিলোহার্টজ (শনির কিলোমিটারিক তরঙ্গ)",
        phenomenonEn = "Cassini Radio Plasma Waves & Ring Echoes",
        phenomenonBn = "ক্যাসিনির রেকর্ডকৃত প্লাজমা তরঙ্গ ও বলয় প্রতিধ্বনি",
        descriptionEn = "Recorded by NASA's Cassini spacecraft, Saturn's polar auroras emit eerie, rising and falling musical tones through the icy dust of its rings.",
        descriptionBn = "নাসার ক্যাসিনি মহাকাশযানের রেকর্ড করা শনির মেরুপ্রভার প্লাজমা তরঙ্গ বরফের বলয় ভেদ করে এক ভৌতিক ও অপরূপ সুরের মূর্ছনা তৈরি করে।",
        waveColor = Color(0xFFFFD54F)
    )
    "mars" -> PlanetaryAcousticData(
        frequencyLabelEn = "1.2 – 10 Hz (Atmospheric Infrasound & Dust Devils)",
        frequencyLabelBn = "১.২ – ১০ হার্টজ (বায়ুমণ্ডলীয় কম্পন ও ধূলিঝড়)",
        phenomenonEn = "InSight Seismometer Micro-tremors",
        phenomenonBn = "ইনসাইট সিসমোমিটারের লাল মাটির দীর্ঘশ্বাস",
        descriptionEn = "NASA's InSight lander captured deep infrasonic vibrations from martian wind rustling through solar panels and low-frequency quakes.",
        descriptionBn = "নাসার ইনসাইট ল্যান্ডারের সিসমোমিটার মঙ্গলের পাতলা বাতাসে সৌর প্যানেল কাঁপানোর গভীর শব্দ ও গ্রহীয় মৃদু কম্পন সরাসরি রেকর্ড করেছে।",
        waveColor = MarsRed
    )
    "venus" -> PlanetaryAcousticData(
        frequencyLabelEn = "350 Hz (Ionospheric Lightning Whistlers)",
        frequencyLabelBn = "৩৫০ হার্টজ (আয়নোস্ফিয়ারিক বজ্রধ্বনি)",
        phenomenonEn = "Sulfuric Cloud Electric Discharges",
        phenomenonBn = "সালফিউরিক অ্যাসিড মেঘের বিদ্যুৎক্ষরণ",
        descriptionEn = "Venus Express probe detected low-frequency electromagnetic whistler waves traveling along Venus's induced magnetic field from dense sulfuric storms.",
        descriptionBn = "শুক্রের ঘন সালফিউরিক মেঘমালায় সংঘটিত বজ্রপাত থেকে সৃষ্ট তড়িৎচুম্বকীয় তরঙ্গ ভেনাস এক্সপ্রেস মহাকাশযান দ্বারা চিহ্নিত হয়েছে।",
        waveColor = Color(0xFFFFB74D)
    )
    else -> PlanetaryAcousticData(
        frequencyLabelEn = "120 – 440 Hz (Solar Wind Magnetospheric Resonance)",
        frequencyLabelBn = "১২০ – ৪৪০ হার্টজ (সৌরবায়ু ও চুম্বকমণ্ডলের অনুরণন)",
        phenomenonEn = "Interplanetary Plasma Waves",
        phenomenonBn = "আন্তঃগ্রহ প্লাজমা তরঙ্গ",
        descriptionEn = "Charged solar wind particles colliding with interplanetary magnetic fields generate harmonically oscillating electromagnetic waves.",
        descriptionBn = "সূর্য থেকে ধেয়ে আসা আয়নিত সৌরকণা গ্রহের চৌম্বকক্ষেত্রের সাথে মিথস্ক্রিয়ার মাধ্যমে এই মহাজাগতিক স্পন্দন সৃষ্টি করে।",
        waveColor = PulsarCyan
    )
}

@Composable
fun CosmicAudioDialog(
    body: CelestialBody,
    currentLanguage: AppLanguage,
    isPlaying: Boolean,
    onTogglePlay: () -> Unit,
    onDismiss: () -> Unit
) {
    val isEnglish = currentLanguage == AppLanguage.ENGLISH
    val bodyName = if (isEnglish) body.nameEn else body.nameBn
    val acousticData = remember(body.id) { getPlanetaryAcoustics(body.id) }

    // Waveform continuous smooth animation
    val infiniteTransition = rememberInfiniteTransition(label = "audio_wave")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = if (isPlaying) 1200 else 4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase"
    )

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = CosmicSurface.copy(alpha = 0.98f),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, acousticData.waveColor.copy(alpha = 0.8f)),
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .wrapContentHeight()
                .testTag("cosmic_audio_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(acousticData.waveColor.copy(alpha = 0.2f))
                                .border(1.dp, acousticData.waveColor, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (isPlaying) Icons.Default.GraphicEq else Icons.Default.VolumeUp,
                                contentDescription = "Audio",
                                tint = acousticData.waveColor,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (isEnglish) "Cosmic Sound & Frequency" else "মহাজাগতিক ধ্বনি ও ফ্রিকোয়েন্সি",
                                color = StarlightWhite,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (isEnglish) "$bodyName • Planetary Acoustic Signature" else "$bodyName • মহাজাগতিক স্পন্দন ও তরঙ্গ",
                                color = acousticData.waveColor,
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

                Spacer(modifier = Modifier.height(16.dp))

                // Real-time Oscilloscope Waveform Canvas
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFF040714),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CosmicBorder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val w = size.width
                            val h = size.height
                            val midY = h / 2f

                            // Draw subtle grid lines
                            drawLine(
                                color = Color(0x22FFFFFF),
                                start = Offset(0f, midY),
                                end = Offset(w, midY),
                                strokeWidth = 1f
                            )

                            // Primary glowing waveform
                            val path = Path()
                            val amplitude = if (isPlaying) (h * 0.35f) else (h * 0.12f)
                            val frequency = 3.5f

                            for (x in 0..w.toInt() step 3) {
                                val normX = x / w
                                val y = midY + sin((normX * frequency * 2 * PI + phase).toDouble()).toFloat() * amplitude *
                                        (0.8f + 0.2f * sin((normX * 8 + phase * 2).toDouble()).toFloat())
                                if (x == 0) path.moveTo(x.toFloat(), y) else path.lineTo(x.toFloat(), y)
                            }

                            drawPath(
                                path = path,
                                color = acousticData.waveColor,
                                style = Stroke(width = if (isPlaying) 3.5f else 2.0f)
                            )

                            // Harmonic secondary ghost wave
                            val path2 = Path()
                            val amplitude2 = amplitude * 0.5f
                            for (x in 0..w.toInt() step 4) {
                                val normX = x / w
                                val y = midY + sin((normX * frequency * 4 * PI - phase * 1.5).toDouble()).toFloat() * amplitude2
                                if (x == 0) path2.moveTo(x.toFloat(), y) else path2.lineTo(x.toFloat(), y)
                            }
                            drawPath(
                                path = path2,
                                color = acousticData.waveColor.copy(alpha = 0.4f),
                                style = Stroke(width = 1.5f)
                            )
                        }

                        // Live status indicator badge
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (isPlaying) Color(0x8800E5FF) else Color(0x66101730))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = if (isPlaying) {
                                    if (isEnglish) "● LIVE SYNTH" else "● সক্রিয় শব্দ"
                                } else {
                                    if (isEnglish) "STANDBY" else "প্রস্তুত"
                                },
                                color = if (isPlaying) SpaceBlack else StarlightMuted,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Frequency Metric Card
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0x22101730),
                    border = androidx.compose.foundation.BorderStroke(1.dp, acousticData.waveColor.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = if (isEnglish) "Resonance Frequency:" else "অনুরণন ফ্রিকোয়েন্সি:",
                            color = StarlightMuted,
                            fontSize = 11.sp
                        )
                        Text(
                            text = if (isEnglish) acousticData.frequencyLabelEn else acousticData.frequencyLabelBn,
                            color = acousticData.waveColor,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (isEnglish) acousticData.phenomenonEn else acousticData.phenomenonBn,
                            color = StarlightWhite,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Scientific Description
                Text(
                    text = if (isEnglish) acousticData.descriptionEn else acousticData.descriptionBn,
                    color = StarlightMuted,
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Play / Pause Sound Button
                Button(
                    onClick = onTogglePlay,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isPlaying) StarGold else acousticData.waveColor,
                        contentColor = SpaceBlack
                    ),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("toggle_cosmic_audio_button")
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Stop else Icons.Default.PlayArrow,
                        contentDescription = "Toggle Audio",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isPlaying) {
                            if (isEnglish) "Stop Space Resonance Audio" else "মহাজাগতিক ধ্বনি বন্ধ করুন"
                        } else {
                            if (isEnglish) "Listen to $bodyName's Cosmic Frequency" else "$bodyName-এর মহাজাগতিক ধ্বনি শুনুন"
                        },
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
