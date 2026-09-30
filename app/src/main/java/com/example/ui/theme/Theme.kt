package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val SpaceColorScheme = darkColorScheme(
    primary = SpacePrimary,
    onPrimary = SpaceOnPrimary,
    primaryContainer = SpacePrimaryContainer,
    onPrimaryContainer = SpaceOnPrimaryContainer,
    secondary = SpaceSecondary,
    onSecondary = SpaceOnSecondary,
    secondaryContainer = SpaceSecondaryContainer,
    onSecondaryContainer = SpaceOnSecondaryContainer,
    tertiary = StarGold,
    background = SpaceBackground,
    onBackground = SpaceOnBackground,
    surface = CosmicSurface,
    onSurface = StarlightWhite,
    surfaceVariant = CosmicSurfaceLight,
    onSurfaceVariant = StarlightMuted,
    outline = CosmicBorder
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Forced cosmic theme for authentic astronomical exploration
    content: @Composable () -> Unit,
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = SpaceBlack.toArgb()
                window.navigationBarColor = SpaceBlack.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = SpaceColorScheme,
        typography = Typography,
        content = content
    )
}
