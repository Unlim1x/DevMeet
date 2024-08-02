package ru.lim1x.domain.interfaces.usecases

import kotlinx.coroutines.flow.StateFlow
import ru.lim1x.domain.models.AuthorizationResult


interface IValidateCodeUseCase {
    fun execute(code:String): StateFlow<AuthorizationResult?>
}