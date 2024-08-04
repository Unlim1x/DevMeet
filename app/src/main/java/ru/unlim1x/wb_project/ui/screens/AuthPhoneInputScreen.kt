package ru.unlim1x.wb_project.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import ru.unlim1x.wb_project.R
import ru.unlim1x.wb_project.ui.navigation.AuthNavGraphNodes
import ru.unlim1x.wb_project.ui.theme.DevMeetTheme
import ru.unlim1x.wb_project.ui.uiKit.buttons.PrimaryButton
import ru.unlim1x.wb_project.ui.uiKit.custominputview.PhoneInput
import ru.unlim1x.wb_project.ui.viewmodels.auth_phone_input_screen.AuthPhoneInputScreenEvent
import ru.unlim1x.wb_project.ui.viewmodels.auth_phone_input_screen.AuthPhoneInputScreenViewModel
import ru.unlim1x.wb_project.ui.viewmodels.auth_phone_input_screen.AuthPhoneInputScreenViewState

private val FIGMA_MAIN_TEXT_HORIZONTAL_PADDING = 40.dp
private val FIGMA_MAIN_TEXT_TOP_PADDING = 169.dp
private val COLUMN_HORIZONTAL_PADDING = 24.dp
private val BOTTOM_PADDING = 48.dp
private val MAIN_TEXT_HORIZONTAL_PADDING =
    FIGMA_MAIN_TEXT_HORIZONTAL_PADDING - COLUMN_HORIZONTAL_PADDING

@Composable
fun AuthPhoneInputScreen(
    navController: NavController,
    viewModel: AuthPhoneInputScreenViewModel = koinViewModel()
) {

    val viewState = viewModel.viewState().collectAsStateWithLifecycle()

    Scaffold(containerColor = DevMeetTheme.colorScheme.neutralWhite,
        topBar = {
        }) {
        val modifier = Modifier.padding(top = it.calculateTopPadding())

        when (val state = viewState.value) {
            is AuthPhoneInputScreenViewState.Display -> {
                PhoneInputBody(modifier = modifier) { countryCode, phoneNumber ->
                    viewModel.obtain(AuthPhoneInputScreenEvent.SendCode(countryCode, phoneNumber))
                }
            }

            is AuthPhoneInputScreenViewState.Sent -> {
                LaunchedEffect(key1 = viewState) {
                    navController.navigate(route = AuthNavGraphNodes.CodeNode.route + "/${state.countryCode}/${state.phone}")
                }
            }

            else -> throw NotImplementedError("Unexpected state")
        }

    }

}

@Composable
private fun PhoneInputBody(
    modifier: Modifier = Modifier,
    onButtonClick: (countyCode: String, phoneNumber: String) -> Unit
) {
    var buttonState by remember { mutableStateOf(false) }
    var codeCountry by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        modifier = modifier.padding(
            horizontal = COLUMN_HORIZONTAL_PADDING
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        contentPadding = PaddingValues(
            top = FIGMA_MAIN_TEXT_TOP_PADDING
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
                onButtonClick(codeCountry, phoneNumber)
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
