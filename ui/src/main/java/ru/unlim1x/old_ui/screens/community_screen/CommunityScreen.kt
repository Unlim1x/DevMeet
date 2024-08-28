package ru.unlim1x.old_ui.screens.community_screen

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import ru.lim1x.domain.models.Community
import ru.unlim1x.old_ui.navigation.nav_graph.bottom.NavGraphNodes
import ru.unlim1x.old_ui.theme.DevMeetTheme
import ru.unlim1x.old_ui.uiKit.cards.CommunityCard
import ru.unlim1x.old_ui.uiKit.cards.QuantityMembers
import ru.unlim1x.old_ui.uiKit.cards.loading_cards.LoadingCommunityCardExperimental
import ru.unlim1x.old_ui.uiKit.searchfield.SearchField
import ru.unlim1x.old_ui.uiKit.topbar.TopBar
import ru.unlim1x.ui.R

private val FIGMA_HORIZONTAL_PADDING = 16.dp

@Deprecated(
    message = "This composable function is deprecated since new design was announced." +
            "Be careful, in next patches it will be removed.", level = DeprecationLevel.WARNING
)
@Composable
internal fun CommunityScreen(
    navController: NavController,
    viewModel: CommunityScreenViewModel = koinViewModel()
) {


    val viewState = viewModel.viewState().collectAsStateWithLifecycle()
    Scaffold(containerColor = DevMeetTheme.colorScheme.neutralWhite,
        topBar = {
            TopBar(header = stringResource(id = R.string.communities))
        }) {

        val modifier = Modifier.padding(top = it.calculateTopPadding())

        when (val state = viewState.value) {
            is CommunityScreenViewState.Display -> {

                CommunityLoadedScreen(
                    modifier = modifier, navController = navController,
                    communityList = state.communities.collectAsStateWithLifecycle(initialValue = emptyList()).value
                )
            }

            CommunityScreenViewState.Loading -> {
                CommunitiesLoadingScreen()
            }
            else -> throw NotImplementedError("Unexpected state")
        }

    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CommunityLoadedScreen(
    modifier: Modifier,
    navController: NavController,
    communityList: List<Community>
) {
    var animate by remember {
        mutableStateOf(false)
    }
    Log.e("", "${communityList}")
    AnimatedVisibility(visible = animate,
        enter = fadeIn(animationSpec = tween(500))
    ) {
        Column(modifier = modifier.padding(horizontal = FIGMA_HORIZONTAL_PADDING)) {
            SearchField(state = rememberTextFieldState()) {}
            Spacer(modifier = Modifier.size(8.dp))
            LazyColumn {

                items(communityList) { community ->
                    CommunityCard(
                        heading = community.name, quantity = QuantityMembers(
                            numberOfMembers = community.quantityMembers.toLong(),
                            context = LocalContext.current
                        ).quantityString
                    ) {
                        navController.navigate(NavGraphNodes.CommunityRoot.CommunityDetailed.route + "/${community.id}/${community.name}")
                    }
                }

            }
        }
    }
    LaunchedEffect(key1 = Unit) {
        animate = true
    }
}
@Composable
private fun CommunitiesLoadingScreen(){
    LazyColumn(userScrollEnabled = false,
        modifier = Modifier
            .padding(horizontal = FIGMA_HORIZONTAL_PADDING)
            .fillMaxHeight()
            .background(color = DevMeetTheme.colorScheme.neutralWhite)
            .padding(top = 4.dp)
    ) {
        items(MutableList(20){}) {_  ->
            LoadingCommunityCardExperimental()
            Spacer(modifier = Modifier.size(12.dp))
        }
    }
}


@Composable
@Preview
private fun ShowCommunityScreen() {
    CommunityScreen(rememberNavController())
}