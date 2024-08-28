package ru.unlim1x.ui.screens.meeting_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import ru.unlim1x.wb_project.R
import ru.unlim1x.wb_project.ui.theme.DevMeetTheme
import ru.unlim1x.wb_project.ui.uiKit.searchfield.SearchField
import ru.unlim1x.wb_project.ui.uiKit.tab_layout.PageMeetingsActive
import ru.unlim1x.wb_project.ui.uiKit.tab_layout.PageMeetingsAll
import ru.unlim1x.wb_project.ui.uiKit.tab_layout.PageMeetingsLoading
import ru.unlim1x.wb_project.ui.uiKit.tab_layout.TabLayout
import ru.unlim1x.wb_project.ui.uiKit.tab_layout.model.TabData
import ru.unlim1x.wb_project.ui.uiKit.topbar.TopBar

@Deprecated(
    message = "This composable function is deprecated since new design was announced." +
            "Be careful, in next patches it will be removed.", level = DeprecationLevel.WARNING
)
@Composable
internal fun MeetingScreen(
    navController: NavController,
    viewModel: MeetingScreenViewModel = koinViewModel()
) {

    val viewState by viewModel.viewState().collectAsStateWithLifecycle()
    Scaffold(containerColor = DevMeetTheme.colorScheme.neutralWhite,
        topBar = {
            TopBar(header = stringResource(id = R.string.meetings))
        }) {


        val modifier = Modifier.padding(top = it.calculateTopPadding())


        TabDataContainer(
            modifier = modifier,
            navController = navController,
            state = viewState
        )


    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TabDataContainer(
    modifier: Modifier,
    navController: NavController,
    state: MeetingScreenViewState
) {
    val tabs = when (state) {
        is MeetingScreenViewState.Display -> {

            listOf(
                TabData(text = stringResource(id = R.string.all_meetings), screen = {
                    PageMeetingsAll(
                        navController = navController,
                        listMeetings = state.allMeetings.collectAsStateWithLifecycle(
                            initialValue = emptyList()
                        ).value
                    )
                }),
                TabData(text = stringResource(id = R.string.active), screen = {
                    PageMeetingsActive(
                        navController = navController,
                        listMeetings = state.activeMeetings.collectAsStateWithLifecycle(initialValue = emptyList()).value
                    )
                }),
            )
        }

        MeetingScreenViewState.Loading -> {

            listOf(
                TabData(text = stringResource(id = R.string.all_meetings), screen = {
                    PageMeetingsLoading()
                }),
                TabData(text = stringResource(id = R.string.active), screen = {
                    PageMeetingsLoading()
                }),
            )
        }
    }

        Column(modifier = modifier.padding(horizontal = 16.dp)) {
            SearchField(state = rememberTextFieldState()) {}
            TabLayout(tabDataList = tabs, horizontalPaddingOffset = 16)
        }
    }




@Composable
@Preview
private fun show() {
    MeetingScreen(navController = rememberNavController())
}