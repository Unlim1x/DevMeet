package ru.lim1x.domain.interfaces.usecases

interface ISaveNumberUseCase {
    fun execute(phone:String):Boolean
}