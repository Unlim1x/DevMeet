package ru.unlim1x.ui.uiKit.custominputview

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.unlim1x.wb_project.ui.theme.DevMeetTheme

@Composable
internal fun PassCodeInput(modifier: Modifier = Modifier, actionDone: (code: String) -> Unit) {
    var pin by remember {
        mutableStateOf("")
    }
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()

    BasicTextField(modifier = modifier.padding(vertical = 16.dp),
        value = pin,
        onValueChange = { pin = it.take(4) },
        decorationBox = {
            Row(
                modifier = Modifier.height(DevMeetTheme.typography.heading1.lineHeight.value.dp),
                horizontalArrangement = Arrangement.spacedBy(40.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(4) {
                    NumberOrCircle(code = pin, index = it)
                }
            }
        },

        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
                coroutineScope.launch {
                    actionDone(pin)
                }
            }
        )
    )

}

@Composable
private fun NumberOrCircle(code: String, index: Int) {
    if (code.isEmpty() || code.length - 1 < index) {
        Canvas(modifier = Modifier.size(24.dp)) {
            drawCircle(DevMeetTheme.colorScheme.neutralLine)
        }
    } else {
        Box(modifier = Modifier.width(24.dp), contentAlignment = Alignment.Center) {
            Text(text = code[index].toString(), style = DevMeetTheme.typography.heading1)
        }

    }
}

@Preview
@Composable
private fun showItPass() {
    PassCodeInput {}
}

