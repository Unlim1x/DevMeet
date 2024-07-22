package ru.lim1x.domain.usecase_implementation.profile

import ru.lim1x.domain.interfaces.repositories.IProfileRepository
import ru.lim1x.domain.interfaces.usecases.ISaveNumberUseCase

class SaveNumberUseCase(private val profileRepository: IProfileRepository):ISaveNumberUseCase {
    override suspend fun execute(phone: String): Boolean {
        return profileRepository.saveNumber(phoneNumber = phone)
    }
}