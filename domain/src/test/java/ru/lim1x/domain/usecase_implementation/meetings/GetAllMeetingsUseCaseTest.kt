package ru.lim1x.domain.usecase_implementation.meetings

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ru.lim1x.domain.repository.DataSourceTest
import ru.lim1x.domain.repository.MeetingRepositoryStub

class GetAllMeetingsUseCaseTest{
    private val meetingsRepository = MeetingRepositoryStub(DataSourceTest())

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Should return MeetingList with different ids`() = runTest {


        val useCase = GetAllMeetingsUseCase(meetingRepository = meetingsRepository)
        val result= useCase.execute().single()

        val actual = result.groupingBy { it.id }.eachCount().filter { it.value > 1 }

        advanceUntilIdle()
        assertTrue(actual.isEmpty())
    }
}
