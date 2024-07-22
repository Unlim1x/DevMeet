package ru.lim1x.domain.interfaces.repositories

import kotlinx.coroutines.flow.Flow
import org.jetbrains.annotations.ApiStatus.Experimental
import ru.lim1x.domain.models.User

interface IProfileRepository {
    suspend fun loadProfile(userId: Int): Flow<User>
    suspend fun saveUserName(userId:Int,userName:String, userSurname:String = ""):Boolean


    @Experimental
    /**
     * Вероятно будет убрано или заменено, когда будет понятно, как сохраняется номер на сервере
     */
    suspend fun saveNumber(phoneNumber:String):Boolean

    suspend fun userId():Int
}