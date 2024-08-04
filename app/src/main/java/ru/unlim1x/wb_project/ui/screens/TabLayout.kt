package ru.unlim1x.wb_project.ui.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.unlim1x.wb_project.ui.navigation.NavGraphNodes
import ru.unlim1x.wb_project.ui.theme.DevMeetTheme
import ru.unlim1x.wb_project.ui.uiKit.cards.EventCard
import ru.lim1x.domain.models.Meeting
import ru.unlim1x.wb_project.ui.uiKit.tabrow.model.TabData
import java.security.InvalidParameterException

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Throws(InvalidParameterException::class)
fun TabLayout(
    tabDataList: List<TabData>, modifier: Modifier = Modifier, horizontalPaddingOffset: Int = 8
) {

    val size = tabDataList.size
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { tabDataList.size })
    Column(modifier = modifier) {
        ScrollableTabRow(selectedTabIndex = pagerState.currentPage,
            modifier = Modifier.fillMaxWidth(),
            edgePadding = 0.dp,
            containerColor = DevMeetTheme.colorScheme.neutralWhite,
            divider = { HorizontalDivider(color = Color.Transparent) }) {
            tabDataList.forEachIndexed { index, tab ->
                val selected = pagerState.currentPage == index
                Tab(
                    modifier = Modifier.width((LocalConfiguration.current.screenWidthDp / size - horizontalPaddingOffset).dp),
                    text = {
                        Text(
                            text = tab.text,
                            color = if (selected) DevMeetTheme.colorScheme.brandDefault else Color(
                                0xFF666666
                            )
                        )
                    },
                    selected = selected,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                            Log.e("TAB", "${pagerState.currentPage}")
                        }
                    })
            }
        }

        HorizontalPager(state = pagerState) { page ->
            tabDataList[pagerState.currentPage].screen()

        }
    }

}

@Composable
fun PageMeetingsAll(navController: NavController, listMeetings: List<Meeting>) {
    LazyColumn(
        modifier = Modifier
            .background(color = DevMeetTheme.colorScheme.neutralWhite)
            .padding(top = 4.dp)
    ) {
        itemsIndexed(listMeetings) { index, event ->
            EventCard(
                heading = event.name,
                timeAndPlace = event.timeAndPlace.dateAndPlaceString,
                isOver = event.isFinished,
                tags = event.tags
            ) {
                if (!event.isFinished)
                    navController.navigate(NavGraphNodes.MeetingRoot.MeetingDetailed.route + "/${event.id}")
            }
            Spacer(modifier = Modifier.size(12.dp))
        }
    }
}

@Composable
fun PageMeetingsActive(navController: NavController, listMeetings: List<Meeting>) {
    LazyColumn(
        modifier = Modifier
            .background(color = DevMeetTheme.colorScheme.neutralWhite)
            .padding(top = 4.dp)
    ) {
        itemsIndexed(listMeetings) { index, event ->
            EventCard(
                heading = event.name,
                timeAndPlace = event.timeAndPlace.dateAndPlaceString,
                isOver = event.isFinished,
                tags = event.tags
            ) {
                if (!event.isFinished)
                    navController.navigate(NavGraphNodes.MeetingRoot.MeetingDetailed.route + "/${event.id}")
            }
            Spacer(modifier = Modifier.size(12.dp))
        }
    }
}

@Composable
fun PageMeetingsPlanned(navController: NavController, listMeetings: List<Meeting>) {
    LazyColumn(
        modifier = Modifier
            .background(color = DevMeetTheme.colorScheme.neutralWhite)
            .padding(top = 4.dp)
    ) {
        itemsIndexed(listMeetings) { index, event ->
            EventCard(
                heading = event.name,
                timeAndPlace = event.timeAndPlace.dateAndPlaceString,
                isOver = event.isFinished,
                tags = event.tags
            ) {
                navController.navigate(NavGraphNodes.MoreRoot.MeetingDetailed.route + "/${event.id}")
            }
            Spacer(modifier = Modifier.size(12.dp))
        }
    }
}

@Composable
fun PageMeetingsPassed(navController: NavController, listMeetings: List<Meeting>) {
    LazyColumn(
        modifier = Modifier
            .background(color = DevMeetTheme.colorScheme.neutralWhite)
            .padding(top = 4.dp)
    ) {
        itemsIndexed(listMeetings) { index, event ->
            EventCard(
                heading = event.name,
                timeAndPlace = event.timeAndPlace.dateAndPlaceString,
                isOver = event.isFinished,
                tags = event.tags
            ) {
                //navController.navigate(NavGraphNodes.MeetingRoot.MeetingDetailed.route+"/${event.id}/${event.name}")
            }
            Spacer(modifier = Modifier.size(12.dp))
        }
    }
}