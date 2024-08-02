package ru.unlim1x.wb_project.ui.viewmodels.auth_code_input_screen

import ru.unlim1x.wb_project.ui.uiKit.custominputview.model.Country

sealed class AuthCodeInputScreenViewState {
    //data object Init:AuthCodeInputScreenViewState()
    data object Display:AuthCodeInputScreenViewState()
    data object Error:AuthCodeInputScreenViewState()

    data object Valid:AuthCodeInputScreenViewState()

}