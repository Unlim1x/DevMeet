package ru.lim1x.domain.interfaces.repositories

import ru.lim1x.domain.models.AuthorizationResult


interface IAuthorizationRepository {
    suspend fun sendCode(phoneNumber:String)

    suspend fun validateCode(code:String):AuthorizationResult
}