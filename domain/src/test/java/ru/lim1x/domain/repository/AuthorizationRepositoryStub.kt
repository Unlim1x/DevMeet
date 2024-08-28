package ru.lim1x.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ru.lim1x.domain.interfaces.repositories.IAuthorizationRepository
import ru.lim1x.domain.models.AuthorizationResult

class AuthorizationRepositoryStub(private val dataSource: DataSourceTest) :
    IAuthorizationRepository {
    private val codeValidationStateFlow: MutableStateFlow<AuthorizationResult?> =
        MutableStateFlow(null)

    override fun sendCode(phoneNumber: String) {

    }

    override fun validateCode(code: String): StateFlow<AuthorizationResult?> {
        codeValidationStateFlow.update { dataSource.validateCode(code) }
        return codeValidationStateFlow
    }
}