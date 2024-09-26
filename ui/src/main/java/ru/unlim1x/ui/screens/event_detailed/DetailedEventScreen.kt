package ru.unlim1x.ui.screens.event_detailed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.lim1x.domain.models.Tag
import ru.unlim1x.old_ui.theme.DevMeetTheme
import ru.unlim1x.old_ui.uiKit.avatarline.AvatarLine
import ru.unlim1x.old_ui.uiKit.cards.loading_cards.animatedTransitionBrush
import ru.unlim1x.ui.R
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
@Composable
internal fun DetailedEventScreen(viewState:DetailedEventScreenViewState) {
    when (viewState){
        DetailedEventScreenViewState.Loading -> {}
        is DetailedEventScreenViewState.Display -> {
            DetailedEventScreenDisplay(state = viewState)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DetailedEventScreenDisplay(state:DetailedEventScreenViewState.Display){
    LazyColumn(modifier = Modifier.fillMaxSize(), ) {
        item{
            //Image
            Box(modifier = Modifier
                .padding(FIGMA_PADDING.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(FIGMA_PADDING.dp))
                .height(240.dp)
                .background(
                    animatedTransitionBrush()
                ))
        }
        item{
            Text(text = state.header, style = DevMeetTheme.newTypography.h1,
                modifier = Modifier.padding(horizontal = FIGMA_PADDING.dp))
        }
        item{
            Text(text = state.date.format(DateTimeFormatter.ofPattern("dd MMMM"))+ stringResource(id = R.string.event_address_delimeter)+
                    state.address, style = DevMeetTheme.newTypography.secondary,
                modifier = Modifier
                    .padding(horizontal = FIGMA_PADDING.dp)
                    .padding(top = 4.dp))
        }
        item{
            FlowRow {
                state.tags.forEach {
                    TagSmall(text = it.text, modifier = Modifier
                        .padding(vertical = FIGMA_PADDING.dp)
                        .padding(start = FIGMA_PADDING.dp)) {

                    }
                }
            }
        }

        item{
            Text(text = state.description, style = DevMeetTheme.newTypography.secondary,
                modifier = Modifier
                    .padding(horizontal = FIGMA_PADDING.dp)
                    .padding(top = 4.dp))
        }

        item{
            //Ведущий заголовок
            Text(text = "Ведущий", style = DevMeetTheme.newTypography.h2,
                modifier = Modifier.padding(FIGMA_PADDING.dp))
        }
        item{
            //Ведущий todo:сделать отдельную карточку как баннер
            Row(){
                Column(modifier = Modifier.weight(1f)){
                    Text(text = state.presenter.name, style = DevMeetTheme.newTypography.medium, color = Color.Black,
                        modifier = Modifier
                            .padding(horizontal = FIGMA_PADDING.dp)
                            .padding(top = 4.dp))
                    Text(text = state.presenter.description, style = DevMeetTheme.newTypography.medium, color = Color.Black,
                        modifier = Modifier
                            .padding(horizontal = FIGMA_PADDING.dp)
                            .padding(top = 4.dp))
                }
                Box(modifier = Modifier
                    .weight(1f)
                    .size(50.dp)
                    .background(animatedTransitionBrush())){

                }
            }
        }
        item{
            //todo: сделать компонент заголвок + карта
            Column {

                Text(text = state.address, style = DevMeetTheme.newTypography.h2,
                    modifier = Modifier
                        .padding(horizontal = FIGMA_PADDING.dp)
                        .padding(top = FIGMA_PADDING.dp))
                Text(text = state.nearestSubwayStation, style = DevMeetTheme.newTypography.medium, color = Color.Black,
                    modifier = Modifier
                        .padding(horizontal = FIGMA_PADDING.dp)
                        .padding(top = 4.dp))
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(R.drawable.map_sample_2)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.map_sample_2),
                    contentDescription = "Map picture will be replaced in future",
                    modifier = Modifier
                        .padding(horizontal = FIGMA_PADDING.dp)
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(20.dp))

                )
            }
        }
        item {
            // todo: Пойдут или были на встрече
            Column{
                Text(text = "Пойдут на встречу", style = DevMeetTheme.newTypography.h2,
                    modifier = Modifier.padding(FIGMA_PADDING.dp))
                Box(modifier = Modifier.padding(horizontal = FIGMA_PADDING.dp)){
                    AvatarLine(listAvatars = listOf("","","","","","","","","",""))
                }
            }
        }
        item{
            //Организатор
            Text(text = "Организатор", style = DevMeetTheme.newTypography.h2,
                modifier = Modifier.padding(FIGMA_PADDING.dp))
        }
        item {
            // todo: Организатор
            //todo:сделать отдельную карточку как баннер
            Row(){
                Column(modifier = Modifier.weight(1f)){
                    Text(text = state.organizer.name, style = DevMeetTheme.newTypography.medium, color = Color.Black,
                        modifier = Modifier
                            .padding(horizontal = FIGMA_PADDING.dp)
                            .padding(top = 4.dp))
                    Text(text = state.organizer.description, style = DevMeetTheme.newTypography.medium, color = Color.Black,
                        modifier = Modifier
                            .padding(horizontal = FIGMA_PADDING.dp)
                            .padding(top = 4.dp))
                }
                Box(modifier = Modifier
                    .weight(1f)
                    .size(50.dp)
                    .background(animatedTransitionBrush())){

                }
            }
        }
        item{
            //Другие встречи
            Text(text = "Другие встречи сообщества", style = DevMeetTheme.newTypography.h2,
                modifier = Modifier.padding(FIGMA_PADDING.dp))
        }
        item{
            MoreEvents(listMoreEvents = state.otherEventsOfCommunity)
        }
    }
}

@Composable
private fun MoreEvents(modifier: Modifier = Modifier, listMoreEvents: List<EventUI>) {
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
                            .railModifier(index)
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
                        // Handle click event
                    }
                }
            }
        }
    }


}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DESDCut(state:DetailedEventScreenViewState.Display){
    LazyColumn(modifier = Modifier.fillMaxSize(), ) {
        item{
            //Image
            Box(modifier = Modifier
                .padding(FIGMA_PADDING.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(FIGMA_PADDING.dp))
                .height(240.dp)
                .background(
                    animatedTransitionBrush()
                ))
        }
        item{
            Text(text = state.header, style = DevMeetTheme.newTypography.h1,
                modifier = Modifier.padding(horizontal = FIGMA_PADDING.dp))
        }
        item{
            Text(text = state.date.format(DateTimeFormatter.ofPattern("dd MMMM"))+ stringResource(id = R.string.event_address_delimeter)+
                    state.address, style = DevMeetTheme.newTypography.secondary,
                modifier = Modifier
                    .padding(horizontal = FIGMA_PADDING.dp)
                    .padding(top = 4.dp))
        }
        item{
            FlowRow {
                state.tags.forEach {
                    TagSmall(text = it.text, modifier = Modifier
                        .padding(vertical = FIGMA_PADDING.dp)
                        .padding(start = FIGMA_PADDING.dp)) {

                    }
                }
            }
        }

        item{
            Text(text = state.description, style = DevMeetTheme.newTypography.secondary,
                modifier = Modifier
                    .padding(horizontal = FIGMA_PADDING.dp)
                    .padding(top = 4.dp))
        }

        item{
            //Ведущий заголовок
            Text(text = "Ведущий", style = DevMeetTheme.newTypography.h2,
                modifier = Modifier.padding(FIGMA_PADDING.dp))
        }
        item{
            //Ведущий todo:сделать отдельную карточку как баннер
            Row(){
                Column(modifier = Modifier.weight(1f)){
                    Text(text = state.presenter.name, style = DevMeetTheme.newTypography.medium, color = Color.Black,
                        modifier = Modifier
                            .padding(horizontal = FIGMA_PADDING.dp)
                            .padding(top = 4.dp))
                    Text(text = state.presenter.description, style = DevMeetTheme.newTypography.medium, color = Color.Black,
                        modifier = Modifier
                            .padding(horizontal = FIGMA_PADDING.dp)
                            .padding(top = 4.dp))
                }
                Box(modifier = Modifier
                    .weight(1f)
                    .size(50.dp)
                    .background(animatedTransitionBrush())){

                }
            }
        }
        item{
            //todo: сделать компонент заголвок + карта
            Column {

                Text(text = state.address, style = DevMeetTheme.newTypography.h2,
                    modifier = Modifier
                        .padding(horizontal = FIGMA_PADDING.dp)
                        .padding(top = FIGMA_PADDING.dp))
                Text(text = state.nearestSubwayStation, style = DevMeetTheme.newTypography.medium, color = Color.Black,
                    modifier = Modifier
                        .padding(horizontal = FIGMA_PADDING.dp)
                        .padding(top = 4.dp))
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(R.drawable.map_sample_2)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.map_sample_2),
                    contentDescription = "Map picture will be replaced in future",
                    modifier = Modifier
                        .padding(horizontal = FIGMA_PADDING.dp)
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(20.dp))

                )
            }
        }

    }
}

@Preview
@Composable
private fun Show() {

    val state = DetailedEventScreenViewState.Display(
        imageUri = "",
        header = "Как повышать грейд. Лекция Павла Хорикова",
        description = "Узнайте как расти в профессии, получать навыки, знания и прочие важные вещи." +
                "Все самое интересное здесь. \n \nУзнайте как расти в профессии, получать навыки, знания и прочие важные вещи. " +
                "Все самое интересное здесь.",
        date = LocalDate.now(),
        address = "Кожевенная линия, 40",
        tags = listOf(Tag(1,"Разработка"),Tag(2,"Дизайн")),
        presenter = PresenterUi(1,"Павел Хориков", "","Ведущий по подбору персонала в одной из крупнейших IT компаний Европы"),
        nearestSubwayStation = "Приморская",
        comingPeople = listOf(PersonUi(2,"Иван","", ""),
            PersonUi(3,"Саша","", ""),
            PersonUi(4,"Мария","", ""),
            PersonUi(5,"Таня","", "")),
        organizer = CommunityUI("", "Соощебство", 1, "Площадка самых топовых разработчиков и фанатов IT",false),
        otherEventsOfCommunity = listOf(EventUI(1,"Пышки в программах", "", "Завтра", address = "Приморская 47", tags = listOf("Веб-разработка")),
            EventUI(1,"Пышки в программах", "", "Завтра", address = "Приморская 47", tags = listOf("Веб-разработка","Дизайн")),
            EventUI(1,"Пышки в программах", "", "Завтра", address = "Приморская 47", tags = listOf("Веб-разработка")))
    )
    DetailedEventScreen(state)
}