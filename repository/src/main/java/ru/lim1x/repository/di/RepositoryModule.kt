package ru.lim1x.repository.di

import org.koin.dsl.module
import ru.lim1x.domain.interfaces.repositories.IAuthorizationRepository
import ru.lim1x.domain.interfaces.repositories.ICommunityRepository
import ru.lim1x.domain.interfaces.repositories.IEventRepository
import ru.lim1x.domain.interfaces.repositories.IMapRepository
import ru.lim1x.domain.interfaces.repositories.IMeetingsRepository
import ru.lim1x.domain.interfaces.repositories.IProfileRepository
import ru.lim1x.domain.interfaces.repositories.ISearchRepository
import ru.lim1x.repository.implementations.authorization_repository.AuthorizationRepository
import ru.lim1x.repository.implementations.community_repository.CommunityRepository
import ru.lim1x.repository.implementations.event_repository.EventRepository
import ru.lim1x.repository.implementations.map_repository.MapRepository
import ru.lim1x.repository.implementations.meeting_repository.MeetingRepository
import ru.lim1x.repository.implementations.profile_repository.ProfileRepository
import ru.lim1x.repository.implementations.search_repository.SearchRepository
import ru.lim1x.repository.ktor.Ktor
import ru.lim1x.repository.mock_source.FakeDataSource
import ru.lim1x.repository.mock_source.MockDataSource

val repositoryModule = module{
    single<MockDataSource>{ MockDataSource() }
    single<FakeDataSource> { FakeDataSource() }
    single<IAuthorizationRepository> { AuthorizationRepository(get()) }
    single<ICommunityRepository> { CommunityRepository(get()) }
    single<IMeetingsRepository> { MeetingRepository(get()) }
    single<IProfileRepository> { ProfileRepository(get(), get()) }
    single<IEventRepository> { EventRepository(get()) }
    single<ISearchRepository> { SearchRepository(get()) }
    single<IMapRepository> { MapRepository() }
    factory<Ktor> { Ktor }
}