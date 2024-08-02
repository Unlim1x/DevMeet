package ru.lim1x.domain.interfaces.usecases

interface ISendCodeToPhoneUseCase {
    fun execute(phoneNumber:String)
}