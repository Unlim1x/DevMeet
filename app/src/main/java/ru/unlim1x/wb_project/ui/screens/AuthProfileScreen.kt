package ru.unlim1x.wb_project.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.unlim1x.wb_project.R
import ru.unlim1x.wb_project.ui.navigation.AuthNavGraphNodes
import ru.unlim1x.wb_project.ui.theme.Wb_projectTheme
import ru.unlim1x.wb_project.ui.uiKit.avatar.UserAvatar
import ru.unlim1x.wb_project.ui.uiKit.avatar.state.UserAvatarState
import ru.unlim1x.wb_project.ui.uiKit.buttons.PrimaryButton
import ru.unlim1x.wb_project.ui.uiKit.custominputview.TextInput


private val FIGMA_AVATAR_TOP_PADDING = 136.dp
private val AVATAR_BOTTOM = 24.dp
private val TEXT_PADDING = 12.dp
private val HORIZONTAL_PADDING = 24.dp
private val BUTTON_PADDING = 48.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthProfileScreen(navController: NavController) {


    //var topBarAnimation by remember { mutableStateOf(false) }

    var buttonState by remember { mutableStateOf(false) }


    var name by remember {
        mutableStateOf("")
    }
    var surname by remember {
        mutableStateOf("")
    }
    val focusManager = LocalFocusManager.current

    Scaffold(containerColor = Wb_projectTheme.colorScheme.neutralWhite,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Wb_projectTheme.colorScheme.neutralWhite,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }


                },
                title = {
                    Text(
                        stringResource(id = R.string.profile),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = Wb_projectTheme.typography.subheading1
                    )
                }


            )
        }) {


        LazyColumn(
            modifier = Modifier.padding(
                horizontal = HORIZONTAL_PADDING
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            contentPadding = PaddingValues(
                top = it.calculateTopPadding() +
                        FIGMA_AVATAR_TOP_PADDING - it.calculateTopPadding()
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
                TextInput(hint = stringResource(id = R.string.name_necessarily), onTextChanged = {
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
                    hint = stringResource(id = R.string.surname_optionally),
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
                    navController.navigate(route = AuthNavGraphNodes.MainGraphNode.route) {
                        popUpTo(route = AuthNavGraphNodes.PhoneNode.route) {
                            inclusive = true
                        }
                    }
                }
            }


        }

    }

}

@Preview
@Composable
fun ShowAuthProfile() {
    AuthProfileScreen(navController = rememberNavController())
}