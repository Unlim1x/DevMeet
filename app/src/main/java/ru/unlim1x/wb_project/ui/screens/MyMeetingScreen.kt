package ru.unlim1x.wb_project.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.unlim1x.wb_project.ui.theme.Wb_projectTheme
import ru.unlim1x.wb_project.ui.uiKit.cards.TimeAndPlace
import ru.unlim1x.wb_project.ui.uiKit.cards.model.Event
import ru.unlim1x.wb_project.ui.uiKit.tabrow.PageMeetings
import ru.unlim1x.wb_project.ui.uiKit.tabrow.WBTabLayout
import ru.unlim1x.wb_project.ui.uiKit.tabrow.model.TabData

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MyMeetingScreen(bottomPadding: Dp, navController: NavController, tabs: List<TabData>) {

    Scaffold(containerColor = Wb_projectTheme.colorScheme.neutralWhite,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Wb_projectTheme.colorScheme.neutralWhite,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "${navController.currentDestination?.route}",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = Wb_projectTheme.typography.subheading1
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                },

                )
        }) {

        val modifier = Modifier.padding(top = it.calculateTopPadding(), bottom = bottomPadding)


        Column(modifier = modifier.padding(horizontal = 16.dp)) {
            WBTabLayout(tabDataList = tabs, horizontalPaddingOffset = 16)
        }

    }
}


@Composable
@Preview
fun showMyMeeting() {
    //TODO: Пример
    val listOfTags = listOf("Junior", "Python", "Moscow")
    val listEvents: MutableList<Event> = mutableListOf()
    val listEvents2: MutableList<Event> = mutableListOf()
    for (x in 0..3)
        listEvents.add(
            index = x, element = Event(
                name = "Developer meeting",
                timeAndPlace = TimeAndPlace(place = "Moscow", date = 13, month = 9, year = 2024),
                isFinished = x % 4 == 0,
                tags = listOfTags
            )
        )
    for (x in 0..2)
        listEvents2.add(
            index = x, element = Event(
                name = "Developer meeting",
                timeAndPlace = TimeAndPlace(place = "Moscow", date = 13, month = 9, year = 2024),
                isFinished = x % 4 == 0,
                tags = listOfTags
            )
        )

    val tabs = listOf(
        TabData(text = "ЗАПЛАНИРОВАНО", screen = {
            PageMeetings(
                listEvents = listEvents
            )
        }),
        TabData(text = "УЖЕ ПРОШЛИ", screen = {
            PageMeetings(
                listEvents = listEvents2
            )
        }),
    )
    MeetingScreen(bottomPadding = 50.dp, navController = rememberNavController(), tabs = tabs)
}