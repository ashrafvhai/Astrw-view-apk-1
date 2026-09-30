package com.example.data.model

import androidx.compose.ui.graphics.Color
import com.example.engine3d.Vector3D

enum class CelestialType(val labelEn: String, val labelBn: String) {
    STAR("Star", "নক্ষত্র"),
    TERRESTRIAL("Terrestrial Planet", "পাথুরে গ্রহ"),
    GAS_GIANT("Gas Giant", "গ্যাসীয় দানব"),
    ICE_GIANT("Ice Giant", "বরফ দানব"),
    DWARF_PLANET("Dwarf Planet", "বামন গ্রহ"),
    COMET("Comet", "ধূমকেতু")
}

data class RingConfig(
    val innerRadiusFactor: Float,
    val outerRadiusFactor: Float,
    val ringColor: Color,
    val ringColorEdge: Color,
    val tiltRad: Float
)

data class CoreLayer(
    val nameEn: String,
    val nameBn: String,
    val thicknessDescription: String,
    val descriptionEn: String,
    val descriptionBn: String,
    val color: Color
)

data class SpaceMission(
    val year: String,
    val nameEn: String,
    val nameBn: String,
    val agency: String,
    val descriptionEn: String,
    val descriptionBn: String
)

data class PlanetaryTimeInfo(
    val yearInEarthDays: Double,
    val dayInHours: Double,
    val yearSummaryEn: String,
    val yearSummaryBn: String,
    val daySummaryEn: String,
    val daySummaryBn: String,
    val timeComparisonEn: String,
    val timeComparisonBn: String,
    val timeMysteryEn: String,
    val timeMysteryBn: String
)

data class CelestialBody(
    val id: String,
    val nameEn: String,
    val nameBn: String,
    val type: CelestialType,
    val tagLineEn: String,
    val tagLineBn: String,
    val baseRadiusDp: Float,
    val relativeScale: Float,
    val orbitDistanceUnits: Float, // distance in 3D scene
    val orbitSpeedMultiplier: Float, // relative angular speed
    val orbitInclinationDeg: Float = 0f,
    val axialTiltDeg: Float = 0f,
    val rotationSpeed: Float = 1f,
    val primaryColor: Color,
    val secondaryColor: Color,
    val glowColor: Color,
    val atmosphereColor: Color? = null,
    val ringConfig: RingConfig? = null,
    // Scientific Parameters
    val diameterKm: String,
    val massKg: String,
    val surfaceGravityMps2: Double,
    val dayLengthHours: String,
    val yearLengthDays: String,
    val averageTempC: String,
    val distanceFromSunMillionKm: String,
    val numberOfMoons: Int,
    val atmosphereCompositionBn: List<String>,
    val coreLayers: List<CoreLayer>,
    val fascinatingFactsBn: List<String>,
    val cosmicMysteriesBn: List<String>,
    val missions: List<SpaceMission>,
    val timeInfo: PlanetaryTimeInfo
)

data class Star3D(
    val id: String,
    val nameEn: String,
    val nameBn: String,
    val magnitude: Float, // Lower = brighter
    val position: Vector3D, // Direction on celestial sphere
    val spectralColor: Color,
    val spectralClass: String
)

data class Constellation(
    val id: String,
    val nameEn: String,
    val nameBn: String,
    val englishMeaning: String,
    val banglaMeaning: String,
    val mythologyBn: String,
    val seasonBn: String,
    val stars: List<Star3D>,
    val lines: List<Pair<Int, Int>> // Indices in stars list
)

enum class WonderCategory(val labelEn: String, val labelBn: String) {
    BLACK_HOLE("Black Hole", "কৃষ্ণগহ্বর"),
    NEBULA("Nebula", "নীহারিকা"),
    GALAXY("Galaxy", "ছায়াপথ"),
    PULSAR("Pulsar / Magnetar", "পালসার / ম্যাগনেটার"),
    EXOPLANET("Exoplanet System", "বহির্জাগতিক গ্রহব্যবস্থা")
}

data class DeepSpaceWonder(
    val id: String,
    val nameEn: String,
    val nameBn: String,
    val category: WonderCategory,
    val distanceLightYears: String,
    val constellation: String,
    val subtitleBn: String,
    val descriptionBn: String,
    val whyItMattersBn: String,
    val visualPosition: Vector3D,
    val primaryColor: Color,
    val accentColor: Color
)

data class CosmicMysteryTopic(
    val id: String,
    val titleEn: String,
    val titleBn: String,
    val categoryBn: String,
    val overviewBn: String,
    val currentTheoriesBn: List<String>,
    val mindBlowingFactBn: String,
    val iconKey: String,
    val accentColor: Color
)

data class SpaceEvent(
    val dateOrSeason: String,
    val titleBn: String,
    val titleEn: String,
    val descriptionBn: String,
    val viewingTipBn: String
)
