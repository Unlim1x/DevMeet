package ru.lim1x.domain.di

import org.koin.dsl.module
import ru.lim1x.domain.interactor_implementation.GetMainEventsUseCase
import ru.lim1x.domain.interactor_implementation.GetMoreEventsInteractor
import ru.lim1x.domain.interactor_implementation.GetMoreEventsUseCase
import ru.lim1x.domain.interactor_implementation.GetRailUseCase
import ru.lim1x.domain.interactor_implementation.GetSoonEventsUseCase
import ru.lim1x.domain.interactor_implementation.GetTagsUseCase
import ru.lim1x.domain.interactor_implementation.InnerLoadRailInteractor
import ru.lim1x.domain.interactor_implementation.InnerMoreEvents
import ru.lim1x.domain.interactor_implementation.InnerTagsInteractor
import ru.lim1x.domain.interactor_implementation.LoadMainEventsInteractor
import ru.lim1x.domain.interactor_implementation.LoadMoreEventsInteractor
import ru.lim1x.domain.interactor_implementation.LoadRailInteractor
import ru.lim1x.domain.interactor_implementation.LoadSoonEventsInteractor
import ru.lim1x.domain.interactor_implementation.TagsOnboardingGetInteractor
import ru.lim1x.domain.interactor_implementation.TagsOnboardingUpdateInteractor
import ru.lim1x.domain.interfaces.interactors.IGetMoreEventsInteractor
import ru.lim1x.domain.interfaces.interactors.ILoadMainEventsInteractor
import ru.lim1x.domain.interfaces.interactors.ILoadMoreEventsInteractor
import ru.lim1x.domain.interfaces.interactors.ILoadRailInteractor
import ru.lim1x.domain.interfaces.interactors.ILoadSoonEventsInteractor
import ru.lim1x.domain.interfaces.interactors.ITagsOnboardingGetInteractor
import ru.lim1x.domain.interfaces.interactors.ITagsOnboardingUpdateInteractor
import ru.lim1x.domain.interfaces.usecases.IGetActiveMeetingsUseCase
import ru.lim1x.domain.interfaces.usecases.IGetAllMeetingsUseCase
import ru.lim1x.domain.interfaces.usecases.IGetCommunitiesUseCase
import ru.lim1x.domain.interfaces.usecases.IGetCommunityDetailedInfoByIdUseCase
import ru.lim1x.domain.interfaces.usecases.IGetCurrentUserIdUseCase
import ru.lim1x.domain.interfaces.usecases.IGetFinishedMeetingsUseCase
import ru.lim1x.domain.interfaces.usecases.IGetMeetingDetailedInfoByIdUseCase
import ru.lim1x.domain.interfaces.usecases.IGetMeetingsByCommunityIdUseCase
import ru.lim1x.domain.interfaces.usecases.IGetPlannedMeetingsUseCase
import ru.lim1x.domain.interfaces.usecases.IGetUserAvatarByIdUseCase
import ru.lim1x.domain.interfaces.usecases.IGetUserProfileDataUseCase
import ru.lim1x.domain.interfaces.usecases.ISaveNumberUseCase
import ru.lim1x.domain.interfaces.usecases.ISaveUserProfileNameUseCase
import ru.lim1x.domain.interfaces.usecases.ISendCodeToPhoneUseCase
import ru.lim1x.domain.interfaces.usecases.ISetUserPhotoExperimentalUseCase
import ru.lim1x.domain.interfaces.usecases.ISetUserVisitingMeetingValueUseCase
import ru.lim1x.domain.interfaces.usecases.IValidateCodeUseCase
import ru.lim1x.domain.usecase_implementation.authorization.SendCodeToPhoneUseCase
import ru.lim1x.domain.usecase_implementation.authorization.ValidateCodeUseCase
import ru.lim1x.domain.usecase_implementation.communities.GetCommunitiesUseCase
import ru.lim1x.domain.usecase_implementation.communities.GetCommunityDetailedInfoByIdUseCase
import ru.lim1x.domain.usecase_implementation.meetings.GetActiveMeetingsUseCase
import ru.lim1x.domain.usecase_implementation.meetings.GetAllMeetingsUseCase
import ru.lim1x.domain.usecase_implementation.meetings.GetFinishedMeetingsUseCase
import ru.lim1x.domain.usecase_implementation.meetings.GetMeetingDetailedInfoByIdUseCase
import ru.lim1x.domain.usecase_implementation.meetings.GetMeetingsByCommunityIdUseCase
import ru.lim1x.domain.usecase_implementation.meetings.GetPlannedMeetingsUseCase
import ru.lim1x.domain.usecase_implementation.meetings.SetUserVisitingMeetingValueUseCase
import ru.lim1x.domain.usecase_implementation.profile.GetCurrentUserIdIdUseCase
import ru.lim1x.domain.usecase_implementation.profile.GetUserAvatarByIdUseCase
import ru.lim1x.domain.usecase_implementation.profile.GetUserProfileDataUseCase
import ru.lim1x.domain.usecase_implementation.profile.SaveNumberUseCase
import ru.lim1x.domain.usecase_implementation.profile.SaveUserProfileNameUseCase
import ru.lim1x.domain.usecase_implementation.profile.SetUserPhotoExperimentalUseCase

val domainModule = module{
    single<IGetActiveMeetingsUseCase>{GetActiveMeetingsUseCase(get())}
    single<IGetAllMeetingsUseCase>{ GetAllMeetingsUseCase(get()) }
    single<IGetCommunityDetailedInfoByIdUseCase>{ GetCommunityDetailedInfoByIdUseCase(get()) }
    single<IGetCurrentUserIdUseCase>{GetCurrentUserIdIdUseCase(get())}
    single<IGetFinishedMeetingsUseCase>{GetFinishedMeetingsUseCase(get())}
    single<IGetMeetingDetailedInfoByIdUseCase>{GetMeetingDetailedInfoByIdUseCase(get())}
    single<IGetMeetingsByCommunityIdUseCase>{GetMeetingsByCommunityIdUseCase(get())}
    single<IGetPlannedMeetingsUseCase>{GetPlannedMeetingsUseCase(get())}
    single<IGetUserProfileDataUseCase>{GetUserProfileDataUseCase(get())}
    single<ISaveNumberUseCase>{SaveNumberUseCase(get())}
    single<ISaveUserProfileNameUseCase>{SaveUserProfileNameUseCase(get())}
    single<ISendCodeToPhoneUseCase>{SendCodeToPhoneUseCase(get())}
    single<ISetUserVisitingMeetingValueUseCase>{SetUserVisitingMeetingValueUseCase(get())}
    single<IValidateCodeUseCase>{ValidateCodeUseCase(get())}
    single<IGetCommunitiesUseCase>{GetCommunitiesUseCase(get())}
    single<IGetUserAvatarByIdUseCase>{GetUserAvatarByIdUseCase(get())}
    single<ISetUserPhotoExperimentalUseCase>{ SetUserPhotoExperimentalUseCase(get()) }

}
val newDomainModule = module {
    single<ITagsOnboardingGetInteractor> { TagsOnboardingGetInteractor() }
    single<ITagsOnboardingUpdateInteractor> { TagsOnboardingUpdateInteractor() }
    single<InnerTagsInteractor> { InnerTagsInteractor() }
    single<GetTagsUseCase> { GetTagsUseCase() }

    single<ILoadSoonEventsInteractor> { LoadSoonEventsInteractor() }

    single<ILoadRailInteractor> { LoadRailInteractor() }

    single<ILoadMainEventsInteractor> { LoadMainEventsInteractor(get()) }

    single<IGetMoreEventsInteractor> { GetMoreEventsInteractor() }
    single<ILoadMoreEventsInteractor> { LoadMoreEventsInteractor() }
    single<InnerMoreEvents> { InnerMoreEvents() }
    single<GetMoreEventsUseCase> { GetMoreEventsUseCase() }
    single<GetMainEventsUseCase> { GetMainEventsUseCase(get()) }
    single<GetSoonEventsUseCase> { GetSoonEventsUseCase(get()) }
    single<GetRailUseCase> { GetRailUseCase(get()) }
    single<InnerLoadRailInteractor> { InnerLoadRailInteractor() }
}