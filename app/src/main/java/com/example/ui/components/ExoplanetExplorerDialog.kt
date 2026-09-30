package com.example.ui.components

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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
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
import com.example.data.datasource.ExoplanetData
import com.example.data.datasource.ExoplanetTarget
import com.example.ui.localization.AppLanguage
import com.example.ui.theme.*

@Composable
fun ExoplanetExplorerDialog(
    currentLanguage: AppLanguage,
    onDismiss: () -> Unit
) {
    val isEnglish = currentLanguage == AppLanguage.ENGLISH
    var selectedPlanet by remember { mutableStateOf<ExoplanetTarget>(ExoplanetData.exoplanets[0]) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = CosmicSurface.copy(alpha = 0.98f),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, selectedPlanet.primaryColor),
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .fillMaxHeight(0.88f)
                .testTag("exoplanet_explorer_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(18.dp)
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
                                .background(selectedPlanet.primaryColor.copy(alpha = 0.25f))
                                .border(1.dp, selectedPlanet.primaryColor, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "🪐", fontSize = 20.sp)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (isEnglish) "Exoplanets & Alien Worlds" else "বহির্জাগতিক গ্রহ ও প্রাণের সন্ধান",
                                color = StarlightWhite,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (isEnglish) "Confirmed exoplanets discovered by Kepler & JWST" else "কেপলার ও জেমস ওয়েব আবিষ্কৃত অচেনা জগৎ",
                                color = PulsarCyan,
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

                Spacer(modifier = Modifier.height(12.dp))

                // Hero Artwork
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.exoplanet_alien_world_1790580694664),
                        contentDescription = "Alien Worlds",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Horizontal Carousel of Exoplanets
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (planet in ExoplanetData.exoplanets) {
                        val isSelected = planet.id == selectedPlanet.id
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = if (isSelected) CosmicSurfaceLight else Color(0x66080D20),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) planet.primaryColor else CosmicBorder
                            ),
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .clickable { selectedPlanet = planet }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(planet.primaryColor)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = planet.name,
                                    color = if (isSelected) StarlightWhite else StarlightMuted,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Selected Exoplanet Detail Card
                Surface(
                    shape = RoundedCornerShape(18.dp),
                    color = CosmicSurfaceLight,
                    border = androidx.compose.foundation.BorderStroke(1.dp, selectedPlanet.primaryColor.copy(alpha = 0.6f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = selectedPlanet.name,
                                    color = StarlightWhite,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = if (isEnglish) selectedPlanet.type.labelEn else selectedPlanet.type.labelBn,
                                    color = selectedPlanet.primaryColor,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            // Habitability Badge
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(selectedPlanet.primaryColor.copy(alpha = 0.2f))
                                    .border(1.dp, selectedPlanet.primaryColor, RoundedCornerShape(10.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = if (isEnglish) "Habitability Score" else "বাসযোগ্যতা স্কোর",
                                        color = StarlightMuted,
                                        fontSize = 9.sp
                                    )
                                    Text(
                                        text = selectedPlanet.habitabilityScore,
                                        color = selectedPlanet.primaryColor,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Quick Specs Grid
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = if (isEnglish) "Distance" else "দূরত্ব", color = StarlightMuted, fontSize = 11.sp)
                                Text(text = selectedPlanet.getDistance(currentLanguage), color = StarlightWhite, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = if (isEnglish) "Host Star" else "মাতৃ নক্ষত্র", color = StarlightMuted, fontSize = 11.sp)
                                Text(text = selectedPlanet.getHostStar(currentLanguage), color = StarlightWhite, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = if (isEnglish) "Orbital Period (1 Year)" else "১ বছর (কক্ষপথ)", color = StarlightMuted, fontSize = 11.sp)
                                Text(text = "${selectedPlanet.orbitalPeriodDays} Earth Days", color = StarGold, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = if (isEnglish) "Discovery" else "আবিষ্কার", color = StarlightMuted, fontSize = 11.sp)
                                Text(text = selectedPlanet.discoveryTelescope, color = PulsarCyan, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Surface Atmosphere Condition
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0x33101730),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "🌡", fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = selectedPlanet.getSurface(currentLanguage),
                                    color = StarlightWhite,
                                    fontSize = 11.sp,
                                    lineHeight = 16.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Description
                        Text(
                            text = selectedPlanet.getDescription(currentLanguage),
                            color = StarlightMuted,
                            fontSize = 12.sp,
                            lineHeight = 17.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Mind-Blowing Wonder Fact
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = selectedPlanet.primaryColor.copy(alpha = 0.15f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, selectedPlanet.primaryColor.copy(alpha = 0.4f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(modifier = Modifier.padding(10.dp)) {
                                Text(text = "✦", color = selectedPlanet.primaryColor, fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = selectedPlanet.getWonderFact(currentLanguage),
                                    color = StarlightWhite,
                                    fontSize = 11.sp,
                                    lineHeight = 16.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

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
                        text = if (isEnglish) "Return to Solar System" else "সৌরজগতে ফিরে যান",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
