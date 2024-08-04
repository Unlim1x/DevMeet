package ru.unlim1x.wb_project.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import ru.unlim1x.wb_project.R
import ru.unlim1x.wb_project.ui.navigation.AuthNavGraphNodes
import ru.unlim1x.wb_project.ui.theme.DevMeetTheme
import ru.unlim1x.wb_project.ui.uiKit.avatar.UserAvatar
import ru.unlim1x.wb_project.ui.uiKit.avatar.state.UserAvatarState
import ru.unlim1x.wb_project.ui.uiKit.buttons.PrimaryButton
import ru.unlim1x.wb_project.ui.uiKit.custominputview.TextInput
import ru.unlim1x.wb_project.ui.viewmodels.auth_profile_screen.AuthProfileScreenEvent
import ru.unlim1x.wb_project.ui.viewmodels.auth_profile_screen.AuthProfileScreenViewModel
import ru.unlim1x.wb_project.ui.viewmodels.auth_profile_screen.AuthProfileScreenViewState


private val FIGMA_AVATAR_TOP_PADDING = 136.dp
private val AVATAR_BOTTOM = 24.dp
private val TEXT_PADDING = 12.dp
private val HORIZONTAL_PADDING = 24.dp
private val BUTTON_PADDING = 48.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthProfileScreen(navController: NavController, viewModel:AuthProfileScreenViewModel = koinViewModel()) {


    val viewState = viewModel.viewState().collectAsStateWithLifecycle()

    Scaffold(containerColor = DevMeetTheme.colorScheme.neutralWhite,
        topBar = {
            TopBar(header = stringResource(id = R.string.profile),
                backIconIsVisible = true,
                backIconAction = {navController.navigateUp()})

        }) {
            val modifier = Modifier.padding(top = it.calculateTopPadding())

            when (val state = viewState.value){
                AuthProfileScreenViewState.Display -> {
                    AuthProfileBody {name,surname->
                        viewModel.obtain(AuthProfileScreenEvent.Save(name, surname))
                    }
                }
                AuthProfileScreenViewState.Saved -> {
                    LaunchedEffect(key1 = viewState) {
                        navController.navigate(route = AuthNavGraphNodes.MainGraphNode.route) {
                            popUpTo(route = AuthNavGraphNodes.PhoneNode.route) {
                                inclusive = true
                            }
                        }
                    }
                }
                else->throw NotImplementedError("Unexpected state")
            }


    }

}

@Composable
private fun AuthProfileBody(modifier: Modifier = Modifier, onButtonClick:(name:String, surname:String)->Unit){
    var buttonState by remember { mutableStateOf(false) }

    var name by remember {
        mutableStateOf("")
    }
    var surname by remember {
        mutableStateOf("")
    }

    LazyColumn(
        modifier = Modifier.padding(
            horizontal = HORIZONTAL_PADDING
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        contentPadding = PaddingValues(
            top = FIGMA_AVATAR_TOP_PADDING
        )
    ) {

        item {
            UserAvatar(state = UserAvatarState.Edit) {

            }
        }
        item {
            Spacer(modifier = Modifier.size(AVATAR_BOTTOM))
        }
        item {
            TextInput(hint = stringResource(R.string.name_necessarily), onTextChanged = {
                name = it
                buttonState = it.isNotEmpty()
            }) {
                name = it
            }
        }
        item {
            Spacer(modifier = Modifier.size(TEXT_PADDING))
        }
        item {
            TextInput(
                hint = stringResource(R.string.surname_optionally),
                onTextChanged = { surname = it }) {
                surname = it
            }
        }


        item {
            Spacer(modifier = Modifier.size(BUTTON_PADDING))
        }

        item {
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                buttonText = stringResource(R.string.save),
                enabled = buttonState
            ) {
                onButtonClick(name, surname)
            }
        }


    }
}

@Preview
@Composable
fun ShowAuthProfile() {
    AuthProfileScreen(navController = rememberNavController())
}