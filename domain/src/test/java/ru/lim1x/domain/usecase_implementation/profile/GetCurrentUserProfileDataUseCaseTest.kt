package ru.lim1x.domain.usecase_implementation.profile

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ru.lim1x.domain.repository.DataSourceTest
import ru.lim1x.domain.repository.ProfileRepositoryTest


class GetCurrentUserProfileDataUseCaseTest{
    private val profileRepository = ProfileRepositoryTest(dataSource = DataSourceTest())

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Should return current User profile`() = runTest {
        val expectingId = 1

        val useCase = GetUserProfileDataUseCase(profileRepository = profileRepository)
        val actual = useCase.execute(expectingId).last()

        advanceUntilIdle()
        assertEquals(expectingId, actual.id)
    }
}