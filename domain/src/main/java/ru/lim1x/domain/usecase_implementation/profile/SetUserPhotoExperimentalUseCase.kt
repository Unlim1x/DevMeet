package ru.lim1x.domain.usecase_implementation.profile

import ru.lim1x.domain.interfaces.repositories.IProfileRepository
import ru.lim1x.domain.interfaces.usecases.ISetUserPhotoExperimentalUseCase

internal class SetUserPhotoExperimentalUseCase(private val profileRepository: IProfileRepository) :
    ISetUserPhotoExperimentalUseCase {
    override fun execute(uriString: String) {
        profileRepository.saveUserPhoto(stirngUri = uriString)
    }
}