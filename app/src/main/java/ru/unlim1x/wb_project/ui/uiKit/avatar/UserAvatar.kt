package ru.unlim1x.wb_project.ui.uiKit.avatar

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
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.unlim1x.wb_project.R
import ru.unlim1x.wb_project.ui.theme.Wb_projectTheme
import ru.unlim1x.wb_project.ui.uiKit.avatar.state.UserAvatarState
import ru.unlim1x.wb_project.ui.uiKit.theme.NoRippleTheme

@Composable
fun UserAvatar(
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
                    .background(Wb_projectTheme.colorScheme.neutralSecondaryBackground),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.icon_user),
                    contentDescription = "User avatar",
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
                        .background(Wb_projectTheme.colorScheme.neutralSecondaryBackground),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.icon_user),
                        contentDescription = "User avatar",
                        modifier = Modifier.size(imageUserSize)
                    )
                }
                CompositionLocalProvider(
                    LocalRippleTheme provides NoRippleTheme,
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.icon_plus),
                        contentDescription = "User avatar",
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
fun UserAvatar(
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
                    .background(Wb_projectTheme.colorScheme.neutralSecondaryBackground),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    bitmap = bitmap,
                    contentDescription = "User avatar",
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
                        .background(Wb_projectTheme.colorScheme.neutralSecondaryBackground),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        bitmap = bitmap,
                        contentDescription = "User avatar",
                        modifier = Modifier.size(imageUserSize)
                    )
                }
                CompositionLocalProvider(
                    LocalRippleTheme provides NoRippleTheme,
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.icon_plus),
                        contentDescription = "User avatar",
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
fun UserAvatar(
    size: Dp = 100.dp,
    state: UserAvatarState = UserAvatarState.Default,
    bitmap: ImageBitmap,
    onClick: () -> Unit,
    image: @Composable ()->Unit
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
                    .background(Wb_projectTheme.colorScheme.neutralSecondaryBackground),
                contentAlignment = Alignment.Center
            ) {
                image()
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
                        .background(Wb_projectTheme.colorScheme.neutralSecondaryBackground),
                    contentAlignment = Alignment.Center
                ) {
                    image()
                }
                CompositionLocalProvider(
                    LocalRippleTheme provides NoRippleTheme,
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.icon_plus),
                        contentDescription = "User avatar",
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