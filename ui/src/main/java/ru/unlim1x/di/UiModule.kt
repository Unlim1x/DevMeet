package ru.unlim1x.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.unlim1x.old_ui.screens.auth_code_input_screen.AuthCodeInputScreenViewModel
import ru.unlim1x.old_ui.screens.auth_phone_input_screen.AuthPhoneInputScreenViewModel
import ru.unlim1x.old_ui.screens.auth_profile_screen.AuthProfileScreenViewModel
import ru.unlim1x.old_ui.screens.bottom_bar.BottomBarViewModel
import ru.unlim1x.old_ui.screens.community_detailed_screen.CommunityDetailedScreenViewModel
import ru.unlim1x.old_ui.screens.community_screen.CommunityScreenViewModel
import ru.unlim1x.old_ui.screens.image_picker.ImagePickerViewModel
import ru.unlim1x.old_ui.screens.meeting_detailed.MeetingDetailedScreenViewModel
import ru.unlim1x.old_ui.screens.meeting_screen.MeetingScreenViewModel
import ru.unlim1x.old_ui.screens.more_screen.MoreScreenViewModel
import ru.unlim1x.old_ui.screens.my_meetings.MyMeetingScreenViewModel
import ru.unlim1x.old_ui.screens.profile_screen.ProfileScreenViewModel
import ru.unlim1x.old_ui.screens.splash_screen.SplashScreenViewModel
import ru.unlim1x.ui.screens.main_screen.MainScreenViewModel
import ru.unlim1x.ui.screens.onboarding.OnboardingViewModel

val uiModule = module {
    viewModel { MeetingScreenViewModel(get(), get()) }
    viewModel { MyMeetingScreenViewModel(get(), get(), get()) }
    viewModel { CommunityScreenViewModel(get()) }
    viewModel { ProfileScreenViewModel(get(), get(), get()) }
    viewModel { MoreScreenViewModel(get(), get()) }
    viewModel { SplashScreenViewModel() }
    viewModel { BottomBarViewModel() }
    viewModel { AuthPhoneInputScreenViewModel(get()) }
    viewModel { AuthCodeInputScreenViewModel(get(), get()) }
    viewModel { AuthProfileScreenViewModel(get(), get()) }
    viewModel { MeetingDetailedScreenViewModel(get(), get(), get(), get()) }
    viewModel { CommunityDetailedScreenViewModel(get(), get()) }
    viewModel {ImagePickerViewModel(get())}
}

val newUiModule = module {
    viewModel { ru.unlim1x.ui.screens.aka_splash_screen.SplashScreenViewModel() }
    viewModel { OnboardingViewModel(get(), get()) }
    viewModel { MainScreenViewModel(get(), get(), get(), get(), get(), get()) }
}