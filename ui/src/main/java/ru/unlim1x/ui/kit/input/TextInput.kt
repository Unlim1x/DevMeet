package ru.unlim1x.ui.kit.input

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.unlim1x.old_ui.theme.DevMeetTheme
import ru.unlim1x.ui.R

//TODO: Добавить стейт, хранящий текст и ошибку
@Composable
internal fun InputField(
    modifier: Modifier = Modifier,
    maxLines: Int = 1,
    @StringRes
    hint: Int,
    error: State<Boolean>,
    onTextChanged: (text: String) -> Unit,
    onActionNext: (text: String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    //TODO: ВО VIEWMODEL!
    var text by remember {
        mutableStateOf("")
    }
    val defaultModifier = modifier
        .clip(RoundedCornerShape(16.dp))
        .background(if (!error.value) DevMeetTheme.colorScheme.neutralSecondaryBackground else DevMeetTheme.colorScheme.inputError)
        .fillMaxWidth()
        .defaultMinSize(minHeight = 56.dp)

    val borderModifier = defaultModifier.border(
        width = 1.dp,
        color = Color(0xFF9A41FE),
        shape = RoundedCornerShape(16.dp)
    )

    BasicTextField(
        modifier = if (!isFocused) defaultModifier else borderModifier,
        interactionSource = interactionSource,
        maxLines = maxLines,
        value = text,
        onValueChange = { string ->
            text = string
            onTextChanged(text)
        },
        textStyle = DevMeetTheme.newTypography.regular,
        decorationBox = { innerTextField ->
            DecorationBox(text = text, hint = stringResource(hint), error = error.value) {
                innerTextField()
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Ascii,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                focusManager.clearFocus()
                onActionNext(text)
            }
        ),
    )
}

@Composable
private fun DecorationBox(
    text: String,
    hint: String,
    error: Boolean,
    innerTextField: @Composable () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(if (!error) DevMeetTheme.colorScheme.neutralSecondaryBackground else Color.Transparent)
    ) {
        Box {
            if (text.isEmpty())
                Text(
                    text = hint,
                    style = DevMeetTheme.newTypography.regular,
                    color = DevMeetTheme.colorScheme.neutralDisabled
                )

            innerTextField()
        }

    }
}

@Preview
@Composable
fun ShowTextInput() {
    val state1 = remember {
        mutableStateOf(false)
    }
    val state2 = remember {
        mutableStateOf(true)
    }
    Column(modifier = Modifier.background(color = Color.White)) {
        InputField(hint = (R.string.hint_name_surname), onTextChanged = { it }, error = state1) {

        }
        InputField(hint = (R.string.hint_name_surname), onTextChanged = { it }, error = state2) {

        }
    }
}