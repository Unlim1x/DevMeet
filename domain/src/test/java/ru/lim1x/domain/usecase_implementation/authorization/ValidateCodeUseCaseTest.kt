package ru.lim1x.domain.usecase_implementation.authorization

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.lim1x.domain.repository.AuthorizationRepositoryStub
import ru.lim1x.domain.repository.DataSourceTest


class ValidateCodeUseCaseTest {

    private val authRepository = AuthorizationRepositoryStub(DataSourceTest())

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Should return AuthorizationResult and true in its isSuccessful field if code is 4568`() = runTest {
        val useCase = ValidateCodeUseCase(authRepository = authRepository)
        val authResult = useCase.execute("4568")
        advanceUntilIdle()
        Assertions.assertEquals(authResult.isSuccessful, true)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Should return AuthorizationResult false in isSuccessful field if code is 1111`() = runTest {
        val useCase = ValidateCodeUseCase(authRepository = authRepository)
        val authResult = useCase.execute("1111")
        advanceUntilIdle()
        Assertions.assertEquals(authResult.isSuccessful, false)
    }
}
