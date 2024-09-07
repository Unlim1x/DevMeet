package ru.lim1x.repository.di

import org.koin.dsl.module
import ru.lim1x.domain.interfaces.repositories.IAuthorizationRepository
import ru.lim1x.domain.interfaces.repositories.ICommunityRepository
import ru.lim1x.domain.interfaces.repositories.IMeetingsRepository
import ru.lim1x.domain.interfaces.repositories.IProfileRepository
import ru.lim1x.repository.authorization_repository.AuthorizationRepository
import ru.lim1x.repository.community_repository.CommunityRepository
import ru.lim1x.repository.meeting_repository.MeetingRepository
import ru.lim1x.repository.mock_source.FakeDataSource
import ru.lim1x.repository.mock_source.MockDataSource
import ru.lim1x.repository.profile_repository.ProfileRepository

val repositoryModule = module{
    single<MockDataSource>{ MockDataSource() }
    single<FakeDataSource> { FakeDataSource() }
    single<IAuthorizationRepository>{AuthorizationRepository(get())}
    single<ICommunityRepository>{CommunityRepository(get())}
    single<IMeetingsRepository>{MeetingRepository(get())}
    single<IProfileRepository> { ProfileRepository(get(), get()) }
}