package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.CosmicAudioSynthesizer
import com.example.data.datasource.*
import com.example.data.local.CosmicBookmark
import com.example.data.local.CosmicDatabase
import com.example.data.local.CosmicRepository
import com.example.data.model.CelestialBody
import com.example.data.model.Constellation
import com.example.data.model.DeepSpaceWonder
import com.example.sensor.CosmicOrientationSensor
import com.example.sensor.DeviceOrientationAngles
import com.example.ui.localization.AppLanguage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppScreen {
    SOLAR_SYSTEM,
    PLANET_DETAIL,
    STELLAR_MAP,
    COSMIC_JOURNAL
}

enum class CosmicDomain(val labelBn: String, val labelEn: String) {
    SOLAR_SYSTEM("সৌরজগৎ", "Solar System"),
    INTERSTELLAR("আন্তঃনাক্ষত্রিক", "Interstellar"),
    DEEP_GALAXIES("দূর গ্যালাক্সি", "Deep Galaxies")
}

class CosmicViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CosmicRepository
    private val audioSynthesizer = CosmicAudioSynthesizer()
    private val orientationSensor = CosmicOrientationSensor(application)

    init {
        val database = CosmicDatabase.getDatabase(application)
        repository = CosmicRepository(database.bookmarkDao())
    }

    val bookmarks: StateFlow<List<CosmicBookmark>> = repository.allBookmarks
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Language State: DEFAULT IS ENGLISH as requested!
    private val _currentLanguage = MutableStateFlow(AppLanguage.ENGLISH)
    val currentLanguage: StateFlow<AppLanguage> = _currentLanguage.asStateFlow()

    private val _currentScreen = MutableStateFlow(AppScreen.SOLAR_SYSTEM)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    // Full Screen 360 Mode
    private val _isFullScreenMode = MutableStateFlow(false)
    val isFullScreenMode: StateFlow<Boolean> = _isFullScreenMode.asStateFlow()

    // Active Domain (Solar System vs Interstellar vs Deep Galaxies)
    private val _cosmicDomain = MutableStateFlow(CosmicDomain.SOLAR_SYSTEM)
    val cosmicDomain: StateFlow<CosmicDomain> = _cosmicDomain.asStateFlow()

    // Selected Galaxy for deep space inspection
    private val _selectedGalaxy = MutableStateFlow<GalaxyTarget?>(null)
    val selectedGalaxy: StateFlow<GalaxyTarget?> = _selectedGalaxy.asStateFlow()

    // Camera Zoom Multiplier (1.0 = normal, < 1.0 = zoomed in close, > 1.0 = zoomed out into galaxies)
    private val _cameraZoomMultiplier = MutableStateFlow(1.0f)
    val cameraZoomMultiplier: StateFlow<Float> = _cameraZoomMultiplier.asStateFlow()

    private val _selectedBody = MutableStateFlow<CelestialBody>(SolarSystemData.getBodyById("earth"))
    val selectedBody: StateFlow<CelestialBody> = _selectedBody.asStateFlow()

    private val _focusedBody = MutableStateFlow<CelestialBody?>(null)
    val focusedBody: StateFlow<CelestialBody?> = _focusedBody.asStateFlow()

    // DEFAULT ORBIT SPEED IS 0.0f (PAUSED) so celestial objects are steady, perfectly stable, and readable!
    private val _orbitSpeed = MutableStateFlow(0.0f)
    val orbitSpeed: StateFlow<Float> = _orbitSpeed.asStateFlow()

    // Planetary Time Dialog toggle
    private val _showTimeDialog = MutableStateFlow(false)
    val showTimeDialog: StateFlow<Boolean> = _showTimeDialog.asStateFlow()

    // Premier Dialogs
    private val _showVoyagerDialog = MutableStateFlow(false)
    val showVoyagerDialog: StateFlow<Boolean> = _showVoyagerDialog.asStateFlow()

    private val _showExoplanetDialog = MutableStateFlow(false)
    val showExoplanetDialog: StateFlow<Boolean> = _showExoplanetDialog.asStateFlow()

    private val _showTimeWarpDialog = MutableStateFlow(false)
    val showTimeWarpDialog: StateFlow<Boolean> = _showTimeWarpDialog.asStateFlow()

    private val _currentEpoch = MutableStateFlow<CosmicEpoch>(TimeWarpData.epochs[3]) // Present Day (2026)
    val currentEpoch: StateFlow<CosmicEpoch> = _currentEpoch.asStateFlow()

    // Cosmic Audio Synthesizer State
    private val _isAudioPlaying = MutableStateFlow(false)
    val isAudioPlaying: StateFlow<Boolean> = _isAudioPlaying.asStateFlow()

    // Gyroscope / Device 360 Motion Tracker State
    private val _isGyroEnabled = MutableStateFlow(false)
    val isGyroEnabled: StateFlow<Boolean> = _isGyroEnabled.asStateFlow()
    val deviceOrientation: StateFlow<DeviceOrientationAngles> = orientationSensor.orientationAngles

    private val _showInternalLayers = MutableStateFlow(false)
    val showInternalLayers: StateFlow<Boolean> = _showInternalLayers.asStateFlow()

    private val _selectedWonder = MutableStateFlow<DeepSpaceWonder?>(StellarData.deepSpaceWonders[0])
    val selectedWonder: StateFlow<DeepSpaceWonder?> = _selectedWonder.asStateFlow()

    private val _selectedConstellation = MutableStateFlow<Constellation?>(StellarData.constellations[0])
    val selectedConstellation: StateFlow<Constellation?> = _selectedConstellation.asStateFlow()

    // Weight Calculator (kg)
    private val _userWeightKg = MutableStateFlow("65")
    val userWeightKg: StateFlow<String> = _userWeightKg.asStateFlow()

    // Age on Planet Calculator (Earth Years)
    private val _userAgeEarthYears = MutableStateFlow("25")
    val userAgeEarthYears: StateFlow<String> = _userAgeEarthYears.asStateFlow()

    // Cosmic Scale (Powers of Ten index 0..8)
    private val _cosmicScaleIndex = MutableStateFlow(3)
    val cosmicScaleIndex: StateFlow<Int> = _cosmicScaleIndex.asStateFlow()

    fun setLanguage(language: AppLanguage) {
        _currentLanguage.value = language
    }

    fun toggleLanguage() {
        _currentLanguage.value = if (_currentLanguage.value == AppLanguage.ENGLISH) {
            AppLanguage.BENGALI
        } else {
            AppLanguage.ENGLISH
        }
    }

    fun navigateTo(screen: AppScreen) {
        _currentScreen.value = screen
    }

    fun toggleFullScreenMode(forceValue: Boolean? = null) {
        _isFullScreenMode.value = forceValue ?: !_isFullScreenMode.value
    }

    fun setCosmicDomain(domain: CosmicDomain) {
        _cosmicDomain.value = domain
        when (domain) {
            CosmicDomain.SOLAR_SYSTEM -> {
                _cameraZoomMultiplier.value = 1.0f
                _focusedBody.value = null
            }
            CosmicDomain.INTERSTELLAR -> {
                _cameraZoomMultiplier.value = 3.5f
                _focusedBody.value = null
            }
            CosmicDomain.DEEP_GALAXIES -> {
                _cameraZoomMultiplier.value = 8.5f
                _focusedBody.value = null
            }
        }
    }

    fun selectGalaxy(galaxy: GalaxyTarget?) {
        _selectedGalaxy.value = galaxy
    }

    fun setCameraZoom(zoom: Float) {
        _cameraZoomMultiplier.value = zoom.coerceIn(0.4f, 25.0f)
        if (_cameraZoomMultiplier.value > 6.0f) {
            _cosmicDomain.value = CosmicDomain.DEEP_GALAXIES
        } else if (_cameraZoomMultiplier.value > 2.2f) {
            _cosmicDomain.value = CosmicDomain.INTERSTELLAR
        } else {
            _cosmicDomain.value = CosmicDomain.SOLAR_SYSTEM
        }
    }

    fun applyZoomFactor(factor: Float) {
        setCameraZoom(_cameraZoomMultiplier.value * factor)
    }

    fun zoomIn() {
        setCameraZoom(_cameraZoomMultiplier.value * 0.7f)
    }

    fun zoomOut() {
        setCameraZoom(_cameraZoomMultiplier.value * 1.4f)
    }

    fun resetCamera() {
        _cameraZoomMultiplier.value = 1.0f
        _focusedBody.value = null
        _cosmicDomain.value = CosmicDomain.SOLAR_SYSTEM
    }

    fun selectCelestialBody(body: CelestialBody, navigateToDetail: Boolean = false) {
        _selectedBody.value = body
        if (navigateToDetail) {
            _currentScreen.value = AppScreen.PLANET_DETAIL
        }
    }

    fun toggleFocusLock(body: CelestialBody?) {
        if (_focusedBody.value?.id == body?.id) {
            _focusedBody.value = null
        } else {
            _focusedBody.value = body
        }
    }

    fun setOrbitSpeed(speed: Float) {
        _orbitSpeed.value = speed
    }

    fun toggleOrbitPlayPause() {
        _orbitSpeed.value = if (_orbitSpeed.value == 0.0f) 0.3f else 0.0f
    }

    fun openTimeDialog() {
        _showTimeDialog.value = true
    }

    fun closeTimeDialog() {
        _showTimeDialog.value = false
    }

    fun openVoyagerDialog() {
        _showVoyagerDialog.value = true
    }

    fun closeVoyagerDialog() {
        _showVoyagerDialog.value = false
    }

    fun openExoplanetDialog() {
        _showExoplanetDialog.value = true
    }

    fun closeExoplanetDialog() {
        _showExoplanetDialog.value = false
    }

    fun openTimeWarpDialog() {
        _showTimeWarpDialog.value = true
    }

    fun closeTimeWarpDialog() {
        _showTimeWarpDialog.value = false
    }

    fun applyEpoch(epoch: CosmicEpoch) {
        _currentEpoch.value = epoch
        _orbitSpeed.value = epoch.speedMultiplier
    }

    fun toggleAudio() {
        val playing = audioSynthesizer.toggle()
        _isAudioPlaying.value = playing
    }

    fun toggleGyroMode() {
        if (_isGyroEnabled.value) {
            orientationSensor.stopTracking()
            _isGyroEnabled.value = false
        } else {
            val started = orientationSensor.startTracking()
            _isGyroEnabled.value = started
        }
    }

    fun toggleInternalLayers() {
        _showInternalLayers.value = !_showInternalLayers.value
    }

    fun selectWonder(wonder: DeepSpaceWonder) {
        _selectedWonder.value = wonder
    }

    fun selectConstellation(constellation: Constellation) {
        _selectedConstellation.value = constellation
    }

    fun updateUserWeight(weightText: String) {
        _userWeightKg.value = weightText.filter { it.isDigit() || it == '.' }
    }

    fun updateUserAge(ageText: String) {
        _userAgeEarthYears.value = ageText.filter { it.isDigit() || it == '.' }
    }

    fun setCosmicScaleIndex(index: Int) {
        _cosmicScaleIndex.value = index.coerceIn(0, 7)
    }

    fun isBookmarked(targetId: String): Boolean {
        return bookmarks.value.any { it.targetId == targetId }
    }

    fun toggleBookmark(targetId: String, nameEn: String, nameBn: String, category: String) {
        viewModelScope.launch {
            val currentlyBookmarked = isBookmarked(targetId)
            repository.toggleBookmark(
                targetId = targetId,
                nameEn = nameEn,
                nameBn = nameBn,
                category = category,
                isCurrentlyBookmarked = currentlyBookmarked
            )
        }
    }

    fun updateBookmarkNote(bookmark: CosmicBookmark, note: String) {
        viewModelScope.launch {
            repository.saveNote(bookmark, note)
        }
    }

    fun deleteBookmark(bookmark: CosmicBookmark) {
        viewModelScope.launch {
            repository.deleteBookmark(bookmark)
        }
    }

    override fun onCleared() {
        super.onCleared()
        audioSynthesizer.stop()
        orientationSensor.stopTracking()
    }
}
