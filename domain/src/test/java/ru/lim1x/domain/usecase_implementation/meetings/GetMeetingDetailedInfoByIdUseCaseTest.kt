package ru.lim1x.domain.usecase_implementation.meetings

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ru.lim1x.domain.repository.DataSourceTest
import ru.lim1x.domain.repository.MeetingRepositoryStub

//Этот тест не проходит, пока я не разобрался как изменить состояние и пихнуть его во flow
class GetMeetingDetailedInfoByIdUseCaseTest{
    private val meetingsRepository = MeetingRepositoryStub(DataSourceTest())


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Should return MeetingDetailedExt with id = 4`() = runTest {
        val expectingId = 4

        val useCase = GetMeetingDetailedInfoByIdUseCase(meetingRepository = meetingsRepository)
        val actual = useCase.execute(expectingId).last()

        advanceUntilIdle()
        assertEquals(expectingId, actual.id)
    }
}