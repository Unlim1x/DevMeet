package ru.lim1x.domain.interfaces.usecases

interface IGetUserAvatarByIdUseCase {
    fun execute(userId:Int):String
}