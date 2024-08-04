package ru.unlim1x.wb_project.ui.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import ru.lim1x.domain.models.CommunityDetailed
import ru.lim1x.domain.models.Meeting
import ru.unlim1x.wb_project.R
import ru.unlim1x.wb_project.ui.navigation.NavGraphNodes
import ru.unlim1x.wb_project.ui.theme.DevMeetTheme
import ru.unlim1x.wb_project.ui.uiKit.cards.EventCard
import ru.unlim1x.wb_project.ui.viewmodels.community_detailed_screen.CommunityDetailedScreenEvent
import ru.unlim1x.wb_project.ui.viewmodels.community_detailed_screen.CommunityDetailedScreenViewModel
import ru.unlim1x.wb_project.ui.viewmodels.community_detailed_screen.CommunityDetailedScreenViewState

private val FIGMA_HORIZONTAL_PADDING = 16.dp
private val FIGMA_GAP_BIG = 20.dp
private val FIGMA_GAP_SMALL = 2.dp

@Composable
fun CommunityDetailedScreen(
    navController: NavController, communityName: String, communityId: Int,
    viewModel: CommunityDetailedScreenViewModel = koinViewModel()
) {

    val viewState = viewModel.viewState().collectAsStateWithLifecycle()

    Scaffold(containerColor = DevMeetTheme.colorScheme.neutralWhite,
        topBar = {
            TopBar(header = "$communityName $communityId",
                backIconIsVisible = true,
                backIconAction = { navController.navigateUp() })
        }) {

        val modifier = Modifier.padding(top = it.calculateTopPadding())

        when (val state = viewState.value) {
            is CommunityDetailedScreenViewState.Display -> {
                CommunityDetailedBody(
                    modifier = modifier,
                    navController = navController,
                    community = state.community.collectAsState(state.initial).value,
                    listMeetings = state.listOfMeetings.collectAsState(emptyList()).value
                )
            }

            CommunityDetailedScreenViewState.Init -> {
                viewModel.obtain(CommunityDetailedScreenEvent.OpenScreen(id = communityId))
            }

            else -> throw NotImplementedError("Unexpected state")
        }


    }
}

@Composable
private fun CommunityDetailedBody(
    modifier: Modifier = Modifier,
    navController: NavController,
    community: CommunityDetailed,
    listMeetings: List<Meeting>
) {
    LazyColumn(modifier = modifier.padding(horizontal = FIGMA_HORIZONTAL_PADDING)) {
        item { Spacer(modifier = Modifier.size(FIGMA_GAP_BIG)) }

        item {
            Text(
                text = community.description,
                style = DevMeetTheme.typography.metadata1
            )
        }

        item { Spacer(modifier = Modifier.size(FIGMA_GAP_BIG)) }

        item {
            Text(
                stringResource(id = R.string.community_meetings),
                style = DevMeetTheme.typography.bodyText1,
                color = DevMeetTheme.colorScheme.neutralWeak
            )
        }

        item { Spacer(modifier = Modifier.size(FIGMA_GAP_SMALL)) }

        items(listMeetings) { event ->
            EventCard(
                heading = event.name,
                timeAndPlace = event.timeAndPlace.dateAndPlaceString,
                tags = event.tags
            ) {
                navController.navigate(NavGraphNodes.CommunityRoot.CommunityDetailed.MeetingDetailed.route + "/${event.id}")
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