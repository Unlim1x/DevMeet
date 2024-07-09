package ru.unlim1x.wb_project.ui.uiKit.custominputview

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.unlim1x.wb_project.ui.theme.Wb_projectTheme
import ru.unlim1x.wb_project.ui.uiKit.custominputview.model.Country
import ru.unlim1x.wb_project.ui.uiKit.custominputview.model.PhoneNumberTransformation


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhoneInput(modifier: Modifier = Modifier, actionNext: (phone: String) -> Unit) {
    val FIGMA_HORIZONTAL_PADDING = 8.dp
    var selectedCountry by remember { mutableStateOf(Country.Russia) }
    var phone by remember {
        mutableStateOf("")
    }
    var expanded by remember {
        mutableStateOf(false)
    }
    val focusManager = LocalFocusManager.current
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .background(Wb_projectTheme.colorScheme.neutralSecondaryBackground)
                .padding(vertical = FIGMA_HORIZONTAL_PADDING)
                .clickable { expanded = !expanded },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.padding(horizontal = FIGMA_HORIZONTAL_PADDING),
                painter = painterResource(id = selectedCountry.flagPainterId),
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(end = FIGMA_HORIZONTAL_PADDING),
                text = selectedCountry.phoneCode,
                style = Wb_projectTheme.typography.bodyText1,
                color = Wb_projectTheme.colorScheme.neutralDisabled
            )
        }

        DropdownMenu(
            modifier = Modifier
                .background(Wb_projectTheme.colorScheme.neutralSecondaryBackground),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Country.entries.forEach { country ->
                DropdownMenuItem(
                    modifier = Modifier
                        .background(Wb_projectTheme.colorScheme.neutralSecondaryBackground),
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(FIGMA_HORIZONTAL_PADDING),
                        ) {
                            Icon(
                                painter = painterResource(id = country.flagPainterId),
                                contentDescription = null,
                                tint = Color.Unspecified
                            )
                            Text(
                                text = country.phoneCode,
                                style = Wb_projectTheme.typography.bodyText1,
                                color = Wb_projectTheme.colorScheme.neutralDisabled
                            )
                        }
                    },
                    onClick = {
                        selectedCountry = country
                        expanded = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.size(FIGMA_HORIZONTAL_PADDING))

        BasicTextField(
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .background(Wb_projectTheme.colorScheme.neutralSecondaryBackground)
                .fillMaxWidth(),
            value = phone,
            onValueChange = { phone = it.take(10) },
            textStyle = Wb_projectTheme.typography.bodyText1,
            decorationBox = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(Wb_projectTheme.colorScheme.neutralSecondaryBackground)
                ) {
                    if (phone.isEmpty())
                        Text(
                            modifier = Modifier.padding(start = FIGMA_HORIZONTAL_PADDING),
                            text = "999 999-99-99",
                            style = Wb_projectTheme.typography.bodyText1,
                            color = Wb_projectTheme.colorScheme.neutralDisabled
                        )
                    it()
                }
            },
            visualTransformation = PhoneNumberTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.clearFocus()
                    actionNext(selectedCountry.phoneCode + phone)
                }
            ),
        )


    }

}

fun printToLog(string: String) {
    Log.e("CALL PRINT", string)
}

@Composable
@Preview
fun ShowPhoneInput() {
    PhoneInput() {}
}