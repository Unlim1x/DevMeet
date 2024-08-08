package ru.unlim1x.wb_project.ui.uiKit.cards.loading_cards

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
internal fun LoadingCommunityCardExperimental(modifier: Modifier = Modifier){
    val canvasHeightMain = DevMeetTheme.typography.bodyText1.lineHeight.value.dp
    val canvasHeightMeta = DevMeetTheme.typography.metadata1.lineHeight.value.dp
    val canvasWidth = (LocalConfiguration.current.screenWidthDp/6).dp
    val canvasWidthMeta = (LocalConfiguration.current.screenWidthDp/3).dp
    val cornerRadius = CornerRadius(40f,40f)
    val cornerRadiusText = CornerRadius(10f,10f)
    val modifierImage = Modifier.size(50.dp)
    val modifierHeader = Modifier
        .height(canvasHeightMain)
        .width(canvasWidth)
    val modifierMeta = Modifier
        .height(canvasHeightMeta)
        .width(canvasWidthMeta)
    Column(modifier = modifier.padding( 4.dp)){
        Row(
            Modifier
                .fillMaxWidth()) {
            AnimatedTransitionRoundRectangle(
                modifier = modifierImage,
                cornerRadius = cornerRadius
            )
            Column(modifier = Modifier.padding(start = 12.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
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

            }
        }
        Spacer(Modifier.size(12.dp))
        HorizontalDivider()
    }

}






@Composable
@Preview
fun ShowLoadingExperimentalCommunityCard(){
    LoadingCommunityCardExperimental()
    //LoadingMeetingCardBody(Color.White)
}