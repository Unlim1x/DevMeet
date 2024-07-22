package ru.lim1x.domain.interfaces.usecases

interface ISendCodeToPhoneUseCase {
    suspend fun execute(phoneNumber:String)
}