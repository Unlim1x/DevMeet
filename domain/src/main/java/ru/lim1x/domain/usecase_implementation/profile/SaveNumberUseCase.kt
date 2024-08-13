package ru.lim1x.domain.usecase_implementation.profile

import ru.lim1x.domain.interfaces.repositories.IProfileRepository
import ru.lim1x.domain.interfaces.usecases.ISaveNumberUseCase

internal class SaveNumberUseCase(private val profileRepository: IProfileRepository) :
    ISaveNumberUseCase {
    override fun execute(phone: String): Boolean {
        return profileRepository.saveNumber(phoneNumber = phone)
    }
}