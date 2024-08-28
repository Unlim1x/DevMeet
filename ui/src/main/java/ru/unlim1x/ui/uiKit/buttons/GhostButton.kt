package ru.unlim1x.ui.uiKit.buttons

import android.content.Context
import android.media.AudioManager
import android.view.SoundEffectConstants
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import ru.unlim1x.wb_project.ui.uiKit.buttons.state.ButtonStateColor
import ru.unlim1x.wb_project.ui.uiKit.theme.PrimaryColorRippleTheme

@Composable
internal fun GhostButton(
    modifier: Modifier = Modifier,
    shape: Shape = ButtonDefaults.textShape,
    buttonText: String = "Button",
    interactionSource: MutableInteractionSource? = null,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    var localInteractionSource = remember { MutableInteractionSource() }
    if (interactionSource != null)
        localInteractionSource = interactionSource
    val isHovered by localInteractionSource.collectIsHoveredAsState()

    val state = remember {
        val buttonStateColor: MutableState<ButtonStateColor> =
            mutableStateOf(ButtonStateColor.Initial())
        buttonStateColor
    }
    if (isHovered)
        state.value = ButtonStateColor.Hovered()
    else if (!enabled)
        state.value = ButtonStateColor.Disabled()
    else
        state.value = ButtonStateColor.Initial()

    val context = LocalContext.current
    val view = LocalView.current
    CompositionLocalProvider(
        LocalRippleTheme provides PrimaryColorRippleTheme,
    ) {
        TextButton(
            modifier = modifier.clickable {
                (context.getSystemService(Context.AUDIO_SERVICE) as AudioManager)
                    .playSoundEffect(AudioManager.FX_KEY_CLICK, 1.0f)
                view.playSoundEffect(SoundEffectConstants.CLICK)
            },
            shape = shape,
            interactionSource = localInteractionSource,
            onClick = {
                view.playSoundEffect(SoundEffectConstants.CLICK)
                onClick()
            },
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0x00000000),
                disabledContainerColor = Color(0x00000000),
            )
        )

        {
            Text(
                text = buttonText,
                style = MaterialTheme.typography.bodySmall,
                color = state.value.color
            )
        }
    }
}

@Preview
@Composable
private fun GhostButtonPreview() {

    GhostButton {}
}