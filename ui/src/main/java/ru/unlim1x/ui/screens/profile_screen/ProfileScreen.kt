package ru.unlim1x.ui.screens.profile_screen

import android.net.Uri
import android.util.Log
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import ru.lim1x.domain.models.User
import ru.unlim1x.wb_project.R
import ru.unlim1x.wb_project.ui.theme.DevMeetTheme
import ru.unlim1x.wb_project.ui.uiKit.avatar.UserAvatar
import ru.unlim1x.wb_project.ui.uiKit.avatar.state.UserAvatarState
import ru.unlim1x.wb_project.ui.uiKit.buttons.SecondaryButton
import ru.unlim1x.wb_project.ui.uiKit.image_bottomsheet_picker.ImagePicker
import ru.unlim1x.wb_project.ui.uiKit.topbar.AppBarMenuItems
import ru.unlim1x.wb_project.ui.uiKit.topbar.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileScreenViewModel = koinViewModel()
) {

    var userAvatarState by remember { mutableStateOf(UserAvatarState.Default as UserAvatarState) }
    val viewState = viewModel.viewState().collectAsStateWithLifecycle()

    Scaffold(containerColor = DevMeetTheme.colorScheme.neutralWhite,
        topBar = {
            TopBar(
                header = stringResource(id = R.string.profile),
                backIconIsVisible = true,
                backIconAction = { navController.navigateUp() },
                actionMenuItem = AppBarMenuItems.Edit
            ) {

                userAvatarState = if (userAvatarState == UserAvatarState.Default)
                    UserAvatarState.Edit
                else
                    UserAvatarState.Default
            }

        }) {
        val modifier = Modifier.padding(top = it.calculateTopPadding())

        when (val state = viewState.value) {
            is ProfileScreenViewState.Display -> {
                ProfileBody(modifier = modifier, avatarState = userAvatarState, user = state.user){
                    viewModel.obtain(ProfileScreenEvent.UserChoseImage(it))
                }
            }

            ProfileScreenViewState.Init -> {}
            else -> throw NotImplementedError("Unexpected state")
        }


    }

}

@Composable
private fun ProfileBody(modifier: Modifier, avatarState: UserAvatarState, user: User, imageUpdate:(uri:Uri)->Unit) {
    val uploadedImage: MutableState<Uri?> = remember { mutableStateOf(null) }

    var openBottomSheet by rememberSaveable { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.size(50.dp))
            when (user.hasAvatar) {
                true -> {
                    Log.e("", "USER AVATAR URI: ${user.avatarURL}")
                    UserAvatar(size = 200.dp, state = avatarState, url = user.avatarURL) {
                        openBottomSheet = true
                    }
                }

                false -> {
                    UserAvatar(size = 200.dp, state = avatarState) {
                        openBottomSheet = true
                    }
                }
            }
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

    when (openBottomSheet){
        true->{
            ImagePicker(onDismiss = {openBottomSheet = false}, onHideCallback = {openBottomSheet = false}) {
                Log.e("", "ВЫЗОВ!!!!!")
                imageUpdate(it)
            }
        }
        else->{

        }
    }
}

@Composable
@Preview
private fun showProfileScreen() {
    ProfileScreen(navController = rememberNavController())
}