package ru.unlim1x.wb_project.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.unlim1x.wb_project.AppBarMenuItems
import ru.unlim1x.wb_project.R
import ru.unlim1x.wb_project.ui.screens.model.User
import ru.unlim1x.wb_project.ui.theme.DevMeetTheme
import ru.unlim1x.wb_project.ui.uiKit.avatar.UserAvatar
import ru.unlim1x.wb_project.ui.uiKit.avatar.state.UserAvatarState
import ru.unlim1x.wb_project.ui.uiKit.buttons.SecondaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {

    var userAvatarState by remember { mutableStateOf(UserAvatarState.Default as UserAvatarState) }
    var topBarAnimation by remember { mutableStateOf(false) }

    Scaffold(containerColor = DevMeetTheme.colorScheme.neutralWhite,
        topBar = {
            TopBar(header = stringResource(id = R.string.profile),
                backIconIsVisible = true,
                backIconAction = {navController.navigateUp()},
                actionMenuItem = AppBarMenuItems.Edit){

                userAvatarState = if (userAvatarState == UserAvatarState.Default)
                    UserAvatarState.Edit
                else
                    UserAvatarState.Default
            }

        }) {
        //временная мера
        val user = User(
            name = "Иван Иванов",
            phone = "+7 999 999-99-99",
            avatarURL = "",
            hasAvatar = false
        )
        topBarAnimation = true

        LazyColumn(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.size(50.dp))
                UserAvatar(size = 200.dp, state = userAvatarState) {}
            }
            item {
                Text(
                    text = user.name,
                    style = DevMeetTheme.typography.heading2,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    text = user.phone,
                    style = DevMeetTheme.typography.subheading1,
                    modifier = Modifier.padding(top = 4.dp),
                    color = DevMeetTheme.colorScheme.neutralWeak
                )
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    SecondaryButton(buttonIconId = R.drawable.twitter, buttonText = "") {}
                    SecondaryButton(buttonIconId = R.drawable.insta, buttonText = "") {}
                    SecondaryButton(buttonIconId = R.drawable.linkedin, buttonText = "") {}
                    SecondaryButton(buttonIconId = R.drawable.facebook, buttonText = "") {}
                }
            }

        }
    }
}

@Composable
@Preview
fun showProfileScreen() {
    ProfileScreen(navController = rememberNavController())
}