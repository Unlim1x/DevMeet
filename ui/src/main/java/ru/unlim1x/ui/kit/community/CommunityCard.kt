package ru.unlim1x.ui.kit.community

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import ru.unlim1x.old_ui.theme.DevMeetTheme
import ru.unlim1x.old_ui.uiKit.cards.loading_cards.AnimatedTransitionRoundRectangle
import ru.unlim1x.ui.R

private const val FIGMA_RADIUS = 16f
private const val FIGMA_DEFAULT_SIZE = 104

@Composable
internal fun CommunityCard(
    modifier: Modifier = Modifier,
    state: CommunityUI,
    onSubscribeClick: () -> Unit
) {
    val imageWidth = remember {
        mutableIntStateOf(0)
    }
    val density = LocalDensity.current
    val imageWidthDp = with(density) { imageWidth.intValue.toDp() }
    val textAndButtonModifier = modifier
        .width(imageWidthDp)
        .padding(top = 8.dp)
    Column(modifier = modifier) {
        SubcomposeAsyncImage(
            model = state.imageUri,
            contentDescription = stringResource(R.string.community_image),
            modifier = modifier
                .onGloballyPositioned {
                    imageWidth.intValue = it.size.width
                },
            loading = {
                AnimatedTransitionRoundRectangle(
                    modifier = modifier.size(FIGMA_DEFAULT_SIZE.dp),
                    cornerRadius = CornerRadius(x = FIGMA_RADIUS, y = FIGMA_RADIUS)
                )
            }
        )
        Text(
            text = state.name,
            style = DevMeetTheme.newTypography.h4,
            color = Color.Black,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = textAndButtonModifier
        )
        SubscribeButton(modifier = textAndButtonModifier, isSubscribed = state.isSubscribed) {
            onSubscribeClick()
        }
    }
}

@Composable
@Preview
private fun ShowCommunityCard() {
    var community1 by remember {
        mutableStateOf(
            CommunityUI(
                imageUri = R.drawable.community_card_placeholder,
                name = "Супер тестировщики", id = 1, isSubscribed = false
            )
        )
    }
    val community2 = CommunityUI(
        imageUri = R.drawable.community_card_placeholder,
        name = "Супер тестировщики",
        id = 1,
        isSubscribed = true
    )

    CommunityCard(state = community1) {
        community1 = community1.copy(isSubscribed = !community1.isSubscribed)
    }

}