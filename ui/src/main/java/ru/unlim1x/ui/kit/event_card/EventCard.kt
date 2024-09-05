package ru.unlim1x.ui.kit.event_card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import ru.unlim1x.old_ui.theme.DevMeetTheme
import ru.unlim1x.old_ui.uiKit.cards.loading_cards.AnimatedTransitionRoundRectangle
import ru.unlim1x.ui.R
import ru.unlim1x.ui.kit.community.CommunityCardState
import ru.unlim1x.ui.kit.tag.TagSmall

private const val FIGMA_RADIUS = 16f
private const val FIGMA_DEFAULT_SIZE = 180

internal enum class EventCardVariant {
    MAX_WIDTH,
    COMPACT
}

@Composable
internal fun EventCard(
    modifier: Modifier = Modifier,
    state: CommunityCardState,
    variant: EventCardVariant = EventCardVariant.MAX_WIDTH
) {
    when (variant) {
        EventCardVariant.MAX_WIDTH -> EventCardMaxBody(modifier.fillMaxWidth(), state)
        EventCardVariant.COMPACT -> EventCardCompactBody(modifier.width(212.dp), state)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun EventCardMaxBody(modifier: Modifier, state: CommunityCardState) {
    val imageWidth = remember {
        mutableIntStateOf(0)
    }



    Column(modifier = modifier) {
        SubcomposeAsyncImage(
            model = state.imageUri,
            contentDescription = stringResource(R.string.community_image),
            modifier = modifier
                .padding(bottom = 2.dp)
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
            style = DevMeetTheme.newTypography.h1,
            color = Color.Black,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.padding(bottom = 6.dp)

        )
        Text(
            text = state.name,
            style = DevMeetTheme.newTypography.secondary,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        FlowRow() {
            TagSmall(Modifier.padding(4.dp), text = state.name) {

            }
            TagSmall(Modifier.padding(4.dp), text = state.name) {

            }
            TagSmall(Modifier.padding(4.dp), text = state.name) {

            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun EventCardCompactBody(modifier: Modifier, state: CommunityCardState) {
    val imageWidth = remember {
        mutableIntStateOf(0)
    }



    Column(modifier = modifier) {
        SubcomposeAsyncImage(
            model = state.imageUri,
            contentDescription = stringResource(R.string.community_image),
            modifier = modifier
                .padding(bottom = 2.dp)
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
            style = DevMeetTheme.newTypography.h3,
            color = Color.Black,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.padding(bottom = 6.dp)

        )
        Text(
            text = state.name,
            style = DevMeetTheme.newTypography.secondary,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        FlowRow() {
            TagSmall(Modifier.padding(4.dp), text = state.name) {

            }
            TagSmall(Modifier.padding(4.dp), text = state.name) {

            }
        }
    }
}

@Composable
@Preview
private fun Show() {
    var community1 by remember {
        mutableStateOf(
            CommunityCardState(
                imageUri = R.drawable.community_card_placeholder,
                name = "Супер тестировщики", id = 1, isSubscribed = false
            )
        )
    }
    EventCard(state = community1, variant = EventCardVariant.COMPACT)
}