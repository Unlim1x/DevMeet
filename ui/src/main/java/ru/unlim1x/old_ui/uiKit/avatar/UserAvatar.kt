package ru.unlim1x.old_ui.uiKit.avatar

import android.view.SoundEffectConstants
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.unlim1x.old_ui.theme.DevMeetTheme
import ru.unlim1x.old_ui.uiKit.avatar.state.UserAvatarState
import ru.unlim1x.old_ui.uiKit.theme.NoRippleTheme
import ru.unlim1x.ui.R

@Composable
internal fun UserAvatar(
    size: Dp = 100.dp,
    state: UserAvatarState = UserAvatarState.Default,
    onClick: () -> Unit
) {
    val boxSize = size
    val imageUserSize = ((76f / 200f) * boxSize.value).dp
    val imagePlusSize = ((20f / 100f) * boxSize.value).dp
    when (state) {
        UserAvatarState.Default -> {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(boxSize)
                    .background(DevMeetTheme.colorScheme.neutralSecondaryBackground),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.icon_user),
                    contentDescription = stringResource(R.string.user_avatar),
                    modifier = Modifier.size(imageUserSize)
                )
            }
        }

        UserAvatarState.Edit -> {
            Box(contentAlignment = Alignment.BottomEnd) {
                val view = LocalView.current
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            view.playSoundEffect(SoundEffectConstants.CLICK)
                            onClick()
                        }
                        .size(boxSize)
                        .background(DevMeetTheme.colorScheme.neutralSecondaryBackground),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.icon_user),
                        contentDescription = stringResource(R.string.user_avatar),
                        modifier = Modifier.size(imageUserSize)
                    )
                }
                CompositionLocalProvider(
                    LocalRippleTheme provides NoRippleTheme,
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.icon_plus),
                        contentDescription = stringResource(R.string.user_avatar),
                        modifier = Modifier
                            .size(imagePlusSize)
                            .clickable {
                                view.playSoundEffect(SoundEffectConstants.CLICK)
                                onClick()
                            }

                    )
                }
            }
        }
    }
}

@Composable
internal fun UserAvatar(
    size: Dp = 100.dp,
    state: UserAvatarState = UserAvatarState.Default,
    bitmap: ImageBitmap,
    onClick: () -> Unit
) {
    val boxSize = size
    val imageUserSize = ((76f / 200f) * boxSize.value).dp
    val imagePlusSize = ((20f / 100f) * boxSize.value).dp
    when (state) {
        UserAvatarState.Default -> {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(boxSize)
                    .background(DevMeetTheme.colorScheme.neutralSecondaryBackground),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    bitmap = bitmap,
                    contentDescription = stringResource(R.string.user_avatar),
                    modifier = Modifier.size(imageUserSize)
                )
            }
        }

        UserAvatarState.Edit -> {
            Box(contentAlignment = Alignment.BottomEnd) {
                val view = LocalView.current
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            view.playSoundEffect(SoundEffectConstants.CLICK)
                            onClick()
                        }
                        .size(boxSize)
                        .background(DevMeetTheme.colorScheme.neutralSecondaryBackground),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        bitmap = bitmap,
                        contentDescription = stringResource(R.string.user_avatar),
                        modifier = Modifier.size(imageUserSize)
                    )
                }
                CompositionLocalProvider(
                    LocalRippleTheme provides NoRippleTheme,
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.icon_plus),
                        contentDescription = stringResource(R.string.user_avatar),
                        modifier = Modifier
                            .size(imagePlusSize)
                            .clickable {
                                view.playSoundEffect(SoundEffectConstants.CLICK)
                                onClick()
                            }

                    )
                }
            }
        }
    }
}

@Composable
internal fun UserAvatar(
    size: Dp = 100.dp,
    state: UserAvatarState = UserAvatarState.Default,
    url: Any?,
    onClick: () -> Unit
) {
    val imagePlusSize = ((20f / 100f) * size.value).dp
    when (state) {
        UserAvatarState.Default -> {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(size)
                    .background(DevMeetTheme.colorScheme.neutralSecondaryBackground),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = url, contentDescription = stringResource(R.string.user_avatar),
                    contentScale = ContentScale.Crop
                )
            }
        }

        UserAvatarState.Edit -> {
            Box(contentAlignment = Alignment.BottomEnd) {
                val view = LocalView.current
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            view.playSoundEffect(SoundEffectConstants.CLICK)
                            onClick()
                        }
                        .size(size)
                        .background(DevMeetTheme.colorScheme.neutralSecondaryBackground),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        modifier = Modifier.size(size),
                        model = url,
                        contentDescription = stringResource(R.string.user_avatar),
                        contentScale = ContentScale.Crop
                    )
                }
                CompositionLocalProvider(
                    LocalRippleTheme provides NoRippleTheme,
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.icon_plus),
                        contentDescription = stringResource(R.string.user_avatar),
                        modifier = Modifier
                            .size(imagePlusSize)
                            .clickable {
                                view.playSoundEffect(SoundEffectConstants.CLICK)
                                onClick()
                            }

                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun ShowUserAvatar() {
    UserAvatar(100.dp, UserAvatarState.Edit) {}
}