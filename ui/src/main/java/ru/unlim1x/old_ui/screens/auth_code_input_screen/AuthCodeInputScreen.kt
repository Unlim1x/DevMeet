package ru.unlim1x.old_ui.screens.auth_code_input_screen

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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import ru.unlim1x.old_ui.navigation.nav_graph.auth.AuthNavGraphNodes
import ru.unlim1x.old_ui.theme.DevMeetTheme
import ru.unlim1x.old_ui.uiKit.buttons.GhostButton
import ru.unlim1x.old_ui.uiKit.custominputview.PassCodeInput
import ru.unlim1x.old_ui.uiKit.custominputview.model.PhoneNumberTransformation
import ru.unlim1x.old_ui.uiKit.topbar.TopBar
import ru.unlim1x.ui.R

private val FIGMA_MAIN_TEXT_HORIZONTAL_PADDING = 40.dp
private val FIGMA_MAIN_TEXT_TOP_PADDING = 169.dp
private val COLUMN_HORIZONTAL_PADDING = 24.dp
private val BOTTOM_PADDING = 48.dp
private val MAIN_TEXT_HORIZONTAL_PADDING =
    FIGMA_MAIN_TEXT_HORIZONTAL_PADDING - COLUMN_HORIZONTAL_PADDING


@Composable
internal fun AuthCodeInputScreen(
    navController: NavController,
    phone: String,
    code: String,
    viewModel: AuthCodeInputScreenViewModel = koinViewModel()
) {

    val viewState = viewModel.viewState().collectAsStateWithLifecycle()

    Scaffold(containerColor = DevMeetTheme.colorScheme.neutralWhite,
        topBar = {
            TopBar(backIconIsVisible = true,
                backIconAction = { navController.popBackStack() }
            )
        }) {
        val modifier = Modifier.padding(top = it.calculateTopPadding())

        when (viewState.value) {
            AuthCodeInputScreenViewState.Display -> {
                CodeInputBody(
                    modifier = modifier,
                    code = code,
                    phone = phone,
                    actionDone = { pinCode ->
                        if (validateCode(pinCode)) {
                            viewModel.obtain(
                                AuthCodeInputScreenEvent.Validate(
                                    phone = phone,
                                    pinCode
                                )
                            )
                        }
                    }) {
                    viewModel.obtain(AuthCodeInputScreenEvent.Resend)
                }
            }

            AuthCodeInputScreenViewState.Error -> {}
            AuthCodeInputScreenViewState.Valid -> {
                LaunchedEffect(key1 = viewState) {
                    navController.navigate(route = AuthNavGraphNodes.ProfileNode.route) {
                        popUpTo(route = AuthNavGraphNodes.PhoneNode.route)
                    }
                }
            }

            else -> throw NotImplementedError("Unexpected state")
        }


    }

}

@Composable
private fun CodeInputBody(
    modifier: Modifier = Modifier, code: String, phone: String,
    actionDone: (pinCode: String) -> Unit, onButtonClick: () -> Unit
) {

    val phoneNumberText = transformPhoneNumber(code, phone)
    var buttonState by remember { mutableStateOf(true) }
    val focusRequester = remember { FocusRequester() }


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
                text = stringResource(R.string.enter_code),
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
                text = stringResource(R.string.we_sent_code_to_number) + phoneNumberText,
                style = DevMeetTheme.typography.bodyText2,
                textAlign = TextAlign.Center,
            )

        }

        item {
            PassCodeInput(
                modifier = Modifier
                    .padding(bottom = BOTTOM_PADDING)
                    .focusRequester(focusRequester)
            ) { code ->
                actionDone(code)
            }
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
        }


        item {
            GhostButton(
                modifier = Modifier.fillMaxWidth(),
                buttonText = stringResource(R.string.request_code_again),
                enabled = buttonState
            ) {
                onButtonClick()
            }
        }


    }
}

internal fun validateCode(code: String): Boolean {
    return code.length == 4
}

internal fun transformPhoneNumber(code: String, phone: String): String {
    return code + " " +
            PhoneNumberTransformation().filter(AnnotatedString(phone, spanStyle = SpanStyle())).text
}

@Preview
@Composable
private fun ShowCodeInputScreen() {
    AuthCodeInputScreen(rememberNavController(), phone = "9999999999", code = "+7")
}
