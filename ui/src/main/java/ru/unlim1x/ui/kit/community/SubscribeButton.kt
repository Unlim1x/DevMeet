package ru.unlim1x.ui.kit.community

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.unlim1x.old_ui.theme.DevMeetTheme

private const val FIGMA_MIN_WIDTH = 38
private const val FIGMA_RADIUS = 12


@Composable
fun SubscribeButton(
    modifier: Modifier = Modifier,
    isSubscribed: Boolean = false,
    onClick: () -> Unit
) {
    val backgroundColor = when (isSubscribed) {
        true -> DevMeetTheme.colorScheme.primary
        false -> Color.White
    }
    val text = when (isSubscribed) {
        true -> "✓"
        false -> "+"
    }
    val textColor = when (isSubscribed) {
        true -> Color.White
        false -> DevMeetTheme.colorScheme.primary
    }
    val testState = Triple(first = backgroundColor, second = text, third = textColor)

    Crossfade(
        targetState = isSubscribed,
        animationSpec = spring(dampingRatio = 3f, stiffness = 300f)
    ) {
        when (it) {
            true -> SubscribeButtonBody(
                modifier = modifier,
                backgroundColor = testState.first,
                text = testState.second,
                textColor = testState.third
            ) {
                onClick()
            }

            false -> SubscribeButtonBody(
                modifier = modifier,
                backgroundColor = testState.first,
                text = testState.second,
                textColor = testState.third
            ) {
                onClick()
            }
        }
    }
}

@Composable
private fun SubscribeButtonBody(
    modifier: Modifier,
    backgroundColor: Color,
    text: String,
    textColor: Color,
    onClick: () -> Unit
) {
    Box(modifier = modifier
        .clip(RoundedCornerShape(FIGMA_RADIUS.dp))
        .defaultMinSize(minWidth = FIGMA_MIN_WIDTH.dp)
        .background(
            color = backgroundColor
        )
        .clickable { onClick() }
//        .pointerInput(Unit){
//            detectTapGestures(onTap = {
//                onClick()
//            })
//        }
        ,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text, style = DevMeetTheme.newTypography.big,
            color = textColor
        )
    }
}

@Composable
private fun SubscribeButtonBody(
    modifier: Modifier,
    backgroundBrush: Brush,
    text: String,
    textColor: Color,
    onClick: () -> Unit
) {
    Box(modifier = modifier
        .clip(RoundedCornerShape(FIGMA_RADIUS.dp))
        .defaultMinSize(minWidth = FIGMA_MIN_WIDTH.dp)

        .background(
            brush = backgroundBrush
        )
        .pointerInput(Unit) {
            detectTapGestures(onTap = {
                onClick()
            })
        },
        //.clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text, style = DevMeetTheme.newTypography.big,
            color = textColor
        )
    }
}

@Composable
@Preview
private fun ShowSubscribeButton() {
    var isSubscribed by remember {
        mutableStateOf(false)
    }

    Column {
        SubscribeButton(isSubscribed = isSubscribed, modifier = Modifier.width(100.dp)) {

            isSubscribed = !isSubscribed

        }
        SubscribeButton(isSubscribed = false, modifier = Modifier.width(30.dp)) {}
        SubscribeButton(isSubscribed = true, modifier = Modifier.width(50.dp)) {}
        SubscribeButton(isSubscribed = false, modifier = Modifier.width(100.dp)) {}
        SubscribeButton(isSubscribed = true) {}
        SubscribeButton(isSubscribed = false) {}
    }
}