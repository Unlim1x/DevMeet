package ru.unlim1x.wb_project.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.unlim1x.wb_project.R
import ru.unlim1x.wb_project.ui.navigation.NavGraphNodes
import ru.unlim1x.wb_project.ui.theme.DevMeetTheme
import ru.unlim1x.wb_project.ui.uiKit.cards.CommunityCard
import ru.unlim1x.wb_project.ui.uiKit.cards.QuantityMembers
import ru.unlim1x.wb_project.ui.uiKit.cards.model.Community
import ru.unlim1x.wb_project.ui.uiKit.cards.model.LoremIpsum
import ru.unlim1x.wb_project.ui.uiKit.searchfield.SearchField

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CommunityScreen(navController: NavController) {
    val FIGMA_HORIZONTAL_PADDING = 16.dp

    Scaffold(containerColor = DevMeetTheme.colorScheme.neutralWhite,
        topBar = {
            TopBar(header = stringResource(id = R.string.communities))
        }) {

        val modifier = Modifier.padding(top = it.calculateTopPadding())
        val communityList: List<Community> = List(20) {id->
            Community(
                "Designa",
                quantityMembers = 10000,
                id = id,
                description = LoremIpsum.Short.text
            )
        }

        Column(modifier = modifier.padding(horizontal = FIGMA_HORIZONTAL_PADDING)) {
            SearchField(state = rememberTextFieldState()) {}
            Spacer(modifier = Modifier.size(8.dp))
            LazyColumn() {

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
}

@Composable
@Preview
fun ShowCommunityScreen() {
    CommunityScreen(rememberNavController())
}