package ru.unlim1x.wb_project.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.unlim1x.wb_project.R
import ru.unlim1x.wb_project.ui.screens.model.User
import ru.unlim1x.wb_project.ui.theme.Wb_projectTheme
import ru.unlim1x.wb_project.ui.uiKit.avatar.UserAvatar
import ru.unlim1x.wb_project.ui.uiKit.avatar.state.UserAvatarState
import ru.unlim1x.wb_project.ui.uiKit.buttons.SecondaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {

    var userAvatarState by remember { mutableStateOf(UserAvatarState.Default as UserAvatarState) }


    Scaffold(containerColor = Wb_projectTheme.colorScheme.neutralWhite,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Wb_projectTheme.colorScheme.neutralWhite,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Профиль",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = Wb_projectTheme.typography.subheading1
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        userAvatarState = if (userAvatarState == UserAvatarState.Default)
                            UserAvatarState.Edit
                        else
                            UserAvatarState.Default
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Изменить"
                        )
                    }
                }

            )
        }) {
        //временная мера
        val user = User(
            name = "Иван Иванов",
            phone = "+7 999 999-99-99",
            avatarURL = "",
            hasAvatar = false
        )


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
                    style = Wb_projectTheme.typography.heading2,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    text = user.phone,
                    style = Wb_projectTheme.typography.subheading1,
                    modifier = Modifier.padding(top = 4.dp),
                    color = Wb_projectTheme.colorScheme.neutralWeak
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