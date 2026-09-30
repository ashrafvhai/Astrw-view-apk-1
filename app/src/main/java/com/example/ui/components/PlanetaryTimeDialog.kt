package com.example.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.CelestialBody
import com.example.ui.localization.AppLanguage
import com.example.ui.theme.*

@Composable
fun PlanetaryTimeDialog(
    body: CelestialBody,
    currentLanguage: AppLanguage,
    earthAgeInput: String,
    onEarthAgeChanged: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val isEnglish = currentLanguage == AppLanguage.ENGLISH
    val bodyName = if (isEnglish) body.nameEn else body.nameBn

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = CosmicSurface.copy(alpha = 0.98f),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, StarGold.copy(alpha = 0.7f)),
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .fillMaxHeight(0.85f)
                .testTag("planetary_time_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Top Close Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = if (isEnglish) "Orbital & Planetary Time" else "মহাজাগতিক সময় ও বর্ষ গণনা",
                            color = StarGold,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (isEnglish) "$bodyName • Orbital Year & Day Duration" else "$bodyName • ১ বছর ও ১ দিনের সময়কাল",
                            color = StarlightMuted,
                            fontSize = 12.sp
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

                // The complete planetary time card with calculator & comparison
                PlanetaryTimeCard(
                    body = body,
                    currentLanguage = currentLanguage,
                    earthAgeInput = earthAgeInput,
                    onEarthAgeChanged = onEarthAgeChanged
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Summary Note
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0x18FFB300),
                    border = androidx.compose.foundation.BorderStroke(1.dp, StarGold.copy(alpha = 0.35f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = if (isEnglish) "💡 What determines a Year and a Day?" else "💡 বছর ও দিন কীভাবে নির্ধারিত হয়?",
                            color = StarGold,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = if (isEnglish)
                                "A Year is the exact time a planet takes to make one complete orbit around the Sun. A Day is the time it takes to spin once on its own axis. Because every planet has a different distance from the Sun and rotational velocity, each world experiences a uniquely foreign flow of time!"
                            else
                                "সূর্যকে সম্পূর্ণ একবার প্রদক্ষিণ করতে যে সময় লাগে তা হলো ঐ গ্রহের ১ বছর। আর নিজের অক্ষে লাটিমের মতো একবার পূর্ণ পাক খেতে যে সময় লাগে তা হলো ১ দিন। সূর্য থেকে দূরত্ব ও ঘূর্ণনগতির ভিন্নতার কারণে প্রতিটি গ্রহের বছর ও দিন সম্পূর্ণ আলাদা!",
                            color = StarlightWhite,
                            fontSize = 12.sp,
                            lineHeight = 17.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = StarGold,
                        contentColor = SpaceBlack
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                ) {
                    Text(
                        text = if (isEnglish) "Close Time Dossier" else "বন্ধ করুন",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
