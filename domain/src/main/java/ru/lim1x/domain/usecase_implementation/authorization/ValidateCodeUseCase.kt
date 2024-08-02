package ru.lim1x.domain.usecase_implementation.authorization

import kotlinx.coroutines.flow.StateFlow
import ru.lim1x.domain.interfaces.repositories.IAuthorizationRepository
import ru.lim1x.domain.interfaces.usecases.IValidateCodeUseCase
import ru.lim1x.domain.models.AuthorizationResult


internal class ValidateCodeUseCase(private val authRepository:IAuthorizationRepository): IValidateCodeUseCase {
    override fun execute(code: String): StateFlow<AuthorizationResult?> {
        return authRepository.validateCode(code)
    }
}