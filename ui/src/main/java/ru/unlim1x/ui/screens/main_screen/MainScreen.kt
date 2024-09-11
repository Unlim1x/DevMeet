package ru.unlim1x.ui.screens.main_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.androidx.compose.koinViewModel
import ru.unlim1x.old_ui.theme.DevMeetTheme
import ru.unlim1x.ui.kit.banner.Banner
import ru.unlim1x.ui.kit.community.CommunityCard
import ru.unlim1x.ui.kit.event_card.EventCard
import ru.unlim1x.ui.kit.event_card.EventCardVariant
import ru.unlim1x.ui.models.EventUI
import ru.unlim1x.ui.kit.person.Person
import ru.unlim1x.ui.models.PersonUi
import ru.unlim1x.ui.kit.tag.TagMedium
import ru.unlim1x.ui.models.CommunityRailUI

private const val HORIZONTAL_PADDING = 16
private const val ROW_CARD_FRACTION = 0.8f
private const val HEADER_SPACE = 16

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun MainScreen(viewModel: MainScreenViewModel = koinViewModel()) {
    val viewState by viewModel.viewState().collectAsStateWithLifecycle()
    when (viewState) {
        is MainScreenViewState.Display -> {
            MainScreenBody(state = viewState as MainScreenViewState.Display) {
                viewModel.obtain(MainScreenEvent.ScrolledToEndOfList)
            }
        }

        is MainScreenViewState.DisplaySearch -> {}
        MainScreenViewState.Error -> {}
        MainScreenViewState.Loading -> {}
    }
}

@OptIn(FlowPreview::class)
@Composable
private fun MainScreenBody(state: MainScreenViewState.Display, onEndOfListReached: () -> Unit) {

    val lazyListState = rememberSaveable(saver = LazyListState.Saver) { LazyListState() }
    val threshold = 2
    val itemsBeforeInfiniteList = 4
    var railIndex = 0

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            //.padding(horizontal = HORIZONTAL_PADDING.dp)
            .padding(bottom = 24.dp)
    ) {
        var topBarPadding by remember { mutableStateOf(0) }
        val density = LocalDensity.current.density
        Text(text = "Здесь будет поисковая строка", modifier = Modifier
            .align(Alignment.TopCenter)
            .onGloballyPositioned {
                topBarPadding =
                    (it.size.height / density).toInt()
            })
        LazyColumn(
            state = lazyListState,
            modifier = Modifier.padding(top = topBarPadding.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp)
        ) {
            //Главные
            item {
                MainEvents(listMainEvents = state.mainEventsList)
            }
            //СКОРО
            item {
                SoonEvents(listSoonEvents = state.soonEventsList)
            }
            //ПЕРВЫЙ РЭИЛ
            item {
                Rail(rail = state.railList[railIndex])
            }


            //КОНЕЦ ГЛАВНОГО БЛОКА

            //ТЭГОВАЯ ИСТОРИЯ
            item {
                TagPart()
            }

            //Здесь идет условно бесконечный список с вставками каждые три ивента

            itemsIndexed(items = state.infiniteEventsListByTag) { index, item ->
                EventCard(
                    state = item,
                    modifier = Modifier.padding(horizontal = HORIZONTAL_PADDING.dp)
                ) {

                }
                if (index == 2) {
                    Banner(Modifier.padding(vertical = 40.dp, horizontal = HORIZONTAL_PADDING.dp)) {

                    }
                }
                if (index == 5) {
                    PersonsToKnow(Modifier.padding(vertical = 40.dp), personsList = emptyList())
                }
                if (index == 8) {
                    //RAIL!!!!

                    Rail(rail = state.railList[railIndex])

                }
            }


        }

    }


    LaunchedEffect(lazyListState, state) {
        snapshotFlow { lazyListState.firstVisibleItemIndex + lazyListState.layoutInfo.visibleItemsInfo.size }
            //.debounce(300)
            .distinctUntilChanged()
            .collect { visibleItems ->
                state.infiniteEventsListByTag.let {
                    if (visibleItems >= it.size + itemsBeforeInfiniteList) {
                        Log.e("", "VISIBLE ITEMS = ${visibleItems}")
                        Log.e(
                            "",
                            "it.size+itemsBeforeInfiniteList = ${it.size + itemsBeforeInfiniteList}"
                        )
                        onEndOfListReached()
                    }
                }
            }
    }
}

@Composable
internal fun PersonsToKnow(modifier: Modifier = Modifier, personsList: List<PersonUi>) {
    Column(verticalArrangement = Arrangement.spacedBy(HEADER_SPACE.dp), modifier = modifier) {
        Text(
            text = "Вы можете их знать", style = DevMeetTheme.newTypography.h2,
            modifier = Modifier.padding(horizontal = HORIZONTAL_PADDING.dp)
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(HORIZONTAL_PADDING.dp)
        ) {
            itemsIndexed(personsList) { index, item ->
                Person(
                    modifier = modifier.railModifier(index),
                    personUi = item
                ) {

                }
            }
        }
    }


}

@Composable
fun MainEvents(modifier: Modifier = Modifier, listMainEvents: List<EventUI>) {
    var maxCardHeight by remember { mutableStateOf(0.dp) }
    var firstRender by remember { mutableStateOf(true) }
    BoxWithConstraints(modifier.fillMaxWidth()) {
        val constraintsHeight = constraints.maxHeight.dp

        LazyRow(
            modifier = modifier
                .fillMaxWidth()
                .height(maxCardHeight.takeIf { !firstRender } ?: constraintsHeight),
            horizontalArrangement = Arrangement.spacedBy(HORIZONTAL_PADDING.dp)
        ) {
            itemsIndexed(listMainEvents) { index, item ->
                EventCard(
                    modifier = modifier
                        .fillParentMaxWidth(ROW_CARD_FRACTION)
                        .heightIn(min = 0.dp) // Ensure that height is not restricted
                        .fillMaxHeight()  // This ensures that cards take up the full available height
                        .railModifier(index),
                    state = item,
                    onHeightMeasured = { height ->
                        if (height > maxCardHeight) {
                            maxCardHeight = height
                        }
                        firstRender = false
                    }
                ) {

                }
            }


        }
    }

}

@Composable
fun SoonEvents(modifier: Modifier = Modifier, listSoonEvents: List<EventUI>) {
    var maxCardHeight by remember { mutableStateOf(0.dp) }
    var firstRender by remember { mutableStateOf(true) }

    // Use BoxWithConstraints to get max height of LazyRow
    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth()
    ) {
        val constraintsHeight = constraints.maxHeight.dp

        LazyRow(
            modifier = modifier
                .fillMaxWidth()
                .height(maxCardHeight.takeIf { !firstRender } ?: constraintsHeight),
            horizontalArrangement = Arrangement.spacedBy(HORIZONTAL_PADDING.dp)
        ) {
            itemsIndexed(listSoonEvents) { index, item ->
                EventCard(
                    modifier = Modifier
                        .padding(horizontal = HORIZONTAL_PADDING.dp)
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

@Composable
private fun Rail(modifier: Modifier = Modifier, rail: CommunityRailUI) {
    Column(verticalArrangement = Arrangement.spacedBy(HEADER_SPACE.dp), modifier = modifier) {
        Text(
            text = rail.title, style = DevMeetTheme.newTypography.h2,
            modifier = Modifier.padding(horizontal = HORIZONTAL_PADDING.dp)
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(HORIZONTAL_PADDING.dp)
        ) {
            itemsIndexed(rail.contentList) { index, item ->
                CommunityCard(
                    modifier = Modifier.railModifier(index),
                    state = item,
                    onSubscribeClick = { /*TODO*/ }) {

                }
            }
        }
    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TagPart(modifier: Modifier = Modifier) {
    val tagList = listOf(
        "Дизайн",
        "Разработка",
        "Mobile",
        "Frontend",
        "Backend",
        "DevOps",
        "AI",
        "Машинное Обучение",
        "Базы Данных",
        "Безопасность",
        "UI/UX",
        "Веб-Разработка",
        "Облачные Технологии",
        "Тестирование",
        "Разработка Игр",
        "Все категории"
    )
    Column(
        verticalArrangement = Arrangement.spacedBy(HEADER_SPACE.dp),
        modifier = Modifier.padding(horizontal = HORIZONTAL_PADDING.dp)
    ) {
        Text(text = "Другие встречи", style = DevMeetTheme.newTypography.h2)

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            tagList.forEach {
                TagMedium(
                    text = it,
                    modifier = Modifier.padding(8.dp),
                    selected = false
                ) {

                }
            }
        }
    }
}


private fun Modifier.railModifier(index: Int): Modifier {
    return if (index == 0) {
        this.then(Modifier.padding(start = HORIZONTAL_PADDING.dp))
    } else {
        this
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun Show() {
    MainScreen()
}