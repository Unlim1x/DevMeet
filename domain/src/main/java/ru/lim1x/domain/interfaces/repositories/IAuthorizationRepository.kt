package ru.lim1x.domain.interfaces.repositories

import kotlinx.coroutines.flow.StateFlow
import ru.lim1x.domain.models.AuthorizationResult


interface IAuthorizationRepository {
    fun sendCode(phoneNumber:String)

    fun validateCode(code:String): StateFlow<AuthorizationResult?>
}