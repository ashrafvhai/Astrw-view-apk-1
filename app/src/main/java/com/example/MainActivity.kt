package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.localization.CosmicStrings
import com.example.ui.screens.CosmicJournalScreen
import com.example.ui.screens.PlanetDetailScreen
import com.example.ui.screens.SolarSystemScreen
import com.example.ui.screens.StellarMapScreen
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.CosmicViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                CosmicApp()
            }
        }
    }
}

private data class NavDestination(
    val screen: AppScreen,
    val getLabel: (com.example.ui.localization.AppLanguage) -> String,
    val icon: ImageVector,
    val testTag: String
)

@Composable
fun CosmicApp(viewModel: CosmicViewModel = viewModel()) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val isFullScreenMode by viewModel.isFullScreenMode.collectAsState()
    val currentLanguage by viewModel.currentLanguage.collectAsState()

    val destinations = listOf(
        NavDestination(AppScreen.SOLAR_SYSTEM, { CosmicStrings.navSolar(it) }, Icons.Default.Public, "nav_solar_system"),
        NavDestination(AppScreen.PLANET_DETAIL, { CosmicStrings.navPlanet(it) }, Icons.Default.Explore, "nav_planet_detail"),
        NavDestination(AppScreen.STELLAR_MAP, { CosmicStrings.navStellar(it) }, Icons.Default.AutoAwesome, "nav_stellar_map"),
        NavDestination(AppScreen.COSMIC_JOURNAL, { CosmicStrings.navJournal(it) }, Icons.Default.MenuBook, "nav_cosmic_journal")
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = SpaceBlack,
        bottomBar = {
            if (!isFullScreenMode) {
                NavigationBar(
                    containerColor = CosmicSurface.copy(alpha = 0.95f),
                    contentColor = StarlightWhite,
                    tonalElevation = 8.dp,
                    windowInsets = WindowInsets.navigationBars,
                    modifier = Modifier.testTag("main_navigation_bar")
                ) {
                    for (dest in destinations) {
                        val isSelected = currentScreen == dest.screen
                        val labelText = dest.getLabel(currentLanguage)
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { viewModel.navigateTo(dest.screen) },
                            icon = {
                                Icon(
                                    imageVector = dest.icon,
                                    contentDescription = labelText,
                                    modifier = Modifier.size(22.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = labelText,
                                    fontSize = 11.sp
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = SpaceBlack,
                                selectedTextColor = PulsarCyan,
                                indicatorColor = PulsarCyan,
                                unselectedIconColor = StarlightMuted,
                                unselectedTextColor = StarlightMuted
                            ),
                            modifier = Modifier.testTag(dest.testTag)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(if (isFullScreenMode) PaddingValues(0.dp) else innerPadding)
        ) {
            when (currentScreen) {
                AppScreen.SOLAR_SYSTEM -> SolarSystemScreen(viewModel = viewModel)
                AppScreen.PLANET_DETAIL -> PlanetDetailScreen(viewModel = viewModel)
                AppScreen.STELLAR_MAP -> StellarMapScreen(viewModel = viewModel)
                AppScreen.COSMIC_JOURNAL -> CosmicJournalScreen(viewModel = viewModel)
            }
        }
    }
}
