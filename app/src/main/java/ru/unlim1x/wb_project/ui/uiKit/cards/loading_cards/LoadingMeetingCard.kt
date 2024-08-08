package ru.unlim1x.wb_project.ui.uiKit.cards.loading_cards

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.unlim1x.wb_project.ui.theme.DevMeetTheme


@Composable
internal fun LoadingMeetingCardExperimental(modifier: Modifier = Modifier) {
    val canvasHeightMain = DevMeetTheme.typography.bodyText1.lineHeight.value.dp
    val canvasHeightMeta = DevMeetTheme.typography.metadata1.lineHeight.value.dp
    val canvasHeightChip = 20.dp
    val canvasWidth = (LocalConfiguration.current.screenWidthDp / 2).dp
    val canvasWidthMeta = (LocalConfiguration.current.screenWidthDp / 3).dp
    val canvasWidthChip = (LocalConfiguration.current.screenWidthDp / 8).dp
    val cornerRadius = CornerRadius(40f, 40f)
    val cornerRadiusText = CornerRadius(10f, 10f)
    val modifierImage = Modifier.size(50.dp)
    val modifierHeader = Modifier
        .height(canvasHeightMain)
        .width(canvasWidth)
    val modifierMeta = Modifier
        .height(canvasHeightMeta)
        .width(canvasWidthMeta)
    val modifierChip = Modifier
        .width(canvasWidthChip)
        .height(canvasHeightChip)
    Column(modifier = modifier.padding(4.dp)) {
        Row(
            Modifier
                .fillMaxWidth()
        ) {
            AnimatedTransitionRoundRectangle(
                modifier = modifierImage,
                cornerRadius = cornerRadius
            )
            Column(modifier = Modifier.padding(start = 12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    AnimatedTransitionRoundRectangle(
                        modifier = modifierHeader,
                        cornerRadius = cornerRadiusText
                    )

                }
                Spacer(modifier = Modifier.height(2.dp))
                AnimatedTransitionRoundRectangle(
                    modifier = modifierMeta,
                    cornerRadius = cornerRadiusText
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row {
                    repeat(3) {
                        AnimatedTransitionRoundRectangle(
                            modifier = modifierChip.padding(end = 4.dp),
                            cornerRadius = cornerRadius
                        )
                    }

                }
            }
        }
        Spacer(Modifier.size(20.dp))
        HorizontalDivider()
    }

}


@Composable
internal fun AnimatedTransitionRoundRectangle(
    modifier: Modifier = Modifier,
    cornerRadius: CornerRadius = CornerRadius.Zero
) {
    val brush =  animatedTransitionBrush()
    Canvas(modifier = modifier) {
        drawRoundRect(brush = brush, cornerRadius = cornerRadius)
    }
}


