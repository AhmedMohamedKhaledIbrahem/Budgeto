package com.budgeto.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkScheme =
    darkColorScheme(
        primary = Primary80, // Bright primary for dark backgrounds
        primaryContainer = Primary30, // Darker container for primary emphasis
        onPrimary = Primary20,
        onPrimaryContainer = Primary90,
        secondary = Secondary80,
        secondaryContainer = Secondary30,
        onSecondary = Secondary20,
        onSecondaryContainer = Secondary90,
        background = Neutral10, // Dark background
        surface = Neutral10, // Dark surface
        onBackground = Neutral90,
        onSurface = Neutral90,
        surfaceVariant = NeutralVariant30,
        onSurfaceVariant = NeutralVariant80,
        inverseSurface = Neutral90,
        inverseOnSurface = Neutral10,
        outline = NeutralVariant60,
        outlineVariant = NeutralVariant30,
        surfaceTint = Primary80,
        error = Error80,
        errorContainer = Error30,
        onError = Error20,
        onErrorContainer = Error90,
    )

private val DefaultScheme =
    lightColorScheme(
        primary = Primary30,
        primaryContainer = Primary50,
        onPrimary = Primary100,
        onPrimaryContainer = Primary95,
        secondary = Secondary30,
        secondaryContainer = Secondary50,
        background = NeutralVariant99,
        surface = Primary100,
        inverseOnSurface = Secondary80,
        onSurface = NeutralVariant10,
        onSurfaceVariant = NeutralVariant30,
        outline = NeutralVariant50,
        outlineVariant = NeutralVariant80,
        surfaceVariant = SurfaceVariant,
        surfaceTint = SurfaceTint12,
        inverseSurface = Secondary95,
        onErrorContainer = Error20,
        errorContainer = Error95,
        onError = Error100,
    )
/* Other default colors to override
background = Color(0xFFFFFBFE),
surface = Color(0xFFFFFBFE),
onPrimary = Color.White,
onSecondary = Color.White,
onTertiary = Color.White,
onBackground = Color(0xFF1C1B1F),
onSurface = Color(0xFF1C1B1F),
*/


@Composable
fun BudgetoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkScheme else DefaultScheme

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = AppShapes,
        typography = Typography,
        content = content,
    )
}