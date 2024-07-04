package ru.unlim1x.wb_project.ui.uiKit.buttons

import android.view.SoundEffectConstants
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.unlim1x.wb_project.R
import ru.unlim1x.wb_project.ui.uiKit.buttons.state.ButtonStateColor
import ru.unlim1x.wb_project.ui.uiKit.theme.PrimaryColorRippleTheme

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    shape: Shape = ButtonDefaults.outlinedShape,
    buttonText: String = "Button",
    buttonIconId: Int = 0,
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

    val view = LocalView.current
    CompositionLocalProvider(
        LocalRippleTheme provides PrimaryColorRippleTheme,
    ) {
        OutlinedButton(
            modifier = modifier,
            shape = shape,
            interactionSource = localInteractionSource,
            onClick = {
                view.playSoundEffect(SoundEffectConstants.CLICK)
                onClick()
            },
            enabled = enabled,
            border = BorderStroke(width = 1.dp, color = state.value.color),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0x00000000),
                disabledContainerColor = Color(0x00000000),
            )
        )

        {
            if (buttonIconId != 0) {
                Image(
                    painter = painterResource(id = buttonIconId),
                    contentDescription = "Image",
                    modifier = Modifier
                        .height(15.dp)
                        .width(15.dp)
                )
            }
            if (buttonText != "") {
                Text(
                    text = buttonText,
                    style = MaterialTheme.typography.bodySmall,
                    color = state.value.color
                )
            }
        }
    }
}

@Preview
@Composable
fun SecondaryButtonPreview() {
    Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
        SecondaryButton {}
        SecondaryButton(buttonIconId = R.drawable.insta, buttonText = "") {}
        SecondaryButton(buttonIconId = R.drawable.insta, buttonText = " mCombo") {}
    }

}

