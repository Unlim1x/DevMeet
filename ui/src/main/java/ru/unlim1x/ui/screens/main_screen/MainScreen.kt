package ru.unlim1x.ui.screens.main_screen

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import org.koin.androidx.compose.koinViewModel
import ru.lim1x.domain.models.Rail
import ru.lim1x.domain.models.RailType
import ru.unlim1x.old_ui.theme.DevMeetTheme
import ru.unlim1x.ui.R
import ru.unlim1x.ui.kit.banner.Banner
import ru.unlim1x.ui.kit.community.CommunityCard
import ru.unlim1x.ui.kit.event_card.EventCard
import ru.unlim1x.ui.kit.event_card.EventCardVariant
import ru.unlim1x.ui.kit.person.Person
import ru.unlim1x.ui.kit.tag.TagMedium
import ru.unlim1x.ui.kit.tag.TagUi
import ru.unlim1x.ui.kit.topbar_row.TopBarSearchRow
import ru.unlim1x.ui.models.CommunityRailUI
import ru.unlim1x.ui.models.EventUI
import ru.unlim1x.ui.models.PersonRailUi
import ru.unlim1x.ui.screens.event_detailed.MoreEvents

private const val HORIZONTAL_PADDING = 16
private const val ROW_CARD_FRACTION = 0.8f
private const val HEADER_SPACE = 16
private const val VERTICAL_GAP = 40

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun MainScreen(viewModel: MainScreenViewModel = koinViewModel()) {
    val viewState by viewModel.viewState().collectAsStateWithLifecycle()
    var topBarPadding by remember { mutableIntStateOf(0) }
    val density = LocalDensity.current.density

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        TopBarSearchRow(modifier = Modifier
            .padding(
                top = 10.dp,
                start = HORIZONTAL_PADDING.dp,
                end = HORIZONTAL_PADDING.dp
            )
            .onGloballyPositioned {
                topBarPadding = (it.size.height / density).toInt()
            },
            onSearch = { viewModel.obtain(MainScreenEvent.OnSearchClicked(it)) },
            onValueChanged = {
                viewModel.obtain(MainScreenEvent.SearchValueChanged(it))
            },
            onMenuItemClick = { }) {
            viewModel.obtain(MainScreenEvent.CanceledSearch)
        }
        when (viewState) {
            is MainScreenViewState.Display -> {
                MainScreenBody(
                    Modifier.padding(top = topBarPadding.dp + 15.dp),
                    state = viewState as MainScreenViewState.Display,
                    onEndOfListReached = { viewModel.obtain(MainScreenEvent.ScrolledToEndOfList) },
                    onTagClicked = { viewModel.obtain(MainScreenEvent.ClickOnTag(it)) }
                ) {
                    viewModel.obtain(
                        (MainScreenEvent.ClickOnEvent(
                            eventId = it.id,
                            eventAddress = it.address
                        ))
                    )
                }

            }

            is MainScreenViewState.DisplaySearch -> {
                MainScreenSearchBody(
                    Modifier.padding(top = topBarPadding.dp + 15.dp),
                    state = viewState as MainScreenViewState.DisplaySearch
                ) {
                    viewModel.obtain(MainScreenEvent.ScrolledToEndOfList)
                }
            }

            MainScreenViewState.Error -> {}
            MainScreenViewState.Loading -> {}
        }

    }

}

@OptIn(FlowPreview::class, ExperimentalFoundationApi::class)
@Composable
private fun MainScreenBody(
    modifier: Modifier,
    state: MainScreenViewState.Display,
    onEndOfListReached: () -> Unit,
    onTagClicked: (id: Int) -> Unit,
    onEventClicked: (event: EventUI) -> Unit
) {
    Log.e("MAIN SCREEN", "$state")
    Log.e("MAIN SCREEN", "INFINITE LIST ${state.infiniteEventsListByTag}")
    Log.e("MAIN SCREEN", "RAILLIST LIST ${state.railList}")
    Log.e("MAIN SCREEN", "TAG LIST ${state.otherTags}")
    val lazyListState = rememberSaveable(saver = LazyListState.Saver) { LazyListState() }
    val threshold = 5
    val itemsBeforeInfiniteList = 4

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {



        LazyColumn(
            state = lazyListState,
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(40.dp),
        ) {
            //Главные
            item {
                MainEvents(
                    modifier = Modifier.padding(top = 20.dp),
                    listMainEvents = state.mainEventsList
                ) {
                    onEventClicked(it)
                }
            }
            //СКОРО
            item {
                SoonEvents(listSoonEvents = state.soonEventsList)
            }
            //ПЕРВЫЙ РЭИЛ
            item {
                if (state.railList.isNotEmpty()) {
                    //if (state.railList[0].railType == RailType.Community)
                    Rail(rail = state.railList[0])
                }
            }


            //КОНЕЦ ГЛАВНОГО БЛОКА

            //ТЭГОВАЯ ИСТОРИЯ
            item {
                TagPart(listTagsUi = state.otherTags) {
                    onTagClicked(it)
                }
            }

            //Здесь идет условно бесконечный список с вставками каждые три ивента

            itemsIndexed(items = state.infiniteEventsListByTag) { index, item ->

                EventCard(
                    state = item,
                    modifier = Modifier.padding(horizontal = HORIZONTAL_PADDING.dp)
                ) {

                }
                if ((index + 1) % 3 == 0 && index < 9) {
                    if (state.railList.size > (index + 1) / 3)
                        Rail(
                            modifier = Modifier.padding(top = VERTICAL_GAP.dp),
                            rail = state.railList[(index + 1) / 3]
                        )
                }

            }


        }

    }


    LaunchedEffect(state.infiniteEventsListByTag.size) {

        snapshotFlow { lazyListState.firstVisibleItemIndex + lazyListState.layoutInfo.visibleItemsInfo.size }
            .map { visibleItems ->
                if (visibleItems >= state.infiniteEventsListByTag.size + itemsBeforeInfiniteList - threshold)
                    true
                else
                    null

            }
            .distinctUntilChanged()
            .filterNotNull()
            .collect { _ ->
                onEndOfListReached()
            }
    }
}

@Composable
internal fun MainScreenSearchBody(
    modifier: Modifier = Modifier, state: MainScreenViewState.DisplaySearch,
    onEndOfListReached: () -> Unit,
) {
    val lazyListState = rememberSaveable(saver = LazyListState.Saver) { LazyListState() }
    val threshold = 1
    val itemsBeforeInfiniteList = 2

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {


        if (state.searchedEventsList.isNotEmpty())
            LazyColumn(
                state = lazyListState,
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(40.dp),
            ) {
                //Первые 3
                items(state.searchedEventsList.take(3)) {
                    EventCard(
                        state = it,
                        modifier = Modifier.padding(horizontal = HORIZONTAL_PADDING.dp)
                    ) {

                    }
                }

                //РЭИЛ
                item {
                    if (state.rail.railType != RailType.Nothing) {
                        //if (state.railList[0].railType == RailType.Community)
                        Rail(rail = state.rail)
                    }
                }

                item {
                    Text(
                        text = state.eventsRailHeader,
                        style = DevMeetTheme.newTypography.h1,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 4.dp)
                    )
                }
                item {
                    MoreEvents(listMoreEvents = state.eventsRail)
                }


                //Здесь идет условно бесконечный список

                itemsIndexed(items = state.searchedEventsList.drop(3)) { index, item ->

                    EventCard(
                        state = item,
                        modifier = Modifier.padding(horizontal = HORIZONTAL_PADDING.dp)
                    ) {

                    }


                }


            }

    }


    LaunchedEffect(state.searchedEventsList.size) {

        snapshotFlow { lazyListState.firstVisibleItemIndex + lazyListState.layoutInfo.visibleItemsInfo.size }
            .map { visibleItems ->
                if (visibleItems >= state.searchedEventsList.size + itemsBeforeInfiniteList - threshold)
                    true
                else
                    null

            }
            .distinctUntilChanged()
            .filterNotNull()
            .collect { _ ->
                Log.e("", "END OF LIST REACHED")
                onEndOfListReached()
            }
    }

}



@Composable
fun MainEvents(
    modifier: Modifier = Modifier,
    listMainEvents: List<EventUI>,
    onEventClick: (EventUI) -> Unit
) {
    var maxCardHeight by remember { mutableStateOf(0.dp) }
    var firstRender by remember { mutableStateOf(true) }
    BoxWithConstraints(modifier.fillMaxWidth()) {
        val constraintsHeight = constraints.maxHeight.dp

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(maxCardHeight.takeIf { !firstRender } ?: constraintsHeight),
            horizontalArrangement = Arrangement.spacedBy(HORIZONTAL_PADDING.dp)
        ) {
            itemsIndexed(listMainEvents) { index, item ->
                EventCard(
                    modifier = Modifier
                        .fillParentMaxWidth(ROW_CARD_FRACTION)
                        .heightIn(min = 0.dp)
                        .fillMaxHeight()
                        .railModifier(index, listMainEvents.size),
                    state = item,
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

@Composable
fun SoonEvents(modifier: Modifier = Modifier, listSoonEvents: List<EventUI>) {
    var maxCardHeight by remember { mutableStateOf(0.dp) }
    var firstRender by remember { mutableStateOf(true) }

    Column(verticalArrangement = Arrangement.spacedBy(HEADER_SPACE.dp), modifier = modifier) {
        Text(
            text = "Ближайшие встречи", style = DevMeetTheme.newTypography.h2,
            modifier = Modifier.padding(horizontal = HORIZONTAL_PADDING.dp)
        )
        //  BoxWithConstraints чтобы узнать max height у LazyRow
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
                            .railModifier(index, listSoonEvents.size)
                            .heightIn(min = 0.dp)
                            .fillMaxHeight(),
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

@Composable
private fun Rail(modifier: Modifier = Modifier, rail: Rail) {
    when (rail.railType) {
        RailType.Community -> RailCommunity(
            modifier = modifier,
            rail = rail.content as CommunityRailUI
        )

        RailType.Banner -> Banner(modifier = modifier.padding(horizontal = HORIZONTAL_PADDING.dp)) {

        }

        RailType.Person -> {
            RailPerson(modifier = modifier, rail = rail.content as PersonRailUi)
        }

        RailType.Nothing -> {}
    }

}

@Composable
private fun RailCommunity(modifier: Modifier = Modifier, rail: CommunityRailUI) {
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
                    modifier = Modifier.railModifier(index, rail.contentList.size),
                    state = item,
                    onSubscribeClick = { /*TODO*/ }) {

                }
            }
        }
    }
}

@Composable
private fun RailPerson(modifier: Modifier = Modifier, rail: PersonRailUi) {
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
                Person(
                    personUi = item,
                    modifier = Modifier.railModifier(index, rail.contentList.size)
                ) {

                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TagPart(
    modifier: Modifier = Modifier,
    listTagsUi: List<TagUi>,
    onTagClicked: (id: Int) -> Unit
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(HEADER_SPACE.dp),
        modifier = modifier.padding(horizontal = HORIZONTAL_PADDING.dp)
    ) {
        Text(text = stringResource(R.string.other_meetings), style = DevMeetTheme.newTypography.h2)

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            listTagsUi.forEach {
                TagMedium(
                    text = it.text.ifBlank { stringResource(id = R.string.all_categories) },
                    modifier = Modifier.padding(8.dp),
                    selected = it.isSelected
                ) {
                    onTagClicked(it.id)
                }
            }
        }
    }
}


fun Modifier.railModifier(index: Int, listSize: Int): Modifier {
    return when (index) {
        0 -> {
            this.then(Modifier.padding(start = HORIZONTAL_PADDING.dp))
        }

        listSize - 1 -> {
            this.then(Modifier.padding(end = HORIZONTAL_PADDING.dp))
        }

        else -> {
            this
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun Show() {
    MainScreen()
}