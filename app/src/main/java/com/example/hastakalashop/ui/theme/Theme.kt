package com.example.hastakalashop.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = LoomTeal,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    primaryContainer = androidx.compose.ui.graphics.Color(0xFFC9F2ED),
    onPrimaryContainer = androidx.compose.ui.graphics.Color(0xFF00201D),
    secondary = Marigold,
    onSecondary = androidx.compose.ui.graphics.Color(0xFF3A2A00),
    secondaryContainer = androidx.compose.ui.graphics.Color(0xFFFFE2A8),
    tertiary = Kumkum,
    tertiaryContainer = androidx.compose.ui.graphics.Color(0xFFFFD9DE),
    background = Sand,
    surface = androidx.compose.ui.graphics.Color(0xFFFFFBF4),
    surfaceContainer = androidx.compose.ui.graphics.Color(0xFFF6EFE4),
    surfaceContainerHigh = androidx.compose.ui.graphics.Color(0xFFFFFFFF)
)

private val DarkColors = darkColorScheme(
    primary = androidx.compose.ui.graphics.Color(0xFF8ED7C4),
    primaryContainer = androidx.compose.ui.graphics.Color(0xFF005047),
    secondary = androidx.compose.ui.graphics.Color(0xFFFFC85A),
    secondaryContainer = androidx.compose.ui.graphics.Color(0xFF5D4300),
    tertiary = androidx.compose.ui.graphics.Color(0xFFFFB1C1),
    tertiaryContainer = androidx.compose.ui.graphics.Color(0xFF7D2938),
    background = androidx.compose.ui.graphics.Color(0xFF111815),
    surface = androidx.compose.ui.graphics.Color(0xFF17211E),
    surfaceContainer = androidx.compose.ui.graphics.Color(0xFF1D2925),
    surfaceContainerHigh = androidx.compose.ui.graphics.Color(0xFF26332F)
)

@Composable
fun HastaKalaShopTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        content = content
    )
}
