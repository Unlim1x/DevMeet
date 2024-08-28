package ru.unlim1x.ui.uiKit.cards.loading_cards

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview

@Composable
internal fun animatedTransitionBrush(): Brush {
    val TRANSITION_LABEL = "ANIMATED_TRANSITION+${LocalView.current}"
    val gradientColors = listOf(
        Color.Gray.copy(alpha = 0.3f),
        Color.Gray.copy(alpha = 0.1f),
        Color.Gray.copy(alpha = 0.3f),)
    val transitionBounds = 1f
    val transition = rememberInfiniteTransition(label = TRANSITION_LABEL)

    val loadingContentAnimation by transition.animateFloat(
        initialValue = -transitionBounds,
        targetValue = transitionBounds,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, delayMillis = 500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = TRANSITION_LABEL
    )
    val width = LocalView.current.width.toFloat()
    val offset = width * loadingContentAnimation
    return Brush.horizontalGradient(
        startX = offset,
        endX = offset + width,
        colors = gradientColors,
        tileMode = TileMode.Clamp
    )
}

@Composable
@Preview
private fun ShowLoadingExperimentalMeetingCard() {
    LoadingMeetingCardExperimental()
}