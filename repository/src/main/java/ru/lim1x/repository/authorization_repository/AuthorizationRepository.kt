package ru.lim1x.repository.authorization_repository

import ru.lim1x.domain.interfaces.repositories.IAuthorizationRepository
import ru.lim1x.domain.models.AuthorizationResult
import ru.lim1x.repository.mock_source.MockDataSource

internal class AuthorizationRepository(private val dataSource: MockDataSource) :IAuthorizationRepository{
    override suspend fun sendCode(phoneNumber: String) {

    }

    override suspend fun validateCode(code: String): AuthorizationResult {
        return dataSource.validateCode(code)
    }
}