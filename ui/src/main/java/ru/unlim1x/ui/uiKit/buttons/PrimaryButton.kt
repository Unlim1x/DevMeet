package ru.unlim1x.ui.uiKit.buttons

import android.view.SoundEffectConstants
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.unlim1x.wb_project.ui.uiKit.buttons.state.ButtonStateColor


@Composable
internal fun PrimaryButton(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(30.dp),
    buttonText: String = "Button",
    interactionSource: MutableInteractionSource? = null,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val state = remember {
        val buttonStateColor: MutableState<ButtonStateColor> =
            mutableStateOf(ButtonStateColor.Initial())
        buttonStateColor
    }

    var localInteractionSource = remember { MutableInteractionSource() }
    if (interactionSource != null)
        localInteractionSource = interactionSource
    val isHovered by localInteractionSource.collectIsHoveredAsState()


    if (isHovered)
        state.value = ButtonStateColor.Hovered()
    else if (!enabled)
        state.value = ButtonStateColor.Disabled()
    else
        state.value = ButtonStateColor.Initial()

    val view = LocalView.current
    Button(
        modifier = modifier,
        shape = shape,
        interactionSource = localInteractionSource,
        onClick = {
            view.playSoundEffect(SoundEffectConstants.CLICK)
            onClick()
        },
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = state.value.color,
            disabledContainerColor = state.value.color,
        )
    ) {
        Text(text = buttonText, color = Color.White)
    }


}

@Preview
@Composable
private fun PrimaryButtonPreview() {

    PrimaryButton {}
}


