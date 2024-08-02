package ru.unlim1x.wb_project.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.unlim1x.wb_project.ui.viewmodels.auth_code_input_screen.AuthCodeInputScreenViewModel
import ru.unlim1x.wb_project.ui.viewmodels.auth_phone_input_screen.AuthPhoneInputScreenViewModel
import ru.unlim1x.wb_project.ui.viewmodels.auth_profile_screen.AuthProfileScreenViewModel
import ru.unlim1x.wb_project.ui.viewmodels.bottom_bar.BottomBarViewModel
import ru.unlim1x.wb_project.ui.viewmodels.community_detailed_screen.CommunityDetailedScreenViewModel
import ru.unlim1x.wb_project.ui.viewmodels.community_screen.CommunityScreenViewModel
import ru.unlim1x.wb_project.ui.viewmodels.meeting_detailed.MeetingDetailedScreenViewModel
import ru.unlim1x.wb_project.ui.viewmodels.meeting_screen.MeetingScreenViewModel
import ru.unlim1x.wb_project.ui.viewmodels.more_screen.MoreScreenViewModel
import ru.unlim1x.wb_project.ui.viewmodels.my_meetings.MyMeetingScreenViewModel
import ru.unlim1x.wb_project.ui.viewmodels.profile_screen.ProfileScreenViewModel
import ru.unlim1x.wb_project.ui.viewmodels.splash_screen.SplashScreenViewModel

val appModule = module {
    viewModel{ MeetingScreenViewModel(get(),get()) }
    viewModel{ MyMeetingScreenViewModel(get(),get()) }
    viewModel{ CommunityScreenViewModel(get()) }
    viewModel{ ProfileScreenViewModel(get(),get()) }
    viewModel{MoreScreenViewModel(get(),get())}
    viewModel{SplashScreenViewModel()}
    viewModel{BottomBarViewModel()}
    viewModel{AuthPhoneInputScreenViewModel(get())}
    viewModel{AuthCodeInputScreenViewModel(get(),get())}
    viewModel{ AuthProfileScreenViewModel(get(),get()) }
    viewModel{MeetingDetailedScreenViewModel(get(),get(), get(), get(), get())}
    viewModel{CommunityDetailedScreenViewModel(get(),get())}
}