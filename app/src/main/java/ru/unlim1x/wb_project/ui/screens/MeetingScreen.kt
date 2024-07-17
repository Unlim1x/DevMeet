package ru.unlim1x.wb_project.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.unlim1x.wb_project.R
import ru.unlim1x.wb_project.ui.theme.DevMeetTheme
import ru.unlim1x.wb_project.ui.uiKit.cards.TimeAndPlace
import ru.unlim1x.wb_project.ui.uiKit.cards.model.Event
import ru.unlim1x.wb_project.ui.uiKit.searchfield.SearchField
import ru.unlim1x.wb_project.ui.uiKit.tabrow.model.TabData

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MeetingScreen(navController: NavController) {

    Scaffold(containerColor = DevMeetTheme.colorScheme.neutralWhite,
        topBar = {
            TopBar(header = stringResource(id = R.string.meetings))
        }) {


        val modifier = Modifier.padding(top = it.calculateTopPadding())


        val listOfTags = listOf("Junior", "Python", "Moscow")
        val listEvents: MutableList<Event> = mutableListOf()
        val listEvents2: MutableList<Event> = mutableListOf()
        for (x in 0..14)
            listEvents.add(
                index = x, element = Event(
                    name = "Developer meeting",
                    timeAndPlace = TimeAndPlace(
                        place = "Moscow",
                        date = 13,
                        month = 9,
                        year = 2024
                    ),
                    isFinished = x % 4 == 0,
                    tags = listOfTags
                )
            )
        for (x in 0..2)
            listEvents2.add(
                index = x, element = Event(
                    name = "Developer meeting",
                    timeAndPlace = TimeAndPlace(
                        place = "Moscow",
                        date = 13,
                        month = 9,
                        year = 2024
                    ),
                    isFinished = x % 4 == 0,
                    tags = listOfTags
                )
            )
        val tabs = listOf(
            TabData(text = stringResource(id = R.string.all_meetings), screen = {
                PageMeetingsAll(
                    navController = navController,
                    listEvents = listEvents
                )
            }),
            TabData(text = stringResource(id = R.string.active), screen = {
                PageMeetingsActive(
                    navController = navController,
                    listEvents = listEvents2
                )
            }),
        )


        Column(modifier = modifier.padding(horizontal = 16.dp)) {
            SearchField(state = rememberTextFieldState()) {}
            TabLayout(tabDataList = tabs, horizontalPaddingOffset = 16)
        }

    }
}


@Composable
@Preview
fun show() {
    MeetingScreen(navController = rememberNavController())
}