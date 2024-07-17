package ru.unlim1x.wb_project.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.unlim1x.wb_project.R
import ru.unlim1x.wb_project.ui.navigation.NavGraphNodes
import ru.unlim1x.wb_project.ui.screens.model.MoreContainerData
import ru.unlim1x.wb_project.ui.screens.model.User
import ru.unlim1x.wb_project.ui.theme.DevMeetTheme
import ru.unlim1x.wb_project.ui.uiKit.avatar.UserAvatar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoreScreen(navController: NavController) {

    Scaffold(containerColor = DevMeetTheme.colorScheme.neutralWhite,
        topBar = {
            TopBar(header = stringResource(id = R.string.more))

        }) {
        //TODO: потом во ViewModel?
        val user = User(
            name = "Иван Иванов",
            phone = "+7 999 999-99-99",
            avatarURL = "",
            hasAvatar = false
        )
        val myMeetings = MoreContainerData(iconId = R.drawable.icon_meeting, text = stringResource(
            id = R.string.my_meetings
        ))
        val theme = MoreContainerData(iconId = R.drawable.icon_theme, text = stringResource(R.string.theme_string))
        val notification =
            MoreContainerData(iconId = R.drawable.icon_notification, text = stringResource(R.string.notifications))
        val safety = MoreContainerData(iconId = R.drawable.icon_safety, text = stringResource(R.string.safety))
        val memory = MoreContainerData(iconId = R.drawable.icon_res, text = stringResource(R.string.memory_res_string))
        val help = MoreContainerData(iconId = R.drawable.icon_help, text = stringResource(R.string.help))
        val invite = MoreContainerData(iconId = R.drawable.icon_invite, text = stringResource(R.string.invite_friend))

        val listOfContainers = listOf(myMeetings, theme, notification, safety, memory, help, invite)



        LazyColumn(
            modifier = Modifier.padding(
                top = it.calculateTopPadding()
            )
        ) {


            item {
                MoreContainer(
                    user = user,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                ) {
                    navController.navigate(NavGraphNodes.MoreRoot.Profile.route)
                }
            }
            // TODO: items(listOfContainers){container->
          //      MoreContainer()
        //    }
            item {
                MoreContainer(
                    moreContainerData = myMeetings,
                    modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                ) {
                    navController.navigate(NavGraphNodes.MoreRoot.MyMeetings.route)
                }
            }
            item {
                MoreContainer(
                    moreContainerData = theme,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {}
            }
            item {
                MoreContainer(
                    moreContainerData = notification,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {}
            }
            item {
                MoreContainer(
                    moreContainerData = safety,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {}
            }
            item {
                MoreContainer(
                    moreContainerData = memory,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {}
            }
            item {
                HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
            }
            item {
                MoreContainer(
                    moreContainerData = help,
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                ) {
                    navController.navigate(NavGraphNodes.MoreRoot.Elements.route)
                }
            }
            item {
                MoreContainer(moreContainerData = invite) {}
            }
        }
    }
}

@Composable
fun MoreContainer(
    moreContainerData: MoreContainerData,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    @Composable
    fun MoreContainerWrapper() {
        Row(
            modifier = Modifier.wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = moreContainerData.iconId),
                contentDescription = moreContainerData.text,
                modifier = Modifier
                    .height(20.dp)
                    .width(18.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = moreContainerData.text, style = DevMeetTheme.typography.bodyText1)
        }
    }

    Row(modifier = modifier
        .requiredHeightIn(min = 40.dp, max = 50.dp)
        .clickable { onClick() }
        .fillMaxWidth()
        .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        MoreContainerWrapper()
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.icon_right_arrow),
            contentDescription = moreContainerData.text,
            modifier = Modifier.height(20.dp)
        )
    }

}

@Composable
fun MoreContainer(user: User, modifier: Modifier = Modifier, onClick: () -> Unit) {
    @Composable
    fun MoreContainerWrapper(Avatar: @Composable () -> Unit) {
        Row(
            modifier = Modifier.wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(modifier = Modifier.size(50.dp)) {
                Avatar()
            }

            Spacer(modifier = Modifier.size(8.dp))
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                Text(text = user.name, style = DevMeetTheme.typography.bodyText1)
                Text(text = user.phone, style = DevMeetTheme.typography.metadata1)
            }
        }
    }

    Row(modifier = modifier
        .requiredHeightIn(min = 40.dp, max = 50.dp)
        .clickable { onClick() }
        .fillMaxWidth()
        .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically) {
        if (user.hasAvatar)
            TODO()
        else
            MoreContainerWrapper {
                UserAvatar {}
            }
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.icon_right_arrow),
            contentDescription = user.name,
            modifier = Modifier.height(20.dp)
        )
    }

}

@Preview
@Composable
fun MoreContainerPreview() {
    val user =
        User(name = "Иван Иванов", phone = "+7 999 999-99-99", avatarURL = "", hasAvatar = false)
    val element = MoreContainerData(iconId = R.drawable.icon_invite, text = "Пригласить друга")
//    Column {
//        MoreContainer(moreContainerData = element) {}
//        MoreContainer(user = user) {}
//    }
    MoreScreen(rememberNavController())
}



