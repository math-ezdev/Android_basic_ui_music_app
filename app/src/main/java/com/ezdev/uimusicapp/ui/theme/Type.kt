package com.ezdev.uimusicapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

val myTypography = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.ExtraBold,
    textAlign = TextAlign.Center,
    color = BlueDark
)
private val defaultTypography = Typography()

// Set of Material typography styles to start with
val Typography = Typography(
    //  display
    displayLarge = myTypography.copy(fontSize = defaultTypography.displayLarge.fontSize),
    displayMedium = myTypography.copy(fontSize = defaultTypography.displayMedium.fontSize),
    displaySmall = myTypography.copy(fontSize = defaultTypography.displaySmall.fontSize),
    //  headline
    headlineLarge = myTypography.copy(fontSize = defaultTypography.headlineLarge.fontSize),
    headlineMedium = myTypography.copy(fontSize = defaultTypography.headlineMedium.fontSize),
    headlineSmall = myTypography.copy(fontSize = defaultTypography.headlineSmall.fontSize),
    //  title
    titleLarge = myTypography.copy(fontSize = defaultTypography.titleLarge.fontSize),
    titleMedium = myTypography.copy(fontSize = defaultTypography.titleMedium.fontSize),
    titleSmall = myTypography.copy(fontSize = defaultTypography.titleSmall.fontSize),
    //  body
    bodyLarge = myTypography.copy(fontSize = defaultTypography.bodyLarge.fontSize),
    bodyMedium = myTypography.copy(fontSize = defaultTypography.bodyMedium.fontSize),
    bodySmall = myTypography.copy(fontSize = defaultTypography.bodySmall.fontSize),
    //  label
    labelLarge = myTypography.copy(fontSize = defaultTypography.labelLarge.fontSize),
    labelMedium = myTypography.copy(fontSize = defaultTypography.labelMedium.fontSize),
    labelSmall = myTypography.copy(fontSize = defaultTypography.labelSmall.fontSize),
)