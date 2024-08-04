package ru.unlim1x.wb_project.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material3.Scaffold
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
import ru.lim1x.domain.models.Meeting
import ru.unlim1x.wb_project.R
import ru.unlim1x.wb_project.ui.theme.DevMeetTheme
import ru.unlim1x.wb_project.ui.uiKit.searchfield.SearchField
import ru.unlim1x.wb_project.ui.uiKit.tabrow.model.TabData
import ru.unlim1x.wb_project.ui.viewmodels.meeting_screen.MeetingScreenViewModel
import ru.unlim1x.wb_project.ui.viewmodels.meeting_screen.MeetingScreenViewState


@Composable
fun MeetingScreen(
    navController: NavController,
    viewModel: MeetingScreenViewModel = koinViewModel()
) {

    val viewState = viewModel.viewState().collectAsStateWithLifecycle()
    Scaffold(containerColor = DevMeetTheme.colorScheme.neutralWhite,
        topBar = {
            TopBar(header = stringResource(id = R.string.meetings))
        }) {


        val modifier = Modifier.padding(top = it.calculateTopPadding())

        when (val state = viewState.value) {
            MeetingScreenViewState.Loading -> {}

            is MeetingScreenViewState.Display -> {
                TabDataContainer(
                    modifier = modifier,
                    navController = navController,
                    listMeetingsAll = state.allMeetings.collectAsState(initial = emptyList()).value,
                    listMeetingsActive = state.activeMeetings.collectAsState(initial = emptyList()).value
                )
            }

            else -> throw NotImplementedError("Unexpected state")
        }


    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TabDataContainer(
    modifier: Modifier,
    navController: NavController,
    listMeetingsAll: List<Meeting>,
    listMeetingsActive: List<Meeting>
) {
    val tabs = listOf(
        TabData(text = stringResource(id = R.string.all_meetings), screen = {
            PageMeetingsAll(
                navController = navController,
                listMeetings = listMeetingsAll
            )
        }),
        TabData(text = stringResource(id = R.string.active), screen = {
            PageMeetingsActive(
                navController = navController,
                listMeetings = listMeetingsActive
            )
        }),
    )


    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        SearchField(state = rememberTextFieldState()) {}
        TabLayout(tabDataList = tabs, horizontalPaddingOffset = 16)
    }
}


@Composable
@Preview
fun show() {
    MeetingScreen(navController = rememberNavController())
}