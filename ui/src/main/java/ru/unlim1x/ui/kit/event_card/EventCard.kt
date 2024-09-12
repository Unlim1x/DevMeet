package ru.unlim1x.ui.kit.event_card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import ru.unlim1x.old_ui.theme.DevMeetTheme
import ru.unlim1x.old_ui.uiKit.cards.loading_cards.AnimatedTransitionRoundRectangle
import ru.unlim1x.ui.R
import ru.unlim1x.ui.kit.tag.TagSmall
import ru.unlim1x.ui.models.EventUI

private const val FIGMA_RADIUS = 16f
private const val FIGMA_DEFAULT_IMAGE_SIZE = 180
private const val FIGMA_SMALL_IMAGE_SIZE = 148
private const val FIGMA_IMAGE_PADDING = 2
private const val FIGMA_TEXT_PADDING = 6

internal enum class EventCardVariant {
    MAX_WIDTH,
    COMPACT
}

@Composable
internal fun EventCard(
    modifier: Modifier = Modifier,
    state: EventUI,
    variant: EventCardVariant = EventCardVariant.MAX_WIDTH,
    onHeightMeasured: (p: Dp) -> Unit = {},
    onClick: () -> Unit
) {
    when (variant) {
        EventCardVariant.MAX_WIDTH -> EventCardMaxBody(
            modifier.fillMaxWidth(),
            state,
            onHeightMeasured = onHeightMeasured
        )

        EventCardVariant.COMPACT -> EventCardCompactBody(
            modifier.width(212.dp),
            state,
            onHeightMeasured = onHeightMeasured
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun EventCardMaxBody(
    modifier: Modifier, state: EventUI,
    onHeightMeasured: (Dp) -> Unit
) {
    val imageWidth = remember {
        mutableIntStateOf(0)
    }
    val tagsToShow = 3

    val density = LocalDensity.current
    Column(modifier = modifier.onGloballyPositioned { coordinates ->
        val height = with(density) {
            coordinates.size.height.toDp()
        }
        onHeightMeasured(height)
    }) {
        SubcomposeAsyncImage(
            model = state.imageUri,
            contentDescription = stringResource(R.string.community_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(FIGMA_DEFAULT_IMAGE_SIZE.dp)
                .padding(bottom = FIGMA_IMAGE_PADDING.dp)
                .clip(RoundedCornerShape(FIGMA_RADIUS.dp))
                .onGloballyPositioned {
                    imageWidth.intValue = it.size.width
                },
            loading = {
                AnimatedTransitionRoundRectangle(
                    modifier = Modifier.size(FIGMA_DEFAULT_IMAGE_SIZE.dp)
                )
            },
            error = {
                AnimatedTransitionRoundRectangle(
                    modifier = Modifier.size(FIGMA_DEFAULT_IMAGE_SIZE.dp)
                )
            }
        )
        Text(
            text = state.name,
            style = DevMeetTheme.newTypography.h1,
            color = Color.Black,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            modifier = Modifier.padding(bottom = FIGMA_TEXT_PADDING.dp)

        )
        Text(
            text = state.date + stringResource(R.string.event_address_delimeter) + state.address,
            style = DevMeetTheme.newTypography.secondary,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.padding(bottom = FIGMA_TEXT_PADDING.dp)
        )
        FlowRow() {
            state.tags.take(tagsToShow).forEach {
                TagSmall(Modifier.padding(4.dp), text = it) {

                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun EventCardCompactBody(
    modifier: Modifier, state: EventUI,
    onHeightMeasured: (Dp) -> Unit
) {
    val imageWidth = remember {
        mutableIntStateOf(0)
    }
    val tagsToShow = 2

    val density = LocalDensity.current
    Column(modifier = modifier.onGloballyPositioned { coordinates ->
        val height = with(density) {
            coordinates.size.height.toDp()
        }
        onHeightMeasured(height)
    }) {
        SubcomposeAsyncImage(
            model = state.imageUri,
            contentDescription = stringResource(R.string.community_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(FIGMA_SMALL_IMAGE_SIZE.dp)
                .padding(bottom = FIGMA_IMAGE_PADDING.dp)
                .clip(RoundedCornerShape(FIGMA_RADIUS.dp))
                .onGloballyPositioned {
                    imageWidth.intValue = it.size.width
                },
            loading = {
                AnimatedTransitionRoundRectangle(
                    modifier = Modifier.size(FIGMA_SMALL_IMAGE_SIZE.dp)
                )
            },
            error = {
                AnimatedTransitionRoundRectangle(
                    modifier = Modifier.size(FIGMA_SMALL_IMAGE_SIZE.dp)
                )
            }
        )
        Text(
            text = state.name,
            style = DevMeetTheme.newTypography.h3,
            color = Color.Black,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.padding(bottom = FIGMA_TEXT_PADDING.dp)

        )
        Text(
            text = state.date + stringResource(R.string.event_address_delimeter) + state.address,
            style = DevMeetTheme.newTypography.secondary,
            //overflow = TextOverflow.Ellipsis,
            //maxLines = 1,
            modifier = Modifier.padding(bottom = FIGMA_TEXT_PADDING.dp)
        )
        FlowRow() {
            state.tags.take(tagsToShow).forEach {
                TagSmall(Modifier.padding(4.dp), text = it) {

                }
            }
        }
    }
}

@Composable
@Preview
private fun Show() {
    var community1 by remember {
        mutableStateOf(
            EventUI(
                imageUri = R.drawable.community_card_placeholder,
                name = "Python days", id = 1, address = "Кожевенная линия, 40",
                date = "10 августа", tags = listOf("Тестирование", "Тестирование", "Тестирование")
            )
        )
    }
    EventCard(state = community1, variant = EventCardVariant.MAX_WIDTH, onHeightMeasured = {}) {}
}