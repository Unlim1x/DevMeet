package ru.lim1x.domain.usecase_implementation.authorization

import ru.lim1x.domain.interfaces.repositories.IAuthorizationRepository
import ru.lim1x.domain.interfaces.usecases.IValidateCodeUseCase
import ru.lim1x.domain.models.AuthorizationResult


internal class ValidateCodeUseCase(private val authRepository:IAuthorizationRepository): IValidateCodeUseCase {
    override suspend fun execute(code: String): AuthorizationResult {
        return authRepository.validateCode(code)
    }
}