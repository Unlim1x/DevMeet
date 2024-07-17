package ru.unlim1x.wb_project.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import ru.unlim1x.wb_project.ui.uiKit.tabrow.model.TabData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyMeetingScreen(navController: NavController) {

    var topBarAnimation by remember { mutableStateOf(false) }
    Scaffold(containerColor = DevMeetTheme.colorScheme.neutralWhite,
        topBar = {
            TopBar(header = stringResource(id = R.string.my_meetings),
                backIconIsVisible = true,
                backIconAction = {navController.navigateUp()})
        }) {
        topBarAnimation = true

        val listEventsPlanned: MutableList<Event> = mutableListOf()
        val listEventsFinished: MutableList<Event> = mutableListOf()
        val listOfTags = listOf("Junior", "Python", "Moscow")
        for (x in 0..14) {
            listEventsPlanned.add(
                index = x,
                element = Event(
                    name = "Developer meeting",
                    timeAndPlace = TimeAndPlace(
                        place = "Moscow",
                        date = 13,
                        month = 9,
                        year = 2024
                    ),
                    isFinished = false,
                    tags = listOfTags,
                    id = x
                )
            )
        }
        for (x in 0..2) {
            listEventsFinished.add(
                index = x, element = Event(
                    name = "Developer meeting",
                    timeAndPlace = TimeAndPlace(
                        place = "Moscow",
                        date = 13,
                        month = 9,
                        year = 2024
                    ),
                    isFinished = true,
                    tags = listOfTags
                )
            )
        }
        val tabs = listOf(
            TabData(text = stringResource(id = R.string.planned), screen = {
                PageMeetingsPlanned(
                    navController = navController,
                    listEvents = listEventsPlanned
                )
            }),
            TabData(text = stringResource(id = R.string.passed), screen = {
                PageMeetingsPassed(
                    navController = navController,
                    listEvents = listEventsFinished
                )
            }),
        )

        Column(
            modifier = Modifier.padding(
                top = it.calculateTopPadding(),
                start = 16.dp,
                end = 16.dp
            )
        ) {
            TabLayout(tabDataList = tabs, horizontalPaddingOffset = 16)
        }

    }
}


@Composable
@Preview
fun showMyMeeting() {

    MyMeetingScreen(navController = rememberNavController())
}