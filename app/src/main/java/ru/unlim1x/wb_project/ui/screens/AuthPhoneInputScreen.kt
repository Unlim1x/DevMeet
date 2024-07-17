package ru.unlim1x.wb_project.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import ru.unlim1x.wb_project.R
import ru.unlim1x.wb_project.ui.navigation.AuthNavGraphNodes
import ru.unlim1x.wb_project.ui.theme.DevMeetTheme
import ru.unlim1x.wb_project.ui.uiKit.buttons.PrimaryButton
import ru.unlim1x.wb_project.ui.uiKit.custominputview.PhoneInput

private val FIGMA_MAIN_TEXT_HORIZONTAL_PADDING = 40.dp
private val FIGMA_MAIN_TEXT_TOP_PADDING = 169.dp
private val COLUMN_HORIZONTAL_PADDING = 24.dp
private val BOTTOM_PADDING = 48.dp
private val MAIN_TEXT_HORIZONTAL_PADDING =
    FIGMA_MAIN_TEXT_HORIZONTAL_PADDING - COLUMN_HORIZONTAL_PADDING

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthPhoneInputScreen(navController: NavController) {

    var buttonState by remember { mutableStateOf(false) }
    var codeCountry by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    Scaffold(containerColor = DevMeetTheme.colorScheme.neutralWhite,
        topBar = {
        }) {
        LazyColumn(
            modifier = Modifier.padding(
                start = COLUMN_HORIZONTAL_PADDING, end = COLUMN_HORIZONTAL_PADDING
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            contentPadding = PaddingValues(
                top = it.calculateTopPadding() +
                        FIGMA_MAIN_TEXT_TOP_PADDING - it.calculateTopPadding()
            )
        ) {

            item {
                Text(
                    modifier = Modifier.padding(horizontal = MAIN_TEXT_HORIZONTAL_PADDING),
                    text = stringResource(R.string.enter_phone_number),
                    style = DevMeetTheme.typography.heading2
                )
            }
            item {
                Spacer(modifier = Modifier.size(8.dp))
            }
            item {
                Text(
                    modifier = Modifier
                        .padding(horizontal = MAIN_TEXT_HORIZONTAL_PADDING)
                        .padding(bottom = BOTTOM_PADDING),
                    text = stringResource(R.string.we_will_send_code),
                    style = DevMeetTheme.typography.bodyText2,
                    textAlign = TextAlign.Center,
                )
            }

            item {
                PhoneInput(modifier = Modifier.padding(bottom = BOTTOM_PADDING)) { code, phone ->
                    coroutineScope.launch {
                        buttonState = validatePhoneNumber(phone)
                        codeCountry = code
                        phoneNumber = phone
                        Log.e("callback", code + phone)
                    }
                }
            }

            item {
                PrimaryButton(
                    modifier = Modifier.fillMaxWidth(),
                    buttonText = stringResource(R.string.continue_text),
                    enabled = buttonState
                ) {
                    navController.navigate(route = AuthNavGraphNodes.CodeNode.route + "/${codeCountry}/${phoneNumber}")
                }
            }


        }

    }

}

fun validatePhoneNumber(phoneNumber: String): Boolean {
    return phoneNumber.length == 10
}

@Preview
@Composable
fun ShowPhoneInputScreen() {
    AuthPhoneInputScreen(rememberNavController())
}
