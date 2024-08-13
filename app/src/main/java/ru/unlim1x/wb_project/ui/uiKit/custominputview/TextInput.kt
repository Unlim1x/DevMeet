package ru.unlim1x.wb_project.ui.uiKit.custominputview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.unlim1x.wb_project.ui.theme.DevMeetTheme

@Composable
internal fun TextInput(
    modifier: Modifier = Modifier,
    maxLines: Int = 1,
    hint: String,
    onTextChanged: (text: String) -> Unit,
    onActionNext: (text: String) -> Unit
) {
    var text by remember {
        mutableStateOf("")
    }
    val focusManager = LocalFocusManager.current
    BasicTextField(
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .background(DevMeetTheme.colorScheme.neutralSecondaryBackground)
            .fillMaxWidth(),
        maxLines = maxLines,
        value = text,
        onValueChange = { string ->
            text = string
            onTextChanged(text)
        },
        textStyle = DevMeetTheme.typography.bodyText1,
        decorationBox = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(DevMeetTheme.colorScheme.neutralSecondaryBackground)
            ) {
                Box {
                    if (text.isEmpty())
                        Text(
                            text = hint,
                            style = DevMeetTheme.typography.bodyText1,
                            color = DevMeetTheme.colorScheme.neutralDisabled
                        )

                    it()
                }

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

@Preview
@Composable
fun ShowTextInput() {
    TextInput(hint = "AMOGUS", onTextChanged = { it }) {

    }
}