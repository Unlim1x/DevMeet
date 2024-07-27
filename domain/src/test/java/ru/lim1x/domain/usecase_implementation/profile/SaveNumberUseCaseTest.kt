package ru.lim1x.domain.usecase_implementation.profile

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ru.lim1x.domain.repository.DataSourceTest
import ru.lim1x.domain.repository.ProfileRepositoryTest

class SaveNumberUseCaseTest{
    private val profileRepository = ProfileRepositoryTest(dataSource = DataSourceTest())

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Should return user with saved number`() = runTest {
        val expectingNumber = "5555555555"
        SaveNumberUseCase(profileRepository = profileRepository).execute(expectingNumber)

        val user = GetUserProfileDataUseCase(profileRepository = profileRepository).execute(0).last()
        val actualNumber = user.phone

        advanceUntilIdle()
        assertEquals(expectingNumber, actualNumber)
    }
}