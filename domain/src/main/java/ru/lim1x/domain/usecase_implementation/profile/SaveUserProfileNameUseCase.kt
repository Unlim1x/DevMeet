package ru.lim1x.domain.usecase_implementation.profile

import ru.lim1x.domain.interfaces.repositories.IProfileRepository
import ru.lim1x.domain.interfaces.usecases.ISaveUserProfileNameUseCase

internal class SaveUserProfileNameUseCase(private val profileRepository: IProfileRepository): ISaveUserProfileNameUseCase {
    override suspend fun execute(userId:Int, name: String, surname: String): Boolean {
        return profileRepository.saveUserName(userId=userId,userName = name, userSurname = surname)
    }
}