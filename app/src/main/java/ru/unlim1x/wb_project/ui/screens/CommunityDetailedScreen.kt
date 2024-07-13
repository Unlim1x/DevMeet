package ru.unlim1x.wb_project.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.unlim1x.wb_project.R
import ru.unlim1x.wb_project.ui.navigation.NavGraphNodes
import ru.unlim1x.wb_project.ui.theme.Wb_projectTheme
import ru.unlim1x.wb_project.ui.uiKit.cards.EventCard
import ru.unlim1x.wb_project.ui.uiKit.cards.TimeAndPlace
import ru.unlim1x.wb_project.ui.uiKit.cards.model.Community
import ru.unlim1x.wb_project.ui.uiKit.cards.model.Event
import ru.unlim1x.wb_project.ui.uiKit.cards.model.LoremIpsum

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityDetailedScreen(navController: NavController, communityName: String, communityId: Int) {
    val FIGMA_HORIZONTAL_PADDING = 16.dp
    val FIGMA_GAP_BIG = 20.dp
    val FIGMA_GAP_SMALL = 2.dp

    var topBarAnimation by remember { mutableStateOf(false) }

    val community = Community(
        name = communityName,
        id = communityId,
        quantityMembers = 10000,
        description = LoremIpsum.Short.text
    )

    Scaffold(containerColor = Wb_projectTheme.colorScheme.neutralWhite,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Wb_projectTheme.colorScheme.neutralWhite,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "${community.name} ${community.id}",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = Wb_projectTheme.typography.subheading1
                    )
                },
                navigationIcon = {
                    AnimatedVisibility(
                        visible = topBarAnimation,
                        enter = slideInHorizontally(
                            animationSpec = tween(durationMillis = 500),
                            initialOffsetX = { -it })
                    ) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Назад"
                            )
                        }
                    }

                }


            )
        }) {
        topBarAnimation = true
        val modifier = Modifier.padding(top = it.calculateTopPadding())

        val listOfTags = listOf("Junior", "Python", "Moscow")
        val listEvents: MutableList<Event> = mutableListOf()
        for (x in 0..14) {
            listEvents.add(
                index = x,
                element = Event(
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
        }

        LazyColumn(modifier = modifier.padding(horizontal = FIGMA_HORIZONTAL_PADDING)) {
            item { Spacer(modifier = Modifier.size(FIGMA_GAP_BIG)) }

            item {
                Text(
                    text = community.description,
                    style = Wb_projectTheme.typography.metadata1
                )
            }

            item { Spacer(modifier = Modifier.size(FIGMA_GAP_BIG)) }

            item {
                Text(
                    stringResource(id = R.string.community_meetings),
                    style = Wb_projectTheme.typography.bodyText1,
                    color = Wb_projectTheme.colorScheme.neutralWeak
                )
            }

            item { Spacer(modifier = Modifier.size(FIGMA_GAP_SMALL)) }

            items(listEvents) { event ->
                EventCard(
                    heading = event.name,
                    timeAndPlace = event.timeAndPlace.dateAndPlaceString,
                    tags = event.tags
                ) {
                    navController.navigate(NavGraphNodes.CommunityRoot.CommunityDetailed.MeetingDetailed.route + "/${event.id}/${event.name}")
                }
            }

        }


    }
}

@Composable
@Preview
fun ShowDetailedCommunity() {
    CommunityDetailedScreen(
        navController = rememberNavController(),
        communityName = "Designa",
        communityId = 1
    )
}