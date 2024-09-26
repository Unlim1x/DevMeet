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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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

private const val HORIZONTAL_PADDING = 16
private const val ROW_CARD_FRACTION = 0.8f
private const val HEADER_SPACE = 16
private const val VERTICAL_GAP = 40

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun MainScreen(viewModel: MainScreenViewModel = koinViewModel()) {
    val viewState by viewModel.viewState().collectAsStateWithLifecycle()
    var topBarPadding by remember { mutableStateOf(0) }
    val density = LocalDensity.current.density
    when (viewState) {
        is MainScreenViewState.Display -> {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)) {
                TopBarSearchRow(modifier = Modifier
                    .padding(
                        top = 10.dp,
                        start = HORIZONTAL_PADDING.dp,
                        end = HORIZONTAL_PADDING.dp
                    )
                    .onGloballyPositioned {
                        topBarPadding = (it.size.height / density).toInt()
                    }, onSearch = {}, onValueChanged = {}, onMenuItemClick = { }) {

                }
                MainScreenBody(
                    Modifier.padding(top = topBarPadding.dp + 15.dp),
                    state = viewState as MainScreenViewState.Display,
                    onEndOfListReached = { viewModel.obtain(MainScreenEvent.ScrolledToEndOfList) }
                ) {
                    viewModel.obtain(MainScreenEvent.ClickOnTag(it))
                }
            }

        }

        is MainScreenViewState.DisplaySearch -> {}
        MainScreenViewState.Error -> {}
        MainScreenViewState.Loading -> {}
    }
}

@OptIn(FlowPreview::class, ExperimentalFoundationApi::class)
@Composable
private fun MainScreenBody(
    modifier: Modifier,
    state: MainScreenViewState.Display,
    onEndOfListReached: () -> Unit,
    onTagClicked: (id: Int) -> Unit
) {
    Log.e("MAIN SCREEN", "$state")
    Log.e("MAIN SCREEN", "INFINITE LIST ${state.infiniteEventsListByTag}")
    Log.e("MAIN SCREEN", "RAILLIST LIST ${state.railList}")
    Log.e("MAIN SCREEN", "TAG LIST ${state.otherTags}")
    val lazyListState = rememberSaveable(saver = LazyListState.Saver) { LazyListState() }
    val threshold = 2
    val itemsBeforeInfiniteList = 4
    var railIndex = 0

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            //.padding(horizontal = HORIZONTAL_PADDING.dp)
        //.padding(bottom = 24.dp)
    ) {


//        Text(text = "Здесь будет поисковая строка", modifier = Modifier
//            .align(Alignment.TopCenter)
//            .onGloballyPositioned {
//                topBarPadding =
//                    (it.size.height / density).toInt()
//            })
        LazyColumn(
            state = lazyListState,
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(40.dp),
            //contentPadding = PaddingValues(bottom = VERTICAL_GAP.dp)
        ) {
            //Главные
            item {
                MainEvents(
                    modifier = Modifier.padding(top = 20.dp),
                    listMainEvents = state.mainEventsList
                )
            }
            //СКОРО
            item {
                SoonEvents(listSoonEvents = state.soonEventsList)
            }
            //ПЕРВЫЙ РЭИЛ
            item {
                //if(state.railList.isNotEmpty())
                if (state.railList[0].railType == RailType.Community)
                    Rail(rail = state.railList[0])
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
                    modifier = Modifier.padding(horizontal = HORIZONTAL_PADDING.dp)//.animateItemPlacement(
                    //animationSpec = tween(durationMillis = 600)
                    //)
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


    LaunchedEffect(lazyListState, state) {

        snapshotFlow { lazyListState.firstVisibleItemIndex + lazyListState.layoutInfo.visibleItemsInfo.size }
            //.debounce(300)
            .distinctUntilChanged()
            .collect { visibleItems ->
                state.infiniteEventsListByTag.let {
                    if (visibleItems >= it.size + itemsBeforeInfiniteList - threshold) {
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
fun MainEvents(modifier: Modifier = Modifier, listMainEvents: List<EventUI>) {
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

    Column(verticalArrangement = Arrangement.spacedBy(HEADER_SPACE.dp), modifier = modifier) {
        Text(
            text = "Ближайшие встречи", style = DevMeetTheme.newTypography.h2,
            modifier = Modifier.padding(horizontal = HORIZONTAL_PADDING.dp)
        )
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
                    modifier = Modifier.railModifier(index),
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
                    modifier = Modifier.railModifier(index)
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
        modifier = Modifier.padding(horizontal = HORIZONTAL_PADDING.dp)
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


fun Modifier.railModifier(index: Int): Modifier {
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