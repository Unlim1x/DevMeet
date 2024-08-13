package ru.lim1x.domain.usecase_implementation.authorization

import ru.lim1x.domain.interfaces.repositories.IAuthorizationRepository
import ru.lim1x.domain.interfaces.usecases.ISendCodeToPhoneUseCase

internal class SendCodeToPhoneUseCase(private val authRepository:IAuthorizationRepository): ISendCodeToPhoneUseCase {
    override fun execute(phoneNumber: String) {
        return authRepository.sendCode(phoneNumber = phoneNumber)
    }
}