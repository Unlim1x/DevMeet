package ru.unlim1x.ui.kit.toggle

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import ru.unlim1x.old_ui.theme.DevMeetTheme

private const val FIGMA_MIN_SIZE = 90
private const val FIGMA_MIN_WIDTH = 200
private const val FIGMA_MIN_HEIGHT = 100

/**
Toggle - переключатель. Чтобы задать свои ширину и высоту используйте width и height.
Modifier так же может изменять размеры, но не позволит достичь приемлемого поведения
сдвигающегося кружка. Метод onToggle() сработает, если переключатель переведен в положение "ON".
Метод onToggleBack() сработает, если переключатель переведен в положение "OFF".
 */
@Composable
internal fun Toggle(
    modifier: Modifier = Modifier,
    width: Dp = FIGMA_MIN_WIDTH.dp,
    height: Dp = FIGMA_MIN_HEIGHT.dp,
    onToggle: () -> Unit,
    onToggleBack: () -> Unit
) {
    var isChecked by remember { mutableStateOf(false) }
    val modifier = modifier
        .width(width = width)
        .height(height = height)

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .width(width = width)
            .height(height = height)
    ) {

        Crossfade(
            targetState = isChecked,
            animationSpec = spring(dampingRatio = 3f, stiffness = 400f)
        ) { targetIsChecked ->
            if (targetIsChecked) {
                SliderEnabled(
                    modifier = modifier
                        .zIndex(0f)
                )
            } else {
                SliderDisabled(
                    modifier = modifier
                        .zIndex(0f)
                )
            }
        }

        SlidingKnob(modifier = modifier, isChecked = isChecked, sliderSize = width) {
            isChecked = !isChecked
            if (isChecked) {
                onToggle()
            } else {
                onToggleBack()
            }
        }

    }
}

@Composable
internal fun SlidingKnob(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    knobSize: Dp = FIGMA_MIN_SIZE.dp,
    sliderSize: Dp = FIGMA_MIN_WIDTH.dp,
    edgePadding: Dp = 8.dp,
    onClick: () -> Unit
) {
    val knobOffset by animateDpAsState(
        targetValue = if (isChecked) sliderSize - knobSize - edgePadding else edgePadding,
        animationSpec = tween(durationMillis = 300)
    )
    Box(
        modifier = Modifier
            .offset(x = knobOffset)
            .size(FIGMA_MIN_SIZE.dp)
            .zIndex(1f)
    ) {

        Box(modifier = modifier
            .zIndex(1f)
            .size(FIGMA_MIN_SIZE.dp)
            .clip(CircleShape)
            .clickable { onClick() }
            .indication(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    color = Color.Gray,
                    bounded = true,
                    radius = (FIGMA_MIN_SIZE).dp
                )
            )
        ) {

        }
        Knob(
            modifier = Modifier
                .matchParentSize()
        )
    }
}

@Composable
private fun Knob(modifier: Modifier = Modifier) {
    Canvas(
        modifier = modifier.defaultMinSize(
            minWidth = FIGMA_MIN_SIZE.dp,
            minHeight = FIGMA_MIN_SIZE.dp
        )
    ) {
        drawCircle(
            color = Color.Black,
            center = center.copy(x = center.x, y = center.y + 20f),
            alpha = 0.15f
        )
        drawCircle(color = Color.White)
    }
}


@Composable
private fun SliderEnabled(
    modifier: Modifier = Modifier,
    width: Dp = FIGMA_MIN_WIDTH.dp,
    height: Dp = FIGMA_MIN_HEIGHT.dp
) {
    Canvas(
        modifier = modifier
            .width(width)  // Гибкая ширина
            .height(height)  // Гибкая высота
    ) {
        drawRoundRect(
            color = DevMeetTheme.colorScheme.primary,  // Задаем цвет
            size = size,  // Размер фигуры
            cornerRadius = CornerRadius(size.height / 2, size.height / 2)  // Закругляем углы
        )
    }

}


@Composable
private fun SliderDisabled(
    modifier: Modifier = Modifier,
    width: Dp = FIGMA_MIN_WIDTH.dp,
    height: Dp = FIGMA_MIN_HEIGHT.dp
) {
    Canvas(
        modifier = modifier
            .width(width)  // Гибкая ширина
            .height(height)  // Гибкая высота

    ) {
        drawRoundRect(
            color = Color(0xFFEFEFEF),  // Задаем цвет
            size = size,  // Размер фигуры
            cornerRadius = CornerRadius(size.height / 2, size.height / 2)  // Закругляем углы
        )
    }
}

@Composable
@Preview
private fun Show() {
    var isChecked by remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.SpaceBetween) {
        SliderDisabled(modifier = Modifier.width(400.dp))
        Spacer(modifier = Modifier.size(4.dp))
        SliderEnabled(
            modifier = Modifier
                .width(400.dp)
                .height(20.dp)
        )
        SliderEnabled(modifier = Modifier)
        Toggle(width = 300.dp, height = 60.dp, onToggle = { /*TODO*/ }) {

        }
        SlidingKnob(isChecked = isChecked, sliderSize = 400.dp) {
            isChecked = !isChecked
        }
        Box(
            modifier = Modifier
                .width(350.dp)
                .height(60.dp)
        ) {
            SliderDisabled(width = 350.dp)
            SlidingKnob(isChecked = isChecked, sliderSize = 350.dp) {

            }
        }
        Toggle(onToggle = { /*TODO*/ }) {

        }
    }

}