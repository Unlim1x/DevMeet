package ru.unlim1x.wb_project.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import ru.unlim1x.wb_project.R
import ru.unlim1x.wb_project.ui.theme.DevMeetTheme
import ru.unlim1x.wb_project.ui.uiKit.cards.TimeAndPlace
import ru.unlim1x.wb_project.ui.uiKit.cards.model.Meeting
import ru.unlim1x.wb_project.ui.uiKit.searchfield.SearchField
import ru.unlim1x.wb_project.ui.uiKit.tabrow.model.TabData
import ru.unlim1x.wb_project.ui.viewmodels.my_meetings.MyMeetingScreenEvent
import ru.unlim1x.wb_project.ui.viewmodels.my_meetings.MyMeetingScreenViewModel
import ru.unlim1x.wb_project.ui.viewmodels.my_meetings.MyMeetingScreenViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyMeetingScreen(navController: NavController, viewModel: MyMeetingScreenViewModel = koinViewModel()) {


    val viewState = viewModel.viewState().observeAsState()
    Scaffold(containerColor = DevMeetTheme.colorScheme.neutralWhite,
        topBar = {
            TopBar(header = stringResource(id = R.string.my_meetings),
                backIconIsVisible = true,
                backIconAction = {navController.navigateUp()})
        }) {
        val modifier = Modifier.padding(top = it.calculateTopPadding())

        when(val state = viewState.value){
            is MyMeetingScreenViewState.Display -> {
                TabDataContainer(
                    modifier = modifier,
                    navController = navController,
                    listMeetingsPlanned = state.plannedMeetings.collectAsState(initial = emptyList()).value,
                    listMeetingsPassed = state.finishedMeetings.collectAsState(initial = emptyList()).value
                )
            }
            MyMeetingScreenViewState.Loading -> {}
            else -> throw NotImplementedError("Unexpected state")
        }


    }
    LaunchedEffect(key1 = viewState) {
        viewModel.obtain(MyMeetingScreenEvent.OpenScreen)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TabDataContainer(
    modifier: Modifier,
    navController: NavController,
    listMeetingsPlanned: List<Meeting>,
    listMeetingsPassed: List<Meeting>
) {
    val tabs = listOf(
        TabData(text = stringResource(id = R.string.planned), screen = {
            PageMeetingsPlanned(
                navController = navController,
                listMeetings = listMeetingsPlanned
            )
        }),
        TabData(text = stringResource(id = R.string.passed), screen = {
            PageMeetingsPassed(
                navController = navController,
                listMeetings = listMeetingsPassed
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
fun showMyMeeting() {

    MyMeetingScreen(navController = rememberNavController())
}