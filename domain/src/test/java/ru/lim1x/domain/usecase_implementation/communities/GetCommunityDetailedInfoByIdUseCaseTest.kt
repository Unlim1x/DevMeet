package ru.lim1x.domain.usecase_implementation.communities

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ru.lim1x.domain.repository.CommunityRepositoryTest
import ru.lim1x.domain.repository.DataSourceTest

class GetCommunityDetailedInfoByIdUseCaseTest{
    private val communityRepository = CommunityRepositoryTest(dataSource = DataSourceTest())

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Should return community with passed id`() = runTest {
        val expectingId = 3

        val useCase = GetCommunityDetailedInfoByIdUseCase(commRepository = communityRepository)

        val actualId = useCase.execute(expectingId).last().id

        advanceUntilIdle()
        assertEquals(expectingId, actualId)
    }
}