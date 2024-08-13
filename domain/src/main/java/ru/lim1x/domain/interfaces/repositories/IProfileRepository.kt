package ru.lim1x.domain.interfaces.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.annotations.ApiStatus.Experimental
import ru.lim1x.domain.models.User

interface IProfileRepository {
    fun loadProfile(userId: Int): StateFlow<User>
    fun saveUserName(userId:Int,userName:String, userSurname:String = ""):Boolean


    @Experimental
    /**
     * Вероятно будет убрано или заменено, когда будет понятно, как сохраняется номер на сервере
     */
    fun saveNumber(phoneNumber:String):Boolean

    fun userId():Int

    fun saveUserPhoto(stirngUri:String)
}