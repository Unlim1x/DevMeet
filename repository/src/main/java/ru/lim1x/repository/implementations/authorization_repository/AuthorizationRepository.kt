package ru.lim1x.repository.implementations.authorization_repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ru.lim1x.domain.interfaces.repositories.IAuthorizationRepository
import ru.lim1x.domain.models.AuthorizationResult
import ru.lim1x.repository.mock_source.MockDataSource

internal class AuthorizationRepository(private val dataSource: MockDataSource) :IAuthorizationRepository{

    private val codeValidationStateFlow:MutableStateFlow<AuthorizationResult?> = MutableStateFlow(null)
    override fun sendCode(phoneNumber: String) {

    }

    override fun validateCode(code: String): StateFlow<AuthorizationResult?> {
        codeValidationStateFlow.update { dataSource.validateCode(code) }
        return codeValidationStateFlow
    }
}