package ru.unlim1x.ui.kit.brush

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import ru.unlim1x.ui.kit.brush.colors.loadingLinearGradientColors
import ru.unlim1x.ui.kit.brush.colors.primaryLinearGradientColors
import ru.unlim1x.ui.kit.brush.colors.secondaryLinearGradientColors

@Composable
internal fun primaryLinearBrush(
    colors: List<Color> = primaryLinearGradientColors,
    start: Offset = Offset.Zero,
    end: Offset = Offset.Infinite,
    tileMode: TileMode = TileMode.Clamp
): Brush {
    return Brush.linearGradient(colors, start, end, tileMode)
}

@Composable
internal fun loadingLinearBrush(
    colors: List<Color> = loadingLinearGradientColors,
    start: Offset = Offset.Zero,
    end: Offset = Offset.Infinite,
    tileMode: TileMode = TileMode.Clamp
): Brush {
    return Brush.linearGradient(colors, start, end, tileMode)
}

@Composable
internal fun secondaryLinearBrush(
    colors: List<Color> = secondaryLinearGradientColors,
    start: Offset = Offset.Zero,
    end: Offset = Offset.Infinite,
    tileMode: TileMode = TileMode.Clamp
): Brush {
    return Brush.linearGradient(colors, start, end, tileMode)
}
