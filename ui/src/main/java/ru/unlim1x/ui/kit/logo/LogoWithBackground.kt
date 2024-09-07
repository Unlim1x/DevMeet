package ru.unlim1x.ui.kit.logo


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import ru.unlim1x.ui.R
import ru.unlim1x.ui.kit.brush.radialBrush

private const val FIGMA_RADIUS = 25

@Composable
internal fun AppLogo(modifier: Modifier = Modifier) {
    val density = LocalDensity.current.density
    var imageSize by remember {
        mutableStateOf(IntSize(0, 0))
    }
    Column {
        Box(modifier = modifier
            .clip(RoundedCornerShape(FIGMA_RADIUS.dp))
            .background(radialBrush(center = Offset(x = 260f, y = -180f), radius = 780f))
            .padding(vertical = 16.dp, horizontal = 10.dp)
            .onGloballyPositioned { imageSize = it.size }


        )
        {
            Image(
                painter = painterResource(id = R.drawable.white_logo), contentDescription = "Logo",
            )
        }
        Box(modifier = modifier
            .fillMaxWidth()
            .height((imageSize.height / density / 4).dp)
            .graphicsLayer {
                renderEffect = BlurEffect(50f, 50f)
            }
            .drawBehind {
                val shadowColor = Color(red = 102, green = 0, blue = 255, alpha = 0x1A) // Цвет тени
                drawRoundRect(
                    color = shadowColor,
                    size = imageSize.toSize(), topLeft = Offset(x = 25f, y = 0f)
                )
            }) {

        }
    }

}

@Composable
@Preview
private fun Show() {
    AppLogo()
}