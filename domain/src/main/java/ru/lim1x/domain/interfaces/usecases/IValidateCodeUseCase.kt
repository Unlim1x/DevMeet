package ru.lim1x.domain.interfaces.usecases

import ru.lim1x.domain.models.AuthorizationResult


interface IValidateCodeUseCase {
    suspend fun execute(code:String):AuthorizationResult
}