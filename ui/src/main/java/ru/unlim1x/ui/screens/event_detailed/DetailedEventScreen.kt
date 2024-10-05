package ru.unlim1x.ui.screens.event_detailed

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import ru.lim1x.domain.models.Tag
import ru.unlim1x.old_ui.theme.DevMeetTheme
import ru.unlim1x.old_ui.uiKit.avatarline.AvatarLine
import ru.unlim1x.old_ui.uiKit.cards.loading_cards.AnimatedTransitionRoundRectangle
import ru.unlim1x.old_ui.uiKit.cards.loading_cards.animatedTransitionBrush
import ru.unlim1x.ui.R
import ru.unlim1x.ui.kit.avatars_row.AvatarRow
import ru.unlim1x.ui.kit.brush.secondaryLinearBrush
import ru.unlim1x.ui.kit.event_card.EventCard
import ru.unlim1x.ui.kit.event_card.EventCardVariant
import ru.unlim1x.ui.kit.tag.TagSmall
import ru.unlim1x.ui.models.CommunityUI
import ru.unlim1x.ui.models.EventUI
import ru.unlim1x.ui.models.PersonUi
import ru.unlim1x.ui.models.PresenterUi
import ru.unlim1x.ui.screens.main_screen.railModifier
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private const val FIGMA_PADDING = 16
private const val FIGMA_MAIN_IMAGE_HEIGHT = 240
private const val FIGMA_MAP_HEIGHT = 180
@Composable
internal fun DetailedEventScreen(viewModel: DetailedEventScreenViewModel) {
    val viewState by viewModel.viewState().collectAsStateWithLifecycle()
    when (viewState){
        DetailedEventScreenViewState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

        }
        is DetailedEventScreenViewState.Display -> {
            DetailedEventScreenDisplay(state = viewState as DetailedEventScreenViewState.Display)
        }
    }
}

@Composable
private fun DetailedEventScreenDisplay(state:DetailedEventScreenViewState.Display){
    val context = LocalContext.current
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item{
            MainImage(
                modifier = Modifier.padding(FIGMA_PADDING.dp),
                url = state.imageUri,
                contentDescription = stringResource(R.string.meeting_image)
            )
        }
        item{
            HeaderItem(
                modifier = Modifier.padding(horizontal = FIGMA_PADDING.dp),
                text = state.header,
                style = DevMeetTheme.newTypography.h1
            )
        }
        item{
            Text(text = state.date.format(DateTimeFormatter.ofPattern("dd MMMM"))+ stringResource(id = R.string.event_address_delimeter)+
                    state.address, style = DevMeetTheme.newTypography.secondary,
                modifier = Modifier
                    .padding(horizontal = FIGMA_PADDING.dp)
                    .padding(top = 4.dp))
        }
        item{
            TagFlowRow(modifier = Modifier.padding(vertical = FIGMA_PADDING.dp), tags = state.tags)
        }

        item{
            Text(text = state.description, style = DevMeetTheme.newTypography.secondary,
                modifier = Modifier
                    .padding(horizontal = FIGMA_PADDING.dp)
                    .padding(top = 4.dp))
        }

        item{
            HeaderItem(
                modifier = Modifier.padding(FIGMA_PADDING.dp),
                text = stringResource(id = R.string.presenter)
            )
        }
        item{
            Presenter(modifier = Modifier.padding(horizontal = FIGMA_PADDING.dp),state = state)
        }
        item{
            HeaderItem(
                modifier = Modifier
                    .padding(horizontal = FIGMA_PADDING.dp)
                    .padding(top = FIGMA_PADDING.dp), text = state.address
            )
        }
        item {
            MapPictureWithSubwayHeader(
                url = state.mapImageUri,
                nearestSubway = state.nearestSubwayStation
            ) {
                if (state.latitude != null && state.longitude != null) {
                    val uri =
                        Uri.parse("geo:${state.latitude},${state.longitude}?q=${state.latitude},${state.longitude}(label)")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    context.startActivity(intent)
                }
            }

        }

        item {
            val text = when (state.date.isBefore(LocalDate.now())) {
                true -> stringResource(R.string.were_at_meeting)
                false -> stringResource(R.string.coming_to_meeting)
            }
            HeaderItem(modifier = Modifier.padding(FIGMA_PADDING.dp), text = text)
        }
        item {
            AvatarRow(modifier = Modifier.padding(horizontal = FIGMA_PADDING.dp),
                listAvatars = state.comingPeople.map { it.imageUri as String })
        }
        item{
            //Организатор
            HeaderItem(
                modifier = Modifier.padding(FIGMA_PADDING.dp),
                text = stringResource(id = R.string.organizer)
            )
        }
        item {
            Organizer(modifier = Modifier.padding(horizontal = FIGMA_PADDING.dp),state = state)
        }
        item{
            //Другие встречи
            HeaderItem(
                modifier = Modifier.padding(FIGMA_PADDING.dp),
                text = stringResource(id = R.string.other_meetings_of_community)
            )
        }
        item{
            MoreEvents(
                modifier = Modifier.padding(bottom = FIGMA_PADDING.dp),
                listMoreEvents = state.otherEventsOfCommunity
            ) {

            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun TagFlowRow(modifier: Modifier = Modifier, tags: List<Tag>) {
    FlowRow(modifier) {
        tags.forEach {
            TagSmall(
                text = it.text, modifier = Modifier
                    .padding(vertical = 4.dp)
                    .padding(start = FIGMA_PADDING.dp)
            ) {

            }
        }
    }
}

@Composable
internal fun MainImage(
    modifier: Modifier = Modifier, url: Any,
    contentDescription: String
) {

    val imageModifier = Modifier
        .then(modifier)
        .fillMaxWidth()
        .height(FIGMA_MAIN_IMAGE_HEIGHT.dp)
        .clip(RoundedCornerShape(FIGMA_PADDING.dp))


    SubcomposeAsyncImage(
        model = url,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = imageModifier,
        loading = {
            AnimatedTransitionRoundRectangle(
                modifier = Modifier.size(FIGMA_MAIN_IMAGE_HEIGHT.dp)
            )
        },
        error = {
            AnimatedTransitionRoundRectangle(
                modifier = Modifier.size(FIGMA_MAIN_IMAGE_HEIGHT.dp)
            )
        }
    )
}

@Composable
internal fun MapPictureWithSubwayHeader(
    modifier: Modifier = Modifier.padding(horizontal = FIGMA_PADDING.dp),
    url: Any,
    nearestSubway: String,
    onClick: () -> Unit
) {
    Column(modifier = modifier) {
        val mapModifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth()
            .height(FIGMA_MAP_HEIGHT.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable { onClick() }

        Text(
            text = nearestSubway, style = DevMeetTheme.newTypography.medium, color = Color.Black,
            modifier = Modifier
                .padding(top = 4.dp)
        )
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = "Map picture",
            modifier = mapModifier,
            loading = {
                AnimatedTransitionRoundRectangle(
                    modifier = Modifier.size(FIGMA_MAP_HEIGHT.dp)
                )
            },
            error = {
                AnimatedTransitionRoundRectangle(
                    modifier = Modifier.size(FIGMA_MAP_HEIGHT.dp)
                )
            }

        )
    }
}


@Composable
internal fun HeaderItem(
    modifier: Modifier = Modifier,
    text: String, style: TextStyle = DevMeetTheme.newTypography.h2,
    color: Color = style.color
) {
    Text(
        text = text, style = style,
        modifier = modifier, color = color
    )
}

@Composable
private fun Organizer(modifier: Modifier = Modifier, state: DetailedEventScreenViewState.Display){
Row(modifier){
    Column(modifier = Modifier.weight(1f)){
        Text(text = state.organizer.name, style = DevMeetTheme.newTypography.h3, color = Color.Black,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(text = state.organizer.description, style = DevMeetTheme.newTypography.medium, color = Color.Black,
            modifier = Modifier
                .padding(top = 4.dp, end = 8.dp)
        )
    }

    Box(modifier = Modifier
        .size(104.dp)
        .clip(RoundedCornerShape(16.dp))
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(state.organizer.imageUri)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = "Map picture",
            modifier = Modifier
                .size(104.dp)
                .clip(RoundedCornerShape(16.dp)),
            loading = {
                AnimatedTransitionRoundRectangle(
                    modifier = Modifier.size(FIGMA_MAP_HEIGHT.dp)
                )
            },
            error = {
                AnimatedTransitionRoundRectangle(
                    modifier = Modifier.size(FIGMA_MAP_HEIGHT.dp)
                )
            }

        )
        if(state.isSubscribedToCommunity)
            Box(modifier = Modifier
                .align(Alignment.BottomStart)
                .size(53.dp)
                .padding(8.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(secondaryLinearBrush())
                ){
                Icon(imageVector = Icons.Filled.Check,
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(48.dp),
                    tint = DevMeetTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
private fun Presenter(modifier: Modifier = Modifier, state: DetailedEventScreenViewState.Display){
    Row(modifier){
        Column(modifier = Modifier.weight(1f)){
            Text(text = state.presenter.name, style = DevMeetTheme.newTypography.h3, color = Color.Black,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(text = state.presenter.description, style = DevMeetTheme.newTypography.medium, color = Color.Black,
                modifier = Modifier
                    .padding(top = 4.dp, end = 8.dp)
            )
        }
        Box(modifier = Modifier
            .size(104.dp)
            .clip(RoundedCornerShape(16.dp))
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(state.presenter.imageUri)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = "Map picture",
                modifier = Modifier
                    .size(104.dp)
                    .clip(RoundedCornerShape(16.dp)),
                loading = {
                    AnimatedTransitionRoundRectangle(
                        modifier = Modifier.size(FIGMA_MAP_HEIGHT.dp)
                    )
                },
                error = {
                    AnimatedTransitionRoundRectangle(
                        modifier = Modifier.size(FIGMA_MAP_HEIGHT.dp)
                    )
                }

            )
        }
    }
}

@Composable
internal fun MoreEvents(
    modifier: Modifier = Modifier, listMoreEvents: List<EventUI>,
    onEventClick: (EventUI) -> Unit
) {
    var maxCardHeight by remember { mutableStateOf(0.dp) }
    var firstRender by remember { mutableStateOf(true) }

    Column(verticalArrangement = Arrangement.spacedBy(FIGMA_PADDING.dp), modifier = modifier) {
        // Use BoxWithConstraints to get max height of LazyRow
        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth()
        ) {
            val constraintsHeight = constraints.maxHeight.dp

            LazyRow(
                modifier = modifier
                    .fillMaxWidth()
                    .height(maxCardHeight.takeIf { !firstRender } ?: constraintsHeight),
                horizontalArrangement = Arrangement.spacedBy(FIGMA_PADDING.dp)
            ) {
                itemsIndexed(listMoreEvents) { index, item ->
                    EventCard(
                        modifier = Modifier
                            .railModifier(index, listMoreEvents.size)
                            //.padding(horizontal = HORIZONTAL_PADDING.dp)
                            .heightIn(min = 0.dp) // Ensure that height is not restricted
                            .fillMaxHeight(),  // This ensures that cards take up the full available height
                        state = item,
                        variant = EventCardVariant.COMPACT,
                        onHeightMeasured = { height ->
                            if (height > maxCardHeight) {
                                maxCardHeight = height
                            }
                            firstRender = false
                        }
                    ) {
                        onEventClick(item)
                    }
                }
            }
        }
    }


}

@Preview
@Composable
private fun Show() {

//    val state = DetailedEventScreenViewState.Display(
//        imageUri = "",
//        header = "Как повышать грейд. Лекция Павла Хорикова",
//        description = "Узнайте как расти в профессии, получать навыки, знания и прочие важные вещи." +
//                "Все самое интересное здесь. \n \nУзнайте как расти в профессии, получать навыки, знания и прочие важные вещи. " +
//                "Все самое интересное здесь.",
//        date = LocalDate.now(),
//        address = "Кожевенная линия, 40",
//        tags = listOf(Tag(1,"Разработка"),Tag(2,"Дизайн")),
//        presenter = PresenterUi(1,"Павел Хориков", "","Ведущий по подбору персонала в одной из крупнейших IT компаний Европы"),
//        nearestSubwayStation = "Приморская",
//        comingPeople = listOf(PersonUi(2,"Иван","", ""),
//            PersonUi(3,"Саша","", ""),
//            PersonUi(4,"Мария","", ""),
//            PersonUi(5,"Таня","", "")),
//        organizer = CommunityUI("", "Соощебство", 1, "Площадка самых топовых разработчиков и фанатов IT",false),
//        isSubscribedToCommunity = true,
//        mapImageUri = "",
//        otherEventsOfCommunity = listOf(EventUI(1,"Пышки в программах", "", "Завтра", address = "Приморская 47", tags = listOf("Веб-разработка")),
//            EventUI(1,"Пышки в программах", "", "Завтра", address = "Приморская 47", tags = listOf("Веб-разработка","Дизайн")),
//            EventUI(1,"Пышки в программах", "", "Завтра", address = "Приморская 47", tags = listOf("Веб-разработка")))
//    )
//    DetailedEventScreen(state)
//    //Presenter(state=state)
////        HostCard(
////        imageUrl = "https://example.com/image.jpg",
////        hostName = "Павел Хориков",
////        hostDescription = "Ведущий специалист по подбору персонала в одной из крупнейших IT-компаний в ЕС."
////    )
}