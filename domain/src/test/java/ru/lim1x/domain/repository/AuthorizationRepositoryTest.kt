package ru.lim1x.domain.repository

import ru.lim1x.domain.interfaces.repositories.IAuthorizationRepository
import ru.lim1x.domain.models.AuthorizationResult

class AuthorizationRepositoryTest(private val dataSource: DataSourceTest) :
    IAuthorizationRepository {
    override suspend fun sendCode(phoneNumber: String) {

    }

    override suspend fun validateCode(code: String): AuthorizationResult {
        return dataSource.validateCode(code)
    }
}