package ru.unlim1x.ui.kit.brush

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import ru.unlim1x.ui.kit.brush.colors.loadingLinearGradientColors

@Composable
internal fun radialBrush(
    colors: List<Color> = loadingLinearGradientColors,
    center: Offset = Offset.Unspecified,
    radius: Float = Float.POSITIVE_INFINITY,
    tileMode: TileMode = TileMode.Clamp
): Brush {
    return Brush.radialGradient(colors, center, radius, tileMode)
}